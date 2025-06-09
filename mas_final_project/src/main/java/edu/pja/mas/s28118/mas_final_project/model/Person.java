package edu.pja.mas.s28118.mas_final_project.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.Period;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Inheritance(strategy = InheritanceType.JOINED) //in the design diagram the inheritence was shown as a compositon which was a mistake bc it should be just normal overlapping inheritence
public abstract class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "First name is mandatory")
    @Size(min = 3, max = 50)
    private String firstName;

    @NotBlank(message = "Last name is mandatory")
    @Size(min = 3, max = 50)
    private String lastName;

    @NotBlank(message = "PESEL is mandatory")
    @Pattern(regexp = "^[0-9]{11}$", message = "PESEL should be 11 digits")
    private String pesel;

    @NotNull(message = "Birth date is mandatory")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Past(message = "Birth date must be in the past")
    private LocalDate birthDate;

    @NotBlank(message = "Phone number is mandatory")
    @Pattern(regexp = "^\\+?[0-9]{1,3}?[-.\\s]?[0-9]{1,4}[-.\\s]?[0-9]{1,4}[-.\\s]?[0-9]{1,9}$", message = "Phone number is not valid")
    private String phone;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    private String email;

    @Transient
    public int getAge(){
        if(birthDate == null) return 0;
        return Period.between(birthDate, LocalDate.now()).getYears();
    }





}
