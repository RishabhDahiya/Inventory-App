package com.nextuple.InventoryApi.payload.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateItemRequest {
    private String status;
    private Double price;
    private Boolean pickupAllowed;
    private Boolean shippingAllowed;
    private Boolean deliveryAllowed;
}
