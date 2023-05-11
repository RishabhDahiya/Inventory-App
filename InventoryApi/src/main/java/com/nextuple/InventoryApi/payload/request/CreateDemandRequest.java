package com.nextuple.InventoryApi.payload.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateDemandRequest {
    @NotBlank(message = "itemId can not be Blank")
    private String itemId;
    @NotBlank(message = "locationId can not be Blank")
    private String locationId;
    @Min(message = "quantity can not be less than 1",value = 1)
    private int quantity;
}
