package com.example.phili.debttracker;


/**
 * This class represents a generic account that appears in the application.
 *
 */
public class Account {

    // Instance variables
    private String name;
    private double amount;
    private int accountType;
    private int photo;

    /**
     * This is the parameterized constructor for the Account class. It instantiates
     * the attributes for the class.
     */
    public Account(String name, double amount, int accountType, int photo) {
        setName(name);
        setAmount(amount);
        setAccountType(accountType);
        setPhoto(photo);
    }

    /**
     * Accessor methods for the Account class.
     */
    public int getAccountType() {
        return accountType;
    }

    public double getAmount() {
        return amount;
    }

    public String getName() {
        return name;
    }

    public int getPhoto() {
        return photo;
    }

    /**
     * Mutator methods for the Account class.
     */
    public void setAccountType(int accountType) {
        this.accountType = accountType;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }

    /**
     * Adds a value to the current amount associated with the Account.
     */
    public void addAmount(double amount) {
        this.amount += amount;
    }
}
