package com.nextuple.InventoryApi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "transactionSequence")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionSequence {
    @Id
    private String  id;
    private int seq;
}
