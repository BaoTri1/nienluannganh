package com.example.shopproject.sqlite.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.shopproject.sqlite.Entity.Address;

import java.util.List;

@Dao
public interface AddressDAO {

    @Insert
    void insertAddress(Address address);

    @Query("SELECT * FROM Address where email = :email")
    List<Address> getListAddress(String email);

    @Query("DELETE FROM Address")
    void deleteAddress();

    @Query("SELECT * FROM Address where address = :address")
    Address getAddress(String address);
}
