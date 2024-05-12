package university.home;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;

public abstract class homeBase extends BorderPane {

    protected final AnchorPane anchorPane;
    protected final ImageView imageView;
    protected final ImageView imageView0;
    protected final Button studentsBttn;
    protected final Button professorBttn;
    protected final Button departmentBttn;
    protected final Button courseBttn;
    protected final Button reportBttn;
    protected final ImageView imageView1;
    protected final ImageView imageView2;
    protected final ImageView imageView3;
    protected final ImageView imageView4;
    protected final ImageView imageView5;
    protected final AnchorPane anchorPane0;
    protected final Label label;
    protected final ImageView imageView6;
    protected final ImageView imageView7;
    protected final Label label0;
    protected final Label label1;

    public homeBase() {

        anchorPane = new AnchorPane();
        imageView = new ImageView();
        imageView0 = new ImageView();
        studentsBttn = new Button();
        professorBttn = new Button();
        departmentBttn = new Button();
        courseBttn = new Button();
        reportBttn = new Button();
        imageView1 = new ImageView();
        imageView2 = new ImageView();
        imageView3 = new ImageView();
        imageView4 = new ImageView();
        imageView5 = new ImageView();
        anchorPane0 = new AnchorPane();
        label = new Label();
        imageView6 = new ImageView();
        imageView7 = new ImageView();
        label0 = new Label();
        label1 = new Label();

        setMaxHeight(USE_PREF_SIZE);
        setMaxWidth(USE_PREF_SIZE);
        setMinHeight(USE_PREF_SIZE);
        setMinWidth(USE_PREF_SIZE);
        setPrefHeight(650.0);
        setPrefWidth(850.0);

        BorderPane.setAlignment(anchorPane, javafx.geometry.Pos.CENTER);
        anchorPane.setPrefHeight(650.0);
        anchorPane.setPrefWidth(214.0);
        anchorPane.setStyle("-fx-background-color: #750B53;");

        imageView.setFitHeight(80.0);
        imageView.setFitWidth(92.0);
        imageView.setLayoutX(69.0);
        imageView.setLayoutY(73.0);
        imageView.setPickOnBounds(true);
        imageView.setPreserveRatio(true);
        imageView.setImage(new Image(getClass().getResource("resources/icons8-a-64.png").toExternalForm()));

        imageView0.setFitHeight(82.0);
        imageView0.setFitWidth(68.0);
        imageView0.setLayoutX(31.0);
        imageView0.setLayoutY(37.0);
        imageView0.setPickOnBounds(true);
        imageView0.setPreserveRatio(true);
        imageView0.setImage(new Image(getClass().getResource("resources/icons8-university-100.png").toExternalForm()));

        studentsBttn.setLayoutX(1.0);
        studentsBttn.setLayoutY(210.0);
        studentsBttn.setMnemonicParsing(false);
        studentsBttn.setOnAction(this::viewStudents);
        studentsBttn.setPrefHeight(68.0);
        studentsBttn.setPrefWidth(225.0);
        studentsBttn.setStyle("-fx-background-color: transparent;");
        studentsBttn.setText("Students");
        studentsBttn.setTextFill(javafx.scene.paint.Color.WHITE);
        studentsBttn.setFont(new Font("Bodoni MT Bold", 28.0));

        professorBttn.setLayoutX(-1.0);
        professorBttn.setLayoutY(296.0);
        professorBttn.setMnemonicParsing(false);
        professorBttn.setOnAction(this::viewProfessor);
        professorBttn.setPrefHeight(69.0);
        professorBttn.setPrefWidth(227.0);
        professorBttn.setStyle("-fx-background-color: transparenr;");
        professorBttn.setText("Professors");
        professorBttn.setTextFill(javafx.scene.paint.Color.WHITE);
        professorBttn.setFont(new Font("Bodoni MT", 28.0));

        departmentBttn.setLayoutY(382.0);
        departmentBttn.setMnemonicParsing(false);
        departmentBttn.setOnAction(this::viewDepartment);
        departmentBttn.setPrefHeight(69.0);
        departmentBttn.setPrefWidth(227.0);
        departmentBttn.setStyle("-fx-background-color: transparent;");
        departmentBttn.setText("Departments");
        departmentBttn.setTextFill(javafx.scene.paint.Color.WHITE);
        departmentBttn.setFont(new Font("Bodoni MT Bold", 28.0));

        courseBttn.setLayoutY(463.0);
        courseBttn.setMnemonicParsing(false);
        courseBttn.setOnAction(this::viewCourse);
        courseBttn.setPrefHeight(69.0);
        courseBttn.setPrefWidth(227.0);
        courseBttn.setStyle("-fx-background-color: transparent;");
        courseBttn.setText("courses");
        courseBttn.setTextFill(javafx.scene.paint.Color.WHITE);
        courseBttn.setFont(new Font("Bodoni MT Bold", 28.0));

        reportBttn.setLayoutX(2.0);
        reportBttn.setLayoutY(540.0);
        reportBttn.setMnemonicParsing(false);
        reportBttn.setOnAction(this::viewReport);
        reportBttn.setPrefHeight(69.0);
        reportBttn.setPrefWidth(227.0);
        reportBttn.setStyle("-fx-background-color: transparent;");
        reportBttn.setText("Report");
        reportBttn.setTextFill(javafx.scene.paint.Color.WHITE);
        reportBttn.setFont(new Font("Bodoni MT Bold", 28.0));

        imageView1.setFitHeight(42.0);
        imageView1.setFitWidth(34.0);
        imageView1.setLayoutX(18.0);
        imageView1.setLayoutY(228.0);
        imageView1.setPickOnBounds(true);
        imageView1.setPreserveRatio(true);
        imageView1.setImage(new Image(getClass().getResource("resources/icons8-student-64.png").toExternalForm()));

        imageView2.setFitHeight(34.0);
        imageView2.setFitWidth(33.0);
        imageView2.setLayoutX(13.0);
        imageView2.setLayoutY(314.0);
        imageView2.setPickOnBounds(true);
        imageView2.setPreserveRatio(true);
        imageView2.setImage(new Image(getClass().getResource("resources/icons8-school-director-64.png").toExternalForm()));

        imageView3.setFitHeight(34.0);
        imageView3.setFitWidth(33.0);
        imageView3.setLayoutX(2.0);
        imageView3.setLayoutY(401.0);
        imageView3.setPickOnBounds(true);
        imageView3.setPreserveRatio(true);
        imageView3.setImage(new Image(getClass().getResource("resources/icons8-department-100.png").toExternalForm()));

        imageView4.setFitHeight(34.0);
        imageView4.setFitWidth(33.0);
        imageView4.setLayoutX(17.0);
        imageView4.setLayoutY(481.0);
        imageView4.setPickOnBounds(true);
        imageView4.setPreserveRatio(true);
        imageView4.setImage(new Image(getClass().getResource("resources/icons8-course-100.png").toExternalForm()));

        imageView5.setFitHeight(34.0);
        imageView5.setFitWidth(33.0);
        imageView5.setLayoutX(29.0);
        imageView5.setLayoutY(558.0);
        imageView5.setPickOnBounds(true);
        imageView5.setPreserveRatio(true);
        imageView5.setImage(new Image(getClass().getResource("resources/icons8-analyze-100.png").toExternalForm()));
        setLeft(anchorPane);

        BorderPane.setAlignment(anchorPane0, javafx.geometry.Pos.CENTER);
        anchorPane0.setPrefHeight(603.0);
        anchorPane0.setPrefWidth(891.0);
        anchorPane0.setStyle("-fx-background-color: #ffffff;");

        label.setLayoutX(113.0);
        label.setLayoutY(65.0);
        label.setText("Alexa University");
        label.setTextFill(javafx.scene.paint.Color.valueOf("#167c76"));
        label.setFont(new Font("Bodoni MT Bold", 64.0));

        imageView6.setFitHeight(321.0);
        imageView6.setFitWidth(614.0);
        imageView6.setLayoutX(2.0);
        imageView6.setLayoutY(325.0);
        imageView6.setPickOnBounds(true);
        imageView6.setPreserveRatio(true);
        imageView6.setImage(new Image(getClass().getResource("resources/high-quality-color-pencils-png-free-qwgHSS.png").toExternalForm()));

        imageView7.setFitHeight(150.0);
        imageView7.setFitWidth(200.0);
        imageView7.setLayoutX(31.0);
        imageView7.setLayoutY(171.0);
        imageView7.setPickOnBounds(true);
        imageView7.setPreserveRatio(true);
        imageView7.setImage(new Image(getClass().getResource("resources/download%20(1).jpg").toExternalForm()));

        label0.setLayoutX(139.0);
        label0.setLayoutY(140.0);
        label0.setPrefHeight(47.0);
        label0.setPrefWidth(370.0);
        label0.setText("Unleash Your Potential, Embrace Your Future ");
        label0.setTextFill(javafx.scene.paint.Color.valueOf("#444444"));
        label0.setFont(new Font("Bodoni MT Bold", 18.0));

        label1.setLayoutX(186.0);
        label1.setLayoutY(189.0);
        label1.setText(" Alexa University the future is here!!");
        label1.setFont(new Font("Bodoni MT Bold", 18.0));
        setRight(anchorPane0);

        anchorPane.getChildren().add(imageView);
        anchorPane.getChildren().add(imageView0);
        anchorPane.getChildren().add(studentsBttn);
        anchorPane.getChildren().add(professorBttn);
        anchorPane.getChildren().add(departmentBttn);
        anchorPane.getChildren().add(courseBttn);
        anchorPane.getChildren().add(reportBttn);
        anchorPane.getChildren().add(imageView1);
        anchorPane.getChildren().add(imageView2);
        anchorPane.getChildren().add(imageView3);
        anchorPane.getChildren().add(imageView4);
        anchorPane.getChildren().add(imageView5);
        anchorPane0.getChildren().add(label);
        anchorPane0.getChildren().add(imageView6);
        anchorPane0.getChildren().add(imageView7);
        anchorPane0.getChildren().add(label0);
        anchorPane0.getChildren().add(label1);

    }

    protected abstract void viewStudents(javafx.event.ActionEvent actionEvent);

    protected abstract void viewProfessor(javafx.event.ActionEvent actionEvent);

    protected abstract void viewDepartment(javafx.event.ActionEvent actionEvent);

    protected abstract void viewCourse(javafx.event.ActionEvent actionEvent);

    protected abstract void viewReport(javafx.event.ActionEvent actionEvent);

}
