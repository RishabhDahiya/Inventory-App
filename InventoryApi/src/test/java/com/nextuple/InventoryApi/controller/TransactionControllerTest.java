package com.nextuple.InventoryApi.controller;

import com.nextuple.InventoryApi.model.Transaction;
import com.nextuple.InventoryApi.service.TransactionService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;

@ExtendWith(MockitoExtension.class)
public class TransactionControllerTest {
    @Mock
    private TransactionService transactionService;
    @InjectMocks
    private TransactionController transactionController;

    @Test
    @DisplayName("show transaction test")
    public void showTransactionTest() {
        ResponseEntity<Object> expectedResponse = new ResponseEntity<>(new ArrayList<Transaction>(), HttpStatus.OK);
        Mockito.when(transactionService.showTransaction()).thenReturn(expectedResponse);
        ResponseEntity<Object> actualResponse = transactionController.showTransaction();
        Assertions.assertEquals(HttpStatus.OK,actualResponse.getStatusCode());

    }
}
