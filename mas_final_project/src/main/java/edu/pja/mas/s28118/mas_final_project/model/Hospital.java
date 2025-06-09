package edu.pja.mas.s28118.mas_final_project.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Hospital {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @NotBlank(message = "Name is mandatory")
    @Size(min = 2, max = 100)
    private String name;

    @ElementCollection
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @CollectionTable(
        name = "hospital_phone_numbers",
        joinColumns = @JoinColumn(name = "hospital_id")
    )

    //TODO dodac wlasna walidacje z regexem dla listy
    @Column(name = "phone_number")
    //@Pattern(regexp = "^\\+?[0-9\\-\\s]{9,}$", message = "Invalid phone number format")
    private List<String> phoneNumbers = new ArrayList<>();

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "hospital", cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OrderBy("floor ASC, roomNumber ASC")
    private List<Room> rooms = new ArrayList<>();





    @OneToOne(cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private PublicHospital publicHospital;

    @OneToOne(cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private PrivateHospital privateHospital;

    //TODO czy too jest poprawne?
//    public void setPublicHospital(PublicHospital publicHospital) {
//        if (publicHospital != null && this.privateHospital != null) {
//            throw new IllegalStateException("Hospital cannot be both public and private");
//        }
//        this.publicHospital = publicHospital;
//    }
//
//    public void setPrivateHospital(PrivateHospital privateHospital) {
//        if (privateHospital != null && this.publicHospital != null) {
//            throw new IllegalStateException("Hospital cannot be both public and private");
//        }
//        this.privateHospital = privateHospital;
//    }

    @AssertTrue(message = "Hospital has to be private or public")
    private boolean isValidXOR() {
        return (publicHospital != null && privateHospital == null) ||
                (publicHospital == null && privateHospital != null);
    }


    @PrePersist
    @PreUpdate
    private void validateXOR() {
        if ((publicHospital == null && privateHospital == null) ||
                (publicHospital != null && privateHospital != null)) {
            throw new IllegalStateException("Szpital musi być albo publiczny, albo prywatny (nie może być obydwoma jednocześnie)");
        }
    }

    public boolean isPublic() {
        return publicHospital != null;
    }

    public boolean isPrivate() {
        return privateHospital != null;
    }



}

