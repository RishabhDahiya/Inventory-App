package com.nextuple.InventoryApi.repository;

import com.nextuple.InventoryApi.model.Demand;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface DemandRepository extends MongoRepository<Demand,String> {
    Demand findByItemItemIdAndLocationLocationId(String itemId, String locationId);

    Demand findByDemandId(String demandId);

    Demand findByLocationLocationId(String locationId);

    Demand findByItemItemId(String itemId);

    List<Demand> findAllByItemItemId(String itemId);
}
