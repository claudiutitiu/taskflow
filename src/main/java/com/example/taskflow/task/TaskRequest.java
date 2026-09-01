package com.example.taskflow.task;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDate;

@Data
public class TaskRequest {
    @NotBlank(message = "Title is required")
    private String title;
    
    private String description;
    
    @FutureOrPresent(message = "Due date cannot be in the past")
    private LocalDate dueDate;
    
    @NotNull(message = "Priority is required")
    private TaskPriority priority;

    private TaskStatus status;
}