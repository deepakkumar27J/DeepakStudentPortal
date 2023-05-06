package com.leedsbeckett.deepakstudentportal.Service;

import com.leedsbeckett.deepakstudentportal.Model.Course;
import com.leedsbeckett.deepakstudentportal.Model.Student;
import com.leedsbeckett.deepakstudentportal.Repository.CourseRepository;
import com.leedsbeckett.deepakstudentportal.Repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

import java.util.*;


@Service
public class CourseServiceLogic implements CourseService {
    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private StudentRepository studentRepository;
    @Override
    public Course saveCourse(Course course) {
        return courseRepository.save(course);
    }

    @Override
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    @Override
    public Course courseDetail(int id) {
        Course courseResponse= courseRepository.findById(id);
        return courseResponse;
    }

    @Override
    public Set<Course> coursesByStudentId(long studentId) throws Exception {
        Student _student = studentRepository.findStudentById((int)studentId);
        if(!studentRepository.existsById((int)studentId)) {
            throw new Exception("No course found for student with Id "+studentId);
        }
//        List<Course> courses = courseRepository.findCoursesByStudentId(studentId);
        Set<Course> courses = _student.getEnrolledCourses();
        return courses;
    }

    @Override
    public String enrolInCourse(int studentId, int courseId) throws Exception {
        String course = studentRepository.findById(studentId).map(student->{
            boolean ifCourseExist = courseRepository.existsById((long) courseId);
            if(ifCourseExist==false) {
                try {
                    throw new Exception("No valid course found for id "+courseId);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            Set<Course> coursesAlready = student.getEnrolledCourses();

            Course currentCourse =courseRepository.findById((long) courseId);
            Course _course = courseDetail(Math.toIntExact(courseId));
            if(coursesAlready.contains(currentCourse)){
                return "{\"enrol\":\"true\"}";
            }

            // Create Invoice
            String uri = "http://localhost:8081/invoice";
            RestTemplate restTemplate = new RestTemplate();
            Invoice _invoice = new Invoice(_course.getCost(), "Course", student.getEmailId());
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity entity = new HttpEntity(_invoice, headers);
            ResponseEntity<String> out = restTemplate.exchange(uri, HttpMethod.POST, entity
                    , String.class);
            System.out.print(out.getBody());
            // Invoice Created
            student.addCourse(_course);
            studentRepository.save(student);
            return out.getBody();
        }).orElseThrow(()-> new Exception("No student with id = "+studentId));

        return course;
        }
}