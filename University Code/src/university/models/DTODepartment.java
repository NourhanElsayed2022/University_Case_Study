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
public class DTODepartment {
    
    private String dept_id ;
    private String dept_name ;
    private int supervisor_id ;

    public DTODepartment(String dept_id, String dept_name, int supervisor_id) {
        this.dept_id = dept_id;
        this.dept_name = dept_name;
        this.supervisor_id = supervisor_id;
    }

    public String getDept_id() {
        return dept_id;
    }

    public void setDept_id(String dept_id) {
        this.dept_id = dept_id;
    }

    public String getDept_name() {
        return dept_name;
    }

    public void setDept_name(String dept_name) {
        this.dept_name = dept_name;
    }

    public int getSupervisor_id() {
        return supervisor_id;
    }

    public void setSupervisor_id(int supervisor_id) {
        this.supervisor_id = supervisor_id;
    }

   
    
    
    
}
