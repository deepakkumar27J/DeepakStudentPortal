package com.leedsbeckett.deepakstudentportal.Service;

import com.leedsbeckett.deepakstudentportal.Model.Student;
import com.leedsbeckett.deepakstudentportal.Repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;


@Service
public class StudentServiceLogic implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public String md5Password(String oldPassword) throws NoSuchAlgorithmException {
        //Create Message Digest Instance of MD5
        MessageDigest md = MessageDigest.getInstance("MD5");

        md.update(oldPassword.getBytes());
        byte[] bytes = md.digest();

        StringBuilder psb = new StringBuilder();
        for (int i= 0; i<bytes.length;i++){
            psb.append(Integer.toString((bytes[i] & 0xff)+0x100,16).substring(1));
        }
        String newPassword = psb.toString();
        return newPassword;
    }
    @Override
    public Student saveStudent(Student student) throws Exception {
        Optional<Student> _student = studentRepository.findByEmailId(student.getEmailId());
        if(!_student.isEmpty()){
            throw new HttpMessageNotReadableException("Student already existing.");
        }
        String newPassword = this.md5Password(student.getPassword());


        // Creating Account on Finance Portal
        String uri="http://localhost:8081/account";
        RestTemplate restTemplate = new RestTemplate();
        Student _financeStudent = new Student(student.getFirstName(), student.getLastName(), student.getEmailId(), student.getPassword());
        restTemplate.postForObject(uri, _financeStudent, Student.class);

        // Creating Account on Library Portal
        String uriLibrary="http://localhost:8082/student/create";
        Student _libraryStudent = new Student(student.getFirstName(), student.getLastName(), student.getEmailId(), student.getPassword());
        restTemplate.postForEntity(uriLibrary, _libraryStudent, String.class);

        student.setPassword(String.valueOf(newPassword));
        return studentRepository.save(student);

    }

    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public Optional<Student> login(String emailId, String password) throws Exception{
        String stringPassword = this.md5Password(password);
        Optional<Student> _student = studentRepository.findByEmailIdAndPassword(emailId,stringPassword);
        if(_student.isEmpty()){
            throw new HttpMessageNotReadableException("Invalid login");
        }
        return _student;
    }

    @Override
    public boolean canGraduate(int id) {
        Student student = studentRepository.findStudentById(id);
        String uri="http://localhost:8081/invoice/dues/"+student.getEmailId();
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> out =restTemplate.getForEntity(uri, String.class);
        String canGraduate = out.getBody();
        if(canGraduate.equals("false")){
            return false;
        }

        return true;

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
