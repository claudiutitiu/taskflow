package com.example.taskflow.notification;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "notifications")
public class Notification {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String message;
    private boolean isRead = false;
    private LocalDateTime createdAt = LocalDateTime.now();
}