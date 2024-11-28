package org.pragadeesh.taskmanagement.repository;

import java.util.Optional;
import java.util.UUID;

import org.pragadeesh.taskmanagement.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, UUID>{
    
    Optional<Department> findByName(String name);
}
