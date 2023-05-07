package com.leedsbeckett.deepakstudentportal.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.leedsbeckett.deepakstudentportal.Model.Course;
import com.leedsbeckett.deepakstudentportal.Model.Student;
import com.leedsbeckett.deepakstudentportal.Service.CourseService;
import com.leedsbeckett.deepakstudentportal.Service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatcher;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(controllers = StudentController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private StudentService studentService;

    @Autowired
    private ObjectMapper objectMapper;

    private Course course = new Course();


    private Student student = new Student();

    @BeforeEach
    public void init(){
        course.setCourseName("Testing");
        course.setId(2);
        student.setId(10);
        student.setEmailId("77330542");
        student.setPassword("John");

    }

    @Test
    void create() throws Exception {
        given(studentService.saveStudent(ArgumentMatchers.any())).willAnswer(invocation -> invocation.getArgument(0));

        ResultActions response = mockMvc.perform(post("/student/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(student)));
        response.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }
}