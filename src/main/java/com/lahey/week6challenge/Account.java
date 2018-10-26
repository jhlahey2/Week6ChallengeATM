package com.lahey.week6challenge;

import java.util.ArrayList;

public class Account {

    private double balance;
    private int accountNumber;
    private ArrayList<Transaction> transactionList = new ArrayList<Transaction>();

    public Account(int number){

        this.accountNumber = number;
        this.balance = 0;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

//    public void setAccountNumber(int accountNumber) {
//        this.accountNumber = accountNumber;
//    }

}
