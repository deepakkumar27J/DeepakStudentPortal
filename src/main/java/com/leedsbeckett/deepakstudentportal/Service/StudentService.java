package com.leedsbeckett.deepakstudentportal.Service;

import com.leedsbeckett.deepakstudentportal.Model.Student;

import java.util.List;
import java.util.Optional;

public interface StudentService {
    public Student saveStudent(Student student) throws Exception;

    public void updateStudent (Student student) throws Exception;
    public List<Student> getAllStudents();

    public Optional<Student> login(String emailId, String password);

    public boolean canGraduate(int id);
}
