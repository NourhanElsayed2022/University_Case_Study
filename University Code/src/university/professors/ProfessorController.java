/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package university.professors;

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
import university.AccessLayer.DAO;
import university.AccessLayer.DAOProfessor;
import static university.AccessLayer.DAOProfessor.checkProfessorIDExistence;
import university.AccessLayer.DAOStudent;
import university.models.DTOProfessor;
import university.models.DTOStudent;
import university.students.StudentController;

/**
 *
 * @author Software
 */
public class ProfessorController implements Initializable{
    
    @FXML
    private AnchorPane professorPane;

    @FXML
    private TextField professorIDField;

    @FXML
    private TextField fNamsField;

    @FXML
    private TextField lNameField;

    @FXML
    private TextField profPhoneField;

    @FXML
    private TextField profEmailField;

    @FXML
    private TextField salaryField;

    @FXML
    private Button addProfBttn;

    @FXML
    private Button removeProfBttn;

    @FXML
    private Button displayProfBttn;

    @FXML
    private Button clearBttn;

    @FXML
    private Button updateProfBttn;

    @FXML
    private Button assignCourseBttn;
    
    @FXML
    private Label dataAlert;
    

    @FXML
void handleAddProf(ActionEvent event) {
    try {
        String professorIdText = professorIDField.getText();
        String salaryText = salaryField.getText();

        if (professorIdText.isEmpty()) {
            // Handle the case where professor ID is empty
            dataAlert.setText("Professor ID cannot be empty");
            return;
        }

        int professorID;
        try {
            professorID = Integer.parseInt(professorIdText);

            // Check if professor_id already exists
            if (checkProfessorIDExistence(professorID)) {
                System.out.println("Professor ID already exists");
                // Show appropriate message to the user
                String e = "<html><body style='font-family: Arial; font-size: 12px;'>This Professor ID already exists</body></html>";
                JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
                return; // Exit the method if professor ID already exists
            }

            // Continue with adding the professor if professor ID does not exist
            String profFirstName = fNamsField.getText();
            String profLastName = lNameField.getText();
            String profPhone = profPhoneField.getText();
            int profSalary = Integer.parseInt(salaryText);
            String profEmail = profEmailField.getText();

            // Check if email contains "@" symbol
            if (!profEmail.contains("@")) {
                System.out.println("Invalid email address");
                // Show appropriate message to the user
                String e = "<html><body style='font-family: Arial; font-size: 12px;'>Invalid Email Address</body></html>";
                JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
                return; // Exit the method if email is invalid
            }

            // Check if all required data is provided
            if (professorID <= 0 || profFirstName.isEmpty()
                    || profLastName.isEmpty() || profPhone.isEmpty()
                    || profSalary <= 0 || profEmail.isEmpty()) {
                dataAlert.setText("Please complete the required data correctly.");
                return;
            }

            // Create DTOProfessor object
            DTOProfessor professor = new DTOProfessor(professorID, profFirstName, profLastName, profPhone, profSalary, profEmail);

            // Add the professor
            DAOProfessor dAOProf = new DAOProfessor();
            int res = dAOProf.addProfessor(professor);

            if (res > 0) {
                System.out.println("Professor inserted successfully!!");
                String e = "<html><body style='font-family: Arial; font-size: 12px;'>Professor Added Successfully!!</body></html>";
                JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
                dataAlert.setText("Professor Added Successfully!!");
                // Clear all text fields
                clearTextFields();
            } else {
                System.out.println("Professor is not inserted successfully!!");
            }
        } catch (NumberFormatException e) {
            // Handle the case where the input is not a valid integer
            dataAlert.setText("Please complete the data correctly.");
        }
    } catch (SQLException ex) {
        Logger.getLogger(StudentController.class.getName()).log(Level.SEVERE, null, ex);
    }
}

    
    // Helper method to clear all text fields
private void clearTextFields() {
    professorIDField.clear();
    fNamsField.clear();
    lNameField.clear();
    profPhoneField.clear();
    profEmailField.clear();
    salaryField.clear();
    dataAlert.setText("");
}


    @FXML
    void handleAssignCourse(ActionEvent event) {
        
        try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/university/prof_courses/prof_courses.fxml"));
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

    @FXML
    void handleClearBttn(ActionEvent event) {
              clearTextFields();
    }

    @FXML
void handleDisplayProf(ActionEvent event) {
    String professorIdText = professorIDField.getText();

    if (professorIdText.isEmpty()) {
        // Handle the case where professor ID is empty
        dataAlert.setText("Professor ID cannot be empty");
        return;
    }

    try {
        int profID = Integer.parseInt(professorIdText);

        // Check if professor ID exists
        if (!checkProfessorIDExistence(profID)) {
            System.out.println("Professor ID does not exist");
            // Show appropriate message to the user
            String e = "<html><body style='font-family: Arial; font-size: 12px;'>This Professor does not exist</body></html>";
            JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
            return; // Exit the method if professor ID does not exist
        }

        DAOProfessor dAOProfessor = new DAOProfessor();
        DTOProfessor dTOProfessor = dAOProfessor.getProfessorData(profID);

        // Check if the professor exists
        if (dTOProfessor == null) {
            dataAlert.setText("Professor not found with ID: " + profID);
            return;
        }

        // Set DTOProfessor values to text fields
        professorIDField.setText(String.valueOf(profID));
        fNamsField.setText(dTOProfessor.getfName());
        lNameField.setText(dTOProfessor.getlName());
        profPhoneField.setText(dTOProfessor.getPhone());
        salaryField.setText(String.valueOf(dTOProfessor.getSalary()));
        profEmailField.setText(dTOProfessor.getEmail());

    } catch (NumberFormatException e) {
        // Handle the case where the input is not a valid integer
        dataAlert.setText("Invalid Professor ID. Please enter a valid integer.");
    } catch (SQLException ex) {
        Logger.getLogger(StudentController.class.getName()).log(Level.SEVERE, null, ex);
        dataAlert.setText("Error retrieving Professor data.");
    }
}


   @FXML
void handleRemoveProf(ActionEvent event) {
    try {
        String professorIdText = professorIDField.getText();

        if (professorIdText.isEmpty()) {
            dataAlert.setText("Please enter a Professor ID.");
            return;
        }

        try {
            int professorID = Integer.parseInt(professorIdText);

            // Check if professor ID exists
            if (!checkProfessorIDExistence(professorID)) {
                System.out.println("Professor ID does not exist");
                // Show appropriate message to the user
                String e = "<html><body style='font-family: Arial; font-size: 12px;'>This Professor does not exist</body></html>";
                JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
                return; // Exit the method if professor ID does not exist
            }

            // Ask for confirmation before removing the professor
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Confirmation");
            confirmationAlert.setHeaderText(null);
            confirmationAlert.setContentText("Are you sure you want to remove the professor with ID " + professorID + "?");

            Optional<ButtonType> result = confirmationAlert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                DAOProfessor dAOProfessor = new DAOProfessor();
                int res = dAOProfessor.removeProfessor(professorID);
                
                if (res > 0){
                    dataAlert.setText("The selected Professor removed successfully.");
                    clearTextFields();
                }
                else
                    dataAlert.setText("Professor with ID " + professorID + " not found.");
            } else {
                dataAlert.setText("Remove operation canceled.");
            }

        } catch (NumberFormatException e) {
            dataAlert.setText("Invalid Professor ID. Please enter a valid integer.");
        }

    } catch (SQLException ex) {
        dataAlert.setText("Error removing the Professor. Please try again.");
        ex.printStackTrace(); // Log the exception for debugging purposes
    }
}


    @FXML
void handleUpdateProf(ActionEvent event) {
    String professorIdText = professorIDField.getText();
    String salaryText = salaryField.getText();
    
    if (professorIdText.isEmpty()) {
        // Handle the case where professor ID is empty
        dataAlert.setText("Professor ID cannot be empty");
        return;
    }
    
    DTOProfessor professor = null;
    try {
        int profID = Integer.parseInt(professorIdText);
        String profFirstName = fNamsField.getText();
        String profLastName = lNameField.getText();
        String profPhone = profPhoneField.getText();
        int profSalary = Integer.parseInt(salaryText);
        String profEmail = profEmailField.getText();

        // Check if professor ID exists
        if (!checkProfessorIDExistence(profID)) {
            System.out.println("Professor ID does not exist");
            // Show appropriate message to the user
            String e = "<html><body style='font-family: Arial; font-size: 12px;'>This sProfessor does not exist</body></html>";
            JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
            return; // Exit the method if professor ID does not exist
        }

        // Check if email contains "@" symbol
        if (!profEmail.contains("@")) {
            System.out.println("Invalid email address");
            // Show appropriate message to the user
            String e = "<html><body style='font-family: Arial; font-size: 12px;'>Invalid Email Address</body></html>";
            JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
            return; // Exit the method if email is invalid
        }

        // Check if all required data is provided
        if (profID <= 0 || profFirstName.isEmpty()
                || profLastName.isEmpty() || profPhone.isEmpty()
                || profSalary <= 0 || profEmail.isEmpty()) {
            dataAlert.setText("Please complete the required data.");
            return;
        }

        professor = new DTOProfessor(profID, profFirstName, profLastName, profPhone, profSalary, profEmail);
    } catch (NumberFormatException e) {
        // Handle the case where the input is not a valid integer
        dataAlert.setText("Invalid Professor ID or Salary. Please enter valid integers.");
        return;
    }

    DAOProfessor dAOProfessor = new DAOProfessor();
    int res = dAOProfessor.updateProfessor(professor);
    if (res > 0) {
        System.out.println("Professor updated successfully!!");
        JOptionPane.showMessageDialog(null, "Professor updated Successfully!!", "Error", JOptionPane.ERROR_MESSAGE);
        dataAlert.setText("Professor updated Successfully!!");

        // Clear all text fields
        clearTextFields();
    } else {
        System.out.println("Professor is not updated successfully!!");
    }
}


    
    
    

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        DAO dao = new DAO();
    }
    
}
