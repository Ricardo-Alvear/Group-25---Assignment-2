package org.example.assignment2;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import javafx.application.Application;
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
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

// Members: Ricardo Alvear, Joe Macdonald, Daniel Le Huenen
// Group: 25
// Course: COMP2130
// Term: Fall 2025


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

//    private final TextArea txtPayrollResults = new TextArea();

    private final Button btnSavePayroll = new Button("Save Payroll");
    private final Button btnCalculateSalaries = new Button("Calculate Salary");
    private final Button btnCalculateTaxes = new Button("Calculate Taxes");
    private final Button btnCalculateDeductions = new Button("Calculate Deductions");
    private final Button btnPayrollNextEmployee = new Button("Next Employee");
    private final Button btnPayrollPrevEmployee = new Button("Previous Employee");

    private final TextArea txtReportingResults = new TextArea();

    private final Button btnCalculateEmpReport = new Button("Employee Report");
    private final Button btnCalculateDeptReport = new Button("Department Report");


    private final Accordion  accordion = new Accordion();

    private ArrayList<Employee> employeeList;
    private Employee currentEmployee;
    private int currentEmployeeIndex;

    private ArrayList<Payroll> payrollList;
    private Payroll currentPayroll;
    private int currentPayrollIndex;



    // ... (imports and class definition remain the same)

    /**
     * Data Storage Description
     * The employees are loaded from a **JSON file** at startup, processed in an **in-memory database** which is **synchronized to the JSON file whenever they are updated**.
     */

    private Payroll createEmptyPayroll() {
        // Create a new Payroll object with default values (zeros) for all fields
        return new Payroll(0, 0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
    }

    // Loads employee & payroll data into the UI fields
    private void loadEmployeeDataIntoFields() {
        if (currentEmployee.payroll == null) {
            currentEmployee.payroll = createEmptyPayroll();
        }

        // Employee fields
        empName.setText(currentEmployee.getName());
        empEmail.setText(currentEmployee.getEmail());
        empPhone.setText(currentEmployee.getPhone());
        empDepartment.setText(currentEmployee.getDepartment());
        empSalary.setText(String.valueOf(currentEmployee.getSalary()));
        empPosition.setText(currentEmployee.getPosition());

        // Payroll fields
        if (currentEmployee.payroll == null) {
            currentEmployee.payroll = new Payroll(
                    0, 0, 0, 0, 0, 0, 0, 0, 0, 0 ); // Ensure payroll object exists
        }

        payRegularRate.setText(String.valueOf(currentEmployee.payroll.getRegularRate()));
        payRegularHours.setText(String.valueOf(currentEmployee.payroll.getRegularHours()));
        payOvertimeRate.setText(String.valueOf(currentEmployee.payroll.getOvertimeRate()));
        payOvertimeHours.setText(String.valueOf(currentEmployee.payroll.getOvertimeHours()));
        payBonus.setText(String.valueOf(currentEmployee.payroll.getBonus()));
        payTaxPercentage.setText(String.valueOf(currentEmployee.payroll.getTaxPercentage()));
        payDeductions.setText(String.valueOf(currentEmployee.payroll.getDeductions()));

        lblPayNameValue.setText(currentEmployee.getName());
    }

    private double parseDoubleOrZero(TextField field) {
        try {
            return Double.parseDouble(field.getText());
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    private void navigateToEmployee(int newIndex) {
        if (employeeList == null || employeeList.isEmpty()) return;

        if (newIndex >= 0 && newIndex < employeeList.size()) {
            currentEmployeeIndex = newIndex;
            currentEmployee = employeeList.get(currentEmployeeIndex);

            loadEmployeeDataIntoFields();

            lblOutput.setText("Showing Employee (ID: " + currentEmployee.getId() + ")");
            lblPayrollOutput.setText("Showing Employee (ID: " + currentEmployee.getId() + ")");
        } else if (newIndex < 0) {
            lblOutput.setText("Already Showing First Employee!");
            lblPayrollOutput.setText(currentEmployee != null ? "Already Showing First Employee!" : "");
        } else {
            lblOutput.setText("Already Showing Last Employee!");
            lblPayrollOutput.setText(currentEmployee != null ? "Already Showing Last Employee!" : "");
        }
    }

    private boolean validateInputs() {
        boolean isValid = true;
        StringBuilder validationMessage = new StringBuilder();

        // Reset styles
        empName.setStyle("");
        empEmail.setStyle("");
        // ... reset other field styles similarly ...

        // --- Employee Details Validation ---

        if (empName.getText().trim().isEmpty()) {
            validationMessage.append("- Name is required.\n");
            empName.setStyle("-fx-border-color: red;");
            isValid = false;
        }

        if (empDepartment.getText().trim().isEmpty()) {
            validationMessage.append("- Department is required.\n");
            empDepartment.setStyle("-fx-border-color: red;");
            isValid = false;
        }

        // --- Payroll Details (Numerical) Validation ---

        // Fields that must be non-negative numbers
        TextField[] numericFields = {
                payRegularRate, payRegularHours, payOvertimeRate, payOvertimeHours,
                payBonus, payTaxPercentage, payDeductions
        };
        String[] fieldNames = {
                "Regular Rate", "Regular Hours", "Overtime Rate", "Overtime Hours",
                "Bonus", "Tax Percentage", "Deductions"
        };

        for (int i = 0; i < numericFields.length; i++) {
            if (!numericFields[i].getText().trim().isEmpty()) {
                try {
                    double value = Double.parseDouble(numericFields[i].getText());
                    if (value < 0) {
                        validationMessage.append(String.format("- %s must be non-negative.\n", fieldNames[i]));
                        numericFields[i].setStyle("-fx-border-color: red;");
                        isValid = false;
                    }
                } catch (NumberFormatException e) {
                    validationMessage.append(String.format("- %s must be a valid number.\n", fieldNames[i]));
                    numericFields[i].setStyle("-fx-border-color: red;");
                    isValid = false;
                }
            }
        }

        // Update the output labels
        if (isValid) {
            lblOutput.setText("Employee Details: Valid");
            lblPayrollOutput.setText("Payroll Details: Valid");
        } else {
            lblOutput.setText("Employee Details: Errors found. See Payroll Output.");
            lblPayrollOutput.setText("Validation Errors:\n" + validationMessage.toString());
        }

        return isValid;
    }

    // In HelloApplication.java
    private void handleExportAllData() {
        if (employeeList == null || employeeList.isEmpty()) {
            lblPayrollOutput.setText("Cannot export: No employee data available.");
            return;
        }

        boolean success = writeEmployeeData(employeeList);

        if (success) {
            lblPayrollOutput.setText("Successfully synchronized all Employee and Payroll data to file.");

        } else {
            lblPayrollOutput.setText("Export Failed: Could not write data to file.");
        }
    }

    @Override
    public void start(Stage stage)  {
        // Load Employee Data
        // Load employees are retrieved from an external JSON file
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

        VBox rootBox = new VBox(10);
        TitledPane employeesPane = new TitledPane("Employees Details", rootBox);
        rootBox.setPadding(new Insets(10,10,10,10));

        HBox rowName = new HBox(10);
        rowName.setStyle("-fx-background-color: lightblue;");
        rowName.setPadding(new Insets(10,10,10,10));
        HBox.setHgrow(empName, Priority.ALWAYS);
        empName.setMaxWidth(400);
        if (currentEmployee != null) {
            empName.setText(currentEmployee.getName());
        }
        lblName.setPadding(new Insets(5,0,0,0));
        rowName.setAlignment(Pos.CENTER);
        rowName.getChildren().addAll(lblName,empName);

        HBox rowEmail = new HBox(10);
        rowEmail.setStyle("-fx-background-color: #87CEEB;");
        rowEmail.setPadding(new Insets(10,10,10,10));
        HBox.setHgrow(empEmail, Priority.ALWAYS);
        empEmail.setMaxWidth(400);
        if (currentEmployee != null) {
            empEmail.setText(currentEmployee.getEmail());
        }
        lblEmail.setPadding(new Insets(5,0,0,0));
        rowEmail.setAlignment(Pos.CENTER);
        rowEmail.getChildren().addAll(lblEmail,empEmail);

        HBox rowPhone = new HBox(10);
        rowPhone.setStyle("-fx-background-color: lightblue;");
        rowPhone.setPadding(new Insets(10,10,10,10));
        HBox.setHgrow(empPhone, Priority.ALWAYS);
        if (currentEmployee != null) {
            empPhone.setText(currentEmployee.getPhone());
        }
        lblPhone.setPadding(new Insets(5,0,0,0));
        empPhone.setMaxWidth(350);
        rowPhone.setAlignment(Pos.CENTER);
        rowPhone.getChildren().addAll(lblPhone,empPhone);


        HBox rowDepartment = new HBox(10);
        rowDepartment.setStyle("-fx-background-color: #87CEEB;");
        rowDepartment.setPadding(new Insets(10,10,10,10));
        HBox.setHgrow(empDepartment, Priority.ALWAYS);
        if (currentEmployee != null) {
            empDepartment.setText(currentEmployee.getDepartment());
        }
        lblDepartment.setPadding(new Insets(5,0,0,0));
        empDepartment.setMaxWidth(370);
        rowDepartment.setAlignment(Pos.CENTER);
        rowDepartment.getChildren().addAll(lblDepartment, empDepartment);

        HBox rowSalary = new HBox(10);
        rowSalary.setStyle("-fx-background-color: lightblue;");
        rowSalary.setPadding(new Insets(10,10,10,10));
        HBox.setHgrow(empSalary, Priority.ALWAYS);
        empSalary.setMaxWidth(400);
        // empSalary.setEditable(false);
        if (currentEmployee != null) {
            empSalary.setText(String.valueOf(currentEmployee.getSalary()));
        }
        lblSalary.setPadding(new Insets(5,0,0,0));
        rowSalary.setAlignment(Pos.CENTER);
        rowSalary.getChildren().addAll(lblSalary,empSalary);

        HBox rowPosition = new HBox(10);
        rowPosition.setStyle("-fx-background-color: #87CEEB;");
        rowPosition.setPadding(new Insets(10,10,10,10));
        HBox.setHgrow(empPosition, Priority.ALWAYS);
        if (currentEmployee != null) {
            empPosition.setText(currentEmployee.getPosition());
        }
        lblPosition.setPadding(new Insets(5,0,0,0));
        empPosition.setMaxWidth(390);
        rowPosition.setAlignment(Pos.CENTER);
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

            if (!validateInputs()) return;

            // Determine the next ID
            int highestId = employeeList.stream()
                    .mapToInt(Employee::getId)
                    .max()
                    .orElse(0);

            Employee newEmployee = new Employee(
                    highestId + 1,
                    empName.getText(),
                    empEmail.getText(),
                    empPhone.getText(),
                    empDepartment.getText(),
                    // Use a safe parse or default 0.0 for initial salary
                    parseDoubleOrZero(empSalary),
                    empPosition.getText()
            );

            employeeList.add(newEmployee);
            writeEmployeeData(employeeList);

            // Navigate to the newly created employee
            navigateToEmployee(employeeList.size() - 1);

            lblOutput.setText("Created Employee (ID: " + currentEmployee.getId() + ")");
            lblPayrollOutput.setText("Showing Employee (ID: " + currentEmployee.getId() + ")");
        });

        btnUpdateEmployee.setOnAction(e -> {
            if (currentEmployee == null) return;

            if (!validateInputs()) return;

            // Update employee fields
            currentEmployee.setName(empName.getText());
            currentEmployee.setEmail(empEmail.getText());
            currentEmployee.setPhone(empPhone.getText());
            currentEmployee.setDepartment(empDepartment.getText());
            currentEmployee.setPosition(empPosition.getText());

            // Update payroll fields safely
            if (currentEmployee.payroll == null) currentEmployee.payroll = new Payroll(  0, 0, 0, 0, 0, 0, 0, 0, 0, 0 );
            currentEmployee.payroll.setRegularRate(parseDoubleOrZero(payRegularRate));
            currentEmployee.payroll.setRegularHours(parseDoubleOrZero(payRegularHours));
            currentEmployee.payroll.setOvertimeRate(parseDoubleOrZero(payOvertimeRate));
            currentEmployee.payroll.setOvertimeHours(parseDoubleOrZero(payOvertimeHours));
            currentEmployee.payroll.setBonus(parseDoubleOrZero(payBonus));
            currentEmployee.payroll.setTaxPercentage(parseDoubleOrZero(payTaxPercentage));
            currentEmployee.payroll.setDeductions(parseDoubleOrZero(payDeductions));

            // Re-calculate and set the new salary based on the updated payroll values
            double grossSalary = (currentEmployee.payroll.getRegularHours() * currentEmployee.payroll.getRegularRate())
                    + (currentEmployee.payroll.getOvertimeHours() * currentEmployee.payroll.getOvertimeRate())
                    + currentEmployee.payroll.getBonus();
            currentEmployee.setSalary(grossSalary);
            empSalary.setText(String.format("%.2f", grossSalary)); // Update UI field

            writeEmployeeData(employeeList);

            lblOutput.setText("Updated Employee (ID: " + currentEmployee.getId() + ")");
            lblPayrollOutput.setText("Showing Employee (ID: " + currentEmployee.getId() + ")");

            loadEmployeeDataIntoFields(); // Refresh fields
        });


        btnDeleteEmployee.setOnAction(e -> {

            if (currentEmployee == null) return;

            int deletedId = currentEmployee.getId();
            employeeList.remove(currentEmployee);
            writeEmployeeData(employeeList);

            // If there are still employees, navigate to the previous index or first employee
            if (!employeeList.isEmpty()) {
                int newIndex = Math.min(currentEmployeeIndex, employeeList.size() - 1);
                navigateToEmployee(newIndex);
            } else {
                // Clear fields if no employees remain
                currentEmployee = null;
                currentEmployeeIndex = -1;

                empName.clear();
                empEmail.clear();
                empPhone.clear();
                empDepartment.clear();
                empSalary.clear();
                empPosition.clear();

                payRegularRate.clear();
                payRegularHours.clear();
                payOvertimeRate.clear();
                payOvertimeHours.clear();
                payBonus.clear();
                payTaxPercentage.clear();
                payDeductions.clear();

                lblOutput.setText("No Employees Available");
                lblPayrollOutput.setText("");
            }

            lblOutput.setText("Deleted Employee (ID: " + deletedId + ")");
        });

        btnNextEmployee.setOnAction(e -> {
            navigateToEmployee(currentEmployeeIndex + 1);
        });

        btnPrevEmployee.setOnAction(e -> {
            navigateToEmployee(currentEmployeeIndex - 1);
        });

        HBox rowSearchEmp = new HBox(10);
        rowSearchEmp.setStyle("-fx-background-color: #87CEEB;");
        rowSearchEmp.setPadding(new Insets(10,10,10,10));
        rowSearchEmp.setHgrow(lblOutput, Priority.ALWAYS);
        rowSearchEmp.setAlignment(Pos.CENTER);
        final Label lblSearchEmp = new Label("Find Employee by ID");
        lblSearchEmp.setPadding(new Insets(5,0,0,0));
        final TextField txtSearchEmp = new TextField();
        final Button btnSearchEmp = new Button("Find Employee");
        txtSearchEmp.setMinWidth(220);
        rowSearchEmp.getChildren().addAll(lblSearchEmp, txtSearchEmp, btnSearchEmp);

        // TODO - Search Employee button
        btnSearchEmp.setOnAction(e -> {
            // remove println when done
            System.out.println("Search Employee - by id");
        });


        rootBox.getChildren().addAll(rowName, rowEmail, rowPhone,  rowDepartment, rowSalary,  rowPosition,
                rowButtons, rowOutput, rowSearchEmp);

        VBox payrollBox = new VBox(10);
        TitledPane payrollPane = new TitledPane("Payroll Details (Common Navigation)", payrollBox); // 6D Update
        payrollBox.setPadding(new Insets(10,10,10,10));

        HBox rowPayName = new HBox(10);
        rowPayName.setStyle("-fx-background-color: lightgray;");
        rowPayName.setPadding(new Insets(10,10,10,10));
        rowPayName.setHgrow(lblPayNameValue, Priority.ALWAYS);
        lblPayNameValue.setMaxWidth(300);
        if (currentEmployee != null) {
            lblPayNameValue.setText(currentEmployee.getName());
        }
        rowPayName.setAlignment(Pos.CENTER);
        rowPayName.getChildren().addAll(lblPayName, lblPayNameValue);

        HBox rowRegularRate = new HBox(10);
        rowRegularRate.setStyle("-fx-background-color: lightblue;");
        rowRegularRate.setPadding(new Insets(10,10,10,10));
        rowRegularRate.setHgrow(payRegularRate, Priority.ALWAYS);
        payRegularRate.setMaxWidth(300);
        if (currentEmployee != null) {
            payRegularRate.setText(String.valueOf(currentEmployee.payroll.getRegularRate()));
        }
        rowRegularRate.setAlignment(Pos.CENTER);
        lblRegularRate.setPadding(new Insets(5,0,0,0));
        rowRegularRate.getChildren().addAll(lblRegularRate, payRegularRate);

        HBox rowRegularHours = new HBox(10);
        rowRegularHours.setStyle("-fx-background-color: #87CEEB;");
        rowRegularHours.setPadding(new Insets(10,10,10,10));
        rowRegularHours.setHgrow(payRegularHours, Priority.ALWAYS);
        payRegularHours.setMaxWidth(300);
        if (currentEmployee != null) {
            payRegularHours.setText(String.valueOf(currentEmployee.payroll.getRegularHours()));
        }
        lblRegularHours.setPadding(new Insets(5,0,0,0));
        rowRegularHours.setAlignment(Pos.CENTER);
        rowRegularHours.getChildren().addAll(lblRegularHours, payRegularHours);

        HBox rowOvertimeRate = new HBox(10);
        rowOvertimeRate.setStyle("-fx-background-color: lightblue;");
        rowOvertimeRate.setPadding(new Insets(10,10,10,10));
        rowOvertimeRate.setHgrow(payOvertimeRate, Priority.ALWAYS);
        payOvertimeRate.setMaxWidth(300);
        if (currentEmployee != null) {
            payOvertimeRate.setText(String.valueOf(currentEmployee.payroll.getOvertimeRate()));
        }
        lblOvertimeRate.setPadding(new Insets(5,0,0,0));
        rowOvertimeRate.setAlignment(Pos.CENTER);
        rowOvertimeRate.getChildren().addAll(lblOvertimeRate, payOvertimeRate);

        HBox rowOvertimeHours = new HBox(10);
        rowOvertimeHours.setStyle("-fx-background-color: #87CEEB;");
        rowOvertimeHours.setPadding(new Insets(10,10,10,10));
        rowOvertimeHours.setHgrow(payOvertimeHours, Priority.ALWAYS);
        payOvertimeHours.setMaxWidth(300);
        if (currentEmployee != null) {
            payOvertimeHours.setText(String.valueOf(currentEmployee.payroll.getOvertimeHours()));
        }
        rowOvertimeHours.setAlignment(Pos.CENTER);
        lblOvertimeHours.setPadding(new Insets(5,0,0,0));
        rowOvertimeHours.getChildren().addAll(lblOvertimeHours, payOvertimeHours);

        HBox rowBonus = new HBox(10);
        rowBonus.setStyle("-fx-background-color: lightblue;");
        rowBonus.setPadding(new Insets(10,10,10,10));
        rowBonus.setHgrow(payBonus, Priority.ALWAYS);
        payBonus.setMaxWidth(300);
        if (currentEmployee != null) {
            payBonus.setText(String.valueOf(currentEmployee.payroll.getBonus()));
        }
        rowBonus.setAlignment(Pos.CENTER);
        lblBonus.setPadding(new Insets(5,0,0,0));
        payBonus.setMinWidth(390);
        rowBonus.getChildren().addAll(lblBonus, payBonus);

        HBox rowTaxPercentage = new HBox(10);
        rowTaxPercentage.setStyle("-fx-background-color: #87CEEB;");
        rowTaxPercentage.setPadding(new Insets(10,10,10,10));
        rowTaxPercentage.setHgrow(payTaxPercentage, Priority.ALWAYS);
        payTaxPercentage.setMaxWidth(330);
        if (currentEmployee != null) {
            payTaxPercentage.setText(String.valueOf(currentEmployee.payroll.getTaxPercentage()));
        }
        rowTaxPercentage.setAlignment(Pos.CENTER);
        lblTaxPercentage.setPadding(new Insets(5,0,0,0));
        payTaxPercentage.setMinWidth(330);
        rowTaxPercentage.getChildren().addAll(lblTaxPercentage, payTaxPercentage);

        HBox rowDeductions = new HBox(10);
        rowDeductions.setStyle("-fx-background-color: lightblue;");
        rowDeductions.setPadding(new Insets(10,10,10,10));
        rowDeductions.setHgrow(payDeductions, Priority.ALWAYS);
        payDeductions.setMaxWidth(330);
        if (currentEmployee != null) {
            payDeductions.setText(String.valueOf(currentEmployee.payroll.getDeductions()));
        }
        rowDeductions.setAlignment(Pos.CENTER);
        lblDeductions.setPadding(new Insets(5,0,0,0));
        payDeductions.setMinWidth(330);
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
        // Changed to reflect "unambiguous separation" of controls (6B)
        rowPayrollButtons.getChildren().addAll(btnPayrollPrevEmployee, btnSavePayroll, btnCalculateSalaries,
                btnCalculateTaxes, btnCalculateDeductions, btnPayrollNextEmployee);

        HBox rowSearchPayroll = new HBox(10);
        rowSearchPayroll.setStyle("-fx-background-color: #87CEEB;");
        rowSearchPayroll.setPadding(new Insets(10,10,10,10));
        rowSearchPayroll.setHgrow(lblPayrollOutput, Priority.ALWAYS);
        rowSearchPayroll.setAlignment(Pos.CENTER);
        final Label lblSearchPayroll = new Label("Search Payroll by id:");
        final TextField txtSearchPayroll = new TextField();
        txtSearchPayroll.setMinWidth(300);
        final Button btnSearchPayroll = new Button("Search Payroll by id");
        rowSearchPayroll.getChildren().addAll(lblSearchPayroll, txtSearchPayroll, btnSearchPayroll);

        payrollBox.getChildren().addAll(rowPayName, rowRegularRate, rowRegularHours, rowOvertimeRate, rowOvertimeHours,
                rowBonus, rowTaxPercentage, rowDeductions, rowPayrollButtons, rowPayrollOutput, rowSearchPayroll);
        // payrollBox.getChildren().addAll(rowPayrollResults, rowPayrollButtons);

        btnSearchPayroll.setOnAction(e -> {
            System.out.println("Search Payroll by id: ");
        });


        btnPayrollNextEmployee.setOnAction(e -> {
            navigateToEmployee(currentEmployeeIndex + 1);
        });

        btnPayrollPrevEmployee.setOnAction(e -> {
            navigateToEmployee(currentEmployeeIndex - 1);
        });

        btnSavePayroll.setOnAction(e -> {
            if (currentEmployee == null) return;

            // Update payroll fields safely
            if (currentEmployee.payroll == null) currentEmployee.payroll = new Payroll(  0, 0, 0, 0, 0, 0,
                    0, 0, 0, 0 );
            currentEmployee.payroll.setRegularRate(parseDoubleOrZero(payRegularRate));
            currentEmployee.payroll.setRegularHours(parseDoubleOrZero(payRegularHours));
            currentEmployee.payroll.setOvertimeRate(parseDoubleOrZero(payOvertimeRate));
            currentEmployee.payroll.setOvertimeHours(parseDoubleOrZero(payOvertimeHours));
            currentEmployee.payroll.setBonus(parseDoubleOrZero(payBonus));
            currentEmployee.payroll.setTaxPercentage(parseDoubleOrZero(payTaxPercentage));
            currentEmployee.payroll.setDeductions(parseDoubleOrZero(payDeductions));

            // Update salary in employee field based on payroll calculation
            double grossSalary = (currentEmployee.payroll.getRegularHours() * currentEmployee.payroll.getRegularRate())
                    + (currentEmployee.payroll.getOvertimeHours() * currentEmployee.payroll.getOvertimeRate())
                    + currentEmployee.payroll.getBonus();
            currentEmployee.setSalary(grossSalary);
            empSalary.setText(String.format("%.2f", grossSalary));

            lblPayNameValue.setText(currentEmployee.getName());

            writeEmployeeData(employeeList);

            lblOutput.setText("Showing Employee (ID: " + currentEmployee.getId() + ")");
            lblPayrollOutput.setText("Saved Payroll for Employee (ID: " + currentEmployee.getId() + ")");

            loadEmployeeDataIntoFields(); // Refresh fields
        });

        btnCalculateSalaries.setOnAction(e -> {
            double regularHours = parseDoubleOrZero(payRegularHours);
            double regularRate = parseDoubleOrZero(payRegularRate);
            double overtimeHours = parseDoubleOrZero(payOvertimeHours);
            double overtimeRate = parseDoubleOrZero(payOvertimeRate);
            double bonus = parseDoubleOrZero(payBonus);

            double income = (regularHours * regularRate) + (overtimeHours * overtimeRate) + bonus;

            lblPayrollOutput.setText("Gross Salary: " + String.format("%.2f", income));
        });

        btnCalculateTaxes.setOnAction(e -> {
            double regularHours = parseDoubleOrZero(payRegularHours);
            double regularRate = parseDoubleOrZero(payRegularRate);
            double overtimeHours = parseDoubleOrZero(payOvertimeHours);
            double overtimeRate = parseDoubleOrZero(payOvertimeRate);
            double bonus = parseDoubleOrZero(payBonus);
            double taxPercentage = parseDoubleOrZero(payTaxPercentage) / 100.0;

            double income = (regularHours * regularRate) + (overtimeHours * overtimeRate) + bonus;
            double taxes = income * taxPercentage;

            lblPayrollOutput.setText("Taxes: " + String.format("%.2f", taxes));
        });

        btnCalculateDeductions.setOnAction(e -> {
            double regularHours = parseDoubleOrZero(payRegularHours);
            double regularRate = parseDoubleOrZero(payRegularRate);
            double overtimeHours = parseDoubleOrZero(payOvertimeHours);
            double overtimeRate = parseDoubleOrZero(payOvertimeRate);
            double bonus = parseDoubleOrZero(payBonus);
            double taxPercentage = parseDoubleOrZero(payTaxPercentage) / 100.0;
            double fixedDeductions = parseDoubleOrZero(payDeductions); // Assuming payDeductions is a fixed amount

            double income = (regularHours * regularRate) + (overtimeHours * overtimeRate) + bonus;
            double calculatedTaxes = income * taxPercentage;

            // Total deduction is the sum of calculated taxes and fixed deductions
            double totalDeductions = calculatedTaxes + fixedDeductions;

            lblPayrollOutput.setText("Total Deductions (Tax + Fixed): " + String.format("%.2f", totalDeductions));
        });

        VBox reportingBox = new VBox(10);
        TitledPane reportingPane = new TitledPane("Company Reports", reportingBox);
        reportingBox.setPadding(new Insets(10,10,10,10));

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
        final Button btnSaveEmpReport = new Button("Save Employee Report");
        final Button btnSaveDeptReport = new Button("Save Department Report");

        rowReportingButtons.setStyle("-fx-background-color: #E5E4E2;");
        rowReportingButtons.setPadding(new Insets(10,10,10,10));
        rowReportingButtons.setAlignment(Pos.CENTER);
        Button btnSaveCompanyReport = new Button("Save Company Report");
        rowReportingButtons.getChildren().addAll(btnCalculateEmpReport, btnCalculateDeptReport,  btnSaveEmpReport, btnSaveDeptReport, btnSaveCompanyReport);

        HBox rowIndividualEmployee = new HBox(10);
        rowIndividualEmployee.setStyle("-fx-background-color: #E5E4E2;");
        rowIndividualEmployee.setPadding(new Insets(10,10,10,10));
        rowIndividualEmployee.setAlignment(Pos.CENTER);
        Label lblEmployeeID = new Label("Find Employee by ID");
        TextField txtEmployeeID = new TextField();
        Button btnEmployeeID = new Button("Employee ID");
        rowIndividualEmployee.getChildren().addAll(lblEmployeeID, txtEmployeeID, btnEmployeeID);

        HBox rowIndividualDepartment = new HBox(10);
        rowIndividualDepartment.setStyle("-fx-background-color: #E5E4E2;");
        rowIndividualDepartment.setPadding(new Insets(10,10,10,10));
        rowIndividualDepartment.setAlignment(Pos.CENTER);
        Label lblDepartmentName = new Label("Find Department by Name");
        TextField txtDepartmentName = new TextField();
        Button btnDepartmentName = new Button("Department Name");
        rowIndividualDepartment.getChildren().addAll(lblDepartmentName,  txtDepartmentName, btnDepartmentName);

        reportingBox.getChildren().addAll(rowReportingResults, rowReportingButtons,  rowIndividualEmployee, rowIndividualDepartment);

        btnSaveEmpReport.setOnAction(e -> {
            System.out.println("Save Employee Report");

            String employeeReport = GenerateEmployeeReport();
            txtReportingResults.setText(employeeReport);

            try {
                FileWriter fw = new FileWriter("employee_report.txt");
                fw.write(employeeReport);
                fw.close();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        btnSaveDeptReport.setOnAction(e -> {
            System.out.println("Save Department Report");

            String departmentReport = GenerateDepartmentReport();
            txtReportingResults.setText(departmentReport);

            try {
                FileWriter fw = new FileWriter("department_report.txt");
                fw.write(departmentReport);
                fw.close();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        btnSaveCompanyReport.setOnAction(e -> {
            System.out.println("Save Company Report");

            String companyReport = GenerateCompanyReport();
            txtReportingResults.setText(companyReport);

            try {
                FileWriter fw = new FileWriter("company_report.txt");
                fw.write(companyReport);
                fw.close();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        btnEmployeeID.setOnAction(e -> {
            System.out.println("Employee ID: " + txtEmployeeID.getText());
        });

        btnDepartmentName.setOnAction(e -> {
            System.out.println("Department Name: " + txtDepartmentName.getText());
        });

        btnCalculateDeptReport.setOnAction(e -> {
            String departmentReport = GenerateDepartmentReport();
            txtReportingResults.setText(departmentReport);
        });

        btnCalculateEmpReport.setOnAction(e -> {
            String employeeReport = GenerateEmployeeReport();
            txtReportingResults.setText(employeeReport);
        });

        // Defaults to expanded Employee Pane when app is launched
        //        accordion.setExpandedPane(employeesPane);

        // Separation of concerns/screens
        accordion.getPanes().addAll(employeesPane, payrollPane, reportingPane);

        Scene scene = new Scene(accordion,800, 700);


        stage.setMinWidth(800);
        stage.setMaxWidth(800);
        stage.setMinHeight(700);
        stage.setMaxHeight(700);
        stage.resizableProperty().setValue(false);
        stage.setScene(scene);
        stage.show();
    }

    public String GenerateEmployeeReport() {
        if (employeeList == null || employeeList.isEmpty()) {
            return "No employees available.\n";
        }
        else {
            StringBuilder sb = new StringBuilder();
            sb.append("====== INDIVIDUAL EMPLOYEE REPORT ======\n\n");

            for (Employee emp : employeeList) {
                sb.append("ID: ").append(emp.getId()).append("\n");
                sb.append("Name: ").append(emp.getName()).append("\n");
                sb.append("Position: ").append(emp.getPosition()).append("\n");

                // Calculate Net Pay on the fly for the report
                double gross = emp.getSalary();
                double tax = 0;
                double deduct = 0;

                // Check if payroll object exists to get accurate tax/deductions
                if(emp.payroll != null) {
                    tax = gross * (emp.payroll.getTaxPercentage() / 100);
                    deduct = emp.payroll.getDeductions();
                }

                double netPay = gross - tax - deduct;

                sb.append(String.format("Gross Pay:   $%.2f\n", gross));
                sb.append(String.format("Taxes:       $%.2f\n", tax));
                sb.append(String.format("Deductions:  $%.2f\n", deduct));
                sb.append(String.format("NET PAY:     $%.2f\n", netPay));
                sb.append("-----------------------------------\n");
            }

            return sb.toString();
        }
    }

    public String GenerateDepartmentReport() {
        // 1. Check if there is data
        if (employeeList == null || employeeList.isEmpty()) {
            return "No employees available to report.\n";
        }
        else {
            StringBuilder sb = new StringBuilder();
            sb.append("====== DEPARTMENT REPORT ======\n\n");

            // 2. Find all unique departments first
            Set<String> uniqueDepartments = new HashSet<>();
            for (Employee emp : employeeList) {
                // Handle potential null departments to avoid crashes
                String dept = (emp.getDepartment() == null) ? "Unknown" : emp.getDepartment();
                uniqueDepartments.add(dept);
            }

            // 3. Loop through each unique department to calculate totals
            for (String dept : uniqueDepartments) {
                double deptTotalSalary = 0;
                int deptEmployeeCount = 0;
                StringBuilder employeesInDept = new StringBuilder();

                for (Employee emp : employeeList) {
                    String currentEmpDept = (emp.getDepartment() == null) ? "Unknown" : emp.getDepartment();

                    // If this employee belongs to the current department loop
                    if (currentEmpDept.equalsIgnoreCase(dept)) {
                        deptTotalSalary += emp.getSalary();
                        deptEmployeeCount++;
                        // Add their name to a temp list
                        employeesInDept.append(String.format("   - %s (ID: %d)\n", emp.getName(), emp.getId()));
                    }
                }

                // Build the report section for this department
                sb.append("DEPARTMENT: ").append(dept.toUpperCase()).append("\n");
                sb.append("Headcount: ").append(deptEmployeeCount).append("\n");
                sb.append(String.format("Total Salary Budget: $%.2f\n", deptTotalSalary));
                sb.append("Staff List:\n").append(employeesInDept);
                sb.append("-----------------------------------\n");
            }

            return sb.toString();
        }
    }

    private String GenerateCompanyReport() {
        if (employeeList == null || employeeList.isEmpty()) {
            return "No employees to report.";
        }

        int totalEmployees = employeeList.size();

        Set<String> departments = new HashSet<>();
        double totalPayrollCost = 0;

        for (Employee emp : employeeList) {
            departments.add(emp.getDepartment());
            totalPayrollCost += emp.getSalary();
        }

        double averageSalary = totalEmployees > 0 ? totalPayrollCost / totalEmployees : 0;
        int totalDepartments = departments.size();

        StringBuilder report = new StringBuilder();
        report.append("========== COMPANY REPORT ==========\n\n");
        report.append("Total Employees: ").append(totalEmployees).append("\n");
        report.append("Total Departments: ").append(totalDepartments).append("\n");
        report.append(String.format("Total Payroll Cost: $%.2f\n", totalPayrollCost));
        report.append(String.format("Average Salary: $%.2f\n", averageSalary));
        report.append("\n------------------------------------\n");

        report.append("Departments:\n");
        for (String dept : departments) {
            report.append("- ").append(dept).append("\n");
        }

        report.append("------------------------------------\n");
        report.append("Employees:\n");
        for (Employee emp : employeeList) {
            report.append(emp.getName())
                    .append(" | ")
                    .append(emp.getDepartment())
                    .append(" | $")
                    .append(String.format("%.2f", emp.getSalary()))
                    .append("\n");
        }

        return report.toString();
    }

    public ArrayList<Employee> readEmployeeData() {
        ArrayList<Employee> employeeList = new ArrayList<>();

        try {
            Gson gson = new Gson();

            Type employeeListType = new TypeToken<ArrayList<Employee>>(){}.getType();
            FileReader reader = new FileReader("employees.json");

            employeeList = gson.fromJson(reader, employeeListType);
            reader.close();

            // System.out.println(employeeList.size() + " Employee records read from employees.json");
        }
        catch (Exception e) {
           System.out.println("Error Reading Employee Data");
        }

        return employeeList;
    }

    public boolean writeEmployeeData(ArrayList<Employee> employeeList) {
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            FileWriter writer = new FileWriter("employees.json");
            gson.toJson(employeeList, writer);

            writer.close();
            // System.out.println(employeeList.size() + " Employee records written to employees.json"); // Fix the truncated printout
        }
        catch (Exception e) {
           System.out.println("Error Writing Employee Data");
        }
        return false;
    }
}
