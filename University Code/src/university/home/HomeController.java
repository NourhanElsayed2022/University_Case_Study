package university.home;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import university.AccessLayer.DAO;
import static university.University.main;

public class HomeController implements Initializable {

    @FXML
    private Button studentsBttn;

    @FXML
    private Button professorBttn;

    @FXML
    private Button departmentBttn;

    @FXML
    private Button courseBttn;

    @FXML
    private Button reportBttn;

    @FXML
    private BorderPane parentAnchorPane; // Assuming you have an AnchorPane in your FXML file with the fx:id="parentAnchorPane"

    private Stage stage;
    private Stage scene;
    private Parent root;
    
    DAO dao;

    @FXML
    void viewCourse() throws IOException {
        System.out.println("course button clicked");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/university/courses/courses.fxml"));
        Parent root= fxmlLoader.load();
        parentAnchorPane.setCenter(root);
            }

    @FXML
    void viewDepartment() throws IOException {
        System.out.println(" department button clicked");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/university/departments/departments.fxml"));
        Parent root= fxmlLoader.load();
        parentAnchorPane.setCenter(root);
            }

    @FXML
    void viewProfessor() throws IOException {
        
        System.out.println("professor button clicked");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/university/professors/professors.fxml"));
        Parent root= fxmlLoader.load();
        parentAnchorPane.setCenter(root);
            }

    @FXML
    void viewReport() throws IOException {
         System.out.println(" Report button clicked");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/university/report/report.fxml"));
        Parent root= fxmlLoader.load();
        parentAnchorPane.setCenter(root);
    }

    @FXML
    void viewStudents() throws IOException {
        System.out.println(" student button clicked");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/university/students/students.fxml"));
        Parent root= fxmlLoader.load();
        parentAnchorPane.setCenter(root);
        
    }

    

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialization code, if any
       dao = new DAO();
    }
}
