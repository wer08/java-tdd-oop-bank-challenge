package com.booleanuk.core;

import java.util.ArrayList;
import java.util.List;

import static com.booleanuk.core.TransactionType.*;

public class Account {
    private String branchID;
    private final List<Transaction> transactions;
    private final String accountNumber;
    private final User owner;
    private User manager;

    public void setManager(User manager) {
        this.manager = manager;
    }

    public void setBranchID(String branchID) {
        this.branchID = branchID;
    }

    private static int DEFAULT_ACCOUNT_NUMBER = 999;

    public Account(String branchID, User owner, User manager) {
        DEFAULT_ACCOUNT_NUMBER++;
        this.branchID = branchID;
        this.transactions = new ArrayList<>();
        this.accountNumber = String.valueOf(DEFAULT_ACCOUNT_NUMBER);
        this.owner = owner;
        this.manager = manager;
        this.owner.getAccounts().add(this);
        Bank.getAccountList().add(this);
    }

    public String getBranchID() {
        return branchID;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public User getOwner() {
        return owner;
    }

    public User getManager() {
        return manager;
    }

    public double getBalance() {
        return transactions.stream()
                .mapToDouble(transaction -> transaction.getType() == DEPOSIT || transaction.getType() == WIRE_FROM ? transaction.getAmount() : -transaction.getAmount() )
                .sum();
    }

    public void deposit(double amount) {
        transactions.add(new Transaction(DEPOSIT,amount));
        System.out.println("Money was successfully deposed");
    }
}
