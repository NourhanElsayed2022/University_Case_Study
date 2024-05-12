/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package university.enrollment;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javax.swing.JOptionPane;
import university.AccessLayer.DAO;
import university.AccessLayer.DAOEnrollment;
import static university.AccessLayer.DAOEnrollment.checkCourseExistence;
import static university.AccessLayer.DAOEnrollment.checkStudentCourseExistence;
import university.AccessLayer.DAOStudent;
import static university.AccessLayer.DAOStudent.checkStudentExistence;
import university.models.DTOEnrollment;
import university.models.DTOStudent;
import university.students.StudentController;

/**
 *
 * @author Software
 */
public class EnrollmentController implements Initializable{
    
    
       @FXML
    private TextField studentIdField;

    @FXML
    private TextField courseIdField;

    @FXML
    private TextField gradeField;

     @FXML
    private Label dataAlert;
    
    @FXML
    private Button enroleBttn;

    @FXML
    private DatePicker datePickerField;

@FXML
void handleEnrollmentBttn(ActionEvent event) throws SQLException {
    String studentIdText = studentIdField.getText();
    String courseIdText = courseIdField.getText();
    if (studentIdText.isEmpty() || courseIdText.isEmpty()) {
        // Handle the case where student ID or course ID is empty
        dataAlert.setText("Student ID and Course ID cannot be empty");
        return;
    }
    
    int studentID;
    int courseID;
    try {
        studentID = Integer.parseInt(studentIdText);
        courseID = Integer.parseInt(courseIdText);

        // Check if student_id already exists
        if (!checkStudentExistence(studentID)) {
            System.out.println("Student ID does not exist");
            // Show appropriate message to the user
            String e = "<html><body style='font-family: Arial; font-size: 12px;'>Student ID does not exist</body></html>";
            JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
            return; // Exit the method if student ID does not exist
        }

        // Continue with checking course ID existence
        if (!checkCourseExistence(courseID)) {
            System.out.println("Course ID does not exist");
            // Show appropriate message to the user
            String e = "<html><body style='font-family: Arial; font-size: 12px;'>Course ID does not exist</body></html>";
            JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
            return; // Exit the method if course ID does not exist
        }

        // Check if the student-course combination already exists
        if (checkStudentCourseExistence(studentID, courseID)) {
            System.out.println("Student-course combination already exists");
            // Show appropriate message to the user
            String e = "<html><body style='font-family: Arial; font-size: 12px;'>Student-course combination already exists</body></html>";
            JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
            return; // Exit the method if the combination already exists
        }

        // Continue with checking other data and adding the course
        String grade = gradeField.getText();
        LocalDate enrollmentDate = datePickerField.getValue();

        // Check if all required data is provided
        if (grade.isEmpty() || enrollmentDate == null) {
            dataAlert.setText("Please complete the required data.");
            return;
        }

        // Create DTOEnrollment object
        DTOEnrollment course = new DTOEnrollment(studentID, courseID, grade, java.sql.Date.valueOf(enrollmentDate));

        // Add the course
        DAOEnrollment dAOEnrollment = new DAOEnrollment();
        int res = dAOEnrollment.addCourse(course);

        if (res > 0) {
            System.out.println("Course inserted successfully!!");
            dataAlert.setText("Course Added Successfully!!");
            // Clear all text fields
            clearTextFields();
        } else {
            System.out.println("Course is not inserted successfully!!");
        }
    } catch (NumberFormatException e) {
        // Handle the case where the input is not a valid integer
        dataAlert.setText("Invalid Student ID or Course ID. Please enter a valid integer.");
    } catch (SQLException e) {
        // Handle SQL exception
        e.printStackTrace();
        // Show appropriate message to the user
        String errorMessage = "<html><body style='font-family: Arial; font-size: 12px;'>An error occurred while processing the request. Please try again later.</body></html>";
        JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
    }
}

        

 
    // Helper method to clear all text fields
private void clearTextFields() {
    studentIdField.clear();
    courseIdField.clear();
    gradeField.clear();
    datePickerField.setValue(null);
}


    @Override
    public void initialize(URL location, ResourceBundle resources) {
     DAO  dao = new DAO();    }
    
}

