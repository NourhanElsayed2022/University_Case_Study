/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package university.AccessLayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import university.models.DTOProf_course;
import university.models.DTOStudent;

/**
 *
 * @author Software
 */
public class DAOProf_course {
    
   

public int assignProfCourse(DTOProf_course prof_course) {
    int result = 0;

    try (Connection connection = DAO.getConnection();
         PreparedStatement statement = connection.prepareStatement("INSERT INTO professor_courses(pro_id, course_id) VALUES (?, ?)")) {

        statement.setInt(1, prof_course.getProf_id());
        statement.setInt(2, prof_course.getCourse_id());
        result = statement.executeUpdate();
    } catch (SQLException e) {
        // Log the SQL exception
        System.err.println("Failed to execute SQL query:");
        Logger.getLogger(DAOProf_course.class.getName()).log(Level.SEVERE, null, e);

        // Log specific details
        System.err.println("SQL State: " + e.getSQLState());
        System.err.println("Error Code: " + e.getErrorCode());
        System.err.println("Message: " + e.getMessage());

        result = -1; // Set a default error code or handle the error appropriately
    } catch (NullPointerException e) {
        // Log the NullPointerException
        e.printStackTrace();
        result = -1; // Set a default error code or handle the error appropriately
    }

    return result;
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
     
     // check Course Existence
public static boolean checkCourseExistence(int courseID) {
    try (PreparedStatement statement = DAO.getConnection().prepareCall("SELECT 1 FROM Courses WHERE course_id = ?")) {
        statement.setInt(1, courseID);
        try (ResultSet resultSet = statement.executeQuery()) {
            return resultSet.next(); // Return true if course exists
        }
    } catch (SQLException e) {
        // Handle SQLException appropriately
        e.printStackTrace();
        return false; // Return false in case of exception
    }
}
public static boolean checkProfCourseExistence(int professorID, int courseID) {
     try (PreparedStatement statement = DAO.getConnection().prepareCall("SELECT * FROM professor_courses WHERE pro_id = ? AND course_id = ?")) {
        statement.setInt(1, professorID);
        statement.setInt(2, courseID);
        try (ResultSet resultSet = statement.executeQuery()) {
            return resultSet.next(); // Return true if the department-course combination exists
        }
    } catch (SQLException e) {
        // Handle SQLException appropriately
        e.printStackTrace();
        return false; // Return false in case of exception
    }
}

    
    
    
}
