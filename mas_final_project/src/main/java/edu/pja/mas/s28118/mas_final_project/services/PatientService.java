package edu.pja.mas.s28118.mas_final_project.services;

import edu.pja.mas.s28118.mas_final_project.model.Appointment;
import edu.pja.mas.s28118.mas_final_project.model.Doctor;
import edu.pja.mas.s28118.mas_final_project.model.Patient;
import edu.pja.mas.s28118.mas_final_project.repository.AppointmentRepository;
import edu.pja.mas.s28118.mas_final_project.repository.DoctorRepository;
import edu.pja.mas.s28118.mas_final_project.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PatientService {

    private final PatientRepository patientRepository;
    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;

    @Transactional
    public Appointment bookAppointment(Long patientId, Long doctorId, LocalDate date) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new IllegalArgumentException("Patient not found with id: " + patientId));

        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new IllegalArgumentException("Doctor not found with id: " + doctorId));

        boolean doctorBusy = appointmentRepository.existsByDateAndDoctorId(date, doctorId);
        boolean patientBusy = appointmentRepository.existsByDateAndPatientId(date, patientId);

        if (doctorBusy) {
            throw new IllegalStateException("Doctor already has an appointment on that date");
        }

        if (patientBusy) {
            throw new IllegalStateException("Patient already has an appointment on that date");
        }
        Appointment appointment = Appointment.builder()
                .date(date)
                .patient(patient)
                .doctor(doctor)
                .appointmentStatus(Appointment.AppointmentStatus.SCHEDULED)
                .build();

        appointmentRepository.save(appointment);

        return appointment;

    }


    public List<Doctor> getDoctorsWithSpecialization(String specializationName) {
        return doctorRepository.findBySpecializationName(specializationName);
    }

    public List<Appointment> getAppointmentsForPatient(Long patientId) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new IllegalArgumentException("Patient not found with ID: " + patientId));

        return patient.showAppointments();
    }


}
