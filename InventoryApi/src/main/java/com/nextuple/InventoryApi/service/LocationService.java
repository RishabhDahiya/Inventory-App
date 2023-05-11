package com.nextuple.InventoryApi.service;

import com.nextuple.InventoryApi.exception.RecordNotFoundException;
import com.nextuple.InventoryApi.model.Item;
import com.nextuple.InventoryApi.model.Location;
import com.nextuple.InventoryApi.payload.request.CreateLocationRequest;
import com.nextuple.InventoryApi.payload.request.UpdateLocationRequest;
import com.nextuple.InventoryApi.payload.response.MessageResponse;
import com.nextuple.InventoryApi.repository.LocationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LocationService {
    private static final Logger logger = LoggerFactory.getLogger(LocationService.class);
    @Autowired
    private LocationRepository locationRepository;

    public ResponseEntity<MessageResponse> createLocation(CreateLocationRequest createLocationRequest) {
        try {
            String id = createLocationRequest.getLocationId();
            Location savedLocation = locationRepository.findByLocationId(id);
            if (savedLocation != null) {
                logger.warn("Location already exists with LocationId : {}", id);
                return new ResponseEntity<>(new MessageResponse("LocationId already exists for location :" + savedLocation.getLocationDesc())
                       , HttpStatus.CONFLICT);
            }
            savedLocation = locationRepository.findByLocationDesc(createLocationRequest.getLocationDesc());
            if (savedLocation != null) {
                logger.warn("Location already exists with Location description: {}", createLocationRequest.getLocationDesc());
                return new ResponseEntity<>(new MessageResponse("Location already exists with locationId :" + savedLocation.getLocationId())
                       , HttpStatus.CONFLICT);
            }
            Location location = Location.builder()
                   .locationId(createLocationRequest.getLocationId())
                   .locationDesc(createLocationRequest.getLocationDesc())
                   .locationType(createLocationRequest.getLocationType())
                   .pickupAllowed(createLocationRequest.isPickupAllowed())
                   .shippingAllowed(createLocationRequest.isShippingAllowed())
                   .deliveryAllowed(createLocationRequest.isDeliveryAllowed())
                   .addressLine1(createLocationRequest.getAddressLine1())
                   .addressLine2(createLocationRequest.getAddressLine2())
                   .addressLine3(createLocationRequest.getAddressLine3())
                   .city(createLocationRequest.getCity())
                   .state(createLocationRequest.getState())
                   .country(createLocationRequest.getCountry())
                   .pincode(createLocationRequest.getPincode())
                   .build();
            locationRepository.save(location);
            logger.info("Location created successfully with location ID: {}", location.getLocationId());
            return new ResponseEntity<>(new MessageResponse("Location created successfully"), HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Error creating location");
            return new ResponseEntity<>(new MessageResponse("Error creating location"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Object> showLocation(String id) {
        try {
            Location savedLocation = locationRepository.findByLocationId(id);
            if (savedLocation == null) {
                logger.warn("Location not found with id: {}", id);
                return new ResponseEntity<>("Location not found with id :" + id, HttpStatus.NOT_FOUND);
            }
            logger.info("Location found with id: {}", id);
            return new ResponseEntity<>(savedLocation, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error in finding location");
            return new ResponseEntity<>(new MessageResponse("error in finding location"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Object> showAllLocations() {
        try {
            List<Location> locationList = locationRepository.findAll();
            if (!locationList.isEmpty()) {
                logger.info("location list fetched");
                return new ResponseEntity<>(locationList, HttpStatus.OK);
            }
            logger.warn("location List is empty");
            return new ResponseEntity<>(new MessageResponse("no location found"), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error("Error in finding locations");
            return new ResponseEntity<>(new MessageResponse("error in finding locations"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<MessageResponse> deleteLocation(String id) {
        try {
            Location savedLocation = locationRepository.findByLocationId(id);
            if (savedLocation == null) {
                logger.warn("Location not found with id: {}", id);
                return new ResponseEntity<>(new MessageResponse("Location not found with id :" + id), HttpStatus.NOT_FOUND);
            }
            locationRepository.delete(savedLocation);
            logger.info("Location deleted with id: {}", id);
            return new ResponseEntity<>(new MessageResponse("Location with given id :" + id + " deleted successfully"), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error in deleting locations");
            return new ResponseEntity<>(new MessageResponse("error in deleting locations"), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public ResponseEntity<Object> updateLocation(String id, UpdateLocationRequest updateLocationRequest) {
        try {
            Location savedLocation = locationRepository.findByLocationId(id);
            if (savedLocation == null) {
                logger.warn("location not found");
                return new ResponseEntity<>(new MessageResponse("location not found"), HttpStatus.NOT_FOUND);
            }
                if(updateLocationRequest.getLocationDesc()!=null)
                {
                    savedLocation.setLocationDesc(updateLocationRequest.getLocationDesc());
                }

            if(updateLocationRequest.getLocationType()!=null)
            {
                savedLocation.setLocationType(updateLocationRequest.getLocationType());
            }
            if(updateLocationRequest.getPickupAllowed()!=null)
            {
                savedLocation.setPickupAllowed(updateLocationRequest.getPickupAllowed());
            }
            if(updateLocationRequest.getShippingAllowed()!=null)
            {
                savedLocation.setShippingAllowed(updateLocationRequest.getShippingAllowed());
            }
            if(updateLocationRequest.getDeliveryAllowed()!=null)
            {
                savedLocation.setDeliveryAllowed(updateLocationRequest.getDeliveryAllowed());
            }
            if(updateLocationRequest.getAddressLine1()!=null)
            {
                savedLocation.setAddressLine1(updateLocationRequest.getAddressLine1());
            }
            if(updateLocationRequest.getAddressLine2()!=null)
            {
                savedLocation.setAddressLine2(updateLocationRequest.getAddressLine2());
            }
            if(updateLocationRequest.getAddressLine3()!=null)
            {
                savedLocation.setAddressLine3(updateLocationRequest.getAddressLine3());
            }
            if(updateLocationRequest.getCity()!=null)
            {
                savedLocation.setCity(updateLocationRequest.getCity());
            }
            if(updateLocationRequest.getState()!=null)
            {
                savedLocation.setState(updateLocationRequest.getState());
            }
            if(updateLocationRequest.getCountry()!=null)
            {
                savedLocation.setCountry(updateLocationRequest.getCountry());
            }
            if(updateLocationRequest.getPincode()!=null)
            {
                savedLocation.setPincode(updateLocationRequest.getPincode());
            }
            locationRepository.save(savedLocation);
            return new ResponseEntity<>(new MessageResponse("location updated successfully"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new MessageResponse("error in updating location"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
