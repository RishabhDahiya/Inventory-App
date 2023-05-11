package com.nextuple.InventoryApi.service;

import com.nextuple.InventoryApi.model.Transaction;
import com.nextuple.InventoryApi.repository.TransactionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {
    @Mock
    private TransactionRepository transactionRepository;
    @InjectMocks
    private TransactionService transactionService;

    @Test
    @DisplayName("show transaction returns list of transaction")
    public void showTransactionTest()
    {
        List<Transaction> list = new ArrayList<>();
        Mockito.when(transactionRepository.findAll()).thenReturn(list);
        ResponseEntity<Object> response = transactionService.showTransaction();
        Assertions.assertEquals(HttpStatus.OK,response.getStatusCode());
    }

}
