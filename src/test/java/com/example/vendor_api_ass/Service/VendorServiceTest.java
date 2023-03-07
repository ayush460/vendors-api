package com.example.vendor_api_ass.Service;
import com.example.vendor_api_ass.Entity.Vendor;
import com.example.vendor_api_ass.Repository.VendorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VendorServiceTest {
    @Mock
    private VendorRepository vendorRepository;

    @InjectMocks
    private VendorService vendorService;


    @Test
    void createVendor() {
        Vendor vendor = new Vendor();
        vendor.setVendorCode("V001");
        vendor.setVendorName("Test Vendor");
        vendor.setAddress("Test Address");
        vendor.setCity("Test City");
        vendor.setEmail("test@vendor.com");
        vendor.setMobileNumber("1234567890");
        vendor.setPinCode("123456");
        vendor.setState("Test State");

        when(vendorRepository.findByVendorCode("V001")).thenReturn(null);

        // Act
        String result = vendorService.createVendor(vendor);

        // Assert
        verify(vendorRepository).findByVendorCode("V001");
        verify(vendorRepository).save(vendor);
        assertEquals("vendor added successfully", result);
    }

    @Test
    void testCreateVendorWithExistingCode() {
        // Arrange
        Vendor vendor = new Vendor();
        vendor.setVendorCode("V001");
        vendor.setVendorName("Test Vendor");
        vendor.setAddress("Test Address");
        vendor.setCity("Test City");
        vendor.setEmail("test@vendor.com");
        vendor.setMobileNumber("1234567890");
        vendor.setPinCode("123456");
        vendor.setState("Test State");

        when(vendorRepository.findByVendorCode("V001")).thenReturn(vendor);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> vendorService.createVendor(vendor));
    }


    @Test
    void getAllVendors() {
        Vendor vendor1 = new Vendor();
        vendor1.setVendorCode("V001");
        vendor1.setVendorName("Test Vendor 1");

        Vendor vendor2 = new Vendor();
        vendor2.setVendorCode("V002");
        vendor2.setVendorName("Test Vendor 2");

        List<Vendor> vendorList = Arrays.asList(vendor1, vendor2);

        when(vendorRepository.findAll()).thenReturn(vendorList);

        // Act
        List<Vendor> result = vendorService.getAllVendors();

        // Assert
        verify(vendorRepository).findAll();
        assertEquals(vendorList.size(), result.size());

        for (int i = 0; i < result.size(); i++) {
            assertEquals(vendorList.get(i).getVendorCode(), result.get(i).getVendorCode());
            assertEquals(vendorList.get(i).getVendorName(), result.get(i).getVendorName());
            // Add additional assertions for other properties if needed
        }
    }

    @Test
    void testGetAllVendorsWhenEmpty() {
        // Arrange
        when(vendorRepository.findAll()).thenThrow(new RuntimeException());

        // Act
        List<Vendor> result = vendorService.getAllVendors();

        // Assert
        verify(vendorRepository).findAll();
        assertTrue(result.isEmpty());
    }


    @Test
    void updateVendor() {
        Vendor existingVendor = new Vendor();
        existingVendor.setVendorId(1);
        existingVendor.setVendorCode("V001");
        existingVendor.setVendorName("Test Vendor");

        Vendor updatedVendor = new Vendor();
        updatedVendor.setVendorCode("V002");
        updatedVendor.setVendorName("Updated Vendor");
        updatedVendor.setAddress("New Address");
        updatedVendor.setCity("New City");
        updatedVendor.setState("New State");
        updatedVendor.setPinCode("123456");
        updatedVendor.setMobileNumber("9876543210");
        updatedVendor.setEmail("newemail@example.com");

        when(vendorRepository.findById(existingVendor.getVendorId())).thenReturn(Optional.of(existingVendor));

        // Act
        String result = vendorService.updateVendor(existingVendor.getVendorId(), updatedVendor);

        // Assert
        verify(vendorRepository).findById(existingVendor.getVendorId());
        verify(vendorRepository).save(existingVendor);

        assertEquals("updated successfully", result);
        assertEquals(updatedVendor.getVendorCode(), existingVendor.getVendorCode());
        assertEquals(updatedVendor.getVendorName(), existingVendor.getVendorName());
        assertEquals(updatedVendor.getAddress(), existingVendor.getAddress());
        assertEquals(updatedVendor.getCity(), existingVendor.getCity());
        assertEquals(updatedVendor.getState(), existingVendor.getState());
        assertEquals(updatedVendor.getPinCode(), existingVendor.getPinCode());
        assertEquals(updatedVendor.getMobileNumber(), existingVendor.getMobileNumber());
        assertEquals(updatedVendor.getEmail(), existingVendor.getEmail());
    }

    @Test
    void testUpdateVendorWhenNotFound() {
        // Arrange
        int nonExistingVendorId = 999;

        when(vendorRepository.findById(nonExistingVendorId)).thenReturn(Optional.empty());

        // Act
        String result = vendorService.updateVendor(nonExistingVendorId, new Vendor());

        // Assert
        verify(vendorRepository).findById(nonExistingVendorId);
        verify(vendorRepository, never()).save(any());

        assertEquals("Failed to update vendor with id " + nonExistingVendorId, result);
    }




    @Test
    void deleteVendor() {
        int vendorId = 1;
        Vendor vendor = new Vendor();
        vendor.setVendorId(vendorId);
        when(vendorRepository.findById(vendorId)).thenReturn(Optional.of(vendor));

        // When
        vendorService.deleteVendor(vendorId);

        // Then
        verify(vendorRepository).findById(vendorId);
        verify(vendorRepository).delete(vendor);
    }

    @Test
    public void testDeleteVendorNotFound() {
        // Given
        int vendorId = 1;
        when(vendorRepository.findById(vendorId)).thenReturn(Optional.empty());

        // When
        vendorService.deleteVendor(vendorId);

        // Then
        verify(vendorRepository).findById(vendorId);
        verify(vendorRepository, never()).delete(any());
    }

    @Test
    public void testDeleteVendorError() {
        // Given
        int vendorId = 1;
        when(vendorRepository.findById(vendorId)).thenThrow(new RuntimeException());

        // When
        vendorService.deleteVendor(vendorId);

        // Then
        verify(vendorRepository).findById(vendorId);
        verify(vendorRepository, never()).delete(any());
    }

}
