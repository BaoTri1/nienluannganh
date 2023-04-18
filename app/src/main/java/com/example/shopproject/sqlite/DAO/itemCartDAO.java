package com.example.shopproject.sqlite.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.shopproject.sqlite.Entity.itemCart;

import java.util.List;

@Dao
public interface itemCartDAO {

    @Insert
    void insertItemCart(itemCart item);

    @Query("SELECT count(*) FROM ItemCart where email = :email")
    int getNumItemCart(String email);

    @Query("DELETE FROM ItemCart where slug = :slug and email = :email and size = :size")
    void deleteItemCart(String slug, String email, String size);

    @Query("DELETE FROM ItemCart ")
    void deleteAllItemCart();

    @Query("Update ItemCart set quantity = :quantity where email = :email and slug = :slug and size = :size")
    void updateQuantityItemCart(String email, String slug, String size, int quantity);

    @Query("SELECT * FROM ItemCart where email = :email")
    List<itemCart> getListItemCart(String email);

    @Query("SELECT * FROM ItemCart where email = :email and slug = :slug and size = :size")
    itemCart getItemCartBySlug(String email, String slug, String size);



}
