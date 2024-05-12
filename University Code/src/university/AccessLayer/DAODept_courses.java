/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package university.AccessLayer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import university.models.DTODept_courses;
import university.models.DTOStudent;

/**
 *
 * @author Software
 */
public class DAODept_courses {
     public int addCourses(DTODept_courses courses) throws SQLException {
    int result;

    try (PreparedStatement statement = DAO.getConnection().prepareCall("INSERT INTO dept_courses(dept_id, course_id ) Values (?,?)")) {
        statement.setString(1, courses.getDept_id());
        statement.setInt(2, courses.getCourse_id());
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


public static boolean checkDeptCourseExistence(String deptId, int courseId) {
    try (PreparedStatement statement = DAO.getConnection().prepareCall("SELECT * FROM dept_courses WHERE dept_id = ? AND course_id = ?")) {
        statement.setString(1, deptId);
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
