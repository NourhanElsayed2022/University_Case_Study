/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package university.prof_courses;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javax.swing.JOptionPane;
import university.AccessLayer.DAO;
import static university.AccessLayer.DAODepartment.checkProfessorIDExistence;
import static university.AccessLayer.DAOEnrollment.checkCourseExistence;
import university.AccessLayer.DAOProf_course;
import static university.AccessLayer.DAOProf_course.checkProfCourseExistence;
import university.models.DTOProf_course;

public class Prof_courseController  implements  Initializable{

    @FXML
    private TextField profIdfield;

    @FXML
    private TextField courseIdField;

    @FXML
    private Button assignCourseBttn;

    @FXML
    private Label dataAlert;

 @FXML
void handleAssignCourse(ActionEvent event) {
    String profIdText = profIdfield.getText();
    String courseIdText = courseIdField.getText();

    if (profIdText.isEmpty() || courseIdText.isEmpty()) {
        // Handle the case where professor ID or course ID is empty
        dataAlert.setText("Professor ID and Course ID cannot be empty");
        return;
    }

    try {
        int professorID = Integer.parseInt(profIdText);
        int courseID = Integer.parseInt(courseIdText);

        // Check if IDs are valid
        if (professorID <= 0 || courseID <= 0) {
            dataAlert.setText("Please enter valid Professor ID and Course ID.");
            return;
        }

        // Check if professor ID exists
        if (!checkProfessorIDExistence(professorID)) {
            String e = "<html><body style='font-family: Arial; font-size: 12px;'>Invalid Professor ID</body></html>";
            JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Check if course ID exists
        if (!checkCourseExistence(courseID)) {
            String e = "<html><body style='font-family: Arial; font-size: 12px;'>Invalid Course ID</body></html>";
            JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Check if the professor-course combination already exists
        if (checkProfCourseExistence(professorID, courseID)) {
            String e = "<html><body style='font-family: Arial; font-size: 12px;'>This Professor-Course combination already exists</body></html>";
            JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Create DTOProf_course instance
        DTOProf_course profCourse = new DTOProf_course(professorID, courseID);

        // Create a background task for the database operation
        Task<Integer> task = new Task<Integer>() {
            @Override
            protected Integer call() throws Exception {
                DAOProf_course daoProf_course = new DAOProf_course();
                return daoProf_course.assignProfCourse(profCourse);
            }
        };

        // Set up event handlers for task completion
        task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                int result = task.getValue();

                if (result > 0) {
                    System.out.println("Course assigned to professor successfully!!");
                    JOptionPane.showMessageDialog(null, "Course assigned to professor successfully!!", "Error", JOptionPane.ERROR_MESSAGE);

                    // Clear all text fields
                    clearTextFields();
                } else {
                    System.out.println("Course is not assigned to professor successfully!!");
                }
            }
        });

        task.setOnFailed(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                // Handle task failure (e.g., display an alert)
                String e = "<html><body style='font-family: Arial; font-size: 12px;'>An error occurred while assigning the course to the professor. Please try again.</body></html>";
                JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Start the background task
        new Thread(task).start();

    } catch (NumberFormatException e) {
        // Handle the case where the input is not a valid integer
        dataAlert.setText("Invalid Professor ID or Course ID. Please enter valid integers.");
    }
}

    // Helper method to clear all text fields
    private void clearTextFields() {
        profIdfield.clear();
        courseIdField.clear();
        dataAlert.setText("");

    }


    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        DAO dao = new DAO();
    }
    
}
