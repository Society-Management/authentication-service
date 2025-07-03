package com.societymanagement.authentication_service.controller;

import com.societymanagement.authentication_service.entity.Users;
import com.societymanagement.authentication_service.service.UserServiceDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserServiceDao userServiceDao;

    @GetMapping("/findAll")
    public ResponseEntity<Users> getUserDetails(){
        return new ResponseEntity<>(userServiceDao.findAll(), HttpStatus.OK);
    }
}
