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

public class HelloApplication extends Application {

    private Label lblFirstName = new Label("First Name:");
    private Label lblLastName = new Label("Last Name:");
    private TextField empFirstName = new TextField();
    private TextField empLastName = new TextField();


    @Override
    public void start(Stage stage) throws IOException {
//        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));

        stage.setTitle("Fun Time's HR Management and Payroll Processing!");

        // Main layout - VBox then use HBox for fields/buttons
        VBox rootBox = new VBox(10);
        rootBox.setPadding(new Insets(10,10,10,10));

        // row for emp first and last name
        HBox rowName = new HBox(10);
        rowName.setPadding(new Insets(10,10,10,10));
        rowName.getChildren().addAll(lblFirstName,empFirstName,lblLastName,empLastName);

        // Add HBoxes to each
        rootBox.getChildren().addAll(rowName);

        Scene scene = new Scene(rootBox,600, 400);
        stage.setScene(scene);
        stage.show();
    }
}
