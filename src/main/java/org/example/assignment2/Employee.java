package org.example.assignment2;

public class Employee {

    private int id;
    protected String name;
    protected String email;
    protected String phone;
    protected String department;
    private double salary; // Holds the last calculated Gross Salary
    protected String position;

    public Payroll payroll; // Publicly accessible for easy management in HelloApplication

    public Employee(int id, String name, String email, String phone, String department, double salary, String position) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.department = department;
        this.position = position;
        this.salary = salary;
        this.payroll = null; // Initialized to null, handled in HelloApplication's load/create logic
    }

    // --- Getters ---
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getDepartment() {
        return department;
    }

    public String getPosition() {
        return position;
    }

    // Returns the calculated Gross Salary based on the linked payroll object.
    public double getSalary() {
        if (payroll != null) {
            return (payroll.getRegularRate() * payroll.getRegularHours())
                    + (payroll.getOvertimeRate() * payroll.getOvertimeHours())
                    + payroll.getBonus();
        }
        return salary; // Fallback to the stored 'salary' field if payroll is null (though this shouldn't happen after initialization)
    }

    // --- Setters ---
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    // This setter is used by HelloApplication to store the calculated GROSS salary back into the employee object.
    public void setSalary(double salary) {
        this.salary = salary;
    }
}