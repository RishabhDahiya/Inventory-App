package com.nextuple.InventoryApi.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "supply")
@Builder
public class Supply {
    @Transient
    public static final String SEQUENCE_NAME = "user_sequence";
    @Id
    private String supplyId;
    @DBRef
    private Item item;
    @DBRef
    private Location location;
    private String supplyType;

    private Integer quantity;

}
