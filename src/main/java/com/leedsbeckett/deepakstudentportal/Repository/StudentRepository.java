package com.leedsbeckett.deepakstudentportal.Repository;

import com.leedsbeckett.deepakstudentportal.Model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student,Integer> {
//    List<Student> findStudentsByCourseId(int courseId);
    Optional<Student> findByEmailIdAndPassword(String emailId, String password);
//    boolean findByDuesClear(int id);
    Optional<Student> findByEmailId(String emailId);
    Student findStudentById(int id);
}
