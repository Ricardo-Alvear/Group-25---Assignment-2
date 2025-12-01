package org.example.assignment2;

public class Employee {

    private int id;
    protected String name;
    protected String email;
    protected String phone;
    protected String department;

    protected String position;

    protected Payroll payroll;

    public Employee(int id, String name, String email, String phone, String department, double salary, String position) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.department = department;
        this.position = position;

        this.payroll = new Payroll(id, id, 0, 0, 0, 0, 0, 0, 0, 0);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public double getSalary(){
        return payroll.calculateTotalSalary();
    }
}
