package com.leedsbeckett.deepakstudentportal.Service;

import com.leedsbeckett.deepakstudentportal.Model.Student;
import com.leedsbeckett.deepakstudentportal.Repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class StudentServiceLogic implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public Student saveStudent(Student student) throws Exception {
        Optional<Student> _student = studentRepository.findByEmailId(student.getEmailId());
        if(!_student.isEmpty()){
            throw new HttpMessageNotReadableException("Student already existing.");
        }
        return studentRepository.save(student);
    }

    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public Optional<Student> login(String emailId, String password) {
        Optional<Student> _student = studentRepository.findByEmailIdAndPassword(emailId,password);
        if(_student.isEmpty()){
            throw new HttpMessageNotReadableException("Invalid login");
        }
        return _student;
    }

    @Override
    public boolean canGraduate(int id) {
        Student student = studentRepository.findStudentById(id);
        return student.isDuesClear();

    }

    @Override
    public void updateStudent(Student student) throws Exception {
        Optional<Student> _student = studentRepository.findById((int)student.getId());
        if(_student.isEmpty()){
            throw new HttpMessageNotReadableException("Student does not exist");
        }
        Student updatedStudent = studentRepository.findStudentById((int)student.getId());
        updatedStudent.setPassword(student.getPassword());
        updatedStudent.setFirstName(student.getFirstName());
        updatedStudent.setLastName(student.getLastName());
        updatedStudent.setPhoneNumber(student.getPhoneNumber());
        updatedStudent.setDob(student.getDob());
        studentRepository.save(updatedStudent);
    }
}
