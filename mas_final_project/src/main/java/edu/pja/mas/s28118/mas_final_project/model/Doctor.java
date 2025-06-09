package edu.pja.mas.s28118.mas_final_project.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.*;


@SuperBuilder
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DiscriminatorValue("DOCTOR")
public class Doctor extends Employee {



    @NotBlank(message = "License number is mandatory")
    @Pattern(regexp = "^\\d{7}[A-Z]?$", message = "Invalid doctor license number (PWZ)")
    @Column(unique = true)
    private String licenseNumber;

    @NotBlank(message = "Title is mandatory")
    @Size(min = 2, max = 50)
    private String title;

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, orphanRemoval = true)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<DoctorSpecialization> doctorSpecializations = new HashSet<>();

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, orphanRemoval = true)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OrderBy("date DESC")
    private List<Appointment> appointments = new ArrayList<>();

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OrderBy("floor ASC, roomNumber ASC")
    private Set<Room> rooms = new HashSet<>();

    @OneToMany(mappedBy = "mentoringDoctor")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Intern> supervisedInterns = new HashSet<>();

    @Override
    public double calculateSalary(double months) {
        return this.getSalary()*months;
    }

    public void addDescription(Long appointmentId, String description) {
        if (description == null || description.isBlank()) {
            System.out.println("Empty description");;
        }

        Appointment appointment = appointments.stream()
                .filter(a -> a.getId().equals(appointmentId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Appointment not found with id: " + appointmentId));

        appointment.setAppointmentDescription(description);
    }

    public List<Appointment> showAppointments() {
        return new ArrayList<>(appointments);
    }



    public Prescription prescribeMedicine(Patient patient, List<MedicinePrescriptionData> medicinesData) {
        if (patient == null) {
            throw new IllegalArgumentException("Patient cannot be null");
        }
        if (medicinesData == null || medicinesData.isEmpty()) {
            throw new IllegalArgumentException("Medicines list cannot be empty");
        }

        Prescription prescription = new Prescription();
        prescription.setPatient(patient);
        prescription.setIssueDate(LocalDate.now());
        prescription.setExpiryDate(LocalDate.now().plusDays(30)); // np. 30 dni ważności

        // Generowanie kodu recepty np. UUID podzielony na 3 części
        String prescriptionCode = generatePrescriptionCode();
        prescription.setPrescriptionCode(prescriptionCode);

        // Ustawienie pustego zestawu itemów
        prescription.setPrescriptionItems(new HashSet<>());

        for (MedicinePrescriptionData data : medicinesData) {
            PrescriptionItem item = new PrescriptionItem();
            item.setMedicine(data.getMedicine());
            item.setDosage(data.getDosage());
            item.setQuantity(data.getQuantity());
            item.setDoctorInstructions(data.getDoctorInstructions());
            item.setPrescription(prescription);
            item.setIsRealized(false);

            prescription.getPrescriptionItems().add(item);
        }

        // TODO: Zapis recepty w bazie - jeśli potrzebujesz, powinno to odbyć się w warstwie serwisu/repozytorium

        return prescription;
    }

    // Pomocnicza metoda do generowania kodu recepty
    private String generatePrescriptionCode() {
        String uuid = UUID.randomUUID().toString().replace("-", "").toUpperCase();
        return uuid.substring(0,4) + "-" + uuid.substring(4,8) + "-" + uuid.substring(8,12);
    }
    @Getter
    @AllArgsConstructor
    public static class MedicinePrescriptionData {
        private Medicine medicine;
        private String dosage;
        private Integer quantity;
        private String doctorInstructions;


    }

}
