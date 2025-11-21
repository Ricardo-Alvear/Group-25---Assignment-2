package org.example.assignment2;

import javafx.application.Application;
//import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

//import java.util.Objects;

public class HelloApplication extends Application {

    private final Label lblName = new Label("Name:");
    private final TextField empName = new TextField();
    private final Label lblEmail = new Label("Email:");
    private final TextField empEmail = new TextField();
    private final Label lblPhone = new Label("Phone Number:");
    private final TextField empPhone = new TextField();
    private final Label lblDepartment = new Label("Department:");
    private final TextField empDepartment = new TextField();
    private final Label lblSalary = new Label("Salary:");
    private final TextField empSalary = new TextField();
    private final Label lblPosition = new Label("Position:");
    private final TextField empPosition = new TextField();

    private final Button btnCreateEmployee = new Button("Create Employee");
    private final Button btnUpdateEmployee = new Button("Update Employee");
    private final Button btnDeleteEmployee = new Button("Delete Employee");
    private final Button btnNextEmployee = new Button("Next Employee");
    private final Button btnPrevEmployee = new Button("Previous Employee");

    @Override
    public void start(Stage stage)  {
//        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));

        stage.setTitle("Fun Time's HR Management and Payroll Processing!");

        // Main layout - VBox then use HBox for fields/buttons
        VBox rootBox = new VBox(10);
        rootBox.setPadding(new Insets(10,10,10,10));

        // row for emp first and last name
        HBox rowName = new HBox(10);
        rowName.setStyle("-fx-background-color: lightblue;");
        rowName.setPadding(new Insets(10,10,10,10));
        rowName.getChildren().addAll(lblName,empName);

        HBox rowEmail = new HBox(10);
        rowEmail.setStyle("-fx-background-color: lightgray;");
        rowEmail.setPadding(new Insets(10,10,10,10));
        rowEmail.getChildren().addAll(lblEmail,empEmail);

        HBox rowPhone = new HBox(10);
        rowPhone.setStyle("-fx-background-color: lightblue;");
        rowPhone.setPadding(new Insets(10,10,10,10));
        rowPhone.getChildren().addAll(lblPhone,empPhone);


        HBox rowDepartment = new HBox(10);
        rowDepartment.setStyle("-fx-background-color: lightgray;");
        rowDepartment.setPadding(new Insets(10,10,10,10));
        rowDepartment.getChildren().addAll(lblDepartment, empDepartment);

        HBox rowSalary = new HBox(10);
        rowSalary.setStyle("-fx-background-color: lightblue;");
        rowSalary.setPadding(new Insets(10,10,10,10));
        rowSalary.getChildren().addAll(lblSalary,empSalary);

        HBox rowPosition = new HBox(10);
        rowPosition.setStyle("-fx-background-color: lightgray;");
        rowPosition.setPadding(new Insets(10,10,10,10));
        rowPosition.getChildren().addAll(lblPosition, empPosition);

        HBox rowButtons = new HBox(10);
        rowButtons.setStyle("-fx-background-color: lightyellow;");
        rowButtons.setPadding(new Insets(10,10,10,10));
        rowButtons.setAlignment(Pos.CENTER);
        rowButtons.getChildren().addAll(btnPrevEmployee, btnCreateEmployee, btnUpdateEmployee, btnDeleteEmployee, btnNextEmployee);



        // Add HBoxes to each
        rootBox.getChildren().addAll(rowName, rowEmail, rowPhone,  rowDepartment, rowSalary,  rowPosition,
                rowButtons);

        Scene scene = new Scene(rootBox,800, 600);

        // FIXME not working correctly yet
//        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("style.css")).toExternalForm());

        stage.setScene(scene);
        stage.show();
    }
}
