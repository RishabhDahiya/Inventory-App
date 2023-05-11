package com.nextuple.InventoryApi.controller;

import com.nextuple.InventoryApi.payload.request.CreateSupplyRequest;
import com.nextuple.InventoryApi.payload.request.UpdateSupplyRequest;
import com.nextuple.InventoryApi.service.SupplyService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
public class SupplyController {
    @Autowired
    private SupplyService supplyService;

    @PostMapping("/supply")
    public ResponseEntity<Object> createSupply(@Valid @RequestBody CreateSupplyRequest createSupplyRequest)
    {
        return supplyService.createSupply(createSupplyRequest);
    }
    @GetMapping("/supply")
    public ResponseEntity<Object> showAllSupply()
    {
        return supplyService.showAllSupply();
    }
    @GetMapping("/supply/{supplyId}")
    public ResponseEntity<Object> showSupply(@PathVariable String supplyId)
    {
        return supplyService.showSupply(supplyId);
    }
    @GetMapping("/supply/{itemId}/{locationId}")
    public ResponseEntity<Object> showSupply(@PathVariable String itemId,@PathVariable String locationId)
    {
        return supplyService.showSupply(itemId,itemId);
    }
//    @GetMapping("/supply/location/{locationId}")
//    public ResponseEntity<Object> showSupplyLocationId(@PathVariable String locationId)
//    {
//        return supplyService.showSupplyLocationId(locationId);
//    }
    @DeleteMapping("/supply/{supplyId}")
    public ResponseEntity<Object> deleteSupply(@PathVariable String supplyId)
    {
        return  supplyService.deleteSupply(supplyId);
    }
    @PatchMapping("/supply/{supplyId}")
    public ResponseEntity<Object> updateSupply(@PathVariable String supplyId, @RequestBody UpdateSupplyRequest updateSupplyRequest)
    {
        return  supplyService.updateSupply(supplyId,updateSupplyRequest);
    }

}
