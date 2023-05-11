package com.nextuple.InventoryApi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "demand_sequence")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DemandSequence {
    @Id
    private String  id;
    private int seq;
}
