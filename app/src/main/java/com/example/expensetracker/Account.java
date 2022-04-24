package com.example.expensetracker;

public class Account {
    private String name;
    private String ammount;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAmmount() {
        return ammount;
    }

    public void setAmmount(String ammount) {
        this.ammount = ammount;
    }


    public Account(String name, String ammount){
        this.name = name;
        this.ammount = ammount;
    }
}
