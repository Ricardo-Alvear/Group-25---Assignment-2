package org.example.assignment2;

public class Department {
    private int id;
    protected String name;
    protected String manager;

    public Department(int id, String name, String manager) {
        this.id = id;
        this.name = name;
        this.manager = manager;
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

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }
}