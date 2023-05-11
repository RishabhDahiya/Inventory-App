package com.nextuple.InventoryApi.controller;

import com.nextuple.InventoryApi.payload.request.CreateDemandRequest;
import com.nextuple.InventoryApi.payload.request.UpdateDemandRequest;
import com.nextuple.InventoryApi.service.DemandService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@CrossOrigin("*")
@RestController
public class DemandController {
    @Autowired
    private DemandService demandService;
    @PostMapping("/demand")
    public ResponseEntity<Object> createDemand(@Valid @RequestBody CreateDemandRequest createDemandRequest)
    {
        return demandService.createDemand(createDemandRequest);
    }
    @GetMapping("/demand")
    public ResponseEntity<Object> showAllDemand()
    {
        return demandService.showAllDemand();
    }
    @GetMapping("/demand/{demandId}")
    public ResponseEntity<Object> showDemand(@PathVariable String demandId)
    {
        return demandService.showDemand(demandId);
    }
    @GetMapping("/demand/{itemId}/{locationId}")
    public ResponseEntity<Object> showDemand(@PathVariable String itemId,@PathVariable String locationId)
    {
        return demandService.showDemand(itemId,locationId);
    }
//    @GetMapping("/demand/location/{locationId}")
//    public ResponseEntity<Object> showDemandLocationId(@PathVariable String locationId)
//    {
//        return demandService.showDemandLocationId(locationId);
//    }
    @DeleteMapping("/demand/{demandId}")
    public ResponseEntity<Object> deleteDemand(@PathVariable String demandId)
    {
        return demandService.deleteDemand(demandId);
    }
    @PatchMapping("/demand/{demandId}")
    public ResponseEntity<Object> updateDemand(@PathVariable String demandId, @RequestBody UpdateDemandRequest updateDemandRequest)
    {
        return demandService.updateDemand(demandId,updateDemandRequest);
    }
}
