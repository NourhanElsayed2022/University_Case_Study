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
import university.models.DTOProfessor;
import university.models.DTOStudent;

/**
 *
 * @author Software
 */
public class DAOProfessor {
    
     public int addProfessor(DTOProfessor prof) throws SQLException {
    int result;

    try (PreparedStatement statement = DAO.getConnection().prepareCall("INSERT INTO Professors(pro_id, f_name, l_name, phone, salary, email ) Values (?,?,?,?,?,?)")) {
        statement.setInt(1, prof.getProfID());
        statement.setString(2, prof.getfName());
        statement.setString(3, prof.getlName());
        statement.setString(4, prof.getPhone());
        statement.setInt(5, prof.getSalary());
        statement.setString(6, prof.getEmail());
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
     
     // get Professor 
    
    public DTOProfessor getProfessorData(int prof_id) throws SQLException {
        try (
                PreparedStatement statement = DAO.getConnection().prepareCall("SELECT pro_id, f_name, l_name, phone,salary ,email From Professors WHERE pro_id = ?")) {
            statement.setInt(1, prof_id);
            System.out.println("SQL Query: " + "SELECT pro_id, f_name, l_name, phone,salary ,email From Professors WHERE pro_id = ?");

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    
                    return new DTOProfessor(
                            prof_id,
                            resultSet.getString("f_name"),
                            resultSet.getString("l_name"),
                            resultSet.getString("phone"),
                            resultSet.getInt("salary"),
                            resultSet.getString("email")                                  
                    );
                }
            }
        }
        return null;
    }
    
    // delete Professor
    public static int removeProfessor(int professorID) throws SQLException {
    int result = 0;
    try {
        // First, delete records from Professor_courses table
        PreparedStatement deleteCoursesStatement = DAO.getConnection().prepareCall("DELETE FROM Professor_courses WHERE pro_id = ?");
        deleteCoursesStatement.setInt(1, professorID);
        deleteCoursesStatement.executeUpdate();
        
        // Then, delete the professor from Professors table
        PreparedStatement deleteProfessorStatement = DAO.getConnection().prepareCall("DELETE FROM Professors WHERE pro_id = ?");
        deleteProfessorStatement.setInt(1, professorID);
        result = deleteProfessorStatement.executeUpdate();
    } catch (SQLException ex) {
        // Handle SQLException appropriately
        ex.printStackTrace();
    } finally {
        // Close resources if needed
        // Example: DAO.closeConnection();
    }
    return result;
}

    
    // update Professor 
    
     public int updateProfessor(DTOProfessor dTOProfessor ) {
        int updateRes = 0;
        try (PreparedStatement statement = DAO.getConnection().prepareCall("update Professors set f_name = ? , l_name = ? ,phone = ? ,salary = ?, email = ?  where pro_id = ?")) {
            statement.setString(1, dTOProfessor.getfName());
            statement.setString(2, dTOProfessor.getlName());
            statement.setString(3, dTOProfessor.getPhone());
            statement.setInt(4, dTOProfessor.getSalary());
            statement.setString(5, dTOProfessor.getEmail());
             statement.setInt(6, dTOProfessor.getProfID());
            
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
     
     
     
     
     
     
     
     
     
     
     
     


    
    

