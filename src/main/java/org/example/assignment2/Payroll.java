package org.example.assignment2;

public class Payroll {
    private int payrollId;
    private int employeeId;
    private double regularRate;
    private double regularHours;
    private double overtimeRate;
    private double overtimeHours;
    private double bonus;
    private double taxPercentage;
    private double deductions;

    // The amount of the salary remaining after the calculations.
    private double totalSalary;

    public Payroll(int payrollId, int employeeId, double regularRate, double regularHours, double overtimeRate, double overtimeHours, double bonus, double taxPercentage, double deductions, double totalSalary) {
        this.payrollId = payrollId;
        this.employeeId = employeeId;
        this.regularRate = regularRate;
        this.regularHours = regularHours;
        this.overtimeRate = overtimeRate;
        this.overtimeHours = overtimeHours;
        this.bonus = bonus;
        this.taxPercentage = taxPercentage;
        this.deductions = deductions;
        this.totalSalary = totalSalary;
    }

    public int getPayrollId() {
        return payrollId;
    }

    public void setPayrollId(int payrollId) {
        this.payrollId = payrollId;
    }

    public double getBonus() {
        return bonus;
    }

    public void setBonus(double bonus) {
        this.bonus = bonus;
    }

    public double getRegularRate() {
        return regularRate;
    }

    public void setRegularRate(double regularRate) {
        this.regularRate = regularRate;
    }

    public double getRegularHours() {
        return regularHours;
    }

    public void setRegularHours(double regularHours) {
        this.regularHours = regularHours;
    }

    public double getOvertimeRate() {
        return overtimeRate;
    }

    public void setOvertimeRate(double overtimeRate) {
        this.overtimeRate = overtimeRate;
    }

    public double getOvertimeHours() {
        return overtimeHours;
    }

    public void setOvertimeHours(double overtimeHours) {
        this.overtimeHours = overtimeHours;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public double getTaxPercentage() {
        return taxPercentage;
    }

    public void setTaxPercentage(double taxPercentage) {
        this.taxPercentage = taxPercentage;
    }

    public double getDeductions() {
        return deductions;
    }

    public void setDeductions(double deductions) {
        this.deductions = deductions;
    }

    public double getTotalSalary() {
        return totalSalary;
    }

    public void setTotalSalary(double totalSalary) {
        this.totalSalary = totalSalary;
    }

    public double calculateTotalSalary(){
        double gross = (regularRate * regularHours) +
                       (overtimeRate * overtimeHours) +
                       bonus;

        double taxAmount = gross * (taxPercentage / 100);
        return gross = taxAmount - deductions;
    }


}
