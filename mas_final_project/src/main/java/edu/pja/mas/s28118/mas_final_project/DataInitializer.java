package edu.pja.mas.s28118.mas_final_project;

import edu.pja.mas.s28118.mas_final_project.model.*;
import edu.pja.mas.s28118.mas_final_project.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationListener<ContextRefreshedEvent> {

    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final AppointmentRepository appointmentRepository;
    private final SpecializationRepository specializationRepository;
    private final DoctorSpecializationRepository doctorSpecializationRepository;
    private final InternRepository internRepository;
    private final NurseRepository nurseRepository;
    private final HospitalRepository hospitalRepository;
    private final MedicineRepository medicineRepository;
    private final PrescriptionRepository prescriptionRepository;
    private final PrescriptionItemRepository prescriptionItemRepository;
    private final RoomRepository roomRepository;
    private final PublicHospitalRepository publicHospitalRepository;
    private final PrivateHospitalRepository privateHospitalRepository;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        // Tworzenie szpitali
        Hospital szpitalPubliczny = Hospital.builder()
                .name("Szpital Kliniczny im. Jana Pawła II")
                .phoneNumbers(List.of("+48 22 555 66 77", "+48 22 555 66 78"))
                .address(new Address("ul. Szpitalna", "12", "Warszawa", "00-001"))
                .build();

        PublicHospital publicHospital = PublicHospital.builder()
                .regonNumber("123456789")
                .build();
        szpitalPubliczny.setPublicHospital(publicHospital);

        Hospital szpitalPrywatny = Hospital.builder()
                .name("LuxMed Centrum")
                .phoneNumbers(List.of("+48 22 333 44 55"))
                .address(new Address("ul. Północna", "8", "Warszawa", "00-002"))
                .build();

        PrivateHospital privateHospital = PrivateHospital.builder()
                .fundingSources(List.of("LuxMed Group"))
                .build();
        szpitalPrywatny.setPrivateHospital(privateHospital);

        hospitalRepository.saveAll(List.of(szpitalPubliczny, szpitalPrywatny));

        // Tworzenie specjalizacji
        Specialization kardiologia = Specialization.builder()
                .specializationName("Kardiologia")
                .build();

        Specialization neurologia = Specialization.builder()
                .specializationName("Neurologia")
                .build();

        Specialization ortopedia = Specialization.builder()
                .specializationName("Ortopedia")
                .build();

        specializationRepository.saveAll(List.of(kardiologia, neurologia, ortopedia));

        // Tworzenie doktorów
        Doctor kardiolog = Doctor.builder()
                .firstName("Jan")
                .lastName("Kowalski")
                .birthDate(LocalDate.of(1975, 5, 15))
                .pesel("75051512345")
                .phone("+48 123456789")
                .email("jan.kowalski@szpital.pl")
                .employmentDate(LocalDate.of(2020, 1, 1))
                .salary(15000.0)
                .licenseNumber("1234567A")
                .title("dr n. med.")
                .build();

        Doctor neurolog = Doctor.builder()
                .firstName("Anna")
                .lastName("Nowak")
                .birthDate(LocalDate.of(1980, 3, 20))
                .pesel("80032087654")
                .phone("+48 987654321")
                .email("anna.nowak@szpital.pl")
                .employmentDate(LocalDate.of(2019, 6, 1))
                .salary(16000.0)
                .licenseNumber("7654321B")
                .title("prof. dr hab.")
                .build();

        doctorRepository.saveAll(List.of(kardiolog, neurolog));

        // Przypisanie specjalizacji do doktorów
        DoctorSpecialization ds1 = DoctorSpecialization.builder()
                .doctor(kardiolog)
                .specialization(kardiologia)
                .issueDate(LocalDate.of(2015, 1, 1))
                .issuingAuthority("Centrum Egzaminów Medycznych")
                .build();

        DoctorSpecialization ds2 = DoctorSpecialization.builder()
                .doctor(neurolog)
                .specialization(neurologia)
                .issueDate(LocalDate.of(2014, 1, 1))
                .issuingAuthority("Centrum Egzaminów Medycznych")
                .build();

        doctorSpecializationRepository.saveAll(List.of(ds1, ds2));

        // Tworzenie pielęgniarek
        Nurse pielegniarka1 = Nurse.builder()
                .firstName("Maria")
                .lastName("Pielęgniarska")
                .birthDate(LocalDate.of(1990, 6, 15))
                .pesel("90061543210")
                .phone("+48 111222333")
                .email("maria.p@szpital.pl")
                .employmentDate(LocalDate.of(2018, 3, 1))
                .salary(6000.0)
                .licenseNumber("P-123456")
                .shiftType(Nurse.ShiftType.DAY)
                .build();

        nurseRepository.save(pielegniarka1);

        // Tworzenie stażystów
        Intern stażysta = Intern.builder()
                .firstName("Adam")
                .lastName("Stażysta")
                .birthDate(LocalDate.of(1995, 6, 15))
                .pesel("95061512345")
                .phone("+48 444555666")
                .email("adam.stazysta@szpital.pl")
                .employmentDate(LocalDate.of(2024, 1, 1))
                .salary(4000.0)
                .internshipEndDate(LocalDate.of(2024, 12, 31))
                .isVoluntary(false)
                .mentoringDoctor(kardiolog)
                .build();

        internRepository.save(stażysta);

        // Tworzenie pacjentów
        Patient pacjent1 = Patient.builder()
                .firstName("Marek")
                .lastName("Zawadzki")
                .birthDate(LocalDate.of(1990, 8, 12))
                .pesel("90081234567")
                .phone("+48 555666777")
                .email("marek.zawadzki@email.pl")
                .insuranceType(Patient.InsuranceType.basic)
                .build();

        Patient pacjent2 = Patient.builder()
                .firstName("Zofia")
                .lastName("Wiśniewska")
                .birthDate(LocalDate.of(1985, 4, 25))
                .pesel("85042598765")
                .phone("+48 111222333")
                .email("zofia.wisniewska@email.pl")
                .insuranceType(Patient.InsuranceType.premium)
                .build();

        patientRepository.saveAll(List.of(pacjent1, pacjent2));

        // Tworzenie wizyt
        Appointment wizyta1 = Appointment.builder()
                .doctor(kardiolog)
                .patient(pacjent1)
                .date(LocalDate.now().plusDays(7))
                .appointmentStatus(Appointment.AppointmentStatus.SCHEDULED)
                .appointmentDescription("Kontrolna wizyta kardiologiczna")
                .build();

        Appointment wizyta2 = Appointment.builder()
                .doctor(neurolog)
                .patient(pacjent2)
                .date(LocalDate.now().plusDays(14))
                .appointmentStatus(Appointment.AppointmentStatus.SCHEDULED)
                .appointmentDescription("Konsultacja neurologiczna")
                .build();

        appointmentRepository.saveAll(List.of(wizyta1, wizyta2));

        // Tworzenie leków
        Medicine lek1 = Medicine.builder()
                .name("Cardiolek")
                .medicineCode("CD1234")
                .requiresPrescription(true)
                .build();

        Medicine lek2 = Medicine.builder()
                .name("Neurolek")
                .medicineCode("NE5678")
                .requiresPrescription(true)
                .build();

        medicineRepository.saveAll(List.of(lek1, lek2));

        // Tworzenie recept
        Prescription recepta1 = Prescription.builder()
                .issueDate(LocalDate.now())
                .expiryDate(LocalDate.now().plusMonths(3))
                .prescriptionCode("1234-5678-9012")
                .patient(pacjent1)
                .build();

        prescriptionRepository.save(recepta1);

        // Tworzenie pozycji na receptach
        PrescriptionItem pozycja1 = PrescriptionItem.builder()
                .prescription(recepta1)
                .medicine(lek1)
                .dosage("1 tabletka 2 razy dziennie")
                .quantity(60)
                .doctorInstructions("Przyjmować rano i wieczorem")
                .isRealized(false)
                .build();

        prescriptionItemRepository.save(pozycja1);

        // Tworzenie sal
        Room sala1 = Room.builder()
                .roomNumber(101)
                .floor(1)
                .hospital(szpitalPubliczny)
                .doctor(kardiolog)
                .build();

        Room sala2 = Room.builder()
                .roomNumber(201)
                .floor(2)
                .hospital(szpitalPubliczny)
                .doctor(neurolog)
                .build();

        roomRepository.saveAll(List.of(sala1, sala2));
    }
}