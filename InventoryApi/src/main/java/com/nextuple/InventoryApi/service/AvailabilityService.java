package com.nextuple.InventoryApi.service;

import com.nextuple.InventoryApi.model.Demand;
import com.nextuple.InventoryApi.model.Supply;
import com.nextuple.InventoryApi.payload.response.MessageResponse;
import com.nextuple.InventoryApi.payload.response.ShowAvailabilityResponse;
import com.nextuple.InventoryApi.repository.DemandRepository;
import com.nextuple.InventoryApi.repository.SupplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AvailabilityService {
    @Autowired
    private SupplyRepository supplyRepository;
    @Autowired
    private DemandRepository demandRepository;

    public ResponseEntity<Object> showAvailability(String itemId, String locationId) {
        try {
            Supply supply = supplyRepository.findByItemItemIdAndLocationLocationId(itemId, locationId);
            Demand demand = demandRepository.findByItemItemIdAndLocationLocationId(itemId, locationId);
            if (supply == null && demand == null) {
                return new ResponseEntity<>(new MessageResponse("Supply and Demand does not exists for above ItemId and LocationId"), HttpStatus.NOT_FOUND);
            }
            if (supply == null) {
                return new ResponseEntity<>(new MessageResponse("Supply does not exists for above ItemId and LocationId"), HttpStatus.NOT_FOUND);
            }
            if (demand == null) {
                return new ResponseEntity<>(new MessageResponse("Demand does not exists for above ItemId and LocationId"), HttpStatus.NOT_FOUND);
            }
            int count = supply.getQuantity() - demand.getQuantity();
            ShowAvailabilityResponse showAvailabilityResponse = ShowAvailabilityResponse.builder()
                   .itemId(itemId)
                   .locationId(locationId)
                   .quantity(count)
                   .build();
            return new ResponseEntity<>(showAvailabilityResponse, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new MessageResponse("error in finding availability"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Object> showAllAvailabilityByItemId(String itemId) {
        try {

            List<Supply> supplies = supplyRepository.findAllByItemItemId(itemId);

            List<Demand> demands = demandRepository.findAllByItemItemId(itemId);
            if (supplies == null || supplies.size() == 0) {
                return new ResponseEntity<>("list of availability has no item", HttpStatus.NOT_FOUND);
            }
            if (demands == null || demands.size() == 0) {
                return new ResponseEntity<>("list of availability has no item", HttpStatus.NOT_FOUND);
            }
            Map<String, Integer> supplyMap = supplies.stream()
                   .collect(Collectors.groupingBy(supply -> supply.getLocation().getLocationId(),
                          Collectors.summingInt(Supply::getQuantity)));

            Map<String, Integer> demandMap = demands.stream()
                   .collect(Collectors.groupingBy(demand -> demand.getLocation().getLocationId(),
                          Collectors.summingInt(Demand::getQuantity)));
            List<ShowAvailabilityResponse> responseList = new ArrayList<>();

            for (String locationId : supplyMap.keySet()) {
                Integer supplyQuantity = supplyMap.getOrDefault(locationId, 0);
                Integer demandQuantity = demandMap.getOrDefault(locationId, 0);
                Integer availability = supplyQuantity - demandQuantity;

                ShowAvailabilityResponse response = ShowAvailabilityResponse.builder()
                       .itemId(itemId)
                       .locationId(locationId)
                       .quantity(availability)
                       .build();
                responseList.add(response);
            }
            return new ResponseEntity<>(responseList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new MessageResponse("error in finding availability"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    public ResponseEntity<Object> showAllAvailability() {
        try {
            List<Supply> supplies = supplyRepository.findAll();

            List<Demand> demands = demandRepository.findAll();
            if (supplies == null || supplies.size() == 0) {
                return new ResponseEntity<>("list of availability has no item", HttpStatus.NOT_FOUND);
            }
            if (demands == null || demands.size() == 0) {
                return new ResponseEntity<>("list of availability has no item", HttpStatus.NOT_FOUND);
            }
            List<ShowAvailabilityResponse> availabilityList = new ArrayList<>();
            for (Supply supply : supplies) {
                for (Demand demand : demands) {
                    if (supply.getItem().getItemId().equals(demand.getItem().getItemId()) && supply.getLocation().getLocationId().equals(demand.getLocation().getLocationId())) {
                        int available = supply.getQuantity() - demand.getQuantity();
                        ShowAvailabilityResponse availability = new ShowAvailabilityResponse(supply.getItem().getItemId(),supply.getItem().getItemDescription(), supply.getLocation().getLocationId()
                               ,supply.getLocation().getLocationDesc(),available);
                        availabilityList.add(availability);
                    }
                }
            }
            return new ResponseEntity<>(availabilityList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new MessageResponse("error in finding availability"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
