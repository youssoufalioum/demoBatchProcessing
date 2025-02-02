package com.youss.demoBatchProcessing.controllers;

import com.youss.demoBatchProcessing.services.TransactionsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/transactions")
public class TransactionsController {

    private final TransactionsService transactionsService;

    public TransactionsController(TransactionsService transactionsService) {
        this.transactionsService = transactionsService;
    }

    @PostMapping("/launchJob")
    public void importCSVToDBJob() {
        transactionsService.importCSVToDBJob();
    }
}
