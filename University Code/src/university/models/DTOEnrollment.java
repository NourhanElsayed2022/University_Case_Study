/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package university.models;

import java.sql.Date;
import java.time.LocalDate;

/**
 *
 * @author Software
 */
public class DTOEnrollment {
    private int student_id ;
    private int course_id ;
    private String grade;
    private java.sql.Date enrollmentDate;

    public DTOEnrollment(int student_id, int course_id, String grade, Date enrollmentDate) {
        this.student_id = student_id;
        this.course_id = course_id;
        this.grade = grade;
        this.enrollmentDate = enrollmentDate;
    }

    public int getStudent_id() {
        return student_id;
    }

    public void setStudent_id(int student_id) {
        this.student_id = student_id;
    }

    public int getCourse_id() {
        return course_id;
    }

    public void setCourse_id(int course_id) {
        this.course_id = course_id;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

   
    
     public java.sql.Date getEnrollmentDate() {
        return (java.sql.Date) this.enrollmentDate;
    }

    public void setEnrollmentDate(LocalDate enrollmentDate) {
        this.enrollmentDate = java.sql.Date.valueOf(enrollmentDate);
    }


  
}
