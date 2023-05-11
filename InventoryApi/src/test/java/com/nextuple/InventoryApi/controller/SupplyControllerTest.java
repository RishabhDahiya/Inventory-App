package com.nextuple.InventoryApi.controller;

import com.nextuple.InventoryApi.model.Supply;
import com.nextuple.InventoryApi.payload.request.CreateSupplyRequest;
import com.nextuple.InventoryApi.payload.request.UpdateSupplyRequest;
import com.nextuple.InventoryApi.payload.response.MessageResponse;
import com.nextuple.InventoryApi.payload.response.ShowSupplyResponse;
import com.nextuple.InventoryApi.service.SupplyService;
import org.junit.jupiter.api.*;
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
public class SupplyControllerTest {
    @Mock
    private SupplyService supplyService;
    @InjectMocks
    private SupplyController supplyController;

    @Nested
    @DisplayName("create supply test")
    class CreateSupply {
        @Test
        @DisplayName("supply created successfully")
        public void createSupplyTest1() {
            CreateSupplyRequest createSupplyRequest = new CreateSupplyRequest();
            ResponseEntity<Object> expectedResponse = new ResponseEntity<>(new MessageResponse("supply created"), HttpStatus.CREATED);
            Mockito.when(supplyService.createSupply(any())).thenReturn(expectedResponse);
            ResponseEntity<Object> actualResponse = supplyController.createSupply(createSupplyRequest);
            Assertions.assertEquals(HttpStatus.CREATED, actualResponse.getStatusCode());
        }

        @Test
        @DisplayName("supply already exists")
        public void createSupplyTest2() {
            CreateSupplyRequest createSupplyRequest = new CreateSupplyRequest();
            ResponseEntity<Object> expectedResponse = new ResponseEntity<>(new MessageResponse("supply already exists"), HttpStatus.CONFLICT);
            Mockito.when(supplyService.createSupply(any())).thenReturn(expectedResponse);
            ResponseEntity<Object> actualResponse = supplyController.createSupply(createSupplyRequest);
            Assertions.assertEquals(HttpStatus.CONFLICT, actualResponse.getStatusCode());
        }
    }

    @Nested
    @DisplayName("show all supply test")
    class ShowAllSupply {
        @Test
        @DisplayName("show all supply return list of supply")
        public void showAllSupplyTest1() {
            ResponseEntity<Object> expectedResponse = new ResponseEntity<>(new ArrayList<ShowSupplyResponse>(), HttpStatus.OK);
            Mockito.when(supplyService.showAllSupply()).thenReturn(expectedResponse);
            ResponseEntity<Object> actualResponse = supplyController.showAllSupply();
            Assertions.assertEquals(HttpStatus.OK, actualResponse.getStatusCode());

        }

        @Test
        @DisplayName("show all supply returns supply not found")
        public void showAllSupplyTest2() {
            ResponseEntity<Object> expectedResponse = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            Mockito.when(supplyService.showAllSupply()).thenReturn(expectedResponse);
            ResponseEntity<Object> actualResponse = supplyController.showAllSupply();
            Assertions.assertEquals(HttpStatus.NOT_FOUND, actualResponse.getStatusCode());

        }
    }

    @Nested
    @DisplayName("show supply test using supplyId")
    class ShowSupply {
        @Test
        @DisplayName("showSupply returns supply")
        public void showSupplyTest1() {
            ResponseEntity<Object> expectedResponse = new ResponseEntity<>(new Supply(), HttpStatus.OK);
            Mockito.when(supplyService.showSupply(anyString())).thenReturn(expectedResponse);
            ResponseEntity<Object> actualResponse = supplyController.showSupply("id");
            Assertions.assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        }

        @Test
        @DisplayName("showSupply returns supply not found")
        public void showSupplyTest2() {
            ResponseEntity<Object> expectedResponse = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            Mockito.when(supplyService.showSupply(anyString())).thenReturn(expectedResponse);
            ResponseEntity<Object> actualResponse = supplyController.showSupply("id");
            Assertions.assertEquals(HttpStatus.NOT_FOUND, actualResponse.getStatusCode());
        }
    }

    @Nested
    @DisplayName("show supply test using itemId and locationId")
    class ShowSupplyWithItemIdAndLocationId {
        @Test
        @DisplayName("showSupply returns supply")
        public void showSupplyTest1() {
            ResponseEntity<Object> expectedResponse = new ResponseEntity<>(new Supply(), HttpStatus.OK);
            Mockito.when(supplyService.showSupply(anyString(), anyString())).thenReturn(expectedResponse);
            ResponseEntity<Object> actualResponse = supplyController.showSupply("id", "id");
            Assertions.assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        }

        @Test
        @DisplayName("showSupply returns supply not found")
        public void showSupplyTest2() {
            ResponseEntity<Object> expectedResponse = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            Mockito.when(supplyService.showSupply(anyString(), anyString())).thenReturn(expectedResponse);
            ResponseEntity<Object> actualResponse = supplyController.showSupply("id", "id");
            Assertions.assertEquals(HttpStatus.NOT_FOUND, actualResponse.getStatusCode());
        }
    }

    @Nested
    @DisplayName("delete supply test")
    class DeleteSupply {
        @Test
        @DisplayName("supply deleted successfully")
        public void deleteSupplyTest1() {
            ResponseEntity<Object> expectedResponse = new ResponseEntity<>(new MessageResponse("deleted"), HttpStatus.OK);
            Mockito.when(supplyService.deleteSupply(anyString())).thenReturn(expectedResponse);
            ResponseEntity<Object> actualResponse = supplyController.deleteSupply("id");
            Assertions.assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        }

        @Test
        @DisplayName("showSupply returns supply not found")
        public void showSupplyTest2() {
            ResponseEntity<Object> expectedResponse = new ResponseEntity<>(new MessageResponse("supply not found"), HttpStatus.NOT_FOUND);
            Mockito.when(supplyService.deleteSupply(anyString())).thenReturn(expectedResponse);
            ResponseEntity<Object> actualResponse = supplyController.deleteSupply("id");
            Assertions.assertEquals(HttpStatus.NOT_FOUND, actualResponse.getStatusCode());
        }
    }
    @Nested
    @DisplayName("update supply test")
    class UpdateSupply{
        @Test
        @DisplayName("supply updated successfully")
        public void updateSupplyTest1()
        {
            ResponseEntity<Object> expectedResponse = new ResponseEntity<>(new MessageResponse("supply updated"), HttpStatus.OK);
            Mockito.when(supplyService.updateSupply(anyString(),any())).thenReturn(expectedResponse);
            ResponseEntity<Object> actualResponse = supplyController.updateSupply("id",new UpdateSupplyRequest());
            Assertions.assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        }
        @Test
        @DisplayName("supply not found")
        public void updateSupplyTest2()
        {
            ResponseEntity<Object> expectedResponse = new ResponseEntity<>(new MessageResponse("supply not found"), HttpStatus.NOT_FOUND);
            Mockito.when(supplyService.updateSupply(anyString(),any())).thenReturn(expectedResponse);
            ResponseEntity<Object> actualResponse = supplyController.updateSupply("id",new UpdateSupplyRequest());
            Assertions.assertEquals(HttpStatus.NOT_FOUND, actualResponse.getStatusCode());
        }
    }
}
