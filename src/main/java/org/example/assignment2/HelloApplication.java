package org.example.assignment2;

import javafx.application.Application;
//import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class HelloApplication extends Application {

    private Label lblName = new Label("Name:");
    private TextField empName = new TextField();
    private Label lblEmail = new Label("Email:");
    private TextField empEmail = new TextField();
    private Label lblPhone = new Label("Phone Number:");
    private TextField empPhone = new TextField();
    private Label lblDepartment = new Label("Department:");
    private TextField empDepartment = new TextField();
    private Label lblSalary = new Label("Salary:");
    private TextField empSalary = new TextField();
    private Label lblPosition = new Label("Position:");
    private TextField empPosition = new TextField();



    @Override
    public void start(Stage stage) throws IOException {
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

        HBox rowPhone = new HBox(10);
        rowPhone.setStyle("-fx-background-color: lightgray;");
        rowPhone.setPadding(new Insets(10,10,10,10));
        rowPhone.getChildren().addAll(lblPhone,empPhone,lblDepartment,lblSalary,lblPosition);

        HBox rowDepartment = new HBox(10);
        rowDepartment.setStyle("-fx-background-color: lightblue;");
        rowDepartment.setPadding(new Insets(10,10,10,10));
        rowDepartment.getChildren().addAll(lblDepartment, empDepartment);

        HBox rowSalary = new HBox(10);
        rowSalary.setStyle("-fx-background-color: lightgray;");
        rowSalary.setPadding(new Insets(10,10,10,10));
        rowSalary.getChildren().addAll(lblSalary,empSalary);

        HBox rowPosition = new HBox(10);
        rowPosition.setStyle("-fx-background-color: lightblue;");
        rowPosition.setPadding(new Insets(10,10,10,10));
        rowPosition.getChildren().addAll(lblPosition, empPosition);

        HBox rowButtons = new HBox(10);
        rowButtons.setStyle("-fx-background-color: lightyellow;");
        rowButtons.setPadding(new Insets(10,10,10,10));


        // Add HBoxes to each
        rootBox.getChildren().addAll(rowName, rowPhone,  rowDepartment, rowSalary,  rowPosition);

        Scene scene = new Scene(rootBox,800, 600);

        // FIXME not working correctly yet
//        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("style.css")).toExternalForm());

        stage.setScene(scene);
        stage.show();
    }
}
