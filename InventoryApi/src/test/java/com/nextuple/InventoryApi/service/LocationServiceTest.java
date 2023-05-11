package com.nextuple.InventoryApi.service;

import com.nextuple.InventoryApi.model.Location;
import com.nextuple.InventoryApi.payload.request.CreateLocationRequest;
import com.nextuple.InventoryApi.payload.request.UpdateLocationRequest;
import com.nextuple.InventoryApi.payload.response.MessageResponse;
import com.nextuple.InventoryApi.repository.LocationRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class LocationServiceTest {
    @Mock
    private LocationRepository locationRepository;
    @InjectMocks
    private LocationService locationService;
    @Nested
    @DisplayName("Create Location Test")
    class CreateLocation{
        @Test
        @DisplayName("Location created successfully")
        public void createLocationTest1()
        {
            CreateLocationRequest createLocationRequest = CreateLocationRequest.builder()
                   .locationId("id")
                   .locationDesc("desc")
                   .locationType("type")
                   .pickupAllowed(true)
                   .shippingAllowed(true)
                   .deliveryAllowed(true)
                   .addressLine1("line1")
                   .addressLine2("line2")
                   .addressLine3("line3")
                   .city("city")
                   .state("state")
                   .country("country")
                   .pincode("pincode")
                   .build();
            Location location = Location.builder()
                   .locationId("id")
                   .locationDesc("desc")
                   .locationType("type")
                   .pickupAllowed(true)
                   .shippingAllowed(true)
                   .deliveryAllowed(true)
                   .addressLine1("line1")
                   .addressLine2("line2")
                   .addressLine3("line3")
                   .city("city")
                   .state("state")
                   .country("country")
                   .pincode("pincode")
                   .build();
            Mockito.when(locationRepository.findByLocationId("id")).thenReturn(null);
            Mockito.when(locationRepository.findByLocationDesc("desc")).thenReturn(null);
            Mockito.when(locationRepository.save(any(Location.class))).thenReturn(location);
            ResponseEntity<MessageResponse> response = locationService.createLocation(createLocationRequest);
                    Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        }
        @Test
        @DisplayName("LocationId already taken")
        public void createLocationTest2()
        {
            CreateLocationRequest createLocationRequest = CreateLocationRequest.builder()
                   .locationId("id")
                   .locationDesc("desc")
                   .locationType("type")
                   .pickupAllowed(true)
                   .shippingAllowed(true)
                   .deliveryAllowed(true)
                   .addressLine1("line1")
                   .addressLine2("line2")
                   .addressLine3("line3")
                   .city("city")
                   .state("state")
                   .country("country")
                   .pincode("pincode")
                   .build();
            Location location = Location.builder()
                   .locationId("id")
                   .locationDesc("desc")
                   .locationType("type")
                   .pickupAllowed(true)
                   .shippingAllowed(true)
                   .deliveryAllowed(true)
                   .addressLine1("line1")
                   .addressLine2("line2")
                   .addressLine3("line3")
                   .city("city")
                   .state("state")
                   .country("country")
                   .pincode("pincode")
                   .build();
            Mockito.when(locationRepository.findByLocationId("id")).thenReturn(new Location());
            ResponseEntity<MessageResponse> response = locationService.createLocation(createLocationRequest);
            Assertions.assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        }
        @Test
        @DisplayName("Location already exist for different location taken")
        public void createLocationTest3()
        {
            CreateLocationRequest createLocationRequest = CreateLocationRequest.builder()
                   .locationId("id")
                   .locationDesc("desc")
                   .locationType("type")
                   .pickupAllowed(true)
                   .shippingAllowed(true)
                   .deliveryAllowed(true)
                   .addressLine1("line1")
                   .addressLine2("line2")
                   .addressLine3("line3")
                   .city("city")
                   .state("state")
                   .country("country")
                   .pincode("pincode")
                   .build();
            Location location = Location.builder()
                   .locationId("id")
                   .locationDesc("desc")
                   .locationType("type")
                   .pickupAllowed(true)
                   .shippingAllowed(true)
                   .deliveryAllowed(true)
                   .addressLine1("line1")
                   .addressLine2("line2")
                   .addressLine3("line3")
                   .city("city")
                   .state("state")
                   .country("country")
                   .pincode("pincode")
                   .build();
            Mockito.when(locationRepository.findByLocationId("id")).thenReturn(null);
            Mockito.when(locationRepository.findByLocationDesc("desc")).thenReturn(new Location());
            ResponseEntity<MessageResponse> response = locationService.createLocation(createLocationRequest);
            Assertions.assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        }
    }

    @Nested
    @DisplayName("showLocation test")
    class ShowLocation
    {
        @Test
        @DisplayName("show location using locationId returns location")
        public void showLocationTest1()
       {
           Mockito.when(locationRepository.findByLocationId("id")).thenReturn(new Location());
           ResponseEntity<Object> response = locationService.showLocation("id");
           Assertions.assertEquals(HttpStatus.OK,response.getStatusCode());
       }
        @Test
        @DisplayName("show location using locationId returns location not found")
        public void showLocationTest2()
        {
            Mockito.when(locationRepository.findByLocationId("id")).thenReturn(null);
            ResponseEntity<Object> response = locationService.showLocation("id");
            Assertions.assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
        }
    }
    @Nested
    @DisplayName("showAllLocations test")
    class ShowAllLocations
    {
        @Test
        @DisplayName("showAllLocations returns list of locations")
        public void showAllLocationsTest1()
        {
            Mockito.when(locationRepository.findAll()).thenReturn(List.of(new Location(),new Location()));
            ResponseEntity<Object> response = locationService.showAllLocations();
            Assertions.assertEquals(HttpStatus.OK,response.getStatusCode());
        }
        @Test
        @DisplayName("showAllLocations returns list of locations")
        public void showAllLocationsTest2()
        {
            Mockito.when(locationRepository.findAll()).thenReturn(new ArrayList<>());
            ResponseEntity<Object> response = locationService.showAllLocations();
            Assertions.assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
        }
    }
    @Nested
    @DisplayName("delete Location test")
    class DeleteLocations{
        @Test
        @DisplayName("Location Deleted Successfully")
        public void deleteLocationTest1()
        {
            Mockito.when(locationRepository.findByLocationId("id")).thenReturn(new Location());
            ResponseEntity<MessageResponse> response = locationService.deleteLocation("id");
            Assertions.assertEquals(HttpStatus.OK,response.getStatusCode());
        }
        @Test
        @DisplayName("Location not found")
        public void deleteLocationTest2()
        {
            Mockito.when(locationRepository.findByLocationId("id")).thenReturn(null);
            ResponseEntity<MessageResponse> response = locationService.deleteLocation("id");
            Assertions.assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
        }
    }
    @Nested
    @DisplayName("updateLocation test")
    class UpdateLocation{
        @Test
        @DisplayName("location updated successfully")
        public void updateLocationTest1()
        {
            UpdateLocationRequest updateLocationRequest = UpdateLocationRequest.builder()
                   .locationDesc("desc")
                   .locationType("type")
                   .pickupAllowed(true)
                   .shippingAllowed(true)
                   .deliveryAllowed(true)
                   .addressLine1("line1")
                   .addressLine2("line2")
                   .addressLine3("line3")
                   .city("city")
                   .state("state")
                   .country("country")
                   .pincode("pincode")
                   .build();
            Location location = Location.builder()
                   .locationId("id")
                   .locationDesc("desc")
                   .locationType("type")
                   .pickupAllowed(true)
                   .shippingAllowed(true)
                   .deliveryAllowed(true)
                   .addressLine1("line1")
                   .addressLine2("line2")
                   .addressLine3("line3")
                   .city("city")
                   .state("state")
                   .country("country")
                   .pincode("pincode")
                   .build();
            Mockito.when(locationRepository.findByLocationId("id")).thenReturn(location);
            ResponseEntity<Object> response = locationService.updateLocation("id",updateLocationRequest);
            Assertions.assertEquals(HttpStatus.OK,response.getStatusCode());
        }
        @Test
        @DisplayName("location not found")
        public void updateLocationTest2()
        {
            UpdateLocationRequest updateLocationRequest = UpdateLocationRequest.builder()
                   .locationDesc("desc")
                   .locationType("type")
                   .pickupAllowed(true)
                   .shippingAllowed(true)
                   .deliveryAllowed(true)
                   .addressLine1("line1")
                   .addressLine2("line2")
                   .addressLine3("line3")
                   .city("city")
                   .state("state")
                   .country("country")
                   .pincode("pincode")
                   .build();
            Mockito.when(locationRepository.findByLocationId("id")).thenReturn(null);
            ResponseEntity<Object> response = locationService.updateLocation("id",updateLocationRequest);
            Assertions.assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
        }
    }
}

