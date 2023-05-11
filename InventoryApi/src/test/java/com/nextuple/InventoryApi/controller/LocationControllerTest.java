package com.nextuple.InventoryApi.controller;

import com.nextuple.InventoryApi.model.Location;
import com.nextuple.InventoryApi.payload.request.CreateLocationRequest;
import com.nextuple.InventoryApi.payload.request.UpdateLocationRequest;
import com.nextuple.InventoryApi.payload.response.MessageResponse;
import com.nextuple.InventoryApi.service.LocationService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(MockitoExtension.class)
public class LocationControllerTest {
    @Mock
    private LocationService locationService;
    @InjectMocks
    private LocationController locationController;

    @Nested
    @DisplayName("Test cases for createLocation")
    class CreateLocation {
        @Test
        @DisplayName("when location is successfully created")
        public void createLocationTest1() {
            CreateLocationRequest createLocationRequest = new CreateLocationRequest();
            ResponseEntity<MessageResponse> expectedResponse = new ResponseEntity<>(new MessageResponse("location created successfully")
                   , HttpStatus.CREATED);
            Mockito.when(locationService.createLocation(createLocationRequest)).thenReturn(expectedResponse);
            ResponseEntity<MessageResponse> actualResponse = locationController.createLocation(createLocationRequest);
            Assertions.assertEquals(expectedResponse, actualResponse);
        }
        @Test
        @DisplayName("when locationId already exists")
        public void createLocationTest2() {
            CreateLocationRequest createLocationRequest = new CreateLocationRequest();
            ResponseEntity<MessageResponse> expectedResponse = new ResponseEntity<>(new MessageResponse("locationId already exists for different location")
                   , HttpStatus.CREATED);
            Mockito.when(locationService.createLocation(createLocationRequest)).thenReturn(expectedResponse);
            ResponseEntity<MessageResponse> actualResponse = locationController.createLocation(createLocationRequest);
            Assertions.assertEquals(expectedResponse, actualResponse);
        }
        @Test
        @DisplayName("when location already exists with different locationId")
        public void createLocationTest3() {
            CreateLocationRequest createLocationRequest = new CreateLocationRequest();
            ResponseEntity<MessageResponse> expectedResponse = new ResponseEntity<>(new MessageResponse("location already exists with different locationId")
                   , HttpStatus.CREATED);
            Mockito.when(locationService.createLocation(createLocationRequest)).thenReturn(expectedResponse);
            ResponseEntity<MessageResponse> actualResponse = locationController.createLocation(createLocationRequest);
            Assertions.assertEquals(expectedResponse, actualResponse);
        }
    }

    @Nested
    @DisplayName("test case for showAllLocations")
    class ShowAllLocations {
        @Test
        @DisplayName("showAllLocations returns list of Location")
        public void showAllLocations()
        {
            ResponseEntity<Object> expectedResponse = new ResponseEntity<>(new ArrayList<Location>(),HttpStatus.OK);
            Mockito.when(locationController.showAllLocations()).thenReturn(expectedResponse);
            ResponseEntity<Object> actualResponse = locationController.showAllLocations();
            Assertions.assertEquals(expectedResponse,actualResponse);
        }
    }

    @Nested
    @DisplayName("Test case for showLocation")
    class ShowLocation {
        @Test
        @DisplayName("when location is present")
        public void showLocationTest1() {
            ResponseEntity<Object> expectedResponse = new ResponseEntity<>(new Location(), HttpStatus.OK);
            Mockito.when(locationService.showLocation(anyString())).thenReturn(expectedResponse);
            ResponseEntity<Object> actualResponse = locationController.showLocation("id");
            Assertions.assertEquals(expectedResponse, actualResponse);
        }
        @Test
        @DisplayName("when location is not present")
        public void showLocationTest2() {
            ResponseEntity<Object> expectedResponse = new ResponseEntity<>(new MessageResponse("location not found"), HttpStatus.NOT_FOUND);
            Mockito.when(locationService.showLocation(anyString())).thenReturn(expectedResponse);
            ResponseEntity<Object> actualResponse = locationController.showLocation("id");
            Assertions.assertEquals(expectedResponse, actualResponse);
        }
    }
    @Nested
    @DisplayName("test case for deleteItem")
    class DeleteItem{
        @Test
        @DisplayName("when location is present")
        public void deleteLocationTest1()
        {
            ResponseEntity<MessageResponse> expectedResponse = new ResponseEntity<>(new MessageResponse("location deleted"),HttpStatus.OK);
            Mockito.when(locationService.deleteLocation(anyString())).thenReturn(expectedResponse);
            ResponseEntity<MessageResponse> actualResponse = locationController.deleteLocation("id");
            Assertions.assertEquals(expectedResponse,actualResponse);
        }
        @Test
        @DisplayName("when location is not present")
        public void deleteLocationTest2()
        {
            ResponseEntity<MessageResponse> expectedResponse = new ResponseEntity<>(new MessageResponse("location not found"),HttpStatus.NOT_FOUND);
            Mockito.when(locationService.deleteLocation(anyString())).thenReturn(expectedResponse);
            ResponseEntity<MessageResponse> actualResponse = locationController.deleteLocation("id");
            Assertions.assertEquals(expectedResponse,actualResponse);
        }
    }
    @Nested
    @DisplayName("update location test")
    class updateLocation{
        @Test
        @DisplayName("location updated successfully")
        public void updateLocationTest1()
        {
            UpdateLocationRequest updateLocationRequest = UpdateLocationRequest.builder()
                   .locationDesc("Desc")
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
            ResponseEntity<Object> expectedResponse = new ResponseEntity<>(new MessageResponse("location updated successfully"),HttpStatus.OK);
            Mockito.when(locationService.updateLocation(anyString(),any())).thenReturn(expectedResponse);
            ResponseEntity<Object> actualResponse = locationController.updateLocation("id",updateLocationRequest);
        }
        @Test
        @DisplayName("location not found")
        public void updateLocationTest2()
        {
            UpdateLocationRequest updateLocationRequest = new UpdateLocationRequest();
            ResponseEntity<Object> expectedResponse = new ResponseEntity<>(new MessageResponse("location not found"),HttpStatus.NOT_FOUND);
            Mockito.when(locationService.updateLocation(anyString(),any())).thenReturn(expectedResponse);
            ResponseEntity<Object> actualResponse = locationController.updateLocation("id",updateLocationRequest);
        }

    }
}
