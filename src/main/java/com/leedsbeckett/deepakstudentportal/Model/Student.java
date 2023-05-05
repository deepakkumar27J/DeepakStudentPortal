package com.leedsbeckett.deepakstudentportal.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Student {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private long id;

    @ManyToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.PERSIST
    )
    @JoinTable(
            name = "enrolled_courses",
            joinColumns = @JoinColumn(name = "student_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "course_id", referencedColumnName = "id"))
    Set<Course> enrolledCourses = new HashSet<Course>();
    @Column(
            name = "year_intake"
    )
    private Integer yearIntake;
    @Column(
            name = "cgpa"
    )
    private float cgpa;
    @Column(
            name = "dues_clear"
    )
    private boolean DuesClear;
    @Column(
            name = "first_name"
    )
    private String firstName;
    @Column(
            name = "last_name"
    )
    private String lastName;
    @Column(
            name = "email_id"
    )
    private String emailId;
    @Column(
            name = "dob"
    )
    private Date dob;
    @Column(
            name = "password"
    )
//    @JsonIgnore
    private String password;
    @Column(
            name = "phone_number"
    )
    private String phoneNumber;

    public long getId() {
        return this.id;
    }

    public Integer getYearIntake() {
        return this.yearIntake;
    }

    public float getCgpa() {
        return this.cgpa;
    }

    public boolean isDuesClear() {
        return this.DuesClear;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getEmailId() {
        return this.emailId;
    }

    public Date getDob() {
        return this.dob;
    }

    public String getPassword() {
        return this.password;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public void setYearIntake(final Integer yearIntake) {
        this.yearIntake = yearIntake;
    }

    public void setCgpa(final float cgpa) {
        this.cgpa = cgpa;
    }

    public void setDuesClear(final boolean DuesClear) {
        this.DuesClear = DuesClear;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    public void setEmailId(final String emailId) {
        this.emailId = emailId;
    }

    public void setDob(final Date dob) {
        this.dob = dob;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public void setPhoneNumber(final String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Student() {
    }

    public void addCourse(Course course){
        this.enrolledCourses.add(course);
        course.getEnrolled().add(this);
    }
    public Set<Course> getEnrolledCourses(){
        return enrolledCourses;
    }

    public void removeCourse(long courseId){
        Course course = this.enrolledCourses.stream().filter(e->e.getId()==courseId).findFirst().orElse(null);
        if(course != null){
            this.enrolledCourses.remove(course);
            course.getEnrolled().remove(this);
        }
    }


    public Student(final String firstName, final String lastName, final String emailId, final String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailId = emailId;
        this.password = password;
    }

}
