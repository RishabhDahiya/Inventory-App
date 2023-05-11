package com.nextuple.InventoryApi.service;

import com.nextuple.InventoryApi.model.*;
import com.nextuple.InventoryApi.payload.request.CreateDemandRequest;
import com.nextuple.InventoryApi.payload.request.UpdateDemandRequest;
import com.nextuple.InventoryApi.payload.request.UpdateSupplyRequest;
import com.nextuple.InventoryApi.repository.*;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(MockitoExtension.class)
public class DemandServiceTest {
    @Mock
    private DemandRepository demandRepository;
    @Mock
    private SupplyRepository supplyRepository;
    @Mock
    private ItemRepository itemRepository;
    @Mock
    private TransactionRepository transactionRepository;
    @Mock
    private LocationRepository locationRepository;
    @Mock
    private SequenceGeneratorService sequenceGeneratorService;
    @InjectMocks
    private DemandService demandService;

    @Nested
    @DisplayName("create demand request")
    class CreateDemand {
        @Test
        @DisplayName("demand successfully created")
        public void createDemandTest1() {
            CreateDemandRequest createDemandRequest = CreateDemandRequest.builder()
                   .itemId("id")
                   .locationId("id")
                   .quantity(100)
                   .build();
            Mockito.when(itemRepository.findByItemId("id")).thenReturn(new Item());
            Mockito.when(locationRepository.findByLocationId("id")).thenReturn(new Location());
            Mockito.when(demandRepository.findByItemItemIdAndLocationLocationId("id", "id"))
                   .thenReturn(null);
            Mockito.when(sequenceGeneratorService.getSequenceNumber(Demand.SEQUENCE_NAME)).thenReturn(1);
            Integer val = 10;
//            Mockito.when(sequenceGeneratorService.getSequenceNumber("sequence")).thenReturn(val);
            Demand demand = Demand.builder()
                   .demandId(String.valueOf(val))
                   .item(Item.builder().build())
                   .location(Location.builder().build())
                   .quantity(createDemandRequest.getQuantity())
                   .demandType("ONHAND")
                   .build();
            Mockito.when(demandRepository.save(any(Demand.class))).thenReturn(demand);
            ResponseEntity<Object> response = demandService.createDemand(createDemandRequest);
            Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        }

        @Test
        @DisplayName("item not found")
        public void createDemandTest2() {
            CreateDemandRequest createDemandRequest = CreateDemandRequest.builder()
                   .itemId("id")
                   .locationId("id")
                   .quantity(100)
                   .build();
            Mockito.when(itemRepository.findByItemId("id")).thenReturn(null);
            ResponseEntity<Object> response = demandService.createDemand(createDemandRequest);
            Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        }

        @Test
        @DisplayName("location not found")
        public void createDemandTest3() {
            CreateDemandRequest createDemandRequest = CreateDemandRequest.builder()
                   .itemId("id")
                   .locationId("id")
                   .quantity(100)
                   .build();
            Mockito.when(itemRepository.findByItemId("id")).thenReturn(new Item());
            Mockito.when(locationRepository.findByLocationId("id")).thenReturn(null);
            ResponseEntity<Object> response = demandService.createDemand(createDemandRequest);
            Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        }

        @Test
        @DisplayName("demand already exists")
        public void createDemandTest4() {
            CreateDemandRequest createDemandRequest = CreateDemandRequest.builder()
                   .itemId("id")
                   .locationId("id")
                   .quantity(100)
                   .build();
            Mockito.when(itemRepository.findByItemId("id")).thenReturn(new Item());
            Mockito.when(locationRepository.findByLocationId("id")).thenReturn(new Location());
            Mockito.when(demandRepository.findByItemItemIdAndLocationLocationId("id", "id"))
                   .thenReturn(new Demand());
            ResponseEntity<Object> response = demandService.createDemand(createDemandRequest);
            Assertions.assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        }
    }

    @Nested
    @DisplayName("showAllDemandTest")
    class ShowAllDemand {
        @Test
        @DisplayName("showAllDemand return list of demand")
        public void showAllDemandTest1() {
            Demand demand = Demand.builder()
                   .demandType("type")
                   .item(Item.builder().itemId("id1").build())
                   .location(Location.builder().locationId("id1").build())
                   .build();
            List<Demand> demandList = List.of(demand, demand);
            Mockito.when(demandRepository.findAll()).thenReturn(demandList);
            ResponseEntity<Object> response = demandService.showAllDemand();
            Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        }

        @Test
        @DisplayName("showAllDemand return demand not found")
        public void showAllDemandTest2() {
            Demand demand = Demand.builder()
                   .demandType("type")
                   .item(Item.builder().itemId("id1").build())
                   .location(Location.builder().locationId("id1").build())
                   .build();
            List<Demand> demandList = new ArrayList<>();
            Mockito.when(demandRepository.findAll()).thenReturn(demandList);
            ResponseEntity<Object> response = demandService.showAllDemand();
            Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        }
    }

    @Nested
    @DisplayName("show demand test")
    class ShowDemand {
        @Test
        @DisplayName("show demand test returns demand")
        public void showDemandTest1() {
            Mockito.when(demandRepository.findByDemandId("id")).thenReturn(new Demand());
            ResponseEntity<Object> response = demandService.showDemand("id");
            Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        }

        @Test
        @DisplayName("show demand test returns demand not found")
        public void showDemandTest2() {
            Mockito.when(demandRepository.findByDemandId("id")).thenReturn(null);
            ResponseEntity<Object> response = demandService.showDemand("id");
            Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        }
    }

    @Nested
    @DisplayName("show demand using itemId and locationId")
    class ShowDemandUsingItemIdAndLocationId {
        @Test
        @DisplayName("show demand returns demand")
        public void showDemandTest1() {
            Mockito.when(demandRepository.findByItemItemIdAndLocationLocationId("id", "id")).thenReturn(new Demand());
            ResponseEntity<Object> response = demandService.showDemand("id", "id");
            Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        }

        @Test
        @DisplayName("show demand returns demand not found")
        public void showDemandTest2() {
            Mockito.when(demandRepository.findByItemItemIdAndLocationLocationId("id", "id")).thenReturn(null);
            ResponseEntity<Object> response = demandService.showDemand("id", "id");
            Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        }
    }

    @Nested
    @DisplayName("delete demand")
    class DeleteDemand {
        @Test
        @DisplayName("demand deleted successfully")
        public void deleteDemandTest1() {
            Mockito.when(demandRepository.findByDemandId("id")).thenReturn(new Demand());
            ResponseEntity<Object> response = demandService.deleteDemand("id");
            Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        }

        @Test
        @DisplayName("demand not found")
        public void deleteDemandTest2() {
            Mockito.when(demandRepository.findByDemandId("id")).thenReturn(null);
            ResponseEntity<Object> response = demandService.deleteDemand("id");
            Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        }
    }

    @Nested
    @DisplayName("update demand test")
    class UpdateDemand {
        // to do update test case
        @Test
        @DisplayName("demand not found")
        public void updateDemandTest1() {
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
            String demandId = "12345";
            UpdateDemandRequest updateDemandRequest = new UpdateDemandRequest();
            updateDemandRequest.setQuantity(10);

            Demand demand = Demand.builder()
                   .demandId(demandId)
                   .item(new Item())
                   .location(new Location())
                   .quantity(10)
                   .build();
            String supplyId = "12345";
            Supply supply = Supply.builder()
                   .supplyId(demandId)
                   .item(item)
                   .location(location)
                   .supplyType("type")
                   .quantity(100)
                   .build();
            Mockito.when(demandRepository.findByDemandId(demandId)).thenReturn(null);
//            Mockito.when(supplyRepository.findByItemItemIdAndLocationLocationId(anyString(), anyString())).thenReturn(supply);
            ResponseEntity<Object> response = demandService.updateDemand(demandId, updateDemandRequest);
//            Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        }
        @Test
        @DisplayName("demand updated successfully")
        public void updateDemandTest2() {
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

            String demandId = "12345";
            UpdateDemandRequest updateDemandRequest = UpdateDemandRequest.builder()
                   .quantity(45)
                   .build();
            Demand demand = Demand.builder()
                   .demandId(demandId)
                   .item(item)
                   .location(location)
                   .demandType("type")
                   .quantity(30)
                   .build();

            String supplyId = "12345";
            Supply supply = Supply.builder()
                   .supplyId(demandId)
                   .item(item)
                   .location(location)
                   .supplyType("type")
                   .quantity(100)
                   .build();
            Mockito.when(demandRepository.findByDemandId(demandId)).thenReturn(demand);
            Mockito.when(supplyRepository.findByItemItemIdAndLocationLocationId(anyString(), anyString())).thenReturn(supply);
            ResponseEntity<Object> response = demandService.updateDemand(demandId, updateDemandRequest);
            Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        }
    }
}

