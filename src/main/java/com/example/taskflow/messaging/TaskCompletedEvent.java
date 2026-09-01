package com.example.taskflow.messaging;

public record TaskCompletedEvent(
        Long taskId,
        String taskTitle,
        String username
) {}