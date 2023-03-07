package com.example.vendor_api_ass.Entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="vendor")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Vendor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int vendorId;
    private String vendorName;
    private String address;
    private String city;
    private String state;
    @Column(unique = true)
    private String vendorCode;
    @NotNull
    @Size(min = 6, max = 6)
    private String pinCode;
    @NotNull
    @Size(min = 10, max = 10)
    private String mobileNumber;
    @Column(unique = true)
    @Email(message = "Invalid email address")
    private String email;


    public Vendor(int i, String s) {

    }
}


