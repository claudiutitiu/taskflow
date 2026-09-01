package com.example.taskflow.task;

import com.example.taskflow.auth.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {

    Page<Task> findAllByUser(User user, Pageable pageable);
    
    Page<Task> findAllByUserAndStatus(User user, TaskStatus status, Pageable pageable);

    Optional<Task> findByIdAndUser(Long id, User user);
}