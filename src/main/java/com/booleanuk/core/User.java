package com.booleanuk.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.booleanuk.core.TransactionType.WITHDRAW;

public class User {
    private final String name;
    private final List<Account> accounts;

    private boolean isManager;

    public boolean isManager() {
        return isManager;
    }

    public void setManager(boolean manager) {
        isManager = manager;
    }

    public User(String name) {
        this.name = name;
        accounts = new ArrayList<>();
        Bank.getUsersList().add(this);
    }

    public String getName() {
        return name;
    }


    public List<Account> getAccounts() {
        return accounts;
    }

    public BankStatement generateStatement() {
        Map<Account, List<Transaction>> transactionMap = this.getAccounts().stream()
                .collect(Collectors.toMap(account -> account,Account::getTransactions));
        return new BankStatement(this,transactionMap);

    }
    public void printStatement(){
        System.out.println(this.generateStatement().toString());
    }
    public void sendStatementViaSMS(){
        TwilioService twilioService = new TwilioService();
        twilioService.send(this.generateStatement().toString());
    }

    public Request sendRequest(double amount, String accountNumber) {
        Account account = Bank.getAccount(accountNumber);
        Request request = new Request(amount,account);
        account.getManager().approveRequest(request);
        return request;
    }

    public boolean approveRequest(Request request) {
        if(isManager){
            //Automatically approves all request but logic can be added to set isApproved
            boolean isApproved = true;
            if(isApproved) request.account().getTransactions().add(new Transaction(WITHDRAW, request.amount()));
            else return false;
            return true;
        }
        return false;
    }

    public List<Account> getAccountsByBranch(String branch){
        if(isManager){
            return Bank.getAccountList().stream().filter(account -> account.getBranchID().equals(branch)).toList();
        }else throw new IllegalArgumentException("Only managers have access to this method");
    }
}
