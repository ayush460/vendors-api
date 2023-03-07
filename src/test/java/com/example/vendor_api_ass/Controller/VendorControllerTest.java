package com.example.vendor_api_ass.Controller;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

import com.example.vendor_api_ass.Entity.Vendor;
import com.example.vendor_api_ass.Service.VendorService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

public class VendorControllerTest {

    private VendorController vendorController;
    private VendorService vendorService;

    @Before
    public void setUp() {
        vendorService = mock(VendorService.class);
        vendorController = new VendorController();
        vendorController.vendorService = vendorService;
    }

    @Test
    public void testCreateVendorSuccess() {
        Vendor vendor = new Vendor();
        vendor.setVendorName("Test Vendor");
        when(vendorService.createVendor(vendor)).thenReturn("Vendor created successfully");
        ResponseEntity<String> response = vendorController.createVendor(vendor);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Vendor created successfully", response.getBody());
    }

    @Test
    public void testCreateVendorFailure() {
        Vendor vendor = new Vendor();
        vendor.setVendorName("Test Vendor");
        when(vendorService.createVendor(vendor)).thenThrow(new RuntimeException("Unable to create vendor"));
        ResponseEntity<String> response = vendorController.createVendor(vendor);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Error creating vendor", response.getBody());
    }

  @Test
    public void testUpdateVendor_Success() {
        int vendorId = 1;
        Vendor vendor = new Vendor();
        vendor.setVendorName("Test Vendor");
        vendor.setAddress("123 Main St.");
        vendor.setMobileNumber("555-555-5555");

        when(vendorService.updateVendor(vendorId, vendor)).thenReturn("Vendor updated successfully");

        ResponseEntity<String> response = vendorController.updateVendor(vendorId, vendor);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Vendor updated successfully", response.getBody());
    }

    @Test
    public void testUpdateVendor_NotFound() {
        int vendorId = 1;
        Vendor vendor = new Vendor();
        vendor.setVendorName("Test Vendor");
        vendor.setAddress("123 Main St.");
        vendor.setMobileNumber("555-555-5555");

        when(vendorService.updateVendor(vendorId, vendor)).thenThrow(new NoSuchElementException());

        ResponseEntity<String> response = vendorController.updateVendor(vendorId, vendor);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testUpdateVendor_InternalServerError() {
        int vendorId = 1;
        Vendor vendor = new Vendor();
        vendor.setVendorName("Test Vendor");
        vendor.setAddress("123 Main St.");
        vendor.setMobileNumber("555-555-5555");

        when(vendorService.updateVendor(vendorId, vendor)).thenThrow(new RuntimeException());

        ResponseEntity<String> response = vendorController.updateVendor(vendorId, vendor);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Error updating vendor", response.getBody());
    }

    @Test
    public void testDeleteVendor_Success() {
        int vendorId = 1;

        doNothing().when(vendorService).deleteVendor(vendorId);

        ResponseEntity<String> response = vendorController.deleteVendor(vendorId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Vendor deleted successfully", response.getBody());
    }

    @Test
    public void testDeleteVendor_NotFound() {
        int vendorId = 1;

        doThrow(new EmptyResultDataAccessException(1)).when(vendorService).deleteVendor(vendorId);

        ResponseEntity<String> response = vendorController.deleteVendor(vendorId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testDeleteVendor_InternalServerError() {
        int vendorId = 1;

        doThrow(new RuntimeException()).when(vendorService).deleteVendor(vendorId);

        ResponseEntity<String> response = vendorController.deleteVendor(vendorId);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Error deleting vendor", response.getBody());
    }
    @Test
    public void testGetAllVendor_Success() {
        List<Vendor> vendors = Arrays.asList(
                new Vendor(1, "Vendor 1"),
                new Vendor(2, "Vendor 2")
        );

        when(vendorService.getAllVendors()).thenReturn(vendors);

        ResponseEntity<List<Vendor>> response = vendorController.getAllVendor();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(vendors, response.getBody());
    }

    @Test
    public void testGetAllVendor_NoContent() {
        when(vendorService.getAllVendors()).thenReturn(Collections.emptyList());

        ResponseEntity<List<Vendor>> response = vendorController.getAllVendor();

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
    }
}

