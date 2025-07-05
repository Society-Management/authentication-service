package com.societymanagement.authentication_service.controller;

import com.societymanagement.authentication_service.dto.LoginRequest;
import com.societymanagement.authentication_service.dto.RegisterRequest;
import com.societymanagement.authentication_service.dto.TokenRequest;
import com.societymanagement.authentication_service.dto.TokenResponse;
import com.societymanagement.authentication_service.exception.CustomException;
import com.societymanagement.authentication_service.service.UserServiceDao;
import com.societymanagement.authentication_service.utils.JwtUtils;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {
    private static final Logger log = LoggerFactory.getLogger(AuthController.class);
    @Autowired
    private UserServiceDao userDetailsService;
    @Autowired
    private JwtUtils jwtUtils;
    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterRequest request) {
        userDetailsService.saveUser(request);
        return new ResponseEntity<>("User successfully registered", HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginRequest user) {
        return new ResponseEntity<>(userDetailsService.loginUser(user), HttpStatus.OK);
    }
    @PostMapping("/validate")
    public ResponseEntity<TokenResponse> validateToken(@RequestBody @Valid TokenRequest request){
        log.warn("Token: {}" , request.getBearerToken());
            TokenResponse t = new TokenResponse();
            boolean isValid = jwtUtils.validateToken(request.getBearerToken());
            t.setValid(isValid);
            return new ResponseEntity<>(t, HttpStatus.OK);
    }
}