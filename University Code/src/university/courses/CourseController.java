/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package university.courses;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javax.swing.JOptionPane;
import university.AccessLayer.DAOCourse;
import static university.AccessLayer.DAOCourse.checkCourseExistence;
import university.AccessLayer.DAOStudent;
import university.models.DTOCourse;
import university.models.DTOStudent;
import university.students.StudentController;

/**
 *
 * @author Software
 */
public class CourseController implements Initializable{
    
    @FXML
    private AnchorPane coursePane;

    @FXML
    private TextField courseIdField;

    @FXML
    private TextField courseNameField;

    @FXML
    private TextField CourseDescribtionField;

    @FXML
    private TextField CourseCodeField;

    @FXML
    private TextField creditHoursField;

    @FXML
    private TextField semesterField;

    @FXML
    private Button addCourseBttn;

    @FXML
    private Button removeCourseBttn;

    @FXML
    private Button displayCourseBttn;

    @FXML
    private Button clearBttn;

    @FXML
    private Button updateCourseBtn;
    
    @FXML
    private Label dataAlert;
@FXML
void handleAddCourse(ActionEvent event) {
    String courseIdText = courseIdField.getText();
    String creditHoursText = creditHoursField.getText();

    if (courseIdText.isEmpty()) {
        // Handle the case where course ID is empty
        dataAlert.setText("Course ID cannot be empty");
        return;
    }

    try {
        int courseID = Integer.parseInt(courseIdText);
        String courseName = courseNameField.getText();
        String description = CourseDescribtionField.getText();
        String code = CourseCodeField.getText();
        int credit_hours = Integer.parseInt(creditHoursText);
        String semester = semesterField.getText();

        // Check if all required data is provided
        if (courseID <= 0 || courseName.isEmpty()
                || description.isEmpty() || code.isEmpty()
                || semester.isEmpty() || credit_hours <= 0) {
            dataAlert.setText("Please complete the required data correctly.");
            return;
        }

        // Check if the course ID already exists
        if (checkCourseExistence(courseID)) {
            String e = "<html><body style='font-family: Arial; font-size: 12px;'>Course with ID " + courseID + " already exists.</body></html>";
                JOptionPane.showMessageDialog(null, e, "Success", JOptionPane.INFORMATION_MESSAGE);
            //dataAlert.setText("Course with ID " + courseID + " already exists.");
            return;
        }

        DTOCourse course = new DTOCourse(courseID, courseName, description, code, credit_hours, semester);

        // Perform database operation in a background thread
        Task<Integer> addCourseTask = new Task<Integer>() {
            @Override
            protected Integer call() throws Exception {
                DAOCourse dAOCourse = new DAOCourse();
                return dAOCourse.addCourse(course);
            }
        };

        // Set up event handlers for task completion
        addCourseTask.setOnSucceeded(e -> {
            int res = addCourseTask.getValue();
            if (res > 0) {
                System.out.println("Course inserted successfully!!");
                JOptionPane.showMessageDialog(null, "Course Added Successfully!!", "Success", JOptionPane.INFORMATION_MESSAGE);
               // dataAlert.setText("Course Added Successfully!!");
                clearTextFields();
            } else {
                System.out.println("Course cannot be inserted!!");
            }
        });

        addCourseTask.setOnFailed(e -> {
            dataAlert.setText("Error adding the Course. Please try again.");
            e.getSource().getException().printStackTrace(); // Log the exception for debugging purposes
        });

        // Start the task
        new Thread(addCourseTask).start();

    } catch (NumberFormatException e) {
        // Handle the case where the input is not a valid integer
        dataAlert.setText("Complete data correctly.");
    }
}


// Helper method to clear all text fields
private void clearTextFields() {
    courseIdField.clear();
    courseNameField.clear();
    CourseDescribtionField.clear();
    CourseCodeField.clear();
    creditHoursField.clear();
    semesterField.clear();
    dataAlert.setText(" ");
    
}


    

    @FXML
    void handleClearBttn(ActionEvent event) {
                  clearTextFields();
    }

    @FXML
void handleDisplayCourse(ActionEvent event) {
    String courseIdText = courseIdField.getText();
    if (courseIdText.isEmpty()) {
        // Handle the case where course ID is empty
        dataAlert.setText("Course ID cannot be empty");
        return;
    }

    try {
        int courseId = Integer.parseInt(courseIdText);
        DAOCourse dAOCourse = new DAOCourse();

        // Check if course exists
        if (!dAOCourse.checkCourseExistence(courseId)) {
             String e = "<html><body style='font-family: Arial; font-size: 12px;'>Course with ID " + courseId + " does not exist</body></html>";
            JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        DTOCourse course = dAOCourse.getCourseData(courseId);

        // Set course values to text fields
        courseIdField.setText(String.valueOf(courseId));
        courseNameField.setText(course.getCourse_name());
        CourseDescribtionField.setText(course.getDescribtion());
        CourseCodeField.setText(course.getCode());
        creditHoursField.setText(String.valueOf(course.getCredit_hours()));
        semesterField.setText(course.getSemester());

    } catch (NumberFormatException e) {
        // Handle the case where the input is not a valid integer
        dataAlert.setText("Invalid Course ID. Please enter a valid integer.");
    } catch (SQLException ex) {
        Logger.getLogger(StudentController.class.getName()).log(Level.SEVERE, null, ex);
        dataAlert.setText("Error retrieving Course data.");
    }
}

@FXML
void handleRemoveCourse(ActionEvent event) {
    String courseIdText = courseIdField.getText();
    if (courseIdText.isEmpty()) {
        dataAlert.setText("Please enter a Course ID.");
        return;
    }

    try {
        int courseId = Integer.parseInt(courseIdText);
        DAOCourse dAOCourse = new DAOCourse();

        // Check if course exists
        if (!dAOCourse.checkCourseExistence(courseId)) {
            String e = "<html><body style='font-family: Arial; font-size: 12px;'>Course with ID " + courseId + " does not exist</body></html>";
            JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // Ask for confirmation before removing the course
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation");
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText("Are you sure you want to remove the Course with ID " + courseId + "?");

        Optional<ButtonType> result = confirmationAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            int res = dAOCourse.removeCourse(courseId);
            
            if (res > 0)
                JOptionPane.showMessageDialog(null, "The selected Course removed successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
               // dataAlert.setText("The selected Course removed successfully.");
            else
                dataAlert.setText("Course with ID " + courseId + " not found.");
        } else {
            dataAlert.setText("Remove operation canceled.");
        }

    } catch (NumberFormatException e) {
        dataAlert.setText("Invalid Course ID. Please enter a valid integer.");
    } catch (SQLException ex) {
        dataAlert.setText("Error removing the Course. Please try again.");
        Logger.getLogger(CourseController.class.getName()).log(Level.SEVERE, null, ex);
    }
}

@FXML
void handleUpdateCourse(ActionEvent event) {
    String courseIdText = courseIdField.getText();
    String creditHoursText = creditHoursField.getText();
    if (courseIdText.isEmpty()) {
        // Handle the case where course ID is empty
        dataAlert.setText("Course ID cannot be empty");
        return;
    }
    
    DTOCourse course = null;
    try {
        int courseID = Integer.parseInt(courseIdText);
        String courseName = courseNameField.getText();
        String description = CourseDescribtionField.getText();
        String code = CourseCodeField.getText();
        int credit_hours = Integer.parseInt(creditHoursText);
        String semester = semesterField.getText();

        // Check if all required data is provided
        if (courseID <= 0 || courseName.isEmpty()
                || description.isEmpty() || code.isEmpty()
                || semester.isEmpty() || credit_hours <= 0) {
            dataAlert.setText("Please complete the required data correctly.");
            return;
        }

        course = new DTOCourse(courseID, courseName, description, code, credit_hours, semester);

    } catch (NumberFormatException e) {
        // Handle the case where the input is not a valid integer
        dataAlert.setText("Invalid Course ID or Credit Hours. Please enter valid integers.");
        return;
    }

    DAOCourse dAOCourse = new DAOCourse();
    
    // Check if the course exists before updating it
    if (!dAOCourse.checkCourseExistence(course.getCourse_id())) {
        String e = "<html><body style='font-family: Arial; font-size: 12px;'>Course with ID " + course.getCourse_id() + " does not exist</body></html>";
        JOptionPane.showMessageDialog(null, e, "Success", JOptionPane.INFORMATION_MESSAGE);        return;
    }

    int res = dAOCourse.updateCourse(course);
    if (res > 0) {
        String e = "<html><body style='font-family: Arial; font-size: 12px;'>Course updated Successfully</body></html>";
        JOptionPane.showMessageDialog(null, e, "Success", JOptionPane.INFORMATION_MESSAGE);
        System.out.println("Course updated successfully!!");
        //dataAlert.setText("Course updated Successfully!!");

        // Clear all text fields
        clearTextFields();
    } else {
        System.out.println("Course cannot be updated!!");
    }
}


    

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }
    
}
