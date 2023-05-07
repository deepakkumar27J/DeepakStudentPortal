package com.leedsbeckett.deepakstudentportal.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.leedsbeckett.deepakstudentportal.Model.Course;
import com.leedsbeckett.deepakstudentportal.Model.Student;
import com.leedsbeckett.deepakstudentportal.Service.CourseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(controllers = CourseController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class CourseControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CourseService courseService;

    @Autowired
    private ObjectMapper objectMapper;

    private Course course = new Course();


    private Student student = new Student();

    @BeforeEach
    public void init(){
        course.setCourseName("Testing");
        course.setId(2);
        student.setId(10);
    }

    @Test
    void getAllCourses() throws Exception {
        given(courseService.saveCourse(ArgumentMatchers.any())).willAnswer(invocation -> invocation.getArgument(0));

        ResultActions response = mockMvc.perform(get("/course/getAll")
                .contentType(MediaType.APPLICATION_JSON));
        response.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }

    @Test
    void getAllCourseByStudentId() throws Exception {
        given(courseService.saveCourse(ArgumentMatchers.any())).willAnswer(invocation -> invocation.getArgument(0));

        ResultActions response = mockMvc.perform(get("/course/enrolledCourses/10")
                .contentType(MediaType.APPLICATION_JSON));
        response.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }
}