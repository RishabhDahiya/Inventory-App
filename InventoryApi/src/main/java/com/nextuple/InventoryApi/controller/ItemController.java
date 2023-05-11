package com.nextuple.InventoryApi.controller;

import com.nextuple.InventoryApi.model.Item;
import com.nextuple.InventoryApi.payload.request.CreateItemRequest;
import com.nextuple.InventoryApi.payload.request.UpdateItemRequest;
import com.nextuple.InventoryApi.payload.response.MessageResponse;
import com.nextuple.InventoryApi.service.ItemService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
public class ItemController {
    @Autowired
    private ItemService itemService;

    @PostMapping("/items")
    public ResponseEntity<MessageResponse> createItem(@Valid @RequestBody CreateItemRequest createItemRequest) {
        return itemService.createItem(createItemRequest);
    }

    @GetMapping("/items")
    public ResponseEntity<Object> showAllItem() {
        return itemService.showAllItems();
    }

    @GetMapping("/items/{itemId}")
    public ResponseEntity<Object> showItem(@PathVariable String itemId) {
        return itemService.showItemDetail(itemId);
    }

    @DeleteMapping("/items/{itemId}")
    public ResponseEntity<Object> deleteItem(@PathVariable String itemId) {
        return itemService.deleteItem(itemId);
    }

    @PatchMapping("/items/{itemId}")
    public ResponseEntity<Object> updateItem(@PathVariable String itemId,@RequestBody UpdateItemRequest updateItemRequest) {
        System.out.println("update");
        return itemService.updateItem(itemId,updateItemRequest);
    }
}
