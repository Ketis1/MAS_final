package edu.pja.mas.s28118.mas_final_project.model;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    @NotBlank(message = "Street is mandatory")
    private String street;

    @NotBlank(message = "Building number is mandatory")
    private String number;

    @NotBlank(message = "City is mandatory")
    private String city;

    @NotBlank(message = "Postal code is mandatory")
    @Pattern(regexp = "^\\d{2}-\\d{3}$", message = "Invalid postal code format. Required format: XX-XXX")
    private String postalCode;
}
