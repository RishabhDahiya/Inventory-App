package com.nextuple.InventoryApi.payload.request;

import com.nextuple.InventoryApi.model.Location;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateItemRequest {
    @NotBlank(message = "itemId cannot be blank")
    private String itemId;
    private String itemDescription;
    private String category;
    private String type;
    private String status;
    private double price;
    private boolean pickupAllowed;
    private boolean shippingAllowed;
    private boolean deliveryAllowed;
    private List<String> locationId;
}
