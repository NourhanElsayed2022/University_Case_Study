/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package university.dept_courses;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javax.swing.JOptionPane;
import university.AccessLayer.DAODept_courses;
import static university.AccessLayer.DAODept_courses.checkDeptCourseExistence;
import static university.AccessLayer.DAOEnrollment.checkCourseExistence;
import university.AccessLayer.DAOStudent;
import static university.AccessLayer.DAOStudent.checkDept;
import university.models.DTODept_courses;
import university.models.DTOStudent;
import university.students.StudentController;

/**
 *
 * @author Software
 */
public class dept_coursesController {
    
    @FXML
    private TextField dept_idField;

    @FXML
    private TextField course_idField;

    @FXML
    private Button addCourseBttn;

    @FXML
    private Label dataAlert;

    @FXML
void handleAddCourse(ActionEvent event) {
    try {
        String courseIdText = course_idField.getText();
        String deptIdText = dept_idField.getText();

        if (courseIdText.isEmpty() || deptIdText.isEmpty()) {
            // Handle the case where course ID or department ID is empty
            dataAlert.setText("Course ID and Department ID cannot be empty");
            return;
        }

        try {
            int courseID = Integer.parseInt(courseIdText);

            // Check if course ID is valid
            if (courseID <= 0) {
                dataAlert.setText("Please enter a valid Course ID.");
                return;
            }

            // Check if department ID is valid
            if (!checkDept(deptIdText)) {
                String e = "<html><body style='font-family: Arial; font-size: 12px;'>Invalid Department ID</body></html>";
                JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Check if course ID exists
            if (!checkCourseExistence(courseID)) {
                String e = "<html><body style='font-family: Arial; font-size: 12px;'>Invalid Course ID</body></html>";
                JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Check if the combination of department ID and course ID already exists
            if (checkDeptCourseExistence(deptIdText, courseID)) {
                String e = "<html><body style='font-family: Arial; font-size: 12px;'>This course already exists for the department</body></html>";
                JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Create DTODept_courses instance
            DTODept_courses courses = new DTODept_courses(deptIdText, courseID);
            
            // Add the course
            DAODept_courses dAODept_courses = new DAODept_courses();
            int res = dAODept_courses.addCourses(courses);

            if (res > 0) {
                System.out.println("Course inserted successfully!!");
                dataAlert.setText("Course Added Successfully!!");

                // Clear all text fields
                clearTextFields();
            } else {
                System.out.println("Course cannot be inserted!!");
            }
        } catch (NumberFormatException e) {
            // Handle the case where the input is not a valid integer
            dataAlert.setText("Invalid Course ID. Please enter a valid integer.");
            return;
        }
    } catch (SQLException ex) {
        // Handle the SQL exception
        String e = "<html><body style='font-family: Arial; font-size: 12px;'>Database Error: Failed to add the course</body></html>";
        JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
            dataAlert.setText("");

        Logger.getLogger(dept_coursesController.class.getName()).log(Level.SEVERE, null, ex);
    }
}


// Helper method to clear all text fields
private void clearTextFields() {
    dept_idField.clear();
    course_idField.clear();
   
}


        

    }
    
    
