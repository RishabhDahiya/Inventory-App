package com.nextuple.InventoryApi.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShowDemandLocationIdResponse {
    private String itemId;
    private String locationId;
    private String demandType;
    private Integer quantity;
}
