package com.example.expensetracker;

public class Transaction {

    public String getTransAccount() {
        return transAccount;
    }

    public void setTransAccount(String transAccount) {
        this.transAccount = transAccount;
    }

    public String getTransCategory() {
        return transCategory;
    }

    public void setTransCategory(String transCategory) {
        this.transCategory = transCategory;
    }

    public String getTransAmount() {
        return transAmount;
    }

    public void setTransAmount(String transAmount) {
        this.transAmount = transAmount;
    }

    private String transAccount;
    private String transCategory;
    private String transAmount;

    public Transaction(String transAccount, String transCategory, String transAmount){
        this.transAccount=transAccount;
        this.transAmount=transAmount;
        this.transCategory=transCategory;
    }

    public Transaction(){

    }
}
