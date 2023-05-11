package com.nextuple.InventoryApi.service;

import com.nextuple.InventoryApi.model.*;
import com.nextuple.InventoryApi.payload.request.CreateDemandRequest;
import com.nextuple.InventoryApi.payload.request.UpdateDemandRequest;
import com.nextuple.InventoryApi.payload.response.MessageResponse;
import com.nextuple.InventoryApi.payload.response.ShowDemandLocationIdResponse;
import com.nextuple.InventoryApi.payload.response.ShowDemandResponse;
import com.nextuple.InventoryApi.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DemandService {
    private static final Logger logger = LoggerFactory.getLogger(DemandService.class);

    @Autowired
    private DemandRepository demandRepository;
    @Autowired
    private SupplyRepository supplyRepository;
    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private LocationRepository locationRepository;
    @Autowired
    private TransactionRepository transactionRepository;

    public ResponseEntity<Object> createDemand(CreateDemandRequest createDemandRequest) {
        try {
            String itemId = createDemandRequest.getItemId();
            String locationId = createDemandRequest.getLocationId();
            Item item = itemRepository.findByItemId(itemId);
            Location location = locationRepository.findByLocationId(locationId);
            if (item == null) {
                return new ResponseEntity<>(new MessageResponse("item not found"), HttpStatus.NOT_FOUND);
            }
            if (location == null) {
                return new ResponseEntity<>(new MessageResponse("location not found"), HttpStatus.NOT_FOUND);
            }
            Demand savedDemand = demandRepository.findByItemItemIdAndLocationLocationId(itemId, locationId);
            if (savedDemand != null) {
                return new ResponseEntity<>(new MessageResponse("demand already exits"), HttpStatus.CONFLICT);
            }
            Demand demand = Demand.builder()
                   .item(item)
                   .location(location)
                   .demandType("ONHAND")
                   .quantity(createDemandRequest.getQuantity())
                   .build();
            demand.setDemandId("D"+String.valueOf(sequenceGeneratorService.getSequenceNumber(demand.SEQUENCE_NAME)));
            demandRepository.save(demand);
            return new ResponseEntity<>(new MessageResponse("demand created successfully"), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new MessageResponse("error in creating demand"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Object> showAllDemand() {
        try {
            List<Demand> demandList = demandRepository.findAll();
            if (!demandList.isEmpty()) {
                List<ShowDemandResponse> demands = new ArrayList<>();
                for (Demand demand : demandList) {
                    ShowDemandResponse response = ShowDemandResponse.builder()
                           .demandType("ONHAND")
                           .locationId(demand.getLocation().getLocationId())
                           .itemId(demand.getItem().getItemId())
                           .quantity(demand.getQuantity())
                           .demandId(String.valueOf(demand.getDemandId()))
                           .build();
                    demands.add(response);
                }
                return new ResponseEntity<>(demands, HttpStatus.OK);
            }
            return new ResponseEntity<>(new MessageResponse("demand not found"), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(new MessageResponse("error in finding demand"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Object> showDemand(String demandId) {
        try {
            Demand demand = demandRepository.findByDemandId(demandId);
            if (demand != null) {
                return new ResponseEntity<>(demand, HttpStatus.OK);
            }
            return new ResponseEntity<>(new MessageResponse("demand not found"), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(new MessageResponse("error in finding demand"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Object> showDemand(String itemId, String locationId) {
        try {
            Demand demand = demandRepository.findByItemItemIdAndLocationLocationId(itemId, locationId);
            if (demand != null) {
                ShowDemandResponse demandResponse = ShowDemandResponse.builder()
                       .itemId(itemId)
                       .locationId(locationId)
                       .quantity(demand.getQuantity())
                       .demandType(demand.getDemandType())
                       .build();
                return new ResponseEntity<>(demandResponse, HttpStatus.OK);
            }
            return new ResponseEntity<>(new MessageResponse("demand not found"), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(new MessageResponse("error in finding demand"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Object> deleteDemand(String demandId) {
        try {
            Demand demand = demandRepository.findByDemandId(demandId);
            if (demand != null) {
                demandRepository.delete(demand);
                return new ResponseEntity<>(new MessageResponse("demand deleted successfully"), HttpStatus.OK);
            }
            return new ResponseEntity<>(new MessageResponse("demand not found"), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(new MessageResponse("error in deleting demand"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Object> updateDemand(String demandId, UpdateDemandRequest updateDemandRequest) {
        try {
            logger.info("demandRepository: {}", demandRepository);
            logger.info("transactionRepository: {}", transactionRepository);
            Demand demand = demandRepository.findByDemandId(demandId);
            String itemId = demand.getItem().getItemId();
            String locationId = demand.getLocation().getLocationId();
            Supply supply = supplyRepository.findByItemItemIdAndLocationLocationId(itemId,locationId);
            if(updateDemandRequest.getQuantity() > supply.getQuantity())
            {
                return new ResponseEntity<>(new MessageResponse("demand quantity can not be greater than supply quantity"), HttpStatus.BAD_REQUEST);
            }
            else if (demand != null) {
                Transaction transaction = Transaction.builder()
                       .supplyId("---")
                       .demandId(demandId)
                       .locationId(demand.getLocation().getLocationId())
                       .itemId(demand.getItem().getItemId())
                       .previousQuantity(demand.getQuantity())
                       .newQuantity(updateDemandRequest.getQuantity())
                       .build();
                demand.setQuantity(updateDemandRequest.getQuantity());

                transaction.setTransactionId("T"+String.valueOf(sequenceGeneratorService.getSequenceNumber(transaction.SEQUENCE_NAME)));
                demandRepository.save(demand);
                transactionRepository.save(transaction);
                return new ResponseEntity<>(new MessageResponse("demand updated successfully"), HttpStatus.OK);
            }
            else
            return new ResponseEntity<>(new MessageResponse("demand not found"), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(new MessageResponse("error in updating demand"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
//    public ResponseEntity<Object> showDemandLocationId(String locationId) {
//        try{
//            Demand demand = demandRepository.findByLocationLocationId(locationId);
//            if(demand != null)
//            {
//                ShowDemandLocationIdResponse demandResponse = ShowDemandLocationIdResponse.builder()
//                       .locationId(demand.getLocation().getLocationId())
//                       .quantity(demand.getQuantity())
//                       .demandType(demand.getDemandType())
//                       .build();
//                return  new ResponseEntity<>(demandResponse,HttpStatus.OK);
//            }
//            return new ResponseEntity<>(new MessageResponse("demand not found"), HttpStatus.NOT_FOUND);
//        }catch (Exception e)
//        {
//            return new ResponseEntity<>(new MessageResponse("error in finding demand"), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
}
