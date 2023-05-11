package com.nextuple.InventoryApi.service;

import com.nextuple.InventoryApi.model.Demand;
import com.nextuple.InventoryApi.model.Item;
import com.nextuple.InventoryApi.model.Location;
import com.nextuple.InventoryApi.model.Supply;
import com.nextuple.InventoryApi.payload.response.MessageResponse;
import com.nextuple.InventoryApi.payload.response.ShowAvailabilityResponse;
import com.nextuple.InventoryApi.repository.DemandRepository;
import com.nextuple.InventoryApi.repository.SupplyRepository;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(MockitoExtension.class)
public class AvailabilityServiceTest {
    @Mock
    private SupplyRepository supplyRepository;
    @Mock
    private DemandRepository demandRepository;
    @InjectMocks
    private AvailabilityService availabilityService;

    @Nested
    @DisplayName("showAvailability using itemId and locationId")
    class ShowAvailability {
        @Test
        @DisplayName("showAvailability returns list of availability")
        public void showAvailabilityTest1() {
            Supply supply = Supply.builder().quantity(100).build();
            Demand demand = Demand.builder().quantity(20).build();
            Mockito.when(supplyRepository.findByItemItemIdAndLocationLocationId(anyString(), anyString()))
                   .thenReturn(supply);
            Mockito.when(demandRepository.findByItemItemIdAndLocationLocationId(anyString(), anyString()))
                   .thenReturn(demand);
            ResponseEntity<Object> expectedResponse = new ResponseEntity<>(new ShowAvailabilityResponse(), HttpStatus.OK);
            ResponseEntity<Object> response = availabilityService.showAvailability("id", "id");
            Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        }

        @Test
        @DisplayName("showAvailability when supply does not exist")
        public void showAvailabilityTest2() {
            Mockito.when(supplyRepository.findByItemItemIdAndLocationLocationId(anyString(), anyString()))
                   .thenReturn(null);
            ResponseEntity<Object> expectedResponse = new ResponseEntity<>(new MessageResponse("supply does not exist"), HttpStatus.NOT_FOUND);
            ResponseEntity<Object> actualResponse = availabilityService.showAvailability("id", "id");
            Assertions.assertEquals(HttpStatus.NOT_FOUND, actualResponse.getStatusCode());
        }

        @Test
        @DisplayName("showAvailability when demand does not exist")
        public void showAvailabilityTest3() {
            Mockito.when(supplyRepository.findByItemItemIdAndLocationLocationId(anyString(), anyString()))
                   .thenReturn(new Supply());
            Mockito.when(demandRepository.findByItemItemIdAndLocationLocationId(anyString(), anyString()))
                   .thenReturn(null);
            ResponseEntity<Object> expectedResponse = new ResponseEntity<>(new MessageResponse("demand does not exist"), HttpStatus.NOT_FOUND);
            ResponseEntity<Object> actualResponse = availabilityService.showAvailability("id", "id");
            Assertions.assertEquals(HttpStatus.NOT_FOUND, actualResponse.getStatusCode());
        }

        @Test
        @DisplayName("showAvailability when demand and supply does not exist")
        public void showAvailabilityTest4() {
            Mockito.when(supplyRepository.findByItemItemIdAndLocationLocationId(anyString(), anyString()))
                   .thenReturn(null);
            Mockito.when(demandRepository.findByItemItemIdAndLocationLocationId(anyString(), anyString()))
                   .thenReturn(null);
            ResponseEntity<Object> expectedResponse = new ResponseEntity<>(new MessageResponse("demand and supply does not exist"), HttpStatus.NOT_FOUND);
            ResponseEntity<Object> actualResponse = availabilityService.showAvailability("id", "id");
            Assertions.assertEquals(HttpStatus.NOT_FOUND, actualResponse.getStatusCode());
        }
    }

    @Nested
    @DisplayName("showAllAvailabilityByItemId test")
    class ShowAllAvailabilityByItemId {
        @Test
        @DisplayName("showAllAvailabilityByItemId returns list of availability of an item on all the locations")
        public void showAllAvailabilityByItemIdTest1() {
            Supply supply1 = Supply.builder()
                   .item(Item.builder().build())
                   .location(Location.builder().locationId("id1").build())
                   .supplyType("type")
                   .quantity(100)
                   .build();
            Supply supply2 = Supply.builder()
                   .item(Item.builder().build())
                   .location(Location.builder().locationId("id2").build())
                   .supplyType("type")
                   .quantity(100)
                   .build();
            Demand demand1 = Demand.builder()
                   .item(Item.builder().build())
                   .location(Location.builder().locationId("id1").build())
                   .demandType("type")
                   .quantity(30)
                   .build();
            Demand demand2 = Demand.builder()
                   .item(Item.builder().build())
                   .location(Location.builder().locationId("id2").build())
                   .demandType("type")
                   .quantity(30)
                   .build();
            List<Supply> supplies = List.of(supply1, supply2);
            List<Demand> demands = List.of(demand1, demand2);
            Mockito.when(supplyRepository.findAllByItemItemId("id")).thenReturn(supplies);
            Mockito.when(demandRepository.findAllByItemItemId("id")).thenReturn(demands);
            ResponseEntity<Object> response = availabilityService.showAllAvailabilityByItemId("id");
            List<ShowAvailabilityResponse> responseList = (List<ShowAvailabilityResponse>) response.getBody();
            Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
            Assertions.assertNotNull(responseList);
            assertEquals(2, responseList.size());
        }

        @Test
        @DisplayName("showAllAvailabilityByItemId returns supply  not found")
        public void showAllAvailabilityByItemIdTest2() {
            Mockito.when(supplyRepository.findAllByItemItemId("id")).thenReturn(null);
            Mockito.when(demandRepository.findAllByItemItemId("id")).thenReturn(new ArrayList<Demand>());
            ResponseEntity<Object> response = availabilityService.showAllAvailabilityByItemId("id");
            Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        }

        @Test
        @DisplayName("showAllAvailabilityByItemId returns demand  not found")
        public void showAllAvailabilityByItemIdTest3() {
            Mockito.when(supplyRepository.findAllByItemItemId("id")).thenReturn(new ArrayList<Supply>());
            Mockito.when(demandRepository.findAllByItemItemId("id")).thenReturn(null);
            ResponseEntity<Object> response = availabilityService.showAllAvailabilityByItemId("id");
            Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        }
    }

    @Nested
    @DisplayName("showAllAvailability Test")
    class showAllAvailability {
        @Test
        @DisplayName("showAllAvailability returns list of availability")
        public  void showAllAvailabilityTest1()
        {
            Item item = Item.builder()
                   .itemId("id")
                   .itemDescription("desc")
                   .category("category")
                   .type("type")
                   .status("status")
                   .price(100)
                   .pickupAllowed(true)
                   .shippingAllowed(true)
                   .deliveryAllowed(true)
                   .build();

            Location location = Location.builder()
                   .locationId("id")
                   .locationDesc("desc")
                   .locationType("type")
                   .pickupAllowed(true)
                   .shippingAllowed(true)
                   .deliveryAllowed(true)
                   .addressLine1("addressLine1")
                   .addressLine2("addressLine2")
                   .addressLine3("addressaLine3")
                   .city("city")
                   .state("state")
                   .country("country")
                   .pincode("pincode")
                   .build();
            Supply supply1 = Supply.builder()
                   .item(item)
                   .location(location)
                   .supplyType("type")
                   .quantity(100)
                   .build();
            Supply supply2 = Supply.builder()
                   .item(item)
                   .location(location)
                   .supplyType("type")
                   .quantity(100)
                   .build();
            Demand demand1 = Demand.builder()
                   .item(item)
                   .location(location)
                   .demandType("type")
                   .quantity(30)
                   .build();
            Demand demand2 = Demand.builder()
                   .item(item)
                   .location(location)
                   .demandType("type")
                   .quantity(30)
                   .build();
            List<Supply> supplies = List.of(supply1, supply2);
            List<Demand> demands = List.of(demand1, demand2);
            Mockito.when(supplyRepository.findAll()).thenReturn(supplies);
            Mockito.when(demandRepository.findAll()).thenReturn(demands);
            List<ShowAvailabilityResponse> availabilityResponseList = new ArrayList<>();
            ResponseEntity<Object> response = availabilityService.showAllAvailability();
            Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        }
        @Test
        @DisplayName("showAllAvailability returns supply not found")
        public  void showAllAvailabilityTest2()
        {
            Mockito.when(supplyRepository.findAll()).thenReturn(null);
            Mockito.when(demandRepository.findAll()).thenReturn(new ArrayList<Demand>());
            ResponseEntity<Object> response = availabilityService.showAllAvailability();
            Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        }
        @Test
        @DisplayName("showAllAvailability returns demand  not found")
        public void showAllAvailabilityByItemIdTest3() {
            Mockito.when(supplyRepository.findAll()).thenReturn(new ArrayList<Supply>());
            Mockito.when(demandRepository.findAll()).thenReturn(null);
            ResponseEntity<Object> response = availabilityService.showAllAvailability();
            Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        }
    }


}
