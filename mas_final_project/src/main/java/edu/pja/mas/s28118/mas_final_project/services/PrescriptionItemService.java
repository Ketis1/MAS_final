package edu.pja.mas.s28118.mas_final_project.services;

import edu.pja.mas.s28118.mas_final_project.model.PrescriptionItem;
import edu.pja.mas.s28118.mas_final_project.repository.PrescriptionItemRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PrescriptionItemService {
    private final PrescriptionItemRepository prescriptionItemRepository;


    @Transactional
    public void realizeItem(Long prescriptionItemId) {
        PrescriptionItem item = prescriptionItemRepository.findById(prescriptionItemId)
                .orElseThrow(() -> new IllegalArgumentException("Prescription item not found with id: " + prescriptionItemId));

        item.realizeItem();  // wywołujemy metodę z modelu, która oznacza jako zrealizowany lub rzuca wyjątkiem

        prescriptionItemRepository.save(item);  // zapisujemy zmiany
    }
}
