/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package university.models;

import java.time.LocalDate;
import java.util.Date;

/**
 *
 * @author Software
 */
public class DTOStudent {
    private int studentID;
    private  String studentFirstName ;
    private String studentLastName ;
    private java.sql.Date studentDate;
    private String userEmail ;
    private String studentPhone ;
    private String studentDept_id ;
    private int GPa ;
    
    
    public DTOStudent() {
        
    }

    public DTOStudent(int studentID, String studentFirstName, String studentLastName,  java.sql.Date studentDate,String studentPhone, String userEmail,  String studentDept_id) {
        this.studentID = studentID;
        this.studentFirstName = studentFirstName;
        this.studentLastName = studentLastName;
        this.studentDate = studentDate;
        this.studentPhone = studentPhone;
        this.userEmail = userEmail;
        this.studentDept_id = studentDept_id;
    }


    public DTOStudent(int studentID, String studentFirstName, String studentLastName,  java.sql.Date studentDate, String userEmail, String studentPhone, String studentDept_id, int GPa) {
        this.studentID = studentID;
        this.studentFirstName = studentFirstName;
        this.studentLastName = studentLastName;
        this.studentDate = studentDate;
        this.userEmail = userEmail;
        this.studentPhone = studentPhone;
        this.studentDept_id = studentDept_id;
        this.GPa = GPa;
    }
    

    public int getStudentID() {
        return studentID;
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    public String getStudentFirstName() {
        return studentFirstName;
    }

    public void setStudentFirstName(String studentFirstName) {
        this.studentFirstName = studentFirstName;
    }

    public String getStudentLastName() {
        return studentLastName;
    }

    public void setStudentLastName(String studentLastName) {
        this.studentLastName = studentLastName;
    }

    public java.sql.Date getStudentDate() {
        return (java.sql.Date) this.studentDate;
    }

    public void setStudentDate(LocalDate studentDate) {
        this.studentDate = java.sql.Date.valueOf(studentDate);
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getStudentPhone() {
        return studentPhone;
    }

    public void setStudentPhone(String studentPhone) {
        this.studentPhone = studentPhone;
    }

    public String getStudentDept_id() {
        return studentDept_id;
    }

    public void setStudentDept_id(String studentDept_id) {
        this.studentDept_id = studentDept_id;
    }

    public int getGPa() {
        return GPa;
    }

    public void setGPa(int GPa) {
        this.GPa = GPa;
    }
        
        

    
    
    
}
