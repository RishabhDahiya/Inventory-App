package com.nextuple.InventoryApi.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShowItemResponse {

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
