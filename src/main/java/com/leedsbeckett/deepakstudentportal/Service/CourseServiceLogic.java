package com.leedsbeckett.deepakstudentportal.Service;

import com.leedsbeckett.deepakstudentportal.Model.Course;
import com.leedsbeckett.deepakstudentportal.Model.Student;
import com.leedsbeckett.deepakstudentportal.Repository.CourseRepository;
import com.leedsbeckett.deepakstudentportal.Repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;
import java.util.Set;

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
    public Course enrolInCourse(int studentId, int courseId) throws Exception {
        Course course = studentRepository.findById(studentId).map(student->{
            boolean ifCourseExist = courseRepository.existsById((long) courseId);
            if(ifCourseExist==false) {
                try {
                    throw new Exception("No valid course found for id "+courseId);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            Course currentCourse =courseRepository.findById((long) courseId);
            if(courseId!=0L){
                Course _course = courseDetail(Math.toIntExact(courseId));
                student.addCourse(_course);
                studentRepository.save(student);
                return _course;
            }

            student.addCourse(currentCourse);
            return courseRepository.save(currentCourse);
        }).orElseThrow(()-> new Exception("No student with id = "+studentId));

        return course;
        }
}