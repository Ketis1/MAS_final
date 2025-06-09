package edu.pja.mas.s28118.mas_final_project.services;

import edu.pja.mas.s28118.mas_final_project.model.Appointment;
import edu.pja.mas.s28118.mas_final_project.model.Doctor;
import edu.pja.mas.s28118.mas_final_project.model.Patient;
import edu.pja.mas.s28118.mas_final_project.model.Prescription;
import edu.pja.mas.s28118.mas_final_project.repository.AppointmentRepository;
import edu.pja.mas.s28118.mas_final_project.repository.DoctorRepository;
import edu.pja.mas.s28118.mas_final_project.repository.PrescriptionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DoctorService {

    private final DoctorRepository doctorRepository;
    private final AppointmentRepository appointmentRepository;
    private final PrescriptionRepository prescriptionRepository;

    public List<Appointment> getAppointmentsForDoctor(Long doctorId) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new IllegalArgumentException("Doctor not found with ID: " + doctorId));
        return doctor.showAppointments();
    }

    @Transactional
    public void addDescriptionToAppointment(Long doctorId, Long appointmentId, String description) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new IllegalArgumentException("Doctor not found with ID: " + doctorId));
        doctor.addDescription(appointmentId, description);
    }


    @Transactional
    public Prescription prescribeMedicine(Long doctorId, Patient patient, List<Doctor.MedicinePrescriptionData> medicineDataList) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new IllegalArgumentException("Doctor not found with ID: " + doctorId));

        Prescription prescription = doctor.prescribeMedicine(patient, medicineDataList);


        prescription = prescriptionRepository.save(prescription);

        return prescription;
    }

}
