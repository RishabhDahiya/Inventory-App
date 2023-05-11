package com.nextuple.InventoryApi.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShowSupplyResponse {
    private String supplyId;
    private String itemId;
    private String locationId;
    private String supplyType;
    private Integer quantity;
}
