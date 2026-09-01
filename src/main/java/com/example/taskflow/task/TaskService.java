package com.example.taskflow.task;

import com.example.taskflow.auth.User;
import com.example.taskflow.auth.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.example.taskflow.exception.ResourceNotFoundException;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskResponse createTask(TaskRequest request, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Task task = new Task();
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setDueDate(request.getDueDate());
        task.setPriority(request.getPriority());
        task.setUser(user);

        Task savedTask = taskRepository.save(task);

        return mapToResponse(savedTask);
    }

    public Page<TaskResponse> getTasks(String username, TaskStatus status, Pageable pageable) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Page<Task> tasks;
        if (status != null) {
            tasks = taskRepository.findAllByUserAndStatus(user, status, pageable);
        } else {
            tasks = taskRepository.findAllByUser(user, pageable);
        }

        return tasks.map(this::mapToResponse);
    }

    public TaskResponse getTaskById(Long taskId, String username) {
        Task task = getTaskSecurely(taskId, username);
        return mapToResponse(task);
    }

    public TaskResponse updateTask(Long taskId, TaskRequest request, String username) {
        Task task = getTaskSecurely(taskId, username);

        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setDueDate(request.getDueDate());
        task.setPriority(request.getPriority());
        
        if (request.getStatus() != null) {
            task.setStatus(request.getStatus());
        }

        Task updatedTask = taskRepository.save(task);
        return mapToResponse(updatedTask);
    }

    public void deleteTask(Long taskId, String username) {
        Task task = getTaskSecurely(taskId, username);
        taskRepository.delete(task);
    }


    private TaskResponse mapToResponse(Task task) {
        return TaskResponse.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .dueDate(task.getDueDate())
                .priority(task.getPriority())
                .status(task.getStatus())
                .build();
    }
    private Task getTaskSecurely(Long taskId, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return taskRepository.findByIdAndUser(taskId, user)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found or you do not have permission to access it"));
    }
}