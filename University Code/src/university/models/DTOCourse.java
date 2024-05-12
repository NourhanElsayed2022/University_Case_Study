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
public class DTOCourse {
    
    
    private int course_id;
    private String course_name;
    private String describtion;
    private String code ;
    private int credit_hours;
    private String semester;

    public DTOCourse(int course_id, String course_name, String describtion, String code, int credit_hours, String semester) {
        this.course_id = course_id;
        this.course_name = course_name;
        this.describtion = describtion;
        this.code = code;
        this.credit_hours = credit_hours;
        this.semester = semester;
    }

    public int getCourse_id() {
        return course_id;
    }

    public void setCourse_id(int course_id) {
        this.course_id = course_id;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public String getDescribtion() {
        return describtion;
    }

    public void setDescribtion(String describtion) {
        this.describtion = describtion;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getCredit_hours() {
        return credit_hours;
    }

    public void setCredit_hours(int credit_hours) {
        this.credit_hours = credit_hours;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }
    
    
    
    
}
