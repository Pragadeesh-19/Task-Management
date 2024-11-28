package org.pragadeesh.taskmanagement.service;

import java.util.List;
import java.util.UUID;

import org.pragadeesh.taskmanagement.model.Department;
import org.pragadeesh.taskmanagement.repository.DepartmentRepository;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DepartmentService {
    
    private final DepartmentRepository departmentRepository;

    public Department createDepartment(Department department) {
        return departmentRepository.save(department);
    }

    public List<Department> getAllDepartment() {
        return departmentRepository.findAll();
    }

    public Department getDepartmentById(UUID id) {
        return departmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Department not found with id: " + id));
    }

    public Department updateDepartment(UUID id, Department department) {
        Department existingDepartment = getDepartmentById(id);
        existingDepartment.setName(department.getName());
        existingDepartment.setDescription(department.getDescription());
        return departmentRepository.save(existingDepartment);
    }

    public void deleteDepartment(UUID id) {
        departmentRepository.deleteById(id);
    }
}
