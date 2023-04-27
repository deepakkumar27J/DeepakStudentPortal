package com.leedsbeckett.deepakstudentportal.Controller;

import com.leedsbeckett.deepakstudentportal.Model.Course;
import com.leedsbeckett.deepakstudentportal.Service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/course")
@CrossOrigin
public class CourseController {

    @Autowired
    private CourseService courseService;

    private long courseId;

    @PostMapping("/create")
    public String add(@RequestBody Course course) {
        System.out.print(course.getDescription());
        courseService.saveCourse(course);
        return "Course has been created";
    }

    @GetMapping("/getAll")
    public List<Course> getAllCourses(){
        return courseService.getAllCourses();
    }

    @GetMapping("/getCourse")
    public Course getCourseDetail(@RequestParam int id){
        return courseService.courseDetail(id);
    }

    @GetMapping("/enrolledCourses/{studentId}")
    public ResponseEntity<Set<Course>>getAllCourseByStudentId(@PathVariable(value="studentId") int studentId) throws Exception {
        Set<Course> courses = courseService.coursesByStudentId(studentId);
        return new ResponseEntity<>(courses, HttpStatus.OK);
    }

    @PostMapping("/enrolCourse/{studentId}/{courseId}")
    public ResponseEntity<Course> addCourse (@PathVariable(value="studentId") int studentId,@PathVariable(value="courseId") int courseId) throws Exception {
        Course course = courseService.enrolInCourse(studentId, courseId);

        return new ResponseEntity<>(course, HttpStatus.OK);
    }
}
