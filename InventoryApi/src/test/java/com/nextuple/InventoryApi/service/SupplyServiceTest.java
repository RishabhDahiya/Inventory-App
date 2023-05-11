package com.nextuple.InventoryApi.service;

import com.nextuple.InventoryApi.model.Demand;
import com.nextuple.InventoryApi.model.Item;
import com.nextuple.InventoryApi.model.Location;
import com.nextuple.InventoryApi.model.Supply;
import com.nextuple.InventoryApi.payload.request.CreateSupplyRequest;
import com.nextuple.InventoryApi.payload.request.UpdateSupplyRequest;
import com.nextuple.InventoryApi.repository.ItemRepository;
import com.nextuple.InventoryApi.repository.LocationRepository;
import com.nextuple.InventoryApi.repository.SupplyRepository;
import com.nextuple.InventoryApi.repository.TransactionRepository;
import org.junit.jupiter.api.*;
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

@ExtendWith(MockitoExtension.class)
public class SupplyServiceTest {
    @Mock
    private ItemRepository itemRepository;
    @Mock
    private LocationRepository locationRepository;
    @Mock
    private SupplyRepository supplyRepository;
    @Mock
    private TransactionRepository transactionRepository;
    @Mock
    private SequenceGeneratorService sequenceGeneratorService;
    @InjectMocks
    private SupplyService supplyService;

    @Nested
    @DisplayName("create Supply test")
    class createSupply {
        @Test
        @DisplayName("supply created successfully")
        public void createSupplyTest1() {
            CreateSupplyRequest createSupplyRequest = CreateSupplyRequest.builder()
                   .itemId("id")
                   .locationId("id")
                   .quantity(100)
                   .build();
            Mockito.when(itemRepository.findByItemId("id")).thenReturn(new Item());
            Mockito.when(locationRepository.findByLocationId("id")).thenReturn(new Location());
            Mockito.when(supplyRepository.findByItemItemIdAndLocationLocationId("id", "id"))
                   .thenReturn(null);

            Supply supply = Supply.builder()
                   .item(Item.builder().build())
                   .location(Location.builder().build())
                   .quantity(createSupplyRequest.getQuantity())
                   .supplyType("ONHAND")
                   .build();
            Mockito.when(supplyRepository.save(any(Supply.class))).thenReturn(supply);
            ResponseEntity<Object> response = supplyService.createSupply(createSupplyRequest);
            assertEquals(HttpStatus.CREATED, response.getStatusCode());
        }

        @Test
        @DisplayName("item not found")
        public void createSupplyTest2() {
            CreateSupplyRequest createSupplyRequest = CreateSupplyRequest.builder()
                   .itemId("id")
                   .locationId("id")
                   .quantity(100)
                   .build();
            Mockito.when(itemRepository.findByItemId("id")).thenReturn(null);
            Supply supply = Supply.builder()
                   .item(Item.builder().build())
                   .location(Location.builder().build())
                   .quantity(createSupplyRequest.getQuantity())
                   .supplyType("ONHAND")
                   .build();
            ResponseEntity<Object> response = supplyService.createSupply(createSupplyRequest);
            assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        }

        @Test
        @DisplayName("location not found")
        public void createSupplyTest3() {
            CreateSupplyRequest createSupplyRequest = CreateSupplyRequest.builder()
                   .itemId("id")
                   .locationId("id")
                   .quantity(100)
                   .build();
            Mockito.when(itemRepository.findByItemId("id")).thenReturn(new Item());
            Mockito.when(locationRepository.findByLocationId("id")).thenReturn(null);
            Supply supply = Supply.builder()
                   .item(Item.builder().build())
                   .location(Location.builder().build())
                   .quantity(createSupplyRequest.getQuantity())
                   .supplyType("ONHAND")
                   .build();
            ResponseEntity<Object> response = supplyService.createSupply(createSupplyRequest);
            assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        }

        @Test
        @DisplayName("supply already exists")
        public void createSupplyTest4() {
            CreateSupplyRequest createSupplyRequest = CreateSupplyRequest.builder()
                   .itemId("id")
                   .locationId("id")
                   .quantity(100)
                   .build();
            Mockito.when(itemRepository.findByItemId("id")).thenReturn(new Item());
            Mockito.when(locationRepository.findByLocationId("id")).thenReturn(new Location());
            Mockito.when(supplyRepository.findByItemItemIdAndLocationLocationId("id", "id"))
                   .thenReturn(new Supply());
            Supply supply = Supply.builder()
                   .item(Item.builder().build())
                   .location(Location.builder().build())
                   .quantity(createSupplyRequest.getQuantity())
                   .supplyType("ONHAND")
                   .build();
            ResponseEntity<Object> response = supplyService.createSupply(createSupplyRequest);
            assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        }
    }

    @Nested
    @DisplayName("show all supply")
    class ShowAllSupply {
        @Test
        @DisplayName("showAllSupply returns list of supply")
        public void showAllSupplyTest1() {
            Supply supply = Supply.builder()
                   .supplyId("id")
                   .supplyType("type")
                   .item(Item.builder().itemId("id1").build())
                   .location(Location.builder().locationId("id1").build())
                   .build();
            List<Supply> supplyList = List.of(supply, supply);
            Mockito.when(supplyRepository.findAll()).thenReturn(supplyList);
            ResponseEntity<Object> response = supplyService.showAllSupply();
            assertEquals(HttpStatus.OK, response.getStatusCode());
        }

        @Test
        @DisplayName("showAllSupply returns supply not found")
        public void showAllSupplyTest2() {
            List<Supply> supplyList = new ArrayList<>();
            Mockito.when(supplyRepository.findAll()).thenReturn(supplyList);
            ResponseEntity<Object> response = supplyService.showAllSupply();
            assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        }
    }

    @Nested
    @DisplayName("show supply using supplyId")
    class SupplyId {
        @Test
        @DisplayName("showSupply returns supply")
        public void showSupplyTest1() {
            Mockito.when(supplyRepository.findBySupplyId("id")).thenReturn(new Supply());
            ResponseEntity<Object> response = supplyService.showSupply("id");
            assertEquals(HttpStatus.OK, response.getStatusCode());
        }

        @Test
        @DisplayName("showSupply returns supply not found")
        public void showSupplyTest2() {
            Mockito.when(supplyRepository.findBySupplyId("id")).thenReturn(null);
            ResponseEntity<Object> response = supplyService.showSupply("id");
            assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        }
    }

    @Nested
    @DisplayName("delete supply Test")
    class DeleteSupply {
        @Test
        @DisplayName("supply deleted successfully")
        public void deleteSupplyTest1() {
            Mockito.when(supplyRepository.findBySupplyId("id")).thenReturn(new Supply());
            ResponseEntity<Object> response = supplyService.deleteSupply("id");
            assertEquals(HttpStatus.OK, response.getStatusCode());
        }

        @Test
        @DisplayName("supply deleted successfully")
        public void deleteSupplyTest2() {
            Mockito.when(supplyRepository.findBySupplyId("id")).thenReturn(null);
            ResponseEntity<Object> response = supplyService.deleteSupply("id");
            assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        }
    }

    @Nested
    @DisplayName("update supply test")
    class UpdateSupply {
        @Test
        @DisplayName("supply updated successfully")
        public void updateSupllyTest1() {
            String supplyId = "12345";
            UpdateSupplyRequest updateSupplyRequest = new UpdateSupplyRequest();
            updateSupplyRequest.setQuantity(10);
            Supply supply = new Supply();
            supply.setSupplyId(supplyId);
            supply.setLocation(new Location());
            supply.setItem(new Item());
            supply.setQuantity(5);
            Mockito.when(supplyRepository.findBySupplyId(supplyId)).thenReturn(supply);
            ResponseEntity<Object> response = supplyService.updateSupply(supplyId, updateSupplyRequest);
            Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        }
        @Test
        @DisplayName("supply not found")
        public void updateSupllyTest2() {
            String supplyId = "12345";
            UpdateSupplyRequest updateSupplyRequest = new UpdateSupplyRequest();
            updateSupplyRequest.setQuantity(10);
            Mockito.when(supplyRepository.findBySupplyId(supplyId)).thenReturn(null);
            ResponseEntity<Object> response = supplyService.updateSupply(supplyId, updateSupplyRequest);
            Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        }
    }
    @Nested
    @DisplayName("show supply using itemId and loationId")
    class ShowSupplyUsingLocationIdAndItemId{
        @Test
        @DisplayName("show supply returns supply")
        public void showSupplyTest1()
        {
            Mockito.when(supplyRepository.findByItemItemIdAndLocationLocationId("id","id"))
                   .thenReturn(new Supply());
            ResponseEntity<Object> response = supplyService.showSupply("id","id");
            Assertions.assertEquals(HttpStatus.OK,response.getStatusCode());
        }
        @Test
        @DisplayName("show supply returns supply not found")
        public void showSupplyTest2()
        {
            Mockito.when(supplyRepository.findByItemItemIdAndLocationLocationId("id","id"))
                   .thenReturn(null);
            ResponseEntity<Object> response = supplyService.showSupply("id","id");
            Assertions.assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
        }
    }
}
