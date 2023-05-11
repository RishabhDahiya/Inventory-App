package com.nextuple.InventoryApi.controller;

import com.nextuple.InventoryApi.payload.response.ShowAvailabilityResponse;
import com.nextuple.InventoryApi.service.AvailabilityService;
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

import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(MockitoExtension.class)
public class AvailabilityControllerTest {
    @Mock
    private AvailabilityService availabilityService;
    @InjectMocks
    private AvailabilityController availabilityController;

    @Nested
    @DisplayName("show Availability Controller Test using itemId and locationId")
    class showAvailability {
        @Test
        @DisplayName("showAvailability returns response")
        public void showAvailabilityTest1() {
            ResponseEntity<Object> expectedResponse = new ResponseEntity<>(new ShowAvailabilityResponse(), HttpStatus.OK);
            Mockito.when(availabilityService.showAvailability(anyString(), anyString())).thenReturn(expectedResponse);
            ResponseEntity<Object> actualResponse = availabilityController.showAvailability("id", "id");
            Assertions.assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        }

        @Test
        @DisplayName("showAvailability returns supply and demand does not exist")
        public void showAvailabilityTest2() {
            ResponseEntity<Object> expectedResponse = new ResponseEntity<>("supply and demand does not exist", HttpStatus.NOT_FOUND);
            Mockito.when(availabilityService.showAvailability(anyString(), anyString())).thenReturn(expectedResponse);
            ResponseEntity<Object> actualResponse = availabilityController.showAvailability("id", "id");
            Assertions.assertEquals(HttpStatus.NOT_FOUND, actualResponse.getStatusCode());
        }

        @Test
        @DisplayName("showAvailability returns supply  does not exist")
        public void showAvailabilityTest3() {
            ResponseEntity<Object> expectedResponse = new ResponseEntity<>("supply  does not exist", HttpStatus.NOT_FOUND);
            Mockito.when(availabilityService.showAvailability(anyString(), anyString())).thenReturn(expectedResponse);
            ResponseEntity<Object> actualResponse = availabilityController.showAvailability("id", "id");
            Assertions.assertEquals(HttpStatus.NOT_FOUND, actualResponse.getStatusCode());
        }

        @Test
        @DisplayName("showAvailability returns demand  does not exist")
        public void showAvailabilityTest4() {
            ResponseEntity<Object> expectedResponse = new ResponseEntity<>("demand  does not exist", HttpStatus.NOT_FOUND);
            Mockito.when(availabilityService.showAvailability(anyString(), anyString())).thenReturn(expectedResponse);
            ResponseEntity<Object> actualResponse = availabilityController.showAvailability("id", "id");
            Assertions.assertEquals(HttpStatus.NOT_FOUND, actualResponse.getStatusCode());
        }
    }

    @Nested
    @DisplayName("show Availability test using itemId")
    class showAvailabilityUsingItemId {
        @Test
        @DisplayName("show availability using itemId returns list of availability")
        public void showAvailabilityUsingItemIdTest1()
        {
            ResponseEntity<Object> expectedResponse = new ResponseEntity<>(new ArrayList<ShowAvailabilityResponse>(), HttpStatus.OK);
            Mockito.when(availabilityService.showAllAvailabilityByItemId(anyString())).thenReturn(expectedResponse);
            ResponseEntity<Object> actualResponse = availabilityController.showAvailability("id");
            Assertions.assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        }
        @Test
        @DisplayName("show availability using itemId returns list not found")
        public void showAvailabilityUsingItemIdTest2()
        {
            ResponseEntity<Object> expectedResponse = new ResponseEntity<>("list not found", HttpStatus.NOT_FOUND);
            Mockito.when(availabilityService.showAllAvailabilityByItemId(anyString())).thenReturn(expectedResponse);
            ResponseEntity<Object> actualResponse = availabilityController.showAvailability("id");
            Assertions.assertEquals(HttpStatus.NOT_FOUND, actualResponse.getStatusCode());
        }
    }
    @Nested
    @DisplayName("show Availability test using itemId")
    class showAllAvailability {
        @Test
        @DisplayName("show all availability returns list of availability")
        public void showAllAvailabilityTest1()
        {
            ResponseEntity<Object> expectedResponse = new ResponseEntity<>(new ArrayList<ShowAvailabilityResponse>(), HttpStatus.OK);
            Mockito.when(availabilityService.showAllAvailability()).thenReturn(expectedResponse);
            ResponseEntity<Object> actualResponse = availabilityController.showAllAvailability();
            Assertions.assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        }
        @Test
        @DisplayName("show availability using itemId returns list of availability not found")
        public void showAvailabilityUsingItemIdTest2()
        {
            ResponseEntity<Object> expectedResponse = new ResponseEntity<>("list not found", HttpStatus.NOT_FOUND);
            Mockito.when(availabilityService.showAllAvailability()).thenReturn(expectedResponse);
            ResponseEntity<Object> actualResponse = availabilityController.showAllAvailability();
            Assertions.assertEquals(HttpStatus.NOT_FOUND, actualResponse.getStatusCode());
        }
    }
}
