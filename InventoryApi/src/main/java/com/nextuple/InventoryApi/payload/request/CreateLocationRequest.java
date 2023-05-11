package com.nextuple.InventoryApi.payload.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateLocationRequest {
    @NotBlank(message = "locationId cannot be blank")
    private String locationId;
    private String locationDesc;
    private String locationType;
    private boolean pickupAllowed;
    private boolean shippingAllowed;
    private boolean deliveryAllowed;

    private String addressLine1;
    private String addressLine2;
    private String addressLine3;
    private String city;
    private String state;
    private String country;
    private String pincode;
}
