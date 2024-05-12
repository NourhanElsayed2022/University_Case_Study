/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package university.AccessLayer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import university.models.DTODepartment;
import university.models.DTOStudent;

/**
 *
 * @author Software
 */
public class DAODepartment {
    
    // add Department
    
     public int addDepartment(DTODepartment dept) throws SQLException {
    int result;

    try (PreparedStatement statement = DAO.getConnection().prepareCall("INSERT INTO Departments(dept_id, dept_name, supervisor_id ) Values (?,?,?)")) {
        statement.setString(1, dept.getDept_id());
        statement.setString(2, dept.getDept_name());
        statement.setInt(3, dept.getSupervisor_id());
        result = statement.executeUpdate();
    } catch (SQLException e) {
        // Log the SQL exception
        e.printStackTrace();
        throw e; // Rethrow the exception after logging
    } catch (NullPointerException e) {
        // Log the NullPointerException
        e.printStackTrace();
        throw e; // Rethrow the exception after logging
    }

    return result;
}
    // get Department 
    
    public DTODepartment getDepartmentData(String dept_id) throws SQLException {
        try (
                PreparedStatement statement = DAO.getConnection().prepareCall("SELECT dept_id, dept_name, supervisor_id From Departments WHERE dept_id = ?")) {
            statement.setString(1, dept_id);
            System.out.println("SQL Query: " + "SELECT dept_id, dept_name, supervisor_id From Departments WHERE dept_id = ?");

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    
                    return new DTODepartment(dept_id,
                            resultSet.getString("dept_name"),
                            resultSet.getInt("supervisor_id")                                   
                    );
                }
            }
        }
        return null;
    }
    
    // detete Department
    /*
    public static int removeDepartment(String dept_id) throws SQLException{
     int result=0;
     PreparedStatement statement = DAO.getConnection().prepareCall(" DELETE FROM Departments WHERE dept_id = ? ");
     statement.setString(1, dept_id);
     result = statement.executeUpdate();
    return result;
    } */
    public static int removeDepartment(String dept_id) throws SQLException {
    int result = 0;
    PreparedStatement statement = null;
    try {
        // DELETE from enrollment where student_id in (SELECT student_id from Students where dept_id = ?)
        String deleteEnrollmentQuery = "DELETE FROM Enrollment WHERE student_id IN (SELECT student_id FROM Students WHERE dept_id = ?)";
        statement = DAO.getConnection().prepareStatement(deleteEnrollmentQuery);
        statement.setString(1, dept_id);
        statement.executeUpdate();
        statement.close();

        // Delete from Students where dept_id = ?
        String deleteStudentsQuery = "DELETE FROM Students WHERE dept_id = ?";
        statement = DAO.getConnection().prepareStatement(deleteStudentsQuery);
        statement.setString(1, dept_id);
        statement.executeUpdate();
        statement.close();

        // DELETE FROM dept_courses WHERE dept_id = ?
        String deleteDeptCoursesQuery = "DELETE FROM dept_courses WHERE dept_id = ?";
        statement = DAO.getConnection().prepareStatement(deleteDeptCoursesQuery);
        statement.setString(1, dept_id);
        statement.executeUpdate();
        statement.close();

        // DELETE FROM Departments WHERE dept_id = ?
        String deleteDeptQuery = "DELETE FROM Departments WHERE dept_id = ?";
        statement = DAO.getConnection().prepareStatement(deleteDeptQuery);
        statement.setString(1, dept_id);
        result = statement.executeUpdate();
    } finally {
        if (statement != null) {
            statement.close();
        }
    }
    return result;
}

    
    
     // update Department 
    
     public int updateDepartment(DTODepartment dTODepartment ) {
        int updateRes = 0;
        try (PreparedStatement statement = DAO.getConnection().prepareCall("update Departments set dept_name = ? , supervisor_id = ? where dept_id = ?")) {
            statement.setString(1, dTODepartment.getDept_name());
            statement.setInt(2, dTODepartment.getSupervisor_id());
            statement.setString(3, dTODepartment.getDept_id());
            
            updateRes = statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DAOStudent.class.getName()).log(Level.SEVERE, null, ex);
        }
        return updateRes;
    }
    
     // check ProfessorID Existence
     
     public static boolean checkProfessorIDExistence(int professorID) {
    try (PreparedStatement statement = DAO.getConnection().prepareCall("SELECT 1 FROM Professors WHERE pro_id = ?")) {
        statement.setInt(1, professorID);
        try (ResultSet resultSet = statement.executeQuery()) {
            return resultSet.next(); // Return true if professor ID exists
        }
    } catch (SQLException e) {
        // Handle SQLException appropriately
        e.printStackTrace();
        return false; // Return false in case of exception
    }
}
     
     // check dept_id
public static boolean checkDept(String deptId) {
    try (PreparedStatement statement = DAO.getConnection().prepareCall("SELECT 1 FROM departments WHERE dept_id = ?")) {
        statement.setString(1, deptId);
        try (ResultSet resultSet = statement.executeQuery()) {
            return resultSet.next(); // Return true if dept_id exists
        }
    } catch (SQLException e) {
        // Handle SQLException appropriately
        e.printStackTrace();
        return false; // Return false in case of exception
    }
}
     
     
     
    
}
