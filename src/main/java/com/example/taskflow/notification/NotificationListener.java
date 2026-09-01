package com.example.taskflow.notification;

import com.example.taskflow.messaging.RabbitMQConfig;
import com.example.taskflow.messaging.TaskCompletedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationListener {

    private final NotificationRepository notificationRepository;

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    public void handleTaskCompletedEvent(TaskCompletedEvent event) {
        Notification notification = new Notification();
        notification.setUsername(event.username());
        notification.setMessage("Great job! You completed the task: " + event.taskTitle());
        
        notificationRepository.save(notification);
        
        System.out.println("Notification saved for user: " + event.username());
    }
}