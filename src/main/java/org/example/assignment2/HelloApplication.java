package org.example.assignment2;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import javafx.application.Application;
//import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

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

    private final TextArea txtPayrollResults = new TextArea();

    private final Button btnCalculateSalaries = new Button("Calculate Salaries");
    private final Button btnCalculateTaxes = new Button("Calculate Taxes");
    private final Button btnCalculateDeductions = new Button("Calculate Deductions");

    private final TextArea txtReportingResults = new TextArea();

    private final Button btnCalculateEmpReport = new Button("Calculate Employee Report");
    private final Button btnCalculateDeptReport = new Button("Calculate Department Report");


    private final Accordion  accordion = new Accordion();

    private ArrayList<Employee> employeeList;
    private Employee currentEmployee;
    private int currentEmployeeIndex;

    @Override
    public void start(Stage stage)  {
//        FXMLLoader fxmlLoader1 = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));

        employeeList = readEmployeeData();

        currentEmployee = null;
        currentEmployeeIndex = -1;

        if (employeeList != null && !employeeList.isEmpty()) {
            currentEmployee = employeeList.getFirst();
            currentEmployeeIndex = employeeList.indexOf(currentEmployee);
        }

        stage.setTitle("Fun Time's HR Management and Payroll Processing!");

        // Main layout - VBox then use HBox for fields/buttons
        VBox rootBox = new VBox(10);
        TitledPane employeesPane = new TitledPane("Employees Details", rootBox);
        rootBox.setPadding(new Insets(10,10,10,10));

        // row for emp first and last name
        HBox rowName = new HBox(10);
        rowName.setStyle("-fx-background-color: lightblue;");
        rowName.setPadding(new Insets(10,10,10,10));
        rowName.setHgrow(empName, Priority.ALWAYS);
        empName.setMaxWidth(300);
        if (currentEmployee != null) {
            empName.setText(currentEmployee.getName());
        }
        rowName.getChildren().addAll(lblName,empName);

        HBox rowEmail = new HBox(10);
        rowEmail.setStyle("-fx-background-color: #87CEEB;");
        rowEmail.setPadding(new Insets(10,10,10,10));
        rowEmail.setHgrow(empEmail, Priority.ALWAYS);
        empEmail.setMaxWidth(300);
        if (currentEmployee != null) {
            empEmail.setText(currentEmployee.getEmail());
        }
        rowEmail.getChildren().addAll(lblEmail,empEmail);

        HBox rowPhone = new HBox(10);
        rowPhone.setStyle("-fx-background-color: lightblue;");
        rowPhone.setPadding(new Insets(10,10,10,10));
        rowPhone.setHgrow(empPhone, Priority.ALWAYS);
        empPhone.setMaxWidth(300);
        if (currentEmployee != null) {
            empPhone.setText(currentEmployee.getPhone());
        }
        rowPhone.getChildren().addAll(lblPhone,empPhone);


        HBox rowDepartment = new HBox(10);
        rowDepartment.setStyle("-fx-background-color: #87CEEB;");
        rowDepartment.setPadding(new Insets(10,10,10,10));
        rowDepartment.setHgrow(empDepartment, Priority.ALWAYS);
        empDepartment.setMaxWidth(300);
        if (currentEmployee != null) {
            empDepartment.setText(currentEmployee.getDepartment());
        }
        rowDepartment.getChildren().addAll(lblDepartment, empDepartment);

        HBox rowSalary = new HBox(10);
        rowSalary.setStyle("-fx-background-color: lightblue;");
        rowSalary.setPadding(new Insets(10,10,10,10));
        rowSalary.setHgrow(empSalary, Priority.ALWAYS);
        empSalary.setMaxWidth(300);
        if (currentEmployee != null) {
            empSalary.setText(String.valueOf(currentEmployee.getSalary()));
        }
        rowSalary.getChildren().addAll(lblSalary,empSalary);

        HBox rowPosition = new HBox(10);
        rowPosition.setStyle("-fx-background-color: #87CEEB;");
        rowPosition.setPadding(new Insets(10,10,10,10));
        rowPosition.setHgrow(empPosition, Priority.ALWAYS);
        empPosition.setMaxWidth(300);
        if (currentEmployee != null) {
            empPosition.setText(currentEmployee.getPosition());
        }
        rowPosition.getChildren().addAll(lblPosition, empPosition);

        HBox rowButtons = new HBox(10);
        rowButtons.setStyle("-fx-background-color: #E5E4E2;");
        rowButtons.setPadding(new Insets(10,10,10,10));
        rowButtons.setAlignment(Pos.CENTER);
        rowButtons.getChildren().addAll(btnPrevEmployee, btnCreateEmployee, btnUpdateEmployee, btnDeleteEmployee, btnNextEmployee);


        btnCreateEmployee.setOnAction(e -> {
            System.out.println("Create Employee");

            int highestId = -1;

            for (Employee employee : employeeList) {
                if (employee.getId() > highestId) {
                    highestId = employee.getId();
                }
            }

            if (highestId == -1) {
                highestId = 0;
            }

            Employee newEmployee = new Employee(highestId + 1, empName.getText(), empEmail.getText(), empPhone.getText(), empDepartment.getText(), Double.parseDouble(empSalary.getText()), empPosition.getText());
            employeeList.add(newEmployee);

            currentEmployee = employeeList.getLast();
            currentEmployeeIndex = employeeList.indexOf(currentEmployee);

            writeEmployeeData(employeeList);
        });

        btnUpdateEmployee.setOnAction(e -> {
            System.out.println("Update Employee");

            currentEmployee.setName(empName.getText());
            currentEmployee.setEmail(empEmail.getText());
            currentEmployee.setPhone(empPhone.getText());
            currentEmployee.setDepartment(empDepartment.getText());
            currentEmployee.setSalary(Double.parseDouble(empSalary.getText()));
            currentEmployee.setPosition(empPosition.getText());

            writeEmployeeData(employeeList);
        });

        btnDeleteEmployee.setOnAction(e -> {
            System.out.println("Delete Employee");

            employeeList.remove(currentEmployee);
            writeEmployeeData(employeeList);

            currentEmployee = null;
            currentEmployeeIndex = -1;

            if (employeeList != null && !employeeList.isEmpty()) {
                currentEmployee = employeeList.getFirst();
                currentEmployeeIndex = employeeList.indexOf(currentEmployee);

                empName.setText(currentEmployee.getName());
                empEmail.setText(currentEmployee.getEmail());
                empPhone.setText(currentEmployee.getPhone());
                empDepartment.setText(currentEmployee.getDepartment());
                empSalary.setText(String.valueOf(currentEmployee.getSalary()));
                empPosition.setText(currentEmployee.getPosition());
            }
            else {
                empName.setText("");
                empEmail.setText("");
                empPhone.setText("");
                empDepartment.setText("");
                empSalary.setText("");
                empPosition.setText("");
            }
        });

        btnNextEmployee.setOnAction(e -> {
            System.out.println("Next Employee");

            if (currentEmployeeIndex + 1 < employeeList.size()) {
                currentEmployee = employeeList.get(currentEmployeeIndex + 1);

                empName.setText(currentEmployee.getName());
                empEmail.setText(currentEmployee.getEmail());
                empPhone.setText(currentEmployee.getPhone());
                empDepartment.setText(currentEmployee.getDepartment());
                empSalary.setText(String.valueOf(currentEmployee.getSalary()));
                empPosition.setText(currentEmployee.getPosition());

                currentEmployeeIndex++;
            }
        });

        btnPrevEmployee.setOnAction(e -> {
            System.out.println("Previous Employee");

            if (currentEmployeeIndex - 1 >= 0) {
                currentEmployee = employeeList.get(currentEmployeeIndex - 1);

                empName.setText(currentEmployee.getName());
                empEmail.setText(currentEmployee.getEmail());
                empPhone.setText(currentEmployee.getPhone());
                empDepartment.setText(currentEmployee.getDepartment());
                empSalary.setText(String.valueOf(currentEmployee.getSalary()));
                empPosition.setText(currentEmployee.getPosition());

                currentEmployeeIndex--;
            }
        });


        // Add HBoxes to each rootBox
        rootBox.getChildren().addAll(rowName, rowEmail, rowPhone,  rowDepartment, rowSalary,  rowPosition,
                rowButtons);


        VBox payrollBox = new VBox(10);
        TitledPane payrollPane = new TitledPane("Payroll Details", payrollBox);
        payrollBox.setPadding(new Insets(10,10,10,10));

        // row for Payroll Results
        HBox rowPayrollResults = new HBox(10);
        rowPayrollResults.setStyle("-fx-background-color: #87CEEB;");
        rowPayrollResults.setPadding(new Insets(10,10,10,10));
        txtPayrollResults.setEditable(false);
        txtPayrollResults.setWrapText(true);

        VBox.setVgrow(rowPayrollResults, Priority.ALWAYS);
        rowPayrollResults.setMaxHeight(Double.MAX_VALUE);

        HBox.setHgrow(txtPayrollResults, Priority.ALWAYS);
        txtPayrollResults.setMaxWidth(Double.MAX_VALUE);

        VBox.setVgrow(txtPayrollResults, Priority.ALWAYS);
        txtPayrollResults.setMaxHeight(Double.MAX_VALUE);

        rowPayrollResults.getChildren().add(txtPayrollResults);


        HBox rowPayrollButtons = new HBox(10);
        rowPayrollButtons.setStyle("-fx-background-color: #E5E4E2;");
        rowPayrollButtons.setPadding(new Insets(10,10,10,10));
        rowPayrollButtons.setAlignment(Pos.CENTER);
        rowPayrollButtons.getChildren().addAll(btnCalculateSalaries, btnCalculateTaxes, btnCalculateDeductions);

        // Add HBoxes to each payrollBox
        payrollBox.getChildren().addAll(rowPayrollResults, rowPayrollButtons);

        btnCalculateSalaries.setOnAction(e -> {
            System.out.println("Calculate Salaries");
        });

        btnCalculateTaxes.setOnAction(e -> {
            System.out.println("Calculate Taxes ");
        });

        btnCalculateDeductions.setOnAction(e -> {
            System.out.println("Calculate Deductions");
        });


        VBox reportingBox = new VBox(10);
        TitledPane reportingPane = new TitledPane("Company Reports", reportingBox);
        reportingBox.setPadding(new Insets(10,10,10,10));

        // row for Reporting Results
        HBox rowReportingResults = new HBox(10);
        rowReportingResults.setStyle("-fx-background-color: lightblue;");
        rowReportingResults.setPadding(new Insets(10,10,10,10));

        txtReportingResults.setEditable(false);
        txtReportingResults.setWrapText(true);

        HBox.setHgrow(txtReportingResults, Priority.ALWAYS);
        txtReportingResults.setMaxWidth(Double.MAX_VALUE);

        VBox.setVgrow(rowReportingResults, Priority.ALWAYS);
        rowReportingResults.setMaxHeight(Double.MAX_VALUE);


        rowReportingResults.getChildren().add(txtReportingResults);



        HBox rowReportingButtons = new HBox(10);
        rowReportingButtons.setStyle("-fx-background-color: #E5E4E2;");
        rowReportingButtons.setPadding(new Insets(10,10,10,10));
        rowReportingButtons.setAlignment(Pos.CENTER);
        rowReportingButtons.getChildren().addAll(btnCalculateEmpReport, btnCalculateDeptReport);

        // Add HBoxes to each reportingBox
        reportingBox.getChildren().addAll(rowReportingResults, rowReportingButtons);

        btnCalculateEmpReport.setOnAction(e -> {
            System.out.println("Calculate Employee Report");
        });

        btnCalculateDeptReport.setOnAction(e -> {
            System.out.println("Calculate Department Report");
        });

        // Defaults to expanded Employee Pane when app is launched
//        accordion.setExpandedPane(employeesPane);

        // Add employeePane, payrollPane and reportingPane to Accordion
        accordion.getPanes().addAll(employeesPane, payrollPane, reportingPane);

        Scene scene = new Scene(accordion,1000, 600);


        stage.setMinWidth(1000);
        stage.setMinHeight(600);
        stage.setScene(scene);
        stage.show();
    }

    public ArrayList<Employee> readEmployeeData() {
        System.out.println("Reading Employee Data...");

        ArrayList<Employee> employeeList = new ArrayList<>();

        try {
            Gson gson = new Gson();

            Type employeeListType = new TypeToken<ArrayList<Employee>>(){}.getType();
            FileReader reader = new FileReader("employees.json");

            employeeList = gson.fromJson(reader, employeeListType);

            reader.close();

            for (Employee e : employeeList) {
                System.out.println(e);
            }

            System.out.println(employeeList.size() + " Employees read from employees.json");
        }
        catch (Exception e) {
            System.out.println("Error Reading Employee Data");
            e.printStackTrace();
            employeeList = new ArrayList<>();
        }

        return employeeList;
    }

    public void writeEmployeeData(ArrayList<Employee> employeeList) {
        System.out.println("Writing Employee Data...");

        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            FileWriter writer = new FileWriter("employees.json");
            gson.toJson(employeeList, writer);

            writer.close();
            System.out.println("Employees saved to employees.json");
        }
        catch (Exception e) {
            System.out.println("Error Writing Employee Data");
            e.printStackTrace();
        }
    }
}
