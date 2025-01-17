package com.booleanuk.core;

import static com.booleanuk.core.TransactionType.*;

public class CurrentAccount extends Account{
    public CurrentAccount(String branchID, User owner, User manager) {
        super(branchID, owner, manager);
    }
    public void withdraw(double amount){
        if(this.getBalance() >= amount) this.getTransactions().add(new Transaction(WITHDRAW,amount));
        else throw new IllegalArgumentException("You can't withdraw more than you have. You can send a request ");
    }

    public void wire(double amount, String accountNumber) {
        if(this.getBalance() >= amount){
            this.getTransactions().add(new Transaction(WIRE_TO,amount));
            Account receiver = Bank.getAccount(accountNumber);
            receiver.getTransactions().add(new Transaction(WIRE_TO,amount));

        }
        else throw new IllegalArgumentException("You don't have enough money");
    }
}
