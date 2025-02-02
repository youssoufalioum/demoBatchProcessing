package com.youss.demoBatchProcessing.services;

import com.youss.demoBatchProcessing.entities.TransactionStatus;
import com.youss.demoBatchProcessing.entities.TransactionType;
import com.youss.demoBatchProcessing.entities.Transactions;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TransactionsFieldSetMapper implements FieldSetMapper<Transactions> {

    @Override
    public Transactions mapFieldSet(FieldSet fieldSet) throws BindException {
        Transactions transaction = new Transactions();
        // Conversion de la date
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        transaction.setTransactionId(Long.valueOf(fieldSet.readString("transactionId")));
        transaction.setAccountNumber(fieldSet.readString("accountNumber"));
        transaction.setAccountHolderName(fieldSet.readString("accountHolderName"));
        transaction.setTransactionDate(LocalDate.parse(fieldSet.readString("transactionDate"), formatter));
        transaction.setTransactionType(TransactionType.valueOf(fieldSet.readString("transactionType").toUpperCase())); // Conversion en enum
        transaction.setAmount(fieldSet.readBigDecimal("amount"));
        transaction.setTransactionStatus(TransactionStatus.valueOf(fieldSet.readString("transactionStatus").toUpperCase())); // Conversion en enum

        return transaction;
    }
}
