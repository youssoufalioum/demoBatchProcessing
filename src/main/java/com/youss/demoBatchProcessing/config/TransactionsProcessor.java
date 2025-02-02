package com.youss.demoBatchProcessing.config;

import com.youss.demoBatchProcessing.entities.Transactions;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;


@Component
public class TransactionsProcessor implements ItemProcessor<Transactions,Transactions> {

    @Override
    public Transactions process(Transactions transactions) throws Exception {
        return transactions;
    }
}
