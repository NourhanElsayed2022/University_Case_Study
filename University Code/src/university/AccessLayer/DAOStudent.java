/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package university.AccessLayer;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import university.models.DTOStudent;

/**
 *
 * @author Software
 */
public class DAOStudent {
    
    public int addStudent(DTOStudent student) throws SQLException {
    int result;

    try (PreparedStatement statement = DAO.getConnection().prepareCall("INSERT INTO Students(student_id, f_name, l_name, DOF, phone, email, dept_id ) Values (?,?,?,?,?,?,?)")) {
        statement.setInt(1, student.getStudentID());
        statement.setString(2, student.getStudentFirstName());
        statement.setString(3, student.getStudentLastName());
        statement.setDate(4, student.getStudentDate());
        statement.setString(5, student.getStudentPhone());
        statement.setString(6, student.getUserEmail());
        statement.setString(7, student.getStudentDept_id());
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

 // get student 
    
    public DTOStudent getStudentData(int student_id) throws SQLException {
        try (
                PreparedStatement statement = DAO.getConnection().prepareCall("SELECT student_id, f_name, l_name, DOF, phone, email, dept_id , GPA From Students WHERE student_id = ?")) {
            statement.setInt(1, student_id);
            System.out.println("SQL Query: " + "SELECT student_id, f_name, l_name, DOF, phone, email, dept_id, GPA FROM Students WHERE student_id = ?");

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    
                    return new DTOStudent(
                            student_id,
                            resultSet.getString("f_name"),
                            resultSet.getString("l_name"),
                            resultSet.getDate("dof"),
                            resultSet.getString("phone"),
                            resultSet.getString("email"),
                            resultSet.getString("dept_id"),
                            resultSet.getInt("GPA")                                    
                    );
                }
            }
        }
        return null;
    }
    // detete student
    
   public static int removeStudent(int studentID) throws SQLException {
    int result = 0;
    try (Connection connection = DAO.getConnection()) {
        try (PreparedStatement statement1 = connection.prepareStatement("DELETE FROM Enrollment WHERE student_id = ?")) {
            statement1.setInt(1, studentID);
            result += statement1.executeUpdate();
        }
        try (PreparedStatement statement2 = connection.prepareStatement("DELETE FROM Students WHERE student_id = ?")) {
            statement2.setInt(1, studentID);
            result += statement2.executeUpdate();
        }
    } catch (SQLException e) {
        // Handle the exception appropriately (e.g., log it or throw a custom exception)
        e.printStackTrace();
    }
    return result;
}

    
    
    // update student 
    
     public int updateStudent(DTOStudent dtoStudent ) {
        int updateRes = 0;
        try (PreparedStatement statement = DAO.getConnection().prepareCall("update Students set f_name = ? , l_name = ? , DOF = ? ,phone = ? , email = ? , dept_id=? where student_id = ?")) {
            statement.setString(1, dtoStudent.getStudentFirstName());
            statement.setString(2, dtoStudent.getStudentLastName());
            statement.setDate(3, dtoStudent.getStudentDate());
            statement.setString(4, dtoStudent.getStudentPhone());
            statement.setString(5, dtoStudent.getUserEmail());
            statement.setString(6, dtoStudent.getStudentDept_id());
             statement.setInt(7, dtoStudent.getStudentID());
            
            updateRes = statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DAOStudent.class.getName()).log(Level.SEVERE, null, ex);
        }
        return updateRes;
    }
     
// Modify checkStudent_id method to return true if the student_id exists
public static boolean checkStudent_id(int studentId) {
    try (PreparedStatement statement = DAO.getConnection().prepareCall("SELECT 1 FROM students WHERE student_id = ?")) {
        statement.setInt(1, studentId);
        try (ResultSet resultSet = statement.executeQuery()) {
            return resultSet.next(); // Return true if student_id exists
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
// check student existance

public static boolean checkStudentExistence(int studentID) {
    try (PreparedStatement statement = DAO.getConnection().prepareCall("SELECT 1 FROM Students WHERE student_id = ?")) {
        statement.setInt(1, studentID);
        try (ResultSet resultSet = statement.executeQuery()) {
            return resultSet.next(); // Return true if student exists
        }
    } catch (SQLException e) {
        // Handle SQLException appropriately
        e.printStackTrace();
        return false; // Return false in case of exception
    }
}


}
