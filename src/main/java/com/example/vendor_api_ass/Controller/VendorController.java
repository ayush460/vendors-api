package com.example.vendor_api_ass.Controller;


import com.example.vendor_api_ass.Entity.Vendor;
import com.example.vendor_api_ass.Service.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("vendors")
public class VendorController {
    @Autowired
    VendorService vendorService;
    @PostMapping("/create")
    public ResponseEntity<String> createVendor(@RequestBody Vendor vendor){

        try {
            String message = vendorService.createVendor(vendor);
            return ResponseEntity.ok(message);
        } catch (Exception e) {



            // return an error response with a 500 status code
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating vendor");
        }
    }
    @PutMapping("/update/{vendorId}")
    public ResponseEntity<String> updateVendor(@PathVariable int vendorId, @RequestBody Vendor vendor) {
        try {
            String message = vendorService.updateVendor(vendorId, vendor);
            return ResponseEntity.ok(message);
        } catch (NoSuchElementException e) {
            // return a 404 Not Found response if the vendorId does not exist
            return ResponseEntity.notFound().build();
        } catch (Exception e) {


            // return an error response with a 500 status code
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating vendor");
        }
    }
    @DeleteMapping("/delete/{vendorId}")
    public ResponseEntity<String> deleteVendor(@PathVariable int vendorId) {
        try {
            vendorService.deleteVendor(vendorId);
            return ResponseEntity.ok("Vendor deleted successfully");
        } catch (EmptyResultDataAccessException e) {
            // return a 404 Not Found response if the vendorId does not exist
            return ResponseEntity.notFound().build();
        } catch (Exception e) {


            // return an error response with a 500 status code
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting vendor");
        }
    }
    @GetMapping("/getVendor")
    public ResponseEntity<List<Vendor>> getAllVendor() {
        List<Vendor> vendors = vendorService.getAllVendors();
        if (vendors.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(vendors);
    }

}


