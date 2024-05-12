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
public class DTOProfessor {
    private int profID;
    private String fName;
    private String lName;
    private String phone ;
    private int salary;
     private String email;

    public DTOProfessor(int profID, String fName, String lName, String phone, int salary, String email) {
        this.profID = profID;
        this.fName = fName;
        this.lName = lName;
        this.phone = phone;
        this.salary = salary;
        this.email = email;
    }

  

    public int getProfID() {
        return profID;
    }

    public void setProfID(int profID) {
        this.profID = profID;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }
    
    
    
    
}
