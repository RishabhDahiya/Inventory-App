package com.nextuple.InventoryApi.payload.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateLocationRequest {
    private String locationDesc;
    private String locationType;
    private Boolean pickupAllowed;
    private Boolean shippingAllowed;
    private Boolean deliveryAllowed;

    private String addressLine1;
    private String addressLine2;
    private String addressLine3;
    private String city;
    private String state;
    private String country;
    private String pincode;
}
