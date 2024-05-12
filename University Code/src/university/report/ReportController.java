/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package university.report;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.swing.JOptionPane;
import university.AccessLayer.DAOReport;
import university.models.DTOReport;

/**
 *
 * @author Software
 */
public class ReportController implements Initializable {

    @FXML
    private TableView<DTOReport> reportTable;

    @FXML
    private TableColumn<DTOReport, String> codeCol;

    @FXML
    private TableColumn<DTOReport, String> courseNameCol;

    @FXML
    private TableColumn<DTOReport, String> studentCol;

    @FXML
    private TableColumn<DTOReport, String> StudentGradeCol;

    @FXML
    private TableColumn<DTOReport, Integer> courseAvgCol;

    @FXML
    private TextField searchField;

    @FXML
    private Button searchBttn;

    @FXML
    void handleSearchBttn(ActionEvent event) throws SQLException {

        String courseName = searchField.getText();

        DAOReport daoReport = new DAOReport();
        List<DTOReport> reportDataList;

        // Assuming getReportData returns a List<DTOReport>
        if (searchField.getText().isEmpty()) {
            String e = "<html><body style='font-family: Arial; font-size: 12px;'>Course Name field is necessary to be filled</body></html>";
            JOptionPane.showMessageDialog(null, e);
        } else {
            reportDataList = daoReport.getReportData(courseName);
            // Convert the List to ObservableList
            ObservableList<DTOReport> reportData = FXCollections.observableArrayList(reportDataList);
            if (reportDataList.isEmpty()) {
                System.out.println("Does not exist");
                String e = "<html><body style='font-family: Arial; font-size: 12px;'> This Course Is Not Found!!</body></html>";
                JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                // Set the items to the TableView
                reportTable.setItems(reportData);
            }

        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        codeCol.setCellValueFactory(new PropertyValueFactory<>("code"));
        courseNameCol.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        studentCol.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        StudentGradeCol.setCellValueFactory(new PropertyValueFactory<>("studentGrade"));
        courseAvgCol.setCellValueFactory(new PropertyValueFactory<>("avg_gpa"));
    }

}
