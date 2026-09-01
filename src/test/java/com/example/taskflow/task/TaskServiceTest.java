package com.example.taskflow.task;

import com.example.taskflow.auth.User;
import com.example.taskflow.auth.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private TaskService taskService;

    @Test
    void createTask_ShouldReturnTaskResponse() {
        User user = new User();
        user.setUsername("testuser");

        TaskRequest request = new TaskRequest();
        request.setTitle("Write tests");
        request.setPriority(TaskPriority.HIGH);

        Task savedTask = new Task();
        savedTask.setId(1L);
        savedTask.setTitle("Write tests");
        savedTask.setStatus(TaskStatus.TODO);
        savedTask.setPriority(TaskPriority.HIGH);

        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));
        when(taskRepository.save(any(Task.class))).thenReturn(savedTask);

        TaskResponse response = taskService.createTask(request, "testuser");

        assertNotNull(response);
        assertEquals("Write tests", response.getTitle());
        assertEquals(TaskStatus.TODO, response.getStatus());
        
        verify(taskRepository, times(1)).save(any(Task.class));
    }
}