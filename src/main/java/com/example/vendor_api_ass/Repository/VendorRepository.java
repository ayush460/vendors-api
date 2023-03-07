package com.example.vendor_api_ass.Repository;


import com.example.vendor_api_ass.Entity.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VendorRepository extends JpaRepository<Vendor,Integer> {

    Vendor findByVendorCode(String vendorCode);
}
