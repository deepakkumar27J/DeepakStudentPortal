package com.leedsbeckett.deepakstudentportal.Repository;

import com.leedsbeckett.deepakstudentportal.Model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course,Integer> {
    Course findById(long id);
    boolean existsById(long id);

}
