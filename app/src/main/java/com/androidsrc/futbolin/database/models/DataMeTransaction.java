package com.androidsrc.futbolin.database.models;

public class DataMeTransaction {
    long amount;
    long balance;
    String description;
    String created_at;

    public DataMeTransaction(){

    }

    @Override
    public String toString() {
        return "DataMeTransaction{" +
                "amount=" + amount +
                ", balance=" + balance +
                ", description='" + description + '\'' +
                ", created_at='" + created_at + '\'' +
                '}';
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
