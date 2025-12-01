package org.example.assignment2;

import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.geometry.Insets;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class CompanyReport {

    private VBox root;

    // UI Components
    private Label lblTotalEmployees;
    private Label lblTotalDepartments;
    private Label lblTotalPayrollCost;
    private Label lblAverageSalary;

    private TextArea txtReportOutput;
    private Button btnGenerateReport;
    private Button btnExportReport;

    private ArrayList<Employee> employeeList;

    public CompanyReport(ArrayList<Employee> employeeList) {
        this.employeeList = employeeList;
        buildUI();
    }

    public VBox getUI() {
        return root;
    }

    private void buildUI() {

        root = new VBox(15);
        root.setPadding(new Insets(15));

        Label title = new Label("Company Report");
        title.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");

        // Summary stats
        lblTotalEmployees = new Label("Total Employees: 0");
        lblTotalDepartments = new Label("Total Departments: 0");
        lblTotalPayrollCost = new Label("Total Payroll Cost: $0.00");
        lblAverageSalary = new Label("Average Salary: $0.00");

        VBox summaryBox = new VBox(8,
                lblTotalEmployees,
                lblTotalDepartments,
                lblTotalPayrollCost,
                lblAverageSalary
        );
        summaryBox.setPadding(new Insets(10));
        summaryBox.setStyle("-fx-border-color: #ccc; -fx-border-radius: 5; -fx-padding: 10;");

        // Report output area
        txtReportOutput = new TextArea();
        txtReportOutput.setPrefHeight(250);
        txtReportOutput.setEditable(false);
        txtReportOutput.setStyle("-fx-font-family: 'Consolas'; -fx-font-size: 13px;");

        ScrollPane scrollPane = new ScrollPane(txtReportOutput);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefHeight(300);

        // Buttons
        btnGenerateReport = new Button("Generate Company Report");
        btnExportReport = new Button("Export as Text File");

        HBox buttonBar = new HBox(10, btnGenerateReport, btnExportReport);

        root.getChildren().addAll(
                title,
                summaryBox,
                scrollPane,
                buttonBar
        );

        // Event handlers
        btnGenerateReport.setOnAction(e -> generateCompanyReport());
        btnExportReport.setOnAction(e -> exportReport());
    }

    private void generateCompanyReport() {

        // Total employees
        int totalEmployees = employeeList.size();

        // Total departments
        Set<String> departments = new HashSet<>();
        for (Employee emp : employeeList) {
            departments.add(emp.getDepartment());
        }
        int totalDepartments = departments.size();

        // Total payroll cost and average salary
        double totalPayrollCost = 0;
        double totalSalary = 0;
        for (Employee emp : employeeList) {
            totalPayrollCost += emp.getSalary();
            totalSalary += emp.getSalary();
        }
        double averageSalary = totalEmployees > 0 ? totalSalary / totalEmployees : 0;

        lblTotalEmployees.setText("Total Employees: " + totalEmployees);
        lblTotalDepartments.setText("Total Departments: " + totalDepartments);
        lblTotalPayrollCost.setText(String.format("Total Payroll Cost: $%.2f", totalPayrollCost));
        lblAverageSalary.setText(String.format("Average Salary: $%.2f", averageSalary));

        // Build text report
        StringBuilder report = new StringBuilder();
        report.append("========== COMPANY REPORT ==========\n\n");
        report.append("Total Employees: ").append(totalEmployees).append("\n");
        report.append("Total Departments: ").append(totalDepartments).append("\n");
        report.append(String.format("Total Payroll Cost: $%.2f\n", totalPayrollCost));
        report.append(String.format("Average Salary: $%.2f\n", averageSalary));
        report.append("\n------------------------------------\n");
        report.append("Department Breakdown:\n");
        for (String dept : departments) {
            report.append(" - ").append(dept).append("\n");
        }
        report.append("------------------------------------\n");
        report.append("Employee Summary:\n");
        for (Employee emp : employeeList) {
            report.append("ID: ").append(emp.getId())
                    .append(" | Name: ").append(emp.getName())
                    .append(" | Department: ").append(emp.getDepartment())
                    .append(" | Salary: $").append(String.format("%.2f", emp.getSalary()))
                    .append("\n");
        }
        report.append("------------------------------------\n");

        txtReportOutput.setText(report.toString());
    }

    private void exportReport() {
        try {
            FileWriter fw = new FileWriter("company_report.txt");
            fw.write(txtReportOutput.getText());
            fw.close();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Report Exported");
            alert.setContentText("company_report.txt has been saved successfully.");
            alert.show();

        } catch (Exception ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Export Failed");
            alert.setContentText("Could not save the report.");
            alert.show();
        }
    }
}