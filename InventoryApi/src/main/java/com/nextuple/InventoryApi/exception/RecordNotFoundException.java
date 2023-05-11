package com.nextuple.InventoryApi.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class RecordNotFoundException extends  RuntimeException{
    private String message;
}
