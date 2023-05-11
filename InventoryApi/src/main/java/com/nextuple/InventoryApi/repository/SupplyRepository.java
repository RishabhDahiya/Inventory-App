package com.nextuple.InventoryApi.repository;

import com.nextuple.InventoryApi.model.Supply;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface SupplyRepository extends MongoRepository<Supply,String> {
    Supply findByItemItemIdAndLocationLocationId(String itemId, String locationId);

    Supply findBySupplyId(String supplyId);

    Supply findByLocationLocationId(String locationId);

    Supply findByItemItemId(String itemId);

    List<Supply> findAllByItemItemId(String itemId);
}
