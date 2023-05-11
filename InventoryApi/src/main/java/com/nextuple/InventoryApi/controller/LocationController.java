package com.nextuple.InventoryApi.controller;

import com.nextuple.InventoryApi.model.Location;
import com.nextuple.InventoryApi.payload.request.CreateItemRequest;
import com.nextuple.InventoryApi.payload.request.CreateLocationRequest;
import com.nextuple.InventoryApi.payload.request.UpdateLocationRequest;
import com.nextuple.InventoryApi.payload.response.MessageResponse;
import com.nextuple.InventoryApi.service.LocationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
public class LocationController {
    @Autowired
    private LocationService locationService;
    @PostMapping("/locations")
    public ResponseEntity<MessageResponse> createLocation(@Valid @RequestBody CreateLocationRequest createLocationRequest) {
        return locationService.createLocation(createLocationRequest);
    }
    @GetMapping("/locations")
    public ResponseEntity<Object> showAllLocations() {
        return locationService.showAllLocations();
    }
    @GetMapping("/locations/{locationId}")
    public ResponseEntity<Object> showLocation(@PathVariable String locationId) {
        return locationService.showLocation(locationId);
    }
    @DeleteMapping("/locations/{locationId}")
    public ResponseEntity<MessageResponse> deleteLocation(@PathVariable String locationId) {
        return locationService.deleteLocation(locationId);
    }
    @PatchMapping("/locations/{locationId}")
    public ResponseEntity<Object> updateLocation(@PathVariable String locationId, @RequestBody UpdateLocationRequest updateLocationRequest) {
        return locationService.updateLocation(locationId,updateLocationRequest);
    }

}
