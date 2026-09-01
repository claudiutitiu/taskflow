package com.example.taskflow.task;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.boot.security.autoconfigure.SecurityAutoConfiguration;
import org.springframework.boot.security.autoconfigure.UserDetailsServiceAutoConfiguration;
import org.springframework.boot.security.autoconfigure.web.servlet.ServletWebSecurityAutoConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import com.example.taskflow.task.TaskService;
import com.example.taskflow.auth.JwtService;
import com.example.taskflow.auth.CustomUserDetailsService;
import com.example.taskflow.task.TaskController;
import com.example.taskflow.task.TaskResponse;
import com.example.taskflow.task.TaskStatus;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(
    controllers = TaskController.class,
    excludeAutoConfiguration = {
        SecurityAutoConfiguration.class,
        ServletWebSecurityAutoConfiguration.class,
        UserDetailsServiceAutoConfiguration.class
    }
)
@AutoConfigureMockMvc(addFilters = false)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TaskService taskService;

    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private CustomUserDetailsService customUserDetailsService;

    @Test
    void getTaskById_ShouldReturn200AndTaskData() throws Exception {
        TaskResponse mockResponse = TaskResponse.builder()
                .id(1L)
                .title("Learn MockMvc")
                .status(TaskStatus.TODO)
                .build();

        when(taskService.getTaskById(1L, "testuser")).thenReturn(mockResponse);

        mockMvc.perform(get("/api/tasks/1")
            .principal(new UsernamePasswordAuthenticationToken("testuser", null)))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.title").value("Learn MockMvc"));
    }
}