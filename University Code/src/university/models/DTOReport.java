/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package university.models;

/**
 *
 * @author Software
 */
public class DTOReport {
    
    private String code ;
    private String courseName ;
    private String studentName ;
    private String studentGrade ;
    private int avg_gpa ;

    public DTOReport(String code, String courseName, String studentName, String studentGrade, int avg_gpa) {
        this.code = code;
        this.courseName = courseName;
        this.studentName = studentName;
        this.studentGrade = studentGrade;
        this.avg_gpa = avg_gpa;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentGrade() {
        return studentGrade;
    }

    public void setStudentGrade(String studentGrade) {
        this.studentGrade = studentGrade;
    }

    public int getAvg_gpa() {
        return avg_gpa;
    }

    public void setAvg_gpa(int avg_gpa) {
        this.avg_gpa = avg_gpa;
    }
    
    
    
}
