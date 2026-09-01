package com.example.taskflow.task;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TaskStatusUpdateRequest {
    @NotNull(message = "Status is required")
    private TaskStatus status;
}