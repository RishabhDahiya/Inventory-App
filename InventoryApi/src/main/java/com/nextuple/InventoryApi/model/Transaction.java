package com.nextuple.InventoryApi.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "Transaction")
public class Transaction {
    @Transient
    public static final String SEQUENCE_NAME = "transaction_sequence";
    @Id
    private String transactionId;
    private String supplyId;
    private String demandId;
    private String locationId;
    private String itemId;
    private Integer previousQuantity;
    private Integer newQuantity;
}
