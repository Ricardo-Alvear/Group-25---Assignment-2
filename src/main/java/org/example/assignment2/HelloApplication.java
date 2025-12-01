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
    private final Label lblOutput = new Label("");

    private final Button btnCreateEmployee = new Button("Create Employee");
    private final Button btnUpdateEmployee = new Button("Update Employee");
    private final Button btnDeleteEmployee = new Button("Delete Employee");
    private final Button btnNextEmployee = new Button("Next Employee");
    private final Button btnPrevEmployee = new Button("Previous Employee");

    private final Label lblPayName = new Label("Employee Name");
    private final Label lblPayNameValue = new Label("");
    private final Label lblRegularRate = new Label("Regular Hourly Rate:");
    private final TextField payRegularRate = new TextField();
    private final Label lblRegularHours = new Label("Regular Hourly Hours:");
    private final TextField payRegularHours = new TextField();
    private final Label lblOvertimeRate = new Label("Overtime Hourly Rate:");
    private final TextField payOvertimeRate = new TextField();
    private final Label lblOvertimeHours = new Label("Overtime Hourly Hours:");
    private final TextField payOvertimeHours = new TextField();
    private final Label lblBonus = new Label("Bonus:");
    private final TextField payBonus = new TextField();
    private final Label lblTaxPercentage = new Label("Tax Percentage:");
    private final TextField payTaxPercentage = new TextField();
    private final Label lblDeductions = new Label("Other Deductions:");
    private final TextField payDeductions = new TextField();
    private final Label lblPayrollOutput = new Label("");

    private final TextArea txtPayrollResults = new TextArea();

    private final Button btnSavePayroll = new Button("Save Payroll");
    private final Button btnCalculateSalaries = new Button("Calculate Salary");
    private final Button btnCalculateTaxes = new Button("Calculate Taxes");
    private final Button btnCalculateDeductions = new Button("Calculate Deductions");

    private final TextArea txtReportingResults = new TextArea();

    private final Button btnCalculateEmpReport = new Button("Calculate Employee Report");
    private final Button btnCalculateDeptReport = new Button("Calculate Department Report");


    private final Accordion  accordion = new Accordion();

    private ArrayList<Employee> employeeList;
    private Employee currentEmployee;
    private int currentEmployeeIndex;

    private ArrayList<Payroll> payrollList;
    private Payroll currentPayroll;
    private int currentPayrollIndex;

    private void loadEmployeeDataIntoFields() {       // <<< UPDATED (new)
        empName.setText(currentEmployee.getName());
        empEmail.setText(currentEmployee.getEmail());
        empPhone.setText(currentEmployee.getPhone());
        empDepartment.setText(currentEmployee.getDepartment());
        empSalary.setText(String.valueOf(currentEmployee.getSalary()));
        empPosition.setText(currentEmployee.getPosition());

        lblPayNameValue.setText(currentEmployee.getName());
        payRegularRate.setText(String.valueOf(currentEmployee.payroll.getRegularRate()));
        payRegularHours.setText(String.valueOf(currentEmployee.payroll.getRegularHours()));
        payOvertimeRate.setText(String.valueOf(currentEmployee.payroll.getOvertimeRate()));
        payOvertimeHours.setText(String.valueOf(currentEmployee.payroll.getOvertimeHours()));
        payBonus.setText(String.valueOf(currentEmployee.payroll.getBonus()));
        payTaxPercentage.setText(String.valueOf(currentEmployee.payroll.getTaxPercentage()));
        payDeductions.setText(String.valueOf(currentEmployee.payroll.getDeductions()));
    }

    @Override
    public void start(Stage stage)  {
//        FXMLLoader fxmlLoader1 = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));

        // Load Employee Data

        employeeList = readEmployeeData();

        currentEmployee = null;
        currentEmployeeIndex = -1;

        if (employeeList != null && !employeeList.isEmpty()) {
            currentEmployee = employeeList.getFirst();
            currentEmployeeIndex = employeeList.indexOf(currentEmployee);
            lblOutput.setText("Showing Employee (ID: " + currentEmployee.getId() + ")");
            lblPayrollOutput.setText("Showing Employee (ID: " + currentEmployee.getId() + ")");
        }

        // Load Payroll Data

        /*
        payrollList = readPayrollData();

        currentPayroll = null;
        currentPayrollIndex = -1;

        if (payrollList != null && !payrollList.isEmpty()) {
            currentPayroll = payrollList.getFirst();
            currentPayrollIndex = payrollList.indexOf(currentPayroll);
        }
         */

        // Main Program

        stage.setTitle("Fun Time's HR Management and Payroll Processing!");

        // Main layout - VBox then use HBox for fields/buttons
        VBox rootBox = new VBox(10);
        TitledPane employeesPane = new TitledPane("Employees Details", rootBox);
        rootBox.setPadding(new Insets(10,10,10,10));

        // row for emp first and last name
        HBox rowName = new HBox(10);
        rowName.setStyle("-fx-background-color: lightblue;");
        rowName.setPadding(new Insets(10,10,10,10));
        HBox.setHgrow(empName, Priority.ALWAYS);
        empName.setMaxWidth(300);
        if (currentEmployee != null) {
            empName.setText(currentEmployee.getName());
        }
        rowName.getChildren().addAll(lblName,empName);

        HBox rowEmail = new HBox(10);
        rowEmail.setStyle("-fx-background-color: #87CEEB;");
        rowEmail.setPadding(new Insets(10,10,10,10));
        HBox.setHgrow(empEmail, Priority.ALWAYS);
        empEmail.setMaxWidth(300);
        if (currentEmployee != null) {
            empEmail.setText(currentEmployee.getEmail());
        }
        rowEmail.getChildren().addAll(lblEmail,empEmail);

        HBox rowPhone = new HBox(10);
        rowPhone.setStyle("-fx-background-color: lightblue;");
        rowPhone.setPadding(new Insets(10,10,10,10));
        HBox.setHgrow(empPhone, Priority.ALWAYS);
        empPhone.setMaxWidth(300);
        if (currentEmployee != null) {
            empPhone.setText(currentEmployee.getPhone());
        }
        rowPhone.getChildren().addAll(lblPhone,empPhone);


        HBox rowDepartment = new HBox(10);
        rowDepartment.setStyle("-fx-background-color: #87CEEB;");
        rowDepartment.setPadding(new Insets(10,10,10,10));
        HBox.setHgrow(empDepartment, Priority.ALWAYS);
        empDepartment.setMaxWidth(300);
        if (currentEmployee != null) {
            empDepartment.setText(currentEmployee.getDepartment());
        }
        rowDepartment.getChildren().addAll(lblDepartment, empDepartment);

        HBox rowSalary = new HBox(10);
        rowSalary.setStyle("-fx-background-color: lightblue;");
        rowSalary.setPadding(new Insets(10,10,10,10));
        HBox.setHgrow(empSalary, Priority.ALWAYS);
        empSalary.setMaxWidth(300);
        empSalary.setEditable(false);
        if (currentEmployee != null) {
            empSalary.setText(String.valueOf(currentEmployee.getSalary()));
        }
        rowSalary.getChildren().addAll(lblSalary,empSalary);

        HBox rowPosition = new HBox(10);
        rowPosition.setStyle("-fx-background-color: #87CEEB;");
        rowPosition.setPadding(new Insets(10,10,10,10));
        HBox.setHgrow(empPosition, Priority.ALWAYS);
        empPosition.setMaxWidth(300);
        if (currentEmployee != null) {
            empPosition.setText(currentEmployee.getPosition());
        }
        rowPosition.getChildren().addAll(lblPosition, empPosition);

        HBox rowOutput = new HBox(10);
        rowOutput.setStyle("-fx-background-color: #87CEEB;");
        rowOutput.setPadding(new Insets(10,10,10,10));
        rowOutput.setHgrow(lblOutput, Priority.ALWAYS);
        rowOutput.setAlignment(Pos.CENTER);
        rowOutput.getChildren().addAll(lblOutput);

        HBox rowButtons = new HBox(10);
        rowButtons.setStyle("-fx-background-color: #E5E4E2;");
        rowButtons.setPadding(new Insets(10,10,10,10));
        rowButtons.setAlignment(Pos.CENTER);
        rowButtons.getChildren().addAll(btnPrevEmployee, btnCreateEmployee, btnUpdateEmployee,
                btnDeleteEmployee, btnNextEmployee);


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

            lblOutput.setText("Created Employee (ID: " + currentEmployee.getId() + ")");
            lblPayrollOutput.setText("Showing Employee (ID: " + currentEmployee.getId() + ")");
        });

        btnUpdateEmployee.setOnAction(e -> {
            System.out.println("Update Employee");

            currentEmployee.setName(empName.getText());
            currentEmployee.setEmail(empEmail.getText());
            currentEmployee.setPhone(empPhone.getText());
            currentEmployee.setDepartment(empDepartment.getText());
            currentEmployee.setPosition(empPosition.getText());

            currentEmployee.payroll.setRegularRate(Double.parseDouble(payRegularRate.getText()));
            currentEmployee.payroll.setRegularHours(Double.parseDouble(payRegularHours.getText()));
            currentEmployee.payroll.setOvertimeRate(Double.parseDouble(payOvertimeRate.getText()));
            currentEmployee.payroll.setOvertimeHours(Double.parseDouble(payOvertimeHours.getText()));
            currentEmployee.payroll.setBonus(Double.parseDouble(payBonus.getText()));
            currentEmployee.payroll.setTaxPercentage(Double.parseDouble(payTaxPercentage.getText()));
            currentEmployee.payroll.setDeductions(Double.parseDouble(payDeductions.getText()));

            lblPayNameValue.setText(currentEmployee.getName());

            writeEmployeeData(employeeList);

            lblOutput.setText("Updated Employee (ID: " + currentEmployee.getId() + ")");
            lblPayrollOutput.setText("Showing Employee (ID: " + currentEmployee.getId() + ")");
        });

        btnDeleteEmployee.setOnAction(e -> {
            System.out.println("Delete Employee");

            int employeeId = currentEmployee.getId();

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

                payRegularRate.setText(String.valueOf(currentEmployee.payroll.getRegularRate()));
                payRegularHours.setText(String.valueOf(currentEmployee.payroll.getRegularHours()));
                payOvertimeRate.setText(String.valueOf(currentEmployee.payroll.getOvertimeRate()));
                payOvertimeHours.setText(String.valueOf(currentEmployee.payroll.getOvertimeHours()));
                payBonus.setText(String.valueOf(currentEmployee.payroll.getBonus()));
                payTaxPercentage.setText(String.valueOf(currentEmployee.payroll.getTaxPercentage()));
                payDeductions.setText(String.valueOf(currentEmployee.payroll.getDeductions()));

                lblPayrollOutput.setText("Showing Employee (ID: " + currentEmployee.getId() + ")");
            }
            else {
                empName.setText("");
                empEmail.setText("");
                empPhone.setText("");
                empDepartment.setText("");
                empSalary.setText("");
                empPosition.setText("");

                payRegularRate.setText("");

                lblPayrollOutput.setText("");
            }

            lblOutput.setText("Deleted Employee (ID: " + employeeId + ")");
        });

        btnNextEmployee.setOnAction(e -> {
            System.out.println("Next Employee");

            // Check if we are NOT already at the last employee
            if (currentEmployeeIndex + 1 < employeeList.size()) {

                // Move the index forward by 1
                currentEmployeeIndex++;

                // Update the current employee reference
                currentEmployee = employeeList.get(currentEmployeeIndex);

                // Load all employee & payroll values into the UI fields
                loadEmployeeDataIntoFields();

                // Update status messages on both screens
                lblOutput.setText("Showing Employee (ID: " + currentEmployee.getId() + ")");
                lblPayrollOutput.setText("Showing Employee (ID: " + currentEmployee.getId() + ")");

            } else {
                // If already at the last employee, cannot go forward
                lblOutput.setText("Already Showing Last Employee!");
                lblPayrollOutput.setText("Showing Employee (ID: " + currentEmployee.getId() + ")");
            }
        });

        btnPrevEmployee.setOnAction(e -> {
            System.out.println("Previous Employee");

            // Check if we are NOT already at the first employee
            if (currentEmployeeIndex - 1 >= 0) {

                // Move the index backward by 1
                currentEmployeeIndex--;

                // Update the current employee reference
                currentEmployee = employeeList.get(currentEmployeeIndex);

                // Load all employee & payroll values into the UI fields
                loadEmployeeDataIntoFields();

                // Update status messages on both screens
                lblOutput.setText("Showing Employee (ID: " + currentEmployee.getId() + ")");
                lblPayrollOutput.setText("Showing Employee (ID: " + currentEmployee.getId() + ")");

            } else {
                // If already at index 0, cannot go back further
                lblOutput.setText("Already Showing First Employee!");
                lblPayrollOutput.setText("Showing Employee (ID: " + currentEmployee.getId() + ")");
            }
        });


        // Add HBoxes to each rootBox
        rootBox.getChildren().addAll(rowName, rowEmail, rowPhone,  rowDepartment, rowSalary,  rowPosition,
                rowButtons, rowOutput);


        VBox payrollBox = new VBox(10);
        TitledPane payrollPane = new TitledPane("Payroll Details", payrollBox);
        payrollBox.setPadding(new Insets(10,10,10,10));

        /*
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

        // txtPayrollResults.setText("Test Text" + "\n");
        // txtPayrollResults.appendText("More Text");

        rowPayrollResults.getChildren().add(txtPayrollResults);
         */

        HBox rowPayName = new HBox(10);
        rowPayName.setStyle("-fx-background-color: lightgray;");
        rowPayName.setPadding(new Insets(10,10,10,10));
        rowPayName.setHgrow(lblPayNameValue, Priority.ALWAYS);
        lblPayNameValue.setMaxWidth(300);
        if (currentEmployee != null) {
            lblPayNameValue.setText(currentEmployee.getName());
        }
        rowPayName.getChildren().addAll(lblPayName, lblPayNameValue);

        HBox rowRegularRate = new HBox(10);
        rowRegularRate.setStyle("-fx-background-color: lightblue;");
        rowRegularRate.setPadding(new Insets(10,10,10,10));
        rowRegularRate.setHgrow(payRegularRate, Priority.ALWAYS);
        payRegularRate.setMaxWidth(300);
        if (currentEmployee != null) {
            payRegularRate.setText(String.valueOf(currentEmployee.payroll.getRegularRate()));
        }
        rowRegularRate.getChildren().addAll(lblRegularRate, payRegularRate);

        HBox rowRegularHours = new HBox(10);
        rowRegularHours.setStyle("-fx-background-color: #87CEEB;");
        rowRegularHours.setPadding(new Insets(10,10,10,10));
        rowRegularHours.setHgrow(payRegularHours, Priority.ALWAYS);
        payRegularHours.setMaxWidth(300);
        if (currentEmployee != null) {
            payRegularHours.setText(String.valueOf(currentEmployee.payroll.getRegularHours()));
        }
        rowRegularHours.getChildren().addAll(lblRegularHours, payRegularHours);

        HBox rowOvertimeRate = new HBox(10);
        rowOvertimeRate.setStyle("-fx-background-color: lightblue;");
        rowOvertimeRate.setPadding(new Insets(10,10,10,10));
        rowOvertimeRate.setHgrow(payOvertimeRate, Priority.ALWAYS);
        payOvertimeRate.setMaxWidth(300);
        if (currentEmployee != null) {
            payOvertimeRate.setText(String.valueOf(currentEmployee.payroll.getOvertimeRate()));
        }
        rowOvertimeRate.getChildren().addAll(lblOvertimeRate, payOvertimeRate);

        HBox rowOvertimeHours = new HBox(10);
        rowOvertimeHours.setStyle("-fx-background-color: #87CEEB;");
        rowOvertimeHours.setPadding(new Insets(10,10,10,10));
        rowOvertimeHours.setHgrow(payOvertimeHours, Priority.ALWAYS);
        payOvertimeHours.setMaxWidth(300);
        if (currentEmployee != null) {
            payOvertimeHours.setText(String.valueOf(currentEmployee.payroll.getOvertimeHours()));
        }
        rowOvertimeHours.getChildren().addAll(lblOvertimeHours, payOvertimeHours);

        HBox rowBonus = new HBox(10);
        rowBonus.setStyle("-fx-background-color: lightblue;");
        rowBonus.setPadding(new Insets(10,10,10,10));
        rowBonus.setHgrow(payBonus, Priority.ALWAYS);
        payBonus.setMaxWidth(300);
        if (currentEmployee != null) {
            payBonus.setText(String.valueOf(currentEmployee.payroll.getBonus()));
        }
        rowBonus.getChildren().addAll(lblBonus, payBonus);

        HBox rowTaxPercentage = new HBox(10);
        rowTaxPercentage.setStyle("-fx-background-color: #87CEEB;");
        rowTaxPercentage.setPadding(new Insets(10,10,10,10));
        rowTaxPercentage.setHgrow(payTaxPercentage, Priority.ALWAYS);
        payTaxPercentage.setMaxWidth(300);
        if (currentEmployee != null) {
            payTaxPercentage.setText(String.valueOf(currentEmployee.payroll.getTaxPercentage()));
        }
        rowTaxPercentage.getChildren().addAll(lblTaxPercentage, payTaxPercentage);

        HBox rowDeductions = new HBox(10);
        rowDeductions.setStyle("-fx-background-color: lightblue;");
        rowDeductions.setPadding(new Insets(10,10,10,10));
        rowDeductions.setHgrow(payDeductions, Priority.ALWAYS);
        payDeductions.setMaxWidth(300);
        if (currentEmployee != null) {
            payDeductions.setText(String.valueOf(currentEmployee.payroll.getDeductions()));
        }
        rowDeductions.getChildren().addAll(lblDeductions, payDeductions);

        HBox rowPayrollOutput = new HBox(10);
        rowPayrollOutput.setStyle("-fx-background-color: #87CEEB;");
        rowPayrollOutput.setPadding(new Insets(10,10,10,10));
        rowPayrollOutput.setHgrow(lblPayrollOutput, Priority.ALWAYS);
        rowPayrollOutput.setAlignment(Pos.CENTER);
        rowPayrollOutput.getChildren().addAll(lblPayrollOutput);

        HBox rowPayrollButtons = new HBox(10);
        rowPayrollButtons.setStyle("-fx-background-color: #E5E4E2;");
        rowPayrollButtons.setPadding(new Insets(10,10,10,10));
        rowPayrollButtons.setAlignment(Pos.CENTER);
        rowPayrollButtons.getChildren().addAll(btnPrevEmployee, btnSavePayroll, btnCalculateSalaries, btnCalculateTaxes, btnCalculateDeductions, btnNextEmployee);



        // Add HBoxes to each payrollBox
        payrollBox.getChildren().addAll(rowPayName, rowRegularRate, rowRegularHours, rowOvertimeRate, rowOvertimeHours, rowBonus, rowTaxPercentage, rowDeductions, rowPayrollButtons, rowPayrollOutput);
        // payrollBox.getChildren().addAll(rowPayrollResults, rowPayrollButtons);

        btnSavePayroll.setOnAction(e -> {
            System.out.println("Update Employee");

            currentEmployee.setName(empName.getText());
            currentEmployee.setEmail(empEmail.getText());
            currentEmployee.setPhone(empPhone.getText());
            currentEmployee.setDepartment(empDepartment.getText());
            currentEmployee.setPosition(empPosition.getText());

            currentEmployee.payroll.setRegularRate(Double.parseDouble(payRegularRate.getText()));
            currentEmployee.payroll.setRegularHours(Double.parseDouble(payRegularHours.getText()));
            currentEmployee.payroll.setOvertimeRate(Double.parseDouble(payOvertimeRate.getText()));
            currentEmployee.payroll.setOvertimeHours(Double.parseDouble(payOvertimeHours.getText()));
            currentEmployee.payroll.setBonus(Double.parseDouble(payBonus.getText()));
            currentEmployee.payroll.setTaxPercentage(Double.parseDouble(payTaxPercentage.getText()));
            currentEmployee.payroll.setDeductions(Double.parseDouble(payDeductions.getText()));

            empSalary.setText(String.valueOf(currentEmployee.getSalary()));

            lblPayNameValue.setText(currentEmployee.getName());

            writeEmployeeData(employeeList);

            lblOutput.setText("Showing Employee (ID: " + currentEmployee.getId() + ")");
            lblPayrollOutput.setText("Saved Payroll for Employee (ID: " + currentEmployee.getId() + ")");
        });

        btnCalculateSalaries.setOnAction(e -> {
            double regularHours = Double.parseDouble(payRegularHours.getText());
            double regularRate = Double.parseDouble(payRegularRate.getText());
            double overtimeHours = Double.parseDouble(payOvertimeHours.getText());
            double overtimeRate = Double.parseDouble(payOvertimeRate.getText());
            double bonus = Double.parseDouble(payBonus.getText());

            double income = (regularHours * regularRate) + (overtimeHours * overtimeRate) + bonus;

            lblPayrollOutput.setText("Gross Salary: " + String.format("%.2f", income));
            System.out.println("Calculated Gross Salary: " + String.format("%.2f", income));
        });

        btnCalculateTaxes.setOnAction(e -> {
            double regularHours = Double.parseDouble(payRegularHours.getText());
            double regularRate = Double.parseDouble(payRegularRate.getText());
            double overtimeHours = Double.parseDouble(payOvertimeHours.getText());
            double overtimeRate = Double.parseDouble(payOvertimeRate.getText());
            double bonus = Double.parseDouble(payBonus.getText());
            double taxPercentage = Double.parseDouble(payTaxPercentage.getText()) / 100;

            double income = (regularHours * regularRate) + (overtimeHours * overtimeRate) + bonus;
            double taxes = income * taxPercentage;

            lblPayrollOutput.setText("Taxes: " + String.format("%.2f", taxes));
            System.out.println("Calculated Taxes: " + String.format("%.2f", taxes));
        });

        btnCalculateDeductions.setOnAction(e -> {
            lblPayrollOutput.setText("Calculate Deductions");

            double regularHours = Double.parseDouble(payRegularHours.getText());
            double regularRate = Double.parseDouble(payRegularRate.getText());
            double overtimeHours = Double.parseDouble(payOvertimeHours.getText());
            double overtimeRate = Double.parseDouble(payOvertimeRate.getText());
            double bonus = Double.parseDouble(payBonus.getText());
            double taxPercentage = Double.parseDouble(payTaxPercentage.getText()) / 100;
            double deductions = Double.parseDouble(payDeductions.getText());

            double income = (regularHours * regularRate) + (overtimeHours * overtimeRate) + bonus;
            double taxes = income * taxPercentage;
            double totalDeductions = taxes + deductions;

            lblPayrollOutput.setText("Total Deductions: " + String.format("%.2f", totalDeductions));
            System.out.println("Calculated Deductions:" + String.format("%.2f", totalDeductions));
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

            System.out.println(employeeList.size() + " Employee records read from employees.json");
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
            System.out.println(employeeList.size() + " Employee records saved to employees.json");
        }
        catch (Exception e) {
            System.out.println("Error Writing Employee Data");
            e.printStackTrace();
        }
    }

    /*
    public ArrayList<Payroll> readPayrollData() {
        System.out.println("Reading Payroll Data...");

        ArrayList<Payroll> payrollList = new ArrayList<>();

        try {
            Gson gson = new Gson();

            Type payrollListType = new TypeToken<ArrayList<Payroll>>(){}.getType();
            FileReader reader = new FileReader("payroll.json");

            payrollList = gson.fromJson(reader, payrollListType);
            reader.close();

            System.out.println(payrollList.size() + " Pay records read from payroll.json");
        }
        catch (Exception e) {
            System.out.println("Error Reading Payroll Data");
            e.printStackTrace();
            payrollList = new ArrayList<>();
        }

        return payrollList;
    }

    public void writePayrollData(ArrayList<Payroll> payrollList) {
        System.out.println("Writing Payroll Data...");

        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            FileWriter writer = new FileWriter("payroll.json");
            gson.toJson(payrollList, writer);

            writer.close();
            System.out.println(payrollList.size() + " Pay records saved to payroll.json");
        }
        catch (Exception e) {
            System.out.println("Error Writing Payroll Data");
            e.printStackTrace();
        }
    }
     */
}
