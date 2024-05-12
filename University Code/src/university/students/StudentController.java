/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package university.students;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.swing.JOptionPane;
import university.AccessLayer.DAOStudent;
import static university.AccessLayer.DAOStudent.checkDept;
import static university.AccessLayer.DAOStudent.checkStudent_id;
import static university.AccessLayer.DAOStudent.checkStudentExistence;
import university.models.DTOStudent;

/**
 *
 * @author Software
 */
public class StudentController implements Initializable{
    
     @FXML
    private AnchorPane studentPane;

    @FXML
    private TextField departmentIdField;

    @FXML
    private TextField studentIdField;

    @FXML
    private TextField f_nameField;

    @FXML
    private TextField l_nameField;

    @FXML
    private TextField studentNumberField;

    @FXML
    private TextField studentEmailField;

    @FXML
    private Label GPALabel;

    @FXML
    private Button addStudentBttn;

    @FXML
    private Button removeStudentBttn;

    @FXML
    private Button displayStudentBttn;

    @FXML
    private Button updateStudentBttn;

    @FXML
    private Button clearBttn;

    @FXML
    private Button enrollmentBttn;

    @FXML
    private DatePicker datePicker;
    
     @FXML
    private Label dataAlert;
     
  @FXML
void handleAddStudent(ActionEvent event) {
    try {
        String studentIdText = studentIdField.getText();
        String deptIdText = departmentIdField.getText();
        String studentEmail = studentEmailField.getText();

        if (studentIdText.isEmpty() || deptIdText.isEmpty() || studentEmail.isEmpty()) {
            // Handle the case where student ID, department ID, or email is empty
            dataAlert.setText("Student ID, Department ID, and Email cannot be empty");
            return;
        }

        int studentID;
        try {
            studentID = Integer.parseInt(studentIdText);

            // Check if student_id already exists
            if (checkStudent_id(studentID)) {
                System.out.println("Student ID already exists");
                // Show appropriate message to the user
                String e = "<html><body style='font-family: Arial; font-size: 12px;'>This Student Is Already Exist</body></html>";
                JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
                return; // Exit the method if student already exists
            }

            // Continue with checking department ID existence
            if (!checkDept(deptIdText)) {
                System.out.println("Department ID does not exist");
                // Show appropriate message to the user
                String e = "<html><body style='font-family: Arial; font-size: 12px;'>Invalid Department ID</body></html>";
                JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
                return; // Exit the method if department ID does not exist
            }

            // Check if email contains "@" symbol
            if (!studentEmail.contains("@")) {
                System.out.println("Invalid email address");
                // Show appropriate message to the user
                String e = "<html><body style='font-family: Arial; font-size: 12px;'>Invalid Email Address</body></html>";
                JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
                return; // Exit the method if email is invalid
            }

            // Continue with adding the student if all validations pass
            String studentFirstName = f_nameField.getText();
            String studentLastName = l_nameField.getText();
            LocalDate studentDate = datePicker.getValue();
            String studentPhone = studentNumberField.getText();

            // Check if all required data is provided
            if (studentID <= 0 || studentFirstName.isEmpty()
                    || studentLastName.isEmpty() || studentPhone.isEmpty()
                    || studentDate == null) {
                dataAlert.setText("Please complete the required data correctly.");
                return;
            }

            // Create DTOStudent object
            DTOStudent student = new DTOStudent(studentID, studentFirstName, studentLastName, java.sql.Date.valueOf(studentDate), studentPhone, studentEmail, deptIdText);

            // Add the student
            DAOStudent dAOStudent = new DAOStudent();
            int res = dAOStudent.addStudent(student);

            if (res > 0) {
                System.out.println("Inserted Successfully");
                String e = "<html><body style='font-family: Arial; font-size: 12px;'>Inserted Successfully</body></html>";
                JOptionPane.showMessageDialog(null, e, "Success", JOptionPane.INFORMATION_MESSAGE);
                clearTextFields();
            } else {
                System.out.println("Failed Insert");
                String e = "<html><body style='font-family: Arial; font-size: 12px;'>Failed Insert!! Check The Information You Entered</body></html>";
                JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            // Handle the case where the input is not a valid integer
            dataAlert.setText("Invalid Student ID. Please enter a valid integer.");
        }
    } catch (SQLException ex) {
        Logger.getLogger(StudentController.class.getName()).log(Level.SEVERE, null, ex);
    }
}

// Helper method to clear all text fields
private void clearTextFields() {
    studentIdField.clear();
    f_nameField.clear();
    l_nameField.clear();
    datePicker.setValue(null);
    studentEmailField.clear();
    studentNumberField.clear();
    departmentIdField.clear();
    GPALabel.setText("");
    dataAlert.setText("");
}





    @FXML
    void handleClear(ActionEvent event) {
             clearTextFields();
    }

   @FXML
void handleDisplayStudent(ActionEvent event) throws SQLException {
    String studentIdText = studentIdField.getText();

    if (studentIdText.isEmpty()) {
        // Handle the case where student ID is empty
        dataAlert.setText("Student ID cannot be empty");
        return;
    }

    try {
        int studentID = Integer.parseInt(studentIdText);
        DAOStudent dAOStudent = new DAOStudent();

        // Check if student ID exists
        if (!checkStudentExistence(studentID)) {
            // Show appropriate message to the user
            String e = "<html><body style='font-family: Arial; font-size: 12px;'>Student ID does not exist</body></html>";
            JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
            return; // Exit the method if student ID does not exist
        }

        DTOStudent dTOStudent = dAOStudent.getStudentData(studentID);

        // Check if the student exists
        if (dTOStudent == null) {
            dataAlert.setText("Student not found with ID: " + studentID);
            return;
        }

        // Set DTOStudent values to text fields
        studentIdField.setText(String.valueOf(studentID));
        f_nameField.setText(dTOStudent.getStudentFirstName());
        l_nameField.setText(dTOStudent.getStudentLastName());
        datePicker.setValue(dTOStudent.getStudentDate().toLocalDate());
        studentEmailField.setText(dTOStudent.getUserEmail());
        studentNumberField.setText(dTOStudent.getStudentPhone());
        departmentIdField.setText(dTOStudent.getStudentDept_id());
        GPALabel.setText(String.valueOf(dTOStudent.getGPa()));

    } catch (NumberFormatException e) {
        // Handle the case where the input is not a valid integer
        dataAlert.setText("Invalid Student ID. Please enter a valid integer.");
    } catch (SQLException ex) {
        Logger.getLogger(StudentController.class.getName()).log(Level.SEVERE, null, ex);
        dataAlert.setText("Error retrieving student data.");
    }
}



    @FXML
   
void handleRemoveStudent(ActionEvent event) {
    try {
        String studentIdText = studentIdField.getText();

        if (studentIdText.isEmpty()) {
            dataAlert.setText("Please enter a student ID.");
            return;
        }

        try {
            int studentID = Integer.parseInt(studentIdText);

            // Check if student ID exists
            if (!checkStudentExistence(studentID)) {
                System.out.println("Student ID does not exist");
                // Show appropriate message to the user
                String e = "<html><body style='font-family: Arial; font-size: 12px;'>Student ID does not exist</body></html>";
                JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
                return; // Exit the method if student ID does not exist
            }

            // Ask for confirmation before removing the student
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Confirmation");
            confirmationAlert.setHeaderText(null);
            confirmationAlert.setContentText("Are you sure you want to remove the student with ID " + studentID + "?");

            Optional<ButtonType> result = confirmationAlert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                DAOStudent dAOStudent = new DAOStudent();
                int res = dAOStudent.removeStudent(studentID);
                
                if (res > 0)
                    dataAlert.setText("The selected student removed successfully.");
                else
                    dataAlert.setText("Student with ID " + studentID + " not found.");
            } else {
                dataAlert.setText("Remove operation canceled.");
            }

        } catch (NumberFormatException e) {
            dataAlert.setText("Invalid Student ID. Please enter a valid integer.");
        }

    } catch (SQLException ex) {
        dataAlert.setText("Error removing the student. Please try again.");
        ex.printStackTrace(); // Log the exception for debugging purposes
    }
}


   @FXML
void handleUpdateStudent(ActionEvent event) {
    String studentIdText = studentIdField.getText();
    if (studentIdText.isEmpty()) {
        // Handle the case where student ID is empty
        dataAlert.setText("Student ID cannot be empty");
        return;
    }
    DTOStudent student = null;
    try {
        int studentID = Integer.parseInt(studentIdText);
        String studentFirstName = f_nameField.getText();
        String studentLastName = l_nameField.getText();
        LocalDate studentDate = datePicker.getValue();
        String studentEmail = studentEmailField.getText();
        String studentPhone = studentNumberField.getText();
        String studentDept_id = departmentIdField.getText();

        // Check if all required data is provided
        if (studentID <= 0 || studentFirstName.isEmpty()
                || studentLastName.isEmpty() || studentPhone.isEmpty()
                || studentDept_id.isEmpty() || studentDate == null) {
            dataAlert.setText("Please complete the required data.");
            return;
        }

        // Check if student ID exists
        if (!checkStudentExistence(studentID)) {
            System.out.println("Student ID does not exist");
            // Show appropriate message to the user
            String e = "<html><body style='font-family: Arial; font-size: 12px;'>Student ID does not exist</body></html>";
            JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
            return; // Exit the method if student ID does not exist
        }

        // Check if department ID exists
        if (!checkDept(studentDept_id)) {
            System.out.println("Department ID does not exist");
            // Show appropriate message to the user
            String e = "<html><body style='font-family: Arial; font-size: 12px;'>Invalid Department ID</body></html>";
            JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
            return; // Exit the method if department ID does not exist
        }

        // Check if email contains "@" symbol
        if (!studentEmail.contains("@")) {
            System.out.println("Invalid email address");
            // Show appropriate message to the user
            String e = "<html><body style='font-family: Arial; font-size: 12px;'>Invalid Email Address</body></html>";
            JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
            return; // Exit the method if email is invalid
        }

        student = new DTOStudent(studentID, studentFirstName, studentLastName, java.sql.Date.valueOf(studentDate), studentPhone, studentEmail, studentDept_id);

    } catch (NumberFormatException e) {
        // Handle the case where the input is not a valid integer
        dataAlert.setText("Invalid Student ID. Please enter a valid integer.");
        return;
    }
    DAOStudent dAOStudent = new DAOStudent();
    int res = dAOStudent.updateStudent(student);
    if (res > 0) {
        System.out.println("Student updated successfully!!");
        String e = "<html><body style='font-family: Arial; font-size: 12px;'>Updated Successfully</body></html>";
                JOptionPane.showMessageDialog(null, e, "Success", JOptionPane.INFORMATION_MESSAGE);
                clearTextFields();
    } else {
        System.out.println("Student can not be updated !!");
    }
}

    @FXML
    
void viewEnrollment(ActionEvent event) {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/university/enrollment/enrollment.fxml"));
        AnchorPane enrollmentPane = loader.load();

        // Create a new stage
        Stage popupStage = new Stage();
        popupStage.setTitle("Enrollment Information");

        // Set the modality to APPLICATION_MODAL to block interactions with other windows
        popupStage.initModality(Modality.APPLICATION_MODAL);

        // Set the stage style (if needed)
        popupStage.initStyle(StageStyle.UTILITY);

        // Set the scene with the custom AnchorPane
        Scene scene = new Scene(enrollmentPane);
        popupStage.setScene(scene);

        // Show the stage
        popupStage.showAndWait();

    } catch (IOException ex) {
        ex.printStackTrace();
    }
}
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
    
    
    
    
    
}
