package com.nextuple.InventoryApi.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ShowAvailabilityResponse {
    private String itemId;
    private String itemDesc;
    private String locationId;
    private String locationDesc;
    private Integer quantity;
}
