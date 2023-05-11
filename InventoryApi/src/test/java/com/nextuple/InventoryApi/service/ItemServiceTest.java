package com.nextuple.InventoryApi.service;

import com.nextuple.InventoryApi.exception.RecordNotFoundException;
import com.nextuple.InventoryApi.model.Item;
import com.nextuple.InventoryApi.payload.request.CreateItemRequest;
import com.nextuple.InventoryApi.payload.request.UpdateItemRequest;
import com.nextuple.InventoryApi.payload.response.MessageResponse;
import com.nextuple.InventoryApi.repository.ItemRepository;
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
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(MockitoExtension.class)
public class ItemServiceTest {
    @Mock
    private ItemRepository itemRepository;
    @InjectMocks
    private ItemService itemService;

    @Nested
    @DisplayName("create Item Test")
    class CreateItem {
        @Test
        @DisplayName("create Item Successful")
        public void createItemTest1() {
            CreateItemRequest request = CreateItemRequest.builder()
                   .itemId("1")
                   .itemDescription("Jeans")
                   .build();
            Item item = Item.builder()
                   .itemId("1")
                   .itemDescription("Jeans")
                   .build();
            Mockito.when(itemRepository.findByItemId(anyString())).thenReturn(null);
            Mockito.when(itemRepository.findByItemDescription(anyString())).thenReturn(null);
            Mockito.when(itemRepository.save(any(Item.class))).thenReturn(item);
            ResponseEntity<MessageResponse> actualResponse = itemService.createItem(request);
            Assertions.assertNotNull(actualResponse);
            Assertions.assertEquals(HttpStatus.CREATED, actualResponse.getStatusCode());
        }

        @Test
        @DisplayName("create Item returns itemId already exists and HttpStatus.CONFLICT")
        public void createItemTest2() {
            CreateItemRequest request = CreateItemRequest.builder()
                   .itemId("1")
                   .itemDescription("Jeans")
                   .build();
            Item item = Item.builder()
                   .itemId("1")
                   .itemDescription("Jeans")
                   .build();
            Mockito.when(itemRepository.findByItemId(anyString())).thenReturn(item);
            ResponseEntity<MessageResponse> actualResponse = itemService.createItem(request);
            Assertions.assertNotNull(actualResponse);
            Assertions.assertEquals(HttpStatus.CONFLICT, actualResponse.getStatusCode());
        }

        @Test
        @DisplayName("create Item returns item already exists with different itemId and HttpStatus.CONFLICT")
        public void createItemTest3() {
            CreateItemRequest request = CreateItemRequest.builder()
                   .itemId("1")
                   .itemDescription("Jeans")
                   .build();
            Item item = Item.builder()
                   .itemId("1")
                   .itemDescription("Jeans")
                   .build();
            Mockito.when(itemRepository.findByItemId(anyString())).thenReturn(null);
            Mockito.when(itemRepository.findByItemDescription(anyString())).thenReturn(item);
            ResponseEntity<MessageResponse> actualResponse = itemService.createItem(request);
            Assertions.assertNotNull(actualResponse);
            Assertions.assertEquals(HttpStatus.CONFLICT, actualResponse.getStatusCode());
        }
    }

    @Nested
    @DisplayName("showAllItems Test")
    class ShowAllItems {
        @Test
        @DisplayName("showAllItem returns List of Item")
        public void showAllItemTest1() {
            Item item = Item.builder().build();
            List<Item> itemList = List.of(item, item);
            Mockito.when(itemRepository.findAll()).thenReturn(itemList);
            ResponseEntity<Object> response = itemService.showAllItems();
            Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        }

        @Test
        @DisplayName("showAllItem returns message no item found and NOT_FOUND status code")
        public void showAllItemTest2() {
            List<Item> itemList = new ArrayList<>();
            Mockito.when(itemRepository.findAll()).thenReturn(itemList);
            ResponseEntity<Object> response = itemService.showAllItems();
            Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        }
    }

    @Nested
    @DisplayName("showItemDetail test")
    class ShowItemDetail {
        @Test
        @DisplayName("showItemDetail returns Item")
        public void showItemDetailTest1() {
            Item item = Item.builder().build();
            Mockito.when(itemRepository.findByItemId(anyString())).thenReturn(item);
            ResponseEntity<Object> response = itemService.showItemDetail("id");
            Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        }

        @Test
        @DisplayName("showItemDetail returns returns message item not found")
        public void showItemDetailTest2() {
            Mockito.when(itemRepository.findByItemId(anyString())).thenReturn(null);
            ResponseEntity<Object> response = itemService.showItemDetail("id");
            Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        }
    }

    @Nested
    @DisplayName("showItemDetail test")
    class deleteItem {
        @Test
        @DisplayName("item deleted successfully")
        public void deleteItem1() {
            Item item = Item.builder().build();
            Mockito.when(itemRepository.findByItemId(anyString())).thenReturn(item);
            ResponseEntity<Object> response = itemService.deleteItem("id");
            Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        }

        @Test
        @DisplayName("when item is not present")
        public void deleteItemTest2() {
            Mockito.when(itemRepository.findByItemId(anyString())).thenReturn(null);
            ResponseEntity<Object> response = itemService.deleteItem("id");
            Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        }
    }

    @Nested
    @DisplayName("update Item Test")
    class UpdateItem {
        @Test
        @DisplayName("item updated successfully")
        public void updateItemTest1()
        {
            UpdateItemRequest updateItemRequest = UpdateItemRequest.builder()
                   .status("status")
                   .price(200.0)
                   .pickupAllowed(true)
                   .deliveryAllowed(true)
                   .shippingAllowed(true)
                   .build();

            Item item = Item.builder()
                   .itemId("1")
                   .itemDescription("Jeans")
                   .build();
            Mockito.when(itemRepository.findByItemId(anyString())).thenReturn(item);
            Mockito.when(itemRepository.save(any())).thenReturn(item);

            ResponseEntity<Object> response = itemService.updateItem("id",updateItemRequest);
            Assertions.assertEquals(HttpStatus.OK,response.getStatusCode());
        }
        @Test
        @DisplayName("when item not found")
        public void updateItemTest2()
        {
            UpdateItemRequest updateItemRequest = UpdateItemRequest.builder()
                   .status("status")
                   .price(200.0)
                   .pickupAllowed(true)
                   .deliveryAllowed(true)
                   .shippingAllowed(true)
                   .build();
            Mockito.when(itemRepository.findByItemId(anyString())).thenReturn(null);
            ResponseEntity<Object> response = itemService.updateItem("id",updateItemRequest);
            Assertions.assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
        }
    }

}
