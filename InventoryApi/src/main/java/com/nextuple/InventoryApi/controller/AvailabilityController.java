package com.nextuple.InventoryApi.controller;

import com.nextuple.InventoryApi.service.AvailabilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
@CrossOrigin("*")
@RestController
public class AvailabilityController {
    @Autowired
    private AvailabilityService availabilityService;
    @GetMapping("/v1/availability/{itemId}/{locationId}")
    public ResponseEntity<Object> showAvailability(@PathVariable String itemId,@PathVariable String locationId)
    {
        return availabilityService.showAvailability(itemId,locationId);
    }
    @GetMapping("/v1/availability/{itemId}")
    public ResponseEntity<Object> showAvailability(@PathVariable String itemId)
    {
        return availabilityService.showAllAvailabilityByItemId(itemId);
    }
    @GetMapping("/v1/availability")
    public ResponseEntity<Object> showAllAvailability()
    {
        return availabilityService.showAllAvailability();
    }

}
