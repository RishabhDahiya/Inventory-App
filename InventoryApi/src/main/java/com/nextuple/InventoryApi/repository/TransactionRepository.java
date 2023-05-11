package com.nextuple.InventoryApi.repository;

import com.nextuple.InventoryApi.model.Transaction;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TransactionRepository extends MongoRepository<Transaction, String> {
}
