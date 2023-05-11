package com.nextuple.InventoryApi.controller;

import com.nextuple.InventoryApi.model.Item;
import com.nextuple.InventoryApi.payload.request.CreateItemRequest;
import com.nextuple.InventoryApi.payload.request.UpdateItemRequest;
import com.nextuple.InventoryApi.payload.response.MessageResponse;
import com.nextuple.InventoryApi.service.ItemService;
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
public class ItemControllerTest {
    @Mock
    private ItemService itemService;
    @InjectMocks
    private ItemController itemController;

    @Nested
    @DisplayName("Test cases for createItem")
    class CreateItem {
        @Test
        @DisplayName("when item is successfully created")
        public void createItemTest1() {
            CreateItemRequest createItemRequest = new CreateItemRequest();
            ResponseEntity<MessageResponse> expectedResponse = new ResponseEntity<>(new MessageResponse("item created successfully")
                   , HttpStatus.CREATED);
            Mockito.when(itemService.createItem(createItemRequest)).thenReturn(expectedResponse);
            ResponseEntity<MessageResponse> actualResponse = itemController.createItem(createItemRequest);
            Assertions.assertEquals(expectedResponse, actualResponse);
        }

        @Test
        @DisplayName("when itemId already exists")
        public void createItemTest2() {
            CreateItemRequest createItemRequest = new CreateItemRequest();
            ResponseEntity<MessageResponse> expectedResponse = new ResponseEntity<>(new MessageResponse("Given ItemId already exists"), HttpStatus.OK);
            Mockito.when(itemService.createItem(createItemRequest)).thenReturn(expectedResponse);
            ResponseEntity<MessageResponse> actualResponse = itemController.createItem(createItemRequest);
            Assertions.assertEquals(expectedResponse, actualResponse);
        }

        @Test
        @DisplayName("when item already exists with different itemId")
        public void createItemTest3() {
            CreateItemRequest createItemRequest = new CreateItemRequest();
            ResponseEntity<MessageResponse> expectedResponse = new ResponseEntity<>(new MessageResponse("Item already exits with another itemId"), HttpStatus.OK);

            Mockito.when(itemService.createItem(createItemRequest)).thenReturn(expectedResponse);
            ResponseEntity<MessageResponse> actualResponse = itemController.createItem(createItemRequest);
            Assertions.assertEquals(expectedResponse, actualResponse);
        }
    }

    @Nested
    @DisplayName("test case for showAllItem")
    class ShowAllItem {
        @Test
        @DisplayName("showAllItem returns list of Item")
        public void showAllItemTest()
        {
            ResponseEntity<Object> expectedResponse = new ResponseEntity<>(new ArrayList<Item>(),HttpStatus.OK);
            Mockito.when(itemService.showAllItems()).thenReturn(expectedResponse);
            ResponseEntity<Object> actualResponse = itemController.showAllItem();
            Assertions.assertEquals(expectedResponse,actualResponse);
        }
    }
    @Nested
    @DisplayName("Test case for showItemDetail")
    class ShowItemDetail{
        @Test
        @DisplayName("when item is present")
        public void showItemDetailTest1()
        {
            ResponseEntity<Object> expectedResponse = new ResponseEntity<>(new Item(),HttpStatus.OK);
            Mockito.when(itemService.showItemDetail(anyString())).thenReturn(expectedResponse);
            ResponseEntity<Object> actualResponse = itemController.showItem("id");
            Assertions.assertEquals(expectedResponse,actualResponse);
        }
        @Test
        @DisplayName("when item is not present")
        public void showItemDetailTest2()
        {
            ResponseEntity<Object> expectedResponse = new ResponseEntity<>(new MessageResponse("item not found"),HttpStatus.NOT_FOUND);
            Mockito.when(itemService.showItemDetail(anyString())).thenReturn(expectedResponse);
            ResponseEntity<Object> actualResponse = itemController.showItem("id");
            Assertions.assertEquals(expectedResponse,actualResponse);
        }
    }
    @Nested
    @DisplayName("test case for deleteItem")
    class DeleteItem{
        @Test
        @DisplayName("when item is present")
        public void deleteItemTest1()
        {
            ResponseEntity<Object> expectedResponse = new ResponseEntity<>(new MessageResponse("item deleted"),HttpStatus.OK);
            Mockito.when(itemService.deleteItem(anyString())).thenReturn(expectedResponse);
            ResponseEntity<Object> actualResponse = itemController.deleteItem("id");
            Assertions.assertEquals(expectedResponse,actualResponse);
        }
        @Test
        @DisplayName("when item is not present")
        public void deleteItemTest2()
        {
            ResponseEntity<Object> expectedResponse = new ResponseEntity<>(new MessageResponse("item not found"),HttpStatus.NOT_FOUND);
            Mockito.when(itemService.deleteItem(anyString())).thenReturn(expectedResponse);
            ResponseEntity<Object> actualResponse = itemController.deleteItem("id");
            Assertions.assertEquals(expectedResponse,actualResponse);
        }
    }
    @Nested
    @DisplayName("update item test")
    class updateItem{
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
            ResponseEntity<Object> expectedResponse = new ResponseEntity<>(new MessageResponse("item updated successfully"),HttpStatus.OK);
            Mockito.when(itemService.updateItem(anyString(),any())).thenReturn(expectedResponse);
            ResponseEntity<Object> actualResponse = itemController.updateItem("id",updateItemRequest);
        }
        @Test
        @DisplayName("item not found")
        public void updateItemTest2()
        {
            UpdateItemRequest updateItemRequest = UpdateItemRequest.builder()
                   .status("status")
                   .price(200.0)
                   .pickupAllowed(true)
                   .deliveryAllowed(true)
                   .shippingAllowed(true)
                   .build();
            ResponseEntity<Object> expectedResponse = new ResponseEntity<>(new MessageResponse("item not found"),HttpStatus.NOT_FOUND);
            Mockito.when(itemService.updateItem(anyString(),any())).thenReturn(expectedResponse);
            ResponseEntity<Object> actualResponse = itemController.updateItem("id",updateItemRequest);
        }

    }
}