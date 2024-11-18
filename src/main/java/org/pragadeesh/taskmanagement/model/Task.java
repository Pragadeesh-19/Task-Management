package org.pragadeesh.taskmanagement.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tasks")
@Data
public class Task {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false, length = 255)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    private LocalDate dueDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TaskStatus status = TaskStatus.PENDING;

    @CreationTimestamp
    private LocalDateTime createdAt = LocalDateTime.now();

    @UpdateTimestamp
    private LocalDateTime updatedAt;

}
