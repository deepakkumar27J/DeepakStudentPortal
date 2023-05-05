package com.leedsbeckett.deepakstudentportal.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Course {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private long id;

    public void setId(long id) {
        this.id = id;
    }

    public float getCredit() {
        return credit;
    }

    public boolean isCore() {
        return core;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getDescription() {
        return description;
    }

    public void setCredit(float credit) {
        this.credit = credit;
    }

    public void setCore(boolean core) {
        this.core = core;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = CascadeType.PERSIST,
            mappedBy = "enrolledCourses")
    @JsonIgnore
    private Set<Student> enrolled = new HashSet<Student>();
    @Column(
            name = "credit"
    )
    private float credit;
    @Column(
            name = "core"
    )
    private boolean core;
    @Column(
            name = "course_name"
    )
    private String courseName;

    @Column(
            name = "description"
    )
    private String description;

    @Column(
            name = "cost"
    )
    private int cost = 100;

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public Course() {
    }

    public long getId() {
        return id;
    }
    public Set<Student> getEnrolled(){
        return enrolled;
    }
    public void setEnrolled(Set<Student>enrolled){
        this.enrolled = enrolled;
    }
}
