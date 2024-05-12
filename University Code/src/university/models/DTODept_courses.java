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
public class DTODept_courses {
    
    private String dept_id;
    private int course_id ;

    public DTODept_courses(String dept_id, int course_id) {
        this.dept_id = dept_id;
        this.course_id = course_id;
    }

    public String getDept_id() {
        return dept_id;
    }

    public void setDept_id(String dept_id) {
        this.dept_id = dept_id;
    }

    public int getCourse_id() {
        return course_id;
    }

    public void setCourse_id(int course_id) {
        this.course_id = course_id;
    }
    
    
    
    
}
