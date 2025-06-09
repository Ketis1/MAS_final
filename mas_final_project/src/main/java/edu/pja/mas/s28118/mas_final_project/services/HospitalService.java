package edu.pja.mas.s28118.mas_final_project.services;

import edu.pja.mas.s28118.mas_final_project.model.Hospital;
import edu.pja.mas.s28118.mas_final_project.model.PrivateHospital;
import edu.pja.mas.s28118.mas_final_project.model.PublicHospital;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class HospitalService {
    
    @Transactional
    public void convertToPublic(Hospital hospital, String regonNumber) {
        if (hospital.isPublic()) {
            throw new IllegalStateException("Hospital is already public");
        }
        
        // Remove private hospital if exists
        hospital.setPrivateHospital(null);
        
        // Create new public hospital
        PublicHospital publicHospital = PublicHospital.builder()
                .regonNumber(regonNumber)
                .build();
        
        hospital.setPublicHospital(publicHospital);
    }

    @Transactional
    public void convertToPrivate(Hospital hospital, List<String> fundingSources) {
        if (hospital.isPrivate()) {
            throw new IllegalStateException("Hospital is already private");
        }
        
        // Remove public hospital if exists
        hospital.setPublicHospital(null);
        
        // Create new private hospital
        PrivateHospital privateHospital = PrivateHospital.builder()
                .fundingSources(fundingSources)
                .build();
        
        hospital.setPrivateHospital(privateHospital);
    }
}