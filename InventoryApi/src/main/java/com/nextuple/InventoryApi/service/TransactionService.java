package com.nextuple.InventoryApi.service;

import com.nextuple.InventoryApi.model.Transaction;
import com.nextuple.InventoryApi.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;
    public ResponseEntity<Object> showTransaction() {
        List<Transaction> response = transactionRepository.findAll();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
