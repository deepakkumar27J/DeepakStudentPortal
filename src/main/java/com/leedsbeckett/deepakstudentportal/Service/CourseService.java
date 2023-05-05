package com.leedsbeckett.deepakstudentportal.Service;

import com.leedsbeckett.deepakstudentportal.Model.Course;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Set;

public interface CourseService {
    public Course saveCourse(Course course);
    public List<Course> getAllCourses();

    public Course courseDetail(int id);
    
    public Set<Course> coursesByStudentId(long studentId) throws Exception;

    public String enrolInCourse(int studentId, int courseId) throws Exception;
}
