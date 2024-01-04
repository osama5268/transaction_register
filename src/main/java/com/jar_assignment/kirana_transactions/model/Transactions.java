package com.jar_assignment.kirana_transactions.model;

import java.time.LocalDateTime;
import jakarta.persistence.*;
@Entity
public class Transactions {
    @Id
    @GeneratedValue
    private int id;

    @ManyToOne
    private User user;

    private Double transactionAmount;

    private Double transactionAmountINR;

    private Double transactionAmountUSD;

    public Double getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(Double transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public Double getTransactionAmountINR() {
        return transactionAmountINR;
    }

    public void setTransactionAmountINR(Double transactionAmountINR) {
        this.transactionAmountINR = transactionAmountINR;
    }

    public Double getTransactionAmountUSD() {
        return transactionAmountUSD;
    }

    public void setTransactionAmountUSD(Double transactionAmountUSD) {
        this.transactionAmountUSD = transactionAmountUSD;
    }

    @Enumerated(EnumType.STRING)
    private Currency transactionCurrency;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    private LocalDateTime transactionTime;

    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    public enum Currency {
        INR, USD
    }

    public enum TransactionType {
        CREDIT, DEBIT
    }

    public int getid() {
        return id;
    }

    public void setid(int id) {
        this.id = id;
    }

    public User getuser() {
        return user;
    }

    public void setuser(User user) {
        this.user = user;
    }

    public Currency gettransactionCurrency() {
        return transactionCurrency;
    }

    public void settransactionCurrency(Currency transactionCurrency) {
        this.transactionCurrency = transactionCurrency;
    }

    public TransactionType gettransactionType() {
        return transactionType;
    }

    public void settransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public LocalDateTime gettransactionTime() {
        return transactionTime;
    }

    public void settransactionTime(LocalDateTime transactionTime) {
        this.transactionTime = transactionTime;
    }

    public LocalDateTime getcreatedAt() {
        return createdAt;
    }

    public void setcreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
}
