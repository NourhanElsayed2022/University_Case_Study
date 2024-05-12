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
import university.models.DTOCourse;
import university.models.DTOStudent;

/**
 *
 * @author Software
 */
public class DAOCourse {
    
     public int addCourse(DTOCourse course) throws SQLException {
    int result = 0;

     try (PreparedStatement statement = DAO.getConnection().prepareCall("INSERT INTO Courses(course_id, name, description, code, credit_hours, semester ) Values (?,?,?,?,?,?)")) {
        statement.setInt(1, course.getCourse_id());
        statement.setString(2, course.getCourse_name());
        statement.setString(3, course.getDescribtion());
        statement.setString(4, course.getCode());
        statement.setInt(5, course.getCredit_hours());
        statement.setString(6, course.getSemester());

        // Debug: Print the SQL statement being executed
        System.out.println("Executing SQL statement: " + statement.toString());

        result = statement.executeUpdate();

        // Debug: Print the number of rows affected by the update
        System.out.println("Rows affected: " + result);
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

    // get course
     
     public DTOCourse getCourseData(int course_id) throws SQLException {
        try (
                PreparedStatement statement = DAO.getConnection().prepareCall("SELECT  name, description, code, credit_hours, semester From Courses WHERE course_id = ?")) {
            statement.setInt(1, course_id);
            System.out.println("SQL Query: " + "SELECT  name, description, code, credit_hours, semester From Courses WHERE course_id = ?");

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    
                    return new DTOCourse(
                            course_id,
                            resultSet.getString("name"),
                            resultSet.getString("description"),
                            resultSet.getString("code"),
                            resultSet.getInt("credit_hours"),
                            resultSet.getString("semester")                                   
                    );
                }
            }
        }
        return null;
    }
     
      // detete Course
    
    public static int removeCourse(int courseID) throws SQLException {
    int result = 0;
    Connection connection = null;
    PreparedStatement statement = null;
    
    try {
        connection = DAO.getConnection();
        // Deleting from Enrollment table
        statement = connection.prepareStatement("DELETE FROM Enrollment WHERE course_id = ?");
        statement.setInt(1, courseID);
        result += statement.executeUpdate();
        
        // Deleting from prof_courses table
        statement = connection.prepareStatement("DELETE FROM professor_courses WHERE course_id = ?");
        statement.setInt(1, courseID);
        result += statement.executeUpdate();
        
        // Deleting from dept_courses table
        statement = connection.prepareStatement("DELETE FROM dept_courses WHERE course_id = ?");
        statement.setInt(1, courseID);
        result += statement.executeUpdate();
        
        // Deleting from Courses table
        statement = connection.prepareStatement("DELETE FROM Courses WHERE course_id = ?");
        statement.setInt(1, courseID);
        result += statement.executeUpdate();
    } finally {
        // Close resources
        if (statement != null) {
            statement.close();
        }
        if (connection != null) {
            connection.close();
        }
    }
    
    return result;
}

    
     // update Course 
    
     public int updateCourse(DTOCourse course ) {
        int updateRes = 0;
        try (PreparedStatement statement = DAO.getConnection().prepareCall("update Courses set name = ? , description = ? , code = ? ,credit_hours = ? , semester = ?  where course_id = ?")) {
            statement.setString(1, course.getCourse_name());
            statement.setString(2, course.getDescribtion());
            statement.setString(3, course.getCode());
            statement.setInt(4, course.getCredit_hours());
            statement.setString(5, course.getSemester());
             statement.setInt(6, course.getCourse_id());
            
            updateRes = statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DAOCourse.class.getName()).log(Level.SEVERE, null, ex);
        }
        return updateRes;
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
    
}
