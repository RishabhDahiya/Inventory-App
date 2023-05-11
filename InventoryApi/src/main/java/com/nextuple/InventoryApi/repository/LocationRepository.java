package com.nextuple.InventoryApi.repository;

import com.nextuple.InventoryApi.model.Location;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends MongoRepository<Location,String> {
    Location findByLocationId(String id);

    Location findByLocationDesc(String locationDesc);
}
