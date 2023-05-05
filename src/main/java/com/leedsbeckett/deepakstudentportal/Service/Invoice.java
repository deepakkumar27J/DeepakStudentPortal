package com.leedsbeckett.deepakstudentportal.Service;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.Date;
@XmlRootElement(name="Invoice")
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class Invoice {
    private int amount;

    private String Types;

    private String studentEmail;

    public void setAmount(int amount) {
        this.amount = amount;
    }


    public void setTypes(String types) {
        Types = types;
    }

    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
    }

    public Invoice(int amount, String types, String studentEmail) {
        this.amount = amount;
        Types = types;
        this.studentEmail = studentEmail;
    }

    public int getAmount() {
        return amount;
    }

    public String getTypes() {
        return Types;
    }

    public String getStudentEmail() {
        return studentEmail;
    }
}
