package com.nextuple.InventoryApi.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShowSupplyLocationIdResponse {
    private String locationId;
    private Integer quantity;
    private String supplyType;
}
