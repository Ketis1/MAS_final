package edu.pja.mas.s28118.mas_final_project.services;

import edu.pja.mas.s28118.mas_final_project.model.Employee;
import edu.pja.mas.s28118.mas_final_project.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;


    @Transactional(readOnly = true)
    public double calculateSalary(Long employeeId, double months) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new IllegalArgumentException("Employee not found with ID: " + employeeId));

        return employee.calculateSalary(months);
    }
}