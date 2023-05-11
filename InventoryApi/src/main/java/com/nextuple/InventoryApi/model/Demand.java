package com.nextuple.InventoryApi.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Demand")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Demand {
    @Transient
    public static final String SEQUENCE_NAME = "demand_sequence";
    @Id
    private String demandId;
    private Integer quantity;
    private String demandType;
    @DBRef
    private Item item;
    @DBRef
    private Location location;
}
