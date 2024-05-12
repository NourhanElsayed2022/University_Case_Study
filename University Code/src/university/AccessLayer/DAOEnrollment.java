/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package university.AccessLayer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import university.models.DTOEnrollment;
import university.models.DTOStudent;

/**
 *
 * @author Software
 */
public class DAOEnrollment {
    
    public  static int addCourse(DTOEnrollment course) throws SQLException {
    int result;

    try (PreparedStatement statement = DAO.getConnection().prepareCall("INSERT INTO Enrollment(student_id, course_id, grade, enrollment_date) VALUES (?,?,?,?)")) {
    statement.setInt(1, course.getStudent_id());
    statement.setInt(2, course.getCourse_id());
    statement.setString(3, course.getGrade().toUpperCase());
    statement.setDate(4, course.getEnrollmentDate());
    
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
   
public static boolean checkStudentCourseExistence(int studentId, int courseId) {
    try (PreparedStatement statement = DAO.getConnection().prepareCall("SELECT * FROM Enrollment WHERE student_id = ? AND course_id = ?")) {
        statement.setInt(1, studentId);
        statement.setInt(2, courseId);
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
