package com.nextuple.InventoryApi.controller;

import com.nextuple.InventoryApi.model.Demand;
import com.nextuple.InventoryApi.model.Supply;
import com.nextuple.InventoryApi.payload.request.CreateDemandRequest;
import com.nextuple.InventoryApi.payload.request.CreateSupplyRequest;
import com.nextuple.InventoryApi.payload.request.UpdateDemandRequest;
import com.nextuple.InventoryApi.payload.request.UpdateSupplyRequest;
import com.nextuple.InventoryApi.payload.response.MessageResponse;
import com.nextuple.InventoryApi.payload.response.ShowDemandResponse;
import com.nextuple.InventoryApi.payload.response.ShowSupplyResponse;
import com.nextuple.InventoryApi.service.DemandService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(MockitoExtension.class)
public class DemandControllerTest {
    @Mock
    private DemandService demandService;
    @InjectMocks
    private DemandController demandController;
    @Nested
    @DisplayName("create demand test")
    class CreateDemand {
        @Test
        @DisplayName("demand created successfully")
        public void createDemandTest1() {
            ResponseEntity<Object> expectedResponse = new ResponseEntity<>(new MessageResponse("demand created"), HttpStatus.CREATED);
            Mockito.when(demandService.createDemand(any())).thenReturn(expectedResponse);
            ResponseEntity<Object> actualResponse = demandController.createDemand(new CreateDemandRequest());
            Assertions.assertEquals(HttpStatus.CREATED, actualResponse.getStatusCode());
        }
        @Test
        @DisplayName("demand already exists")
        public void createDemandTest2() {
            ResponseEntity<Object> expectedResponse = new ResponseEntity<>(new MessageResponse("demand already exits"), HttpStatus.CONFLICT);
            Mockito.when(demandService.createDemand(any())).thenReturn(expectedResponse);
            ResponseEntity<Object> actualResponse = demandController.createDemand(new CreateDemandRequest());
            Assertions.assertEquals(HttpStatus.CONFLICT, actualResponse.getStatusCode());
        }

    }

    @Nested
    @DisplayName("show all demand test")
    class ShowAllSupply {
        @Test
        @DisplayName("show all demand return list of demand")
        public void showAllDemandTest1() {
            ResponseEntity<Object> expectedResponse = new ResponseEntity<>(new ArrayList<ShowDemandResponse>(), HttpStatus.OK);
            Mockito.when(demandService.showAllDemand()).thenReturn(expectedResponse);
            ResponseEntity<Object> actualResponse = demandController.showAllDemand();
            Assertions.assertEquals(HttpStatus.OK, actualResponse.getStatusCode());

        }

        @Test
        @DisplayName("show all demand returns demand not found")
        public void showAllDemandTest2() {
            ResponseEntity<Object> expectedResponse = new ResponseEntity<>(new MessageResponse("no demand found"), HttpStatus.NOT_FOUND);
            Mockito.when(demandService.showAllDemand()).thenReturn(expectedResponse);
            ResponseEntity<Object> actualResponse = demandController.showAllDemand();
            Assertions.assertEquals(HttpStatus.NOT_FOUND, actualResponse.getStatusCode());
        }
    }

    @Nested
    @DisplayName("show demand test using demandId")
    class ShowDemand {
        @Test
        @DisplayName("showDemand returns demand")
        public void showDemandTest1() {
            ResponseEntity<Object> expectedResponse = new ResponseEntity<>(new Demand(), HttpStatus.OK);
            Mockito.when(demandService.showDemand(anyString())).thenReturn(expectedResponse);
            ResponseEntity<Object> actualResponse = demandController.showDemand("id");
            Assertions.assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        }

        @Test
        @DisplayName("showDemand returns demand not found")
        public void showDemandTest2() {
            ResponseEntity<Object> expectedResponse = new ResponseEntity<>(new MessageResponse("demand not found"), HttpStatus.NOT_FOUND);
            Mockito.when(demandService.showDemand(anyString())).thenReturn(expectedResponse);
            ResponseEntity<Object> actualResponse = demandController.showDemand("id");
            Assertions.assertEquals(HttpStatus.NOT_FOUND, actualResponse.getStatusCode());
        }
    }

    @Nested
    @DisplayName("show demand test using itemId and locationId")
    class ShowSupplyWithItemIdAndLocationId {
        @Test
        @DisplayName("showDemand returns demand")
        public void showDemandTest1() {
            ResponseEntity<Object> expectedResponse = new ResponseEntity<>(new Demand(), HttpStatus.OK);
            Mockito.when(demandService.showDemand(anyString(),anyString())).thenReturn(expectedResponse);
            ResponseEntity<Object> actualResponse = demandController.showDemand("id","id");
            Assertions.assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        }

        @Test
        @DisplayName("showDemand returns demand not found")
        public void showDemandTest2() {
            ResponseEntity<Object> expectedResponse = new ResponseEntity<>(new Demand(), HttpStatus.NOT_FOUND);
            Mockito.when(demandService.showDemand(anyString(),anyString())).thenReturn(expectedResponse);
            ResponseEntity<Object> actualResponse = demandController.showDemand("id","id");
            Assertions.assertEquals(HttpStatus.NOT_FOUND, actualResponse.getStatusCode());
        }
    }

    @Nested
    @DisplayName("delete demand test")
    class DeleteDemand {
        @Test
        @DisplayName("demand deleted successfully")
        public void deleteSupplyTest1() {
            ResponseEntity<Object> expectedResponse = new ResponseEntity<>(new MessageResponse("deleted"), HttpStatus.OK);
            Mockito.when(demandService.deleteDemand(anyString())).thenReturn(expectedResponse);
            ResponseEntity<Object> actualResponse = demandController.deleteDemand("id");
            Assertions.assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        }

        @Test
        @DisplayName("deleteDemand returns supply not found")
        public void deleteSupplyTest2() {
            ResponseEntity<Object> expectedResponse = new ResponseEntity<>(new MessageResponse("demand not found"), HttpStatus.NOT_FOUND);
            Mockito.when(demandService.deleteDemand(anyString())).thenReturn(expectedResponse);
            ResponseEntity<Object> actualResponse = demandController.deleteDemand("id");
            Assertions.assertEquals(HttpStatus.NOT_FOUND, actualResponse.getStatusCode());
        }
    }
    @Nested
    @DisplayName("update demand test")
    class UpdateDemand{
        @Test
        @DisplayName("demand updated successfully")
        public void updateDemandTest1()
        {
            ResponseEntity<Object> expectedResponse = new ResponseEntity<>(new MessageResponse("demand updated"), HttpStatus.OK);
            Mockito.when(demandService.updateDemand(anyString(),any())).thenReturn(expectedResponse);
            ResponseEntity<Object> actualResponse = demandController.updateDemand("id",new UpdateDemandRequest());
            Assertions.assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        }
        @Test
        @DisplayName("demand not found")
        public void updateDemandTest2()
        {
            ResponseEntity<Object> expectedResponse = new ResponseEntity<>(new MessageResponse("demand not found"), HttpStatus.NOT_FOUND);
            Mockito.when(demandService.updateDemand(anyString(),any())).thenReturn(expectedResponse);
            ResponseEntity<Object> actualResponse = demandController.updateDemand("id",new UpdateDemandRequest());
            Assertions.assertEquals(HttpStatus.NOT_FOUND, actualResponse.getStatusCode());
        }
    }
}
