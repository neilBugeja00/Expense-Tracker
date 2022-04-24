package com.example.expensetracker;

public class Category {

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public String getCatAmmount() {
        return catAmmount;
    }

    public void setCatAmmount(String catAmmount) {
        this.catAmmount = catAmmount;
    }

    private String catName;
    private String catAmmount;

    public Category(){

    }

    public Category(String catName, String catAmmount){
        this.catName = catName;
        this.catAmmount = catAmmount;
    }
}
