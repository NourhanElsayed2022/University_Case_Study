/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package university.departments;

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
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.swing.JOptionPane;
import university.AccessLayer.DAODepartment;
import static university.AccessLayer.DAODepartment.checkProfessorIDExistence;
import university.AccessLayer.DAOStudent;
import static university.AccessLayer.DAOStudent.checkDept;
import university.models.DTODepartment;
import university.models.DTOStudent;
import university.students.StudentController;

/**
 *
 * @author Software
 */
public class DepartmentController implements Initializable{
    
     @FXML
    private AnchorPane departmentPane;

    @FXML
    private TextField departmentIdField;

    @FXML
    private TextField departmentNameField;

    @FXML
    private TextField supercisorIdField;

    @FXML
    private Button addDeptBttn;

    @FXML
    private Button removeDeptBttn;

    @FXML
    private Button displayDeptBttn;

    @FXML
    private Button clearBttn;

    @FXML
    private Button updateDeptBttn;

    @FXML
    private Button addCourseBttn;
    
    @FXML
    private Label dataAlert;

    @FXML
    void handleAddCourses(ActionEvent event) {
        
          try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/university/dept_courses/dept_courses.fxml"));
        AnchorPane enrollmentPane = loader.load();

        // Create a new stage
        Stage popupStage = new Stage();
        popupStage.setTitle("Department Information");

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

   @FXML
void handleAddDept(ActionEvent event) {
    try {
        String deptIdText = departmentIdField.getText();
        String supervisorIdText = supercisorIdField.getText();

        if (deptIdText.isEmpty()) {
            // Handle the case where department ID is empty
            dataAlert.setText("Department ID cannot be empty");
            return;
        }

        DTODepartment dept = null;

        String deptName = departmentNameField.getText();
        int supervisorID;
        try {
            supervisorID = Integer.parseInt(supervisorIdText);
        } catch (NumberFormatException e) {
            // Handle the case where the supervisor ID is not a valid integer
            dataAlert.setText("Please complete tha data Correctly.");
            return;
        }

        // Check if department ID already exists
        if ( checkDept(deptIdText)) {
            System.out.println("Department ID does not exist");
            // Show appropriate message to the user
            String e = "<html><body style='font-family: Arial; font-size: 12px;'>This Department already Exist!!</body></html>";
            JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
            return; // Exit the method if department ID does not exist
        }

        // Check if supervisor ID already exists
        if (!checkProfessorIDExistence(supervisorID)) {
            System.out.println("Supervisor ID does not exist");
            // Show appropriate message to the user
            String e = "<html><body style='font-family: Arial; font-size: 12px;'>Invalid Supervisor ID</body></html>";
            JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
            return; // Exit the method if supervisor ID does not exist
        }

        // Check if all required data is provided
        if (deptIdText.isEmpty() || deptName.isEmpty() || supervisorID <= 0) {
            dataAlert.setText("Please complete the required data.");
            return;
        }

        dept = new DTODepartment(deptIdText, deptName, supervisorID);

        DAODepartment dAODepartment = new DAODepartment();

        int res = dAODepartment.addDepartment(dept);

        if (res > 0) {
            System.out.println("Department inserted successfully!!");
            String e = "<html><body style='font-family: Arial; font-size: 12px;'>Department Added Successfully!!</body></html>";
            JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
           // dataAlert.setText("Department Added Successfully!!");

            // Clear all text fields
            clearTextFields();
        } else {
            System.out.println("Department is not inserted!!");
        }

    } catch (SQLException ex) {
        Logger.getLogger(StudentController.class.getName()).log(Level.SEVERE, null, ex);
    }
}


// Helper method to clear all text fields
private void clearTextFields() {
   
    departmentIdField.clear();
    departmentNameField.clear();
    supercisorIdField.clear();
    dataAlert.setText("");
}


        

    

    @FXML
    void handleClearBttn(ActionEvent event) {
                  clearTextFields();
    }

    @FXML
void handleDisplayDept(ActionEvent event) {
    String dept_id = departmentIdField.getText();

    if (dept_id.isEmpty()) {
        // Handle the case where department ID is empty
        dataAlert.setText("Department ID cannot be empty");
        return;
    }

    // Check if the department ID exists
    if (!checkDept(dept_id)) {
        String e = "<html><body style='font-family: Arial; font-size: 12px;'>This Department does not exist.</body></html>";
        JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    //int superID = Integer.parseInt(supervisorIdText);
    DAODepartment dAODepartment = new DAODepartment();

    try {
        DTODepartment dTODepartment = dAODepartment.getDepartmentData(dept_id);

        // Check if the department exists
        if (dTODepartment == null) {
            dataAlert.setText("Department not found with ID: " + dept_id);
            return;
        }

        // Set DTOStudent values to text fields
        departmentIdField.setText(dTODepartment.getDept_id());
        departmentNameField.setText(dTODepartment.getDept_name());
        supercisorIdField.setText(String.valueOf(dTODepartment.getSupervisor_id()));
        
    } catch (NumberFormatException e) {
        // Handle the case where the input is not a valid integer
        dataAlert.setText("Invalid Supervisor ID. Please enter a valid integer.");
    } catch (SQLException ex) {
        Logger.getLogger(StudentController.class.getName()).log(Level.SEVERE, null, ex);
        dataAlert.setText("Error retrieving Department data.");
    }
}


   @FXML
void handleRemoveDept(ActionEvent event) {
    try {
        String dept_id = departmentIdField.getText();

        if (dept_id.isEmpty()) {
            // Handle the case where department ID is empty
            dataAlert.setText("Department ID cannot be empty");
            return;
        }

        // Check if the department ID exists
        if (!checkDept(dept_id)) {
            String e = "<html><body style='font-family: Arial; font-size: 12px;'>This Department does not exist.</body></html>";
            JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Ask for confirmation before removing the department
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation");
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText("Are you sure you want to remove the Department with ID " + dept_id + "?");

        Optional<ButtonType> result = confirmationAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            DAODepartment dAODepartment = new DAODepartment();
            int res = dAODepartment.removeDepartment(dept_id);

            if (res > 0)
                JOptionPane.showMessageDialog(null, "The selected Department removed successfully.", "Error", JOptionPane.ERROR_MESSAGE);
            else
                dataAlert.setText("Department with ID " + dept_id + " not found.");
        } else {
            dataAlert.setText("Remove operation canceled.");
        }
    } catch (SQLException ex) {
        dataAlert.setText("Error removing the department. Please try again.");
        ex.printStackTrace(); // Log the exception for debugging purposes
    }
}

    
    
@FXML
void handleUpdateDept(ActionEvent event) {
    int superID;
    String superIdText = supercisorIdField.getText();
    
    if (superIdText.isEmpty()) {
        // Handle the case where Supervisor ID is empty
        dataAlert.setText("Supervisor ID cannot be empty");
        return;
    }

    try {
        superID = Integer.parseInt(superIdText);
    } catch (NumberFormatException e) {
        // Handle the case where the input is not a valid integer
        dataAlert.setText("Invalid Supervisor ID. Please enter a valid integer.");
        return;
    }

    DTODepartment dept = null;
    try {
        String dept_id = departmentIdField.getText();
        String dept_name = departmentNameField.getText();

        // Check if all required data is provided
        if (superID <= 0 || dept_id.isEmpty() || dept_name.isEmpty()) {
            dataAlert.setText("Please complete the required data.");
            return;
        }

        // Check if the department ID exists
if (!checkDept(dept_id)) {
    String e = "<html><body style='font-family: Arial; font-size: 12px;'>This Department does not exist.</body></html>";
    JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
    return;
}

// Check if the supervisor ID exists
if (!checkProfessorIDExistence(superID)) {
    String e = "<html><body style='font-family: Arial; font-size: 12px;'>Supervisor ID does not exist.</body></html>";
    JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
    return;
}

        dept = new DTODepartment(dept_id, dept_name, superID);
    } catch (NumberFormatException e) {
        // Handle the case where the input is not a valid integer
        dataAlert.setText("Invalid Supervisor ID. Please enter a valid integer.");
        return;
    }

    DAODepartment dAODepartment = new DAODepartment();
    int res = dAODepartment.updateDepartment(dept);
    
    if (res > 0) {
        
        System.out.println("Department updated successfully!!");
        JOptionPane.showMessageDialog(null, "Department updated Successfully!!", "Error", JOptionPane.ERROR_MESSAGE);
     //   dataAlert.setText("Department updated Successfully!!");
        // Clear all text fields
        clearTextFields();
    } else {
        System.out.println("Department cannot be updated!!");
    }
}


    

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        
    }
    
}
