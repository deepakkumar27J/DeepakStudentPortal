package com.leedsbeckett.deepakstudentportal;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.leedsbeckett.deepakstudentportal.Controller.StudentController;
import com.leedsbeckett.deepakstudentportal.Model.Student;
import com.leedsbeckett.deepakstudentportal.Service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class StudentBasicControllerTest {

    private MockMvc mockMvc;

    @Mock
    private StudentService studentService;

    @InjectMocks
    private StudentController studentController;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(studentController).build();
    }

    @Test
    public void testAddStudent() throws Exception {
        // Given
        Student student = new Student("John", "Doe", "johndoe@example.com", "password");
        when(studentService.saveStudent(any(Student.class))).thenReturn(student);

        // When
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/student/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(student)))
                .andExpect(status().isOk())
                .andReturn();

        // Then
        String responseContent = result.getResponse().getContentAsString();
        assert responseContent.equals("Student account has been created");
    }
}

