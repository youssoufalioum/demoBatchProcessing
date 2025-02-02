package com.youss.demoBatchProcessing.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class Transactions {

    @Id
    private Long transactionId;

    @Column(nullable = false)
    private String accountNumber;

    @Column(nullable = false)
    private String accountHolderName;

    @Column(nullable = false)
    private LocalDate transactionDate;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TransactionStatus transactionStatus;

    public Transactions() {
    }

    public Transactions(Long transactionId, String accountNumber, String accountHolderName, LocalDate transactionDate, TransactionType transactionType, BigDecimal amount, TransactionStatus transactionStatus) {
        this.transactionId = transactionId;
        this.accountNumber = accountNumber;
        this.accountHolderName = accountHolderName;
        this.transactionDate = transactionDate;
        this.transactionType = transactionType;
        this.amount = amount;
        this.transactionStatus = transactionStatus;
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public void setAccountHolderName(String accountHolderName) {
        this.accountHolderName = accountHolderName;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public TransactionStatus getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(TransactionStatus transactionStatus) {
        this.transactionStatus = transactionStatus;
    }
}
