package com.nextuple.InventoryApi.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Items")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Item {
    @Id
    private String itemId;
    private String itemDescription;
    private String category;
    private String type;
    private String status;
    private double price;
    private boolean pickupAllowed;
    private boolean shippingAllowed;
    private boolean deliveryAllowed;
}
