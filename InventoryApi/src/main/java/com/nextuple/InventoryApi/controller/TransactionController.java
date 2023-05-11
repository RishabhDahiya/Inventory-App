package com.nextuple.InventoryApi.controller;

import com.nextuple.InventoryApi.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
@CrossOrigin("*")
@RestController
public class TransactionController {
    @Autowired
    private TransactionService transactionService;
    @GetMapping("/transaction")
    public ResponseEntity<Object> showTransaction()
    {

        return transactionService.showTransaction();
    }
}
