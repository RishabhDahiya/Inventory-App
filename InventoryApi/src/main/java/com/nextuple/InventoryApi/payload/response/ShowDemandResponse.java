package com.nextuple.InventoryApi.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ShowDemandResponse {
    private String demandId;
    private String itemId;
    private String locationId;
    private String demandType;
    private Integer quantity;
}
