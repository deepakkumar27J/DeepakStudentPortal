package com.leedsbeckett.deepakstudentportal.Controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.leedsbeckett.deepakstudentportal.Model.*;
import com.leedsbeckett.deepakstudentportal.Service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.converter.json.Jackson2ObjectMapperBuilder.json;

@RestController
@RequestMapping("/student")
@CrossOrigin
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping("/create")
    public ResponseEntity<String> add(@RequestBody Student student) throws Exception {
        studentService.saveStudent(student);
        return new ResponseEntity<>("Student account has been created", HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public List<Student> getAllStudents(){
        return studentService.getAllStudents();
    }

    @PostMapping("/login")
    public Optional<Student> loginStudent(@RequestBody Student loginDTO) throws Exception {
        System.out.print(loginDTO.getEmailId() + loginDTO.getPassword());
        Optional<Student> student = studentService.login(loginDTO.getEmailId(), loginDTO.getPassword());
        return student;
    }

    @PostMapping("/graduate")
    public Boolean canGraduateStudent(@RequestParam int id) {
        System.out.print(id);
        boolean student = studentService.canGraduate(id);
        return student;
    }

    @PostMapping("/updateProfile")
    public ResponseEntity<String> update(@RequestBody Student student) throws Exception {
        studentService.updateStudent(student);
        return new ResponseEntity<>("Student account has been updated", HttpStatus.OK);
    }
}
