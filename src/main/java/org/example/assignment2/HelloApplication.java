
// Members: Ricardo Alvear, Joe Macdonald, Daniel Le Huenen
// Group: 25
// Course: COMP2130
// Term: Fall 2025


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
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;


class DataIO {
    public static ArrayList<Employee> readEmployeeData() {
        // Mock data for testing
        ArrayList<Employee> list = new ArrayList<>();
        // Mock employees now initialized with payroll fields
        Employee alice = new Employee(1, "Alice Smith", "alice@corp.com", "555-0101", "IT", 60000.0, "Developer");
        alice.payroll = new Payroll(1, 1, 30.0, 160.0, 45.0, 10.0, 500.0, 20.0, 100.0, 0.0); // Sample payroll data
        list.add(alice);

        Employee bob = new Employee(2, "Bob Johnson", "bob@corp.com", "555-0202", "HR", 50000.0, "Recruiter");
        bob.payroll = new Payroll(2, 2, 25.0, 160.0, 37.5, 0.0, 100.0, 15.0, 50.0, 0.0); // Sample payroll data
        list.add(bob);
        return list;
    }
    public static void writeEmployeeData(ArrayList<Employee> employees) {
        // Implementation to write to JSON (omitted)
        System.out.println("Employee data written (mock)");
    }
}


public class HelloApplication extends Application {

    // ... (All existing final fields for UI components remain here)
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

    private final Button btnCalculateEmpReport = new Button("Calculate Employee Report");
    private final Button btnCalculateDeptReport = new Button("Calculate Department Report");


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
        // Use current employee's calculated salary
        empSalary.setText(String.format("%.2f", currentEmployee.getSalary()));
        empPosition.setText(currentEmployee.getPosition());

        // Payroll fields
        // Since we checked and created if null above, we can safely access payroll here
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

    private void showErrorAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    // Comprehensive validation for all input fields (Employee and Payroll)
    private boolean validateInputs() {
        // Employee Fields Validation
        if (empName.getText().trim().isEmpty() ||
                empEmail.getText().trim().isEmpty() ||
                empPhone.getText().trim().isEmpty() ||
                empDepartment.getText().trim().isEmpty() ||
                empPosition.getText().trim().isEmpty()) {
            showErrorAlert("Input Error", "All Employee Name, Email, Phone, Department, and Position fields must be filled.");
            return false;
        }

        // Payroll Fields Validation (Numerical)
        try {
            // We use parseDoubleOrZero inside the actions, but here we validate that the fields *contain* valid numbers
            double regRate = Double.parseDouble(payRegularRate.getText());
            double regHours = Double.parseDouble(payRegularHours.getText());
            double ovtRate = Double.parseDouble(payOvertimeRate.getText());
            double ovtHours = Double.parseDouble(payOvertimeHours.getText());
            double bonus = Double.parseDouble(payBonus.getText());
            double taxPct = Double.parseDouble(payTaxPercentage.getText());
            double deductions = Double.parseDouble(payDeductions.getText());

            if (regRate < 0 || regHours < 0 || ovtRate < 0 || ovtHours < 0 || bonus < 0 || taxPct < 0 || deductions < 0) {
                showErrorAlert("Input Error", "All Payroll numerical fields (Rates, Hours, Bonus, Tax %, Deductions) must be zero or a positive number.");
                return false;
            }

        } catch (NumberFormatException e) {
            showErrorAlert("Input Error", "All Payroll fields must contain valid numbers (e.g., 50.0 or 0).");
            return false;
        }

        return true;
    }


    private String generateCompanyReportString() {
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

    @Override
    public void start(Stage stage)  {
        // Load Employee Data
        // Load employees are retrieved from an external JSON file
        employeeList = DataIO.readEmployeeData(); // Use DataIO

        currentEmployee = null;
        currentEmployeeIndex = -1;

        if (employeeList != null && !employeeList.isEmpty()) {
            currentEmployee = employeeList.getFirst();
            currentEmployeeIndex = employeeList.indexOf(currentEmployee);
            lblOutput.setText("Showing Employee (ID: " + currentEmployee.getId() + ")");
            lblPayrollOutput.setText("Showing Employee (ID: " + currentEmployee.getId() + ")");
        }

        // Load Payroll Data (Commented out as per original code structure)

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
// --- Employees Pane Setup ---
        VBox rootBox = new VBox(10);
        TitledPane employeesPane = new TitledPane("Employees Details", rootBox);
        rootBox.setPadding(new Insets(10,10,10,10));

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
        // empSalary.setEditable(false);
        if (currentEmployee != null) {
            // Using getSalary() which calculates the salary from payroll data
            empSalary.setText(String.format("%.2f", currentEmployee.getSalary()));
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
            if (!validateInputs()) {
                return; // Stop if validation fails
            }

            // Determine the next ID
            int highestId = employeeList.stream()
                    .mapToInt(Employee::getId)
                    .max()
                    .orElse(0);

            // Employee creation: Salary is calculated from payroll values input in the payroll tab
            Employee newEmployee = new Employee(
                    highestId + 1,
                    empName.getText(),
                    empEmail.getText(),
                    empPhone.getText(),
                    empDepartment.getText(),
                    0.0, // Initial salary is set to 0.0, will be updated by payroll
                    empPosition.getText()
            );

            // Initialize a blank payroll object for the new employee
            newEmployee.payroll = createEmptyPayroll();

            // Set initial payroll data from input fields (they are validated to be numbers)
            newEmployee.payroll.setRegularRate(parseDoubleOrZero(payRegularRate));
            newEmployee.payroll.setRegularHours(parseDoubleOrZero(payRegularHours));
            newEmployee.payroll.setOvertimeRate(parseDoubleOrZero(payOvertimeRate));
            newEmployee.payroll.setOvertimeHours(parseDoubleOrZero(payOvertimeHours));
            newEmployee.payroll.setBonus(parseDoubleOrZero(payBonus));
            newEmployee.payroll.setTaxPercentage(parseDoubleOrZero(payTaxPercentage));
            newEmployee.payroll.setDeductions(parseDoubleOrZero(payDeductions));

            // Recalculate salary based on entered payroll data
            double grossSalary = (newEmployee.payroll.getRegularHours() * newEmployee.payroll.getRegularRate())
                    + (newEmployee.payroll.getOvertimeHours() * newEmployee.payroll.getOvertimeRate())
                    + newEmployee.payroll.getBonus();
            newEmployee.setSalary(grossSalary);
            empSalary.setText(String.format("%.2f", grossSalary)); // Update UI field

            employeeList.add(newEmployee);
            DataIO.writeEmployeeData(employeeList); // Use DataIO to save

            // Navigate to the newly created employee
            navigateToEmployee(employeeList.size() - 1);

            lblOutput.setText("Created Employee (ID: " + currentEmployee.getId() + ")");
            lblPayrollOutput.setText("Showing Employee (ID: " + currentEmployee.getId() + ")");
        });

        btnUpdateEmployee.setOnAction(e -> {
            if (currentEmployee == null) {
                lblOutput.setText("No employee selected to update.");
                return;
            }
            if (!validateInputs()) {
                return; // Stop if validation fails
            }

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

            DataIO.writeEmployeeData(employeeList); // Use DataIO to save

            lblOutput.setText("Updated Employee (ID: " + currentEmployee.getId() + ")");
            lblPayrollOutput.setText("Showing Employee (ID: " + currentEmployee.getId() + ")");

            loadEmployeeDataIntoFields(); // Refresh fields
        });


        btnDeleteEmployee.setOnAction(e -> {

            if (currentEmployee == null) return;

            int deletedId = currentEmployee.getId();
            employeeList.remove(currentEmployee);
            DataIO.writeEmployeeData(employeeList); // Use DataIO to save

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

        rootBox.getChildren().addAll(rowName, rowEmail, rowPhone,  rowDepartment, rowSalary,  rowPosition,
                rowButtons, rowOutput);

// --- Payroll Pane Setup ---
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
        rowPayName.getChildren().addAll(lblPayName, lblPayNameValue);

        HBox rowRegularRate = new HBox(10);
        rowRegularRate.setStyle("-fx-background-color: lightblue;");
        rowRegularRate.setPadding(new Insets(10,10,10,10));
        rowRegularRate.setHgrow(payRegularRate, Priority.ALWAYS);
        payRegularRate.setMaxWidth(300);
        // Initial setup for payroll fields
        if (currentEmployee != null && currentEmployee.payroll != null) {
            payRegularRate.setText(String.valueOf(currentEmployee.payroll.getRegularRate()));
        }
        rowRegularRate.getChildren().addAll(lblRegularRate, payRegularRate);

        HBox rowRegularHours = new HBox(10);
        rowRegularHours.setStyle("-fx-background-color: #87CEEB;");
        rowRegularHours.setPadding(new Insets(10,10,10,10));
        rowRegularHours.setHgrow(payRegularHours, Priority.ALWAYS);
        payRegularHours.setMaxWidth(300);
        if (currentEmployee != null && currentEmployee.payroll != null) {
            payRegularHours.setText(String.valueOf(currentEmployee.payroll.getRegularHours()));
        }
        rowRegularHours.getChildren().addAll(lblRegularHours, payRegularHours);

        HBox rowOvertimeRate = new HBox(10);
        rowOvertimeRate.setStyle("-fx-background-color: lightblue;");
        rowOvertimeRate.setPadding(new Insets(10,10,10,10));
        rowOvertimeRate.setHgrow(payOvertimeRate, Priority.ALWAYS);
        payOvertimeRate.setMaxWidth(300);
        if (currentEmployee != null && currentEmployee.payroll != null) {
            payOvertimeRate.setText(String.valueOf(currentEmployee.payroll.getOvertimeRate()));
        }
        rowOvertimeRate.getChildren().addAll(lblOvertimeRate, payOvertimeRate);

        HBox rowOvertimeHours = new HBox(10);
        rowOvertimeHours.setStyle("-fx-background-color: #87CEEB;");
        rowOvertimeHours.setPadding(new Insets(10,10,10,10));
        rowOvertimeHours.setHgrow(payOvertimeHours, Priority.ALWAYS);
        payOvertimeHours.setMaxWidth(300);
        if (currentEmployee != null && currentEmployee.payroll != null) {
            payOvertimeHours.setText(String.valueOf(currentEmployee.payroll.getOvertimeHours()));
        }
        rowOvertimeHours.getChildren().addAll(lblOvertimeHours, payOvertimeHours);

        HBox rowBonus = new HBox(10);
        rowBonus.setStyle("-fx-background-color: lightblue;");
        rowBonus.setPadding(new Insets(10,10,10,10));
        rowBonus.setHgrow(payBonus, Priority.ALWAYS);
        payBonus.setMaxWidth(300);
        if (currentEmployee != null && currentEmployee.payroll != null) {
            payBonus.setText(String.valueOf(currentEmployee.payroll.getBonus()));
        }
        rowBonus.getChildren().addAll(lblBonus, payBonus);

        HBox rowTaxPercentage = new HBox(10);
        rowTaxPercentage.setStyle("-fx-background-color: #87CEEB;");
        rowTaxPercentage.setPadding(new Insets(10,10,10,10));
        rowTaxPercentage.setHgrow(payTaxPercentage, Priority.ALWAYS);
        payTaxPercentage.setMaxWidth(300);
        if (currentEmployee != null && currentEmployee.payroll != null) {
            payTaxPercentage.setText(String.valueOf(currentEmployee.payroll.getTaxPercentage()));
        }
        rowTaxPercentage.getChildren().addAll(lblTaxPercentage, payTaxPercentage);

        HBox rowDeductions = new HBox(10);
        rowDeductions.setStyle("-fx-background-color: lightblue;");
        rowDeductions.setPadding(new Insets(10,10,10,10));
        rowDeductions.setHgrow(payDeductions, Priority.ALWAYS);
        payDeductions.setMaxWidth(300);
        if (currentEmployee != null && currentEmployee.payroll != null) {
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
        // Changed to reflect "unambiguous separation" of controls (6B)
        rowPayrollButtons.getChildren().addAll(btnPayrollPrevEmployee, btnSavePayroll, btnCalculateSalaries, btnCalculateTaxes, btnCalculateDeductions, btnPayrollNextEmployee);

        payrollBox.getChildren().addAll(rowPayName, rowRegularRate, rowRegularHours, rowOvertimeRate, rowOvertimeHours, rowBonus, rowTaxPercentage, rowDeductions, rowPayrollButtons, rowPayrollOutput);
        // payrollBox.getChildren().addAll(rowPayrollResults, rowPayrollButtons);

        btnPayrollNextEmployee.setOnAction(e -> {
            navigateToEmployee(currentEmployeeIndex + 1);
        });

        btnPayrollPrevEmployee.setOnAction(e -> {
            navigateToEmployee(currentEmployeeIndex - 1);
        });

        btnSavePayroll.setOnAction(e -> {
            if (currentEmployee == null) {
                lblPayrollOutput.setText("No employee selected to save payroll for.");
                return;
            }
            if (!validateInputs()) {
                return; // Stop if validation fails
            }

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
            empSalary.setText(String.format("%.2f", grossSalary)); // Update salary in Employee tab

            lblPayNameValue.setText(currentEmployee.getName());

            DataIO.writeEmployeeData(employeeList); // Use DataIO to save

            lblOutput.setText("Showing Employee (ID: " + currentEmployee.getId() + ")");
            lblPayrollOutput.setText("Saved Payroll for Employee (ID: " + currentEmployee.getId() + ")");

            loadEmployeeDataIntoFields(); // Refresh fields
        });

        btnCalculateSalaries.setOnAction(e -> {
            if (currentEmployee == null) {
                lblPayrollOutput.setText("No employee selected.");
                return;
            }
            // No need to validate inputs here as parseDoubleOrZero handles bad input gracefully for calculation display

            double regularHours = parseDoubleOrZero(payRegularHours);
            double regularRate = parseDoubleOrZero(payRegularRate);
            double overtimeHours = parseDoubleOrZero(payOvertimeHours);
            double overtimeRate = parseDoubleOrZero(payOvertimeRate);
            double bonus = parseDoubleOrZero(payBonus);

            double income = (regularHours * regularRate) + (overtimeHours * overtimeRate) + bonus;

            lblPayrollOutput.setText("Gross Salary: " + String.format("%.2f", income));
        });

        btnCalculateTaxes.setOnAction(e -> {
            if (currentEmployee == null) {
                lblPayrollOutput.setText("No employee selected.");
                return;
            }

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
            if (currentEmployee == null) {
                lblPayrollOutput.setText("No employee selected.");
                return;
            }

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

// --- Reporting Pane Setup (Using CompanyReport class) ---
        CompanyReport companyReportModule = new CompanyReport(employeeList);
        VBox reportingBox = companyReportModule.getUI();
        TitledPane reportingPane = new TitledPane("Company Reports", reportingBox);

        // The original reporting pane structure is now replaced by the CompanyReport object's UI.
        // We ensure all necessary controls are accessible.

        // HBox rowReportingResults = new HBox(10); // Not needed, handled internally by CompanyReport
        // HBox rowReportingButtons = new HBox(10); // Not needed, handled internally by CompanyReport

        HBox rowIndividualEmployee = new HBox(10);
        rowIndividualEmployee.setStyle("-fx-background-color: #E5E4E2;");
        rowIndividualEmployee.setPadding(new Insets(10,10,10,10));
        rowIndividualEmployee.setAlignment(Pos.CENTER);
        Label lblEmployeeID = new Label("Find Employee by ID");
        TextField txtEmployeeID = new TextField();
        Button btnEmployeeID = new Button("Generate Employee Report");
        rowIndividualEmployee.getChildren().addAll(lblEmployeeID, txtEmployeeID, btnEmployeeID);

        HBox rowIndividualDepartment = new HBox(10);
        rowIndividualDepartment.setStyle("-fx-background-color: #E5E4E2;");
        rowIndividualDepartment.setPadding(new Insets(10,10,10,10));
        rowIndividualDepartment.setAlignment(Pos.CENTER);
        Label lblDepartmentName = new Label("Find Department by Name");
        TextField txtDepartmentName = new TextField();
        Button btnDepartmentName = new Button("Generate Department Report");
        rowIndividualDepartment.getChildren().addAll(lblDepartmentName,  txtDepartmentName, btnDepartmentName);

        // Append the individual report controls back to the reportingBox (CompanyReport VBox)
        reportingBox.getChildren().addAll(rowIndividualEmployee, rowIndividualDepartment);


        btnEmployeeID.setOnAction(e -> {
            System.out.println("Employee ID: " + txtEmployeeID.getText());
            // Add logic here to find employee by ID and display their individual report
        });


        btnDepartmentName.setOnAction(e -> {
            System.out.println("Department Name: " + txtDepartmentName.getText());
            // Add logic here to find department by name and display their collective report
        });

        // The overall company and department reports are now handled by the buttons inside the CompanyReport UI itself
        // The original btnCalculateDeptReport and btnCalculateEmpReport handlers are functionally replaced by
        // the Generate Company Report button in the CompanyReport module, which calculates the whole report.

        // NOTE: If you need to map the old buttons to the new module's functions:
        // btnCalculateEmpReport.setOnAction(e -> companyReportModule.generateCompanyReport());
        // btnCalculateDeptReport.setOnAction(e -> companyReportModule.generateCompanyReport()); // Use company report for overall summary

// --- Final Scene Setup ---
        // Add the accordion to the main scene
        accordion.getPanes().addAll(employeesPane, payrollPane, reportingPane);
        accordion.setExpandedPane(employeesPane); // Start with Employees expanded

        Scene scene = new Scene(accordion, 800, 700);
        stage.setScene(scene);
        stage.show();

        // Initial load of data into fields if employees exist
        if (currentEmployee != null) {
            loadEmployeeDataIntoFields();
        }
    }
}