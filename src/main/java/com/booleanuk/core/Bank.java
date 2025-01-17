package com.booleanuk.core;

import java.util.ArrayList;
import java.util.List;

public class Bank {
    private static final List<Account> accountList = new ArrayList<>();
    private static final List<User> usersList = new ArrayList<>();

    public static List<User> getUsersList() {
        return usersList;
    }

    public static Account getAccount(String accountNumber){
        return accountList.stream()
                .filter(account -> account.getAccountNumber().equals(accountNumber))
                .findFirst()
                .orElseThrow();
    }


    public static List<Account> getAccountList() {
        return accountList;
    }

    public static void createAccount(String name, boolean isSaving){
        User owner;
        if(usersList.stream().noneMatch(user -> user.getName().equals(name))) {
            owner = new User(name);
        }
        else owner = usersList.stream().filter(user -> user.getName().equals(name)).findFirst().orElseThrow();
        //branchID should be known by system that creates an account based on location
        //manager should be decided by un employee of th bank in my case it will be chosen by random
        User manager = usersList.stream().filter(User::isManager).findAny().orElseThrow();
        if(isSaving) {
            new SavingAccount("PL18",owner,manager);
        } else{
            new CurrentAccount("PL18",owner,manager);
        }
    }
}
