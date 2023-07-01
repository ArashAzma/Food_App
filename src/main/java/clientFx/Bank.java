package clientFx;

import common.Admin;

public abstract class Bank {
    protected double tax = 0.05;
    private String accountNumber;
    private String passWord;
    private String cvv2;
    private int day;
    private int year;
    protected double cost;

    public Bank(String accountNumber, String passWord, String cvv2, int day, int year) {
        this.accountNumber = accountNumber;
        this.passWord = passWord;
        this.cvv2 = cvv2;
        this.day = day;
        this.year = year;
    }

    public abstract void deposit(double amount);
    public double getCost(){ return cost;}
}
