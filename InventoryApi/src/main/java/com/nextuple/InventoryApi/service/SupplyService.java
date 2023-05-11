package com.nextuple.InventoryApi.service;

import com.nextuple.InventoryApi.model.Item;
import com.nextuple.InventoryApi.model.Location;
import com.nextuple.InventoryApi.model.Supply;
import com.nextuple.InventoryApi.model.Transaction;
import com.nextuple.InventoryApi.payload.request.CreateSupplyRequest;
import com.nextuple.InventoryApi.payload.request.UpdateSupplyRequest;
import com.nextuple.InventoryApi.payload.response.MessageResponse;
import com.nextuple.InventoryApi.payload.response.ShowSupplyResponse;
import com.nextuple.InventoryApi.repository.ItemRepository;
import com.nextuple.InventoryApi.repository.LocationRepository;
import com.nextuple.InventoryApi.repository.SupplyRepository;
import com.nextuple.InventoryApi.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class SupplyService {
    @Autowired
    private SupplyRepository supplyRepository;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private LocationRepository locationRepository;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;
    public ResponseEntity<Object> createSupply(CreateSupplyRequest createSupplyRequest) {
        try {
            String itemId = createSupplyRequest.getItemId();
            String locationId = createSupplyRequest.getLocationId();
            Item item = itemRepository.findByItemId(itemId);
            Location location = locationRepository.findByLocationId(locationId);
            if (item == null) {
                return new ResponseEntity<>(new MessageResponse("item not found"), HttpStatus.NOT_FOUND);
            }
            if (location == null) {
                return new ResponseEntity<>(new MessageResponse("location not found"), HttpStatus.NOT_FOUND);
            }
            Supply savedSupply = supplyRepository.findByItemItemIdAndLocationLocationId(itemId, locationId);
            if (savedSupply != null) {
                return new ResponseEntity<>(new MessageResponse("supply already exits"), HttpStatus.CONFLICT);
            }
            Supply supply = Supply.builder()
                   .item(item)
                   .location(location)
                   .quantity(createSupplyRequest.getQuantity())
                   .supplyType("ONHAND")
                   .build();
            supply.setSupplyId("S"+String.valueOf(sequenceGeneratorService.getSequenceNumber(Supply.SEQUENCE_NAME)));
            supplyRepository.save(supply);
            return new ResponseEntity<>(new MessageResponse("supply created successfully"), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new MessageResponse("error in creating supply"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    public ResponseEntity<Object> showAllSupply() {
        try {
            List<Supply> supplyList = supplyRepository.findAll();
            if (!supplyList.isEmpty()) {
                List<ShowSupplyResponse> supplies = new ArrayList<>();
                for(Supply supply:supplyList)
                {
                    ShowSupplyResponse response = ShowSupplyResponse.builder()
                           .supplyType("ONHAND")
                           .itemId(supply.getItem().getItemId())
                           .locationId(supply.getLocation().getLocationId())
                           .quantity(supply.getQuantity())
                           .supplyId(String.valueOf(supply.getSupplyId()))
                           .build();
                    supplies.add(response);
                }
                return new ResponseEntity<>(supplies, HttpStatus.OK);
            }
            return new ResponseEntity<>(new MessageResponse("supply not found"), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(new MessageResponse("error in finding supply"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Object> showSupply(String supplyId) {
        try{
            Supply supply = supplyRepository.findBySupplyId(supplyId);
            if(supply!=null)
            {
                return new ResponseEntity<>(supply, HttpStatus.OK);
            }
            return new ResponseEntity<>(new MessageResponse("supply not found"), HttpStatus.NOT_FOUND);
        }catch (Exception e)
        {
            return new ResponseEntity<>(new MessageResponse("error in finding supply"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Object> showSupply(String itemId, String locationId) {
        try{
            Supply supply = supplyRepository.findByItemItemIdAndLocationLocationId(itemId,locationId);
            if(supply!=null)
            {
                ShowSupplyResponse supplyResponse = ShowSupplyResponse.builder()
                       .itemId(itemId)
                       .locationId(locationId)
                       .quantity(supply.getQuantity())
                       .supplyType(supply.getSupplyType())
                       .build();
                return new ResponseEntity<>(supplyResponse, HttpStatus.OK);
            }
            return new ResponseEntity<>(new MessageResponse("supply not found"), HttpStatus.NOT_FOUND);
        }catch (Exception e)
        {
            return new ResponseEntity<>(new MessageResponse("error in finding supply"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    public ResponseEntity<Object> showSupplyLocationId(String locationId) {
//        try{
//            Supply supply = supplyRepository.findByLocationLocationId(locationId);
//            if(supply != null)
//            {
//                ShowSupplyLocationIdResponse supplyResponse = ShowSupplyLocationIdResponse.builder()
//                       .locationId(supply.getLocation().getLocationId())
//                       .quantity(supply.getQuantity())
//                       .supplyType(supply.getSupplyType())
//                       .build();
//                return  new ResponseEntity<>(supplyResponse,HttpStatus.OK);
//            }
//            return new ResponseEntity<>(new MessageResponse("supply not found"), HttpStatus.NOT_FOUND);
//        }catch (Exception e)
//        {
//            return new ResponseEntity<>(new MessageResponse("error in finding supply"), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

    public ResponseEntity<Object> deleteSupply(String supplyId) {
        try{
            Supply supply = supplyRepository.findBySupplyId(supplyId);
            if(supply!=null)
            {
                supplyRepository.delete(supply);
                return  new ResponseEntity<>(new MessageResponse("supply deleted successfully"),HttpStatus.OK);
            }
            return  new ResponseEntity<>(new MessageResponse("supply not found"),HttpStatus.NOT_FOUND);
        }catch (Exception e)
        {
            return new ResponseEntity<>(new MessageResponse("error in deleting supply"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Object> updateSupply(String supplyId, UpdateSupplyRequest updateSupplyRequest) {
        try{
            Supply supply = supplyRepository.findBySupplyId(supplyId);
            if(supply != null)
            {
                Transaction transaction = Transaction.builder()
                       .supplyId(supplyId)
                       .demandId("--")
                       .locationId(supply.getLocation().getLocationId())
                       .itemId(supply.getItem().getItemId())
                       .previousQuantity(supply.getQuantity())
                       .newQuantity(updateSupplyRequest.getQuantity())
                       .build();
                supply.setQuantity(updateSupplyRequest.getQuantity());
                transaction.setTransactionId(String.valueOf("T"+sequenceGeneratorService.getSequenceNumber(transaction.SEQUENCE_NAME)));
                supplyRepository.save(supply);
                transactionRepository.save(transaction);
                return  new ResponseEntity<>(new MessageResponse("supply updated successfully"),HttpStatus.OK);
            }
            return  new ResponseEntity<>(new MessageResponse("supply not found"),HttpStatus.NOT_FOUND);
        }catch (Exception e)
        {
            return new ResponseEntity<>(new MessageResponse("error in updating supply"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
