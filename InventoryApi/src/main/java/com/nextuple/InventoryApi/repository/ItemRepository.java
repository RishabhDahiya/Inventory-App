package com.nextuple.InventoryApi.repository;

import com.nextuple.InventoryApi.model.Item;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ItemRepository extends MongoRepository<Item,String> {
    Item findByItemId(String id);

    Item findByItemDescription(String itemDescription);
//    int findByItemIdAndLocationId(String itemId,String locationId);
}
