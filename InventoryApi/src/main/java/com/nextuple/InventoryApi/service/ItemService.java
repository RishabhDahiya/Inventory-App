package com.nextuple.InventoryApi.service;

import com.nextuple.InventoryApi.model.Item;
import com.nextuple.InventoryApi.payload.request.CreateItemRequest;
import com.nextuple.InventoryApi.payload.request.UpdateItemRequest;
import com.nextuple.InventoryApi.payload.response.MessageResponse;
import com.nextuple.InventoryApi.repository.ItemRepository;
import com.nextuple.InventoryApi.repository.LocationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {
    private static final Logger logger = LoggerFactory.getLogger(ItemService.class);
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private LocationRepository locationRepository;


    public ResponseEntity<MessageResponse> createItem(CreateItemRequest createItemRequest) {
        try {
            String id = createItemRequest.getItemId();
            Item savedItem = itemRepository.findByItemId(id);
            if (savedItem != null) {
                logger.warn("Item already exists with item ID: {}", id);
                return new ResponseEntity<>(new MessageResponse("Given ItemId already exists for item :" + savedItem.getItemDescription()), HttpStatus.CONFLICT);
            }
            savedItem = itemRepository.findByItemDescription(createItemRequest.getItemDescription());
            if (savedItem != null) {
                logger.warn("Item already exists with item description: {}", createItemRequest.getItemDescription());
                return new ResponseEntity<>(new MessageResponse("Item already exists with itemId : " + savedItem.getItemId()), HttpStatus.CONFLICT);
            }
            Item item = Item.builder()
                   .itemId(createItemRequest.getItemId())
                   .itemDescription(createItemRequest.getItemDescription())
                   .category(createItemRequest.getCategory())
                   .type(createItemRequest.getType())
                   .status(createItemRequest.getStatus())
                   .price(createItemRequest.getPrice())
                   .pickupAllowed(createItemRequest.isPickupAllowed())
                   .shippingAllowed(createItemRequest.isShippingAllowed())
                   .deliveryAllowed(createItemRequest.isDeliveryAllowed())
                   .build();
            itemRepository.save(item);
            logger.info("Item created successfully with item ID: {}", item.getItemId());

            return new ResponseEntity<>(new MessageResponse("Item created successfully"), HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Error creating item: {}", e.getMessage());
            return new ResponseEntity<>(new MessageResponse("Error creating item"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Object> showAllItems() {
        try {
            List<Item> itemList = itemRepository.findAll();
            if (!itemList.isEmpty()) {
                logger.info("Item list fetched");
                return new ResponseEntity<>(itemList, HttpStatus.OK);
            }
            logger.warn("Item List is empty");
            return new ResponseEntity<>(new MessageResponse("no item found"), HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            logger.error("Error in finding Items");
            return new ResponseEntity<>(new MessageResponse("error in finding items"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Object> showItemDetail(String id) {
        try {
            Item savedItem = itemRepository.findByItemId(id);
            if (savedItem == null) {
                logger.warn("Item not found with id: {}", id);
                return new ResponseEntity<>("Item not found with id :" + id, HttpStatus.NOT_FOUND);
            }
            logger.info("Item found with id: {}", id);
            return new ResponseEntity<>(savedItem, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error in finding item");
            return new ResponseEntity<>(new MessageResponse("error in finding item"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Object> deleteItem(String id) {
        try {
            Item savedItem = itemRepository.findByItemId(id);
            if (savedItem == null) {
                logger.warn("Item not found with id: {}", id);
                return new ResponseEntity<>("Item not found with id :" + id, HttpStatus.NOT_FOUND);
            }
            itemRepository.delete(savedItem);
            logger.info("Item deleted with id: {}", id);
            return new ResponseEntity<>(new MessageResponse("Item with id : " + id + " is deleted successfully"), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error in deleting item");
            return new ResponseEntity<>(new MessageResponse("error in deleting item"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Object> updateItem(String id, UpdateItemRequest updateItemRequest) {
        try {
            Item savedItem = itemRepository.findByItemId(id);
            if (savedItem == null) {
                logger.warn("item not found");
                return new ResponseEntity<>(new MessageResponse("item with id :" + id + " not found"), HttpStatus.NOT_FOUND);
            }
            if (updateItemRequest.getStatus() != null) {
                savedItem.setStatus(updateItemRequest.getStatus());
            }
            if (updateItemRequest.getPrice() != null) {
                savedItem.setPrice(updateItemRequest.getPrice());
            }
            if (updateItemRequest.getPickupAllowed() != null) {
                savedItem.setPickupAllowed(updateItemRequest.getPickupAllowed());
            }
            if (updateItemRequest.getShippingAllowed() != null) {
                savedItem.setShippingAllowed(updateItemRequest.getShippingAllowed());
            }
            if (updateItemRequest.getDeliveryAllowed() != null) {
                savedItem.setDeliveryAllowed(updateItemRequest.getDeliveryAllowed());
            }
            itemRepository.save(savedItem);
            logger.info("item successfully updated");
            return new ResponseEntity<>(new MessageResponse("item updated successfully"), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("error in updating item");
            return new ResponseEntity<>(new MessageResponse("error in updating item"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
