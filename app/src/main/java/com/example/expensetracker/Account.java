package com.example.expensetracker;

public class Account {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAmmount() {
        return ammount;
    }

    public void setAmmount(double ammount) {
        this.ammount = ammount;
    }

    private double ammount;

    public Account(String name, double ammount){
        this.name = name;
        this.ammount = ammount;
    }
}
