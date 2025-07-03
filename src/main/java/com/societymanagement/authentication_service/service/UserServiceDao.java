package com.societymanagement.authentication_service.service;

import com.societymanagement.authentication_service.dto.LoginRequest;
import com.societymanagement.authentication_service.dto.RegisterRequest;
import com.societymanagement.authentication_service.entity.Users;

import java.util.List;

public interface UserServiceDao {
    public void saveUser(RegisterRequest registerRequest);
    public String loginUser(LoginRequest user);
    public Users findAll();
}
