package com.example.shopproject.repository;

import com.example.shopproject.mode.User;

import java.util.List;

public interface UserRepository {
    void LoginRequest(String email, String password);
    void RegisterRequest(String Name, String email, String password);
    List<User> getListUser();
}
