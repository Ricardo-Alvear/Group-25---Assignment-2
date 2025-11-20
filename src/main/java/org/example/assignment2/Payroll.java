package org.example.assignment2;

public class Payroll {
    private int payrollId;
    private int employeeId;
    private double baseSalary;
    private double bonus;
    private double deductions;

    public Payroll(int payrollId, int employeeId, double baseSalary, double deductions, double bonus, double totalSalary) {
        this.payrollId = payrollId;
        this.employeeId = employeeId;
        this.baseSalary = baseSalary;
        this.deductions = deductions;
        this.bonus = bonus;
        this.totalSalary = totalSalary;
    }

    // The amount of the salary remaining after the calculations.
    private double totalSalary;

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

    public double getBaseSalary() {
        return baseSalary;
    }

    public void setBaseSalary(double baseSalary) {
        this.baseSalary = baseSalary;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public double getTotalSalary() {
        return totalSalary;
    }

    public void setTotalSalary(double totalSalary) {
        this.totalSalary = totalSalary;
    }

    public double getDeductions() {
        return deductions;
    }

    public void setDeductions(double deductions) {
        this.deductions = deductions;
    }







}
