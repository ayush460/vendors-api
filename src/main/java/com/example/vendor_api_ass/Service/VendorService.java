package com.example.vendor_api_ass.Service;

import com.example.vendor_api_ass.Entity.Vendor;
import com.example.vendor_api_ass.Repository.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class VendorService {
    @Autowired
    VendorRepository vendorRepository;

    public String createVendor(Vendor vendor) {
        if (vendorRepository.findByVendorCode(vendor.getVendorCode()) != null) {
            throw new RuntimeException("Vendor code already exists");
        }
        vendor.setVendorName(vendor.getVendorName());
        vendor.setVendorCode(vendor.getVendorCode());
        vendor.setAddress(vendor.getAddress());
        vendor.setCity(vendor.getCity());
        vendor.setMobileNumber(vendor.getMobileNumber());
        vendor.setEmail(vendor.getEmail());
        vendor.setState(vendor.getState());
        vendor.setPinCode(vendor.getPinCode());
        vendorRepository.save(vendor);
        return "vendor added successfully";
    }

    public List<Vendor> getAllVendors() {

        try {
            return vendorRepository.findAll();
        } catch (Exception e) {

            return Collections.emptyList();
        }
    }
    public String updateVendor(int id, Vendor vendor) {
        try {
            Vendor newVendor = vendorRepository.findById(id).get();
            newVendor.setVendorName(vendor.getVendorName());
            newVendor.setVendorCode(vendor.getVendorCode());
            newVendor.setAddress(vendor.getAddress());
            newVendor.setCity(vendor.getCity());
            newVendor.setState(vendor.getState());
            newVendor.setPinCode(vendor.getPinCode());
            newVendor.setMobileNumber(vendor.getMobileNumber());
            newVendor.setEmail(vendor.getEmail());
            vendorRepository.save(newVendor);
           return "updated successfully";
        } catch (Exception e) {

            return "Failed to update vendor with id ";

        }
    }

    public void deleteVendor(int id) {
        try {
            Vendor vendor = vendorRepository.findById(id).get();
            vendorRepository.delete(vendor);
        } catch (Exception e) {

            System.out.println("Error deleting vendor with id ");
        }
    }



}






