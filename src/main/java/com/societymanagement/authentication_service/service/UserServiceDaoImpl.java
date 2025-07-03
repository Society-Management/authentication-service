package com.societymanagement.authentication_service.service;

import com.societymanagement.authentication_service.dto.LoginRequest;
import com.societymanagement.authentication_service.dto.RegisterRequest;
import com.societymanagement.authentication_service.entity.Role;
import com.societymanagement.authentication_service.entity.Users;
import com.societymanagement.authentication_service.enums.RoleType;
import com.societymanagement.authentication_service.exception.CustomException;
import com.societymanagement.authentication_service.repository.RoleRepository;
import com.societymanagement.authentication_service.repository.UserRepository;
import com.societymanagement.authentication_service.utils.CurrentUser;
import com.societymanagement.authentication_service.utils.JwtUtils;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserServiceDaoImpl implements UserServiceDao{

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public void saveUser(@Valid RegisterRequest registerRequest) {
        Users user = new Users();
        user.setFullName(registerRequest.getUserName());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        if(registerRequest.getRolesNames() == null){
            throw new CustomException("Role is mandatory");
        }
        Set<Role> roles = registerRequest.getRolesNames().stream()
                .map(roleName -> {
                    try{
                        RoleType roleType = RoleType.valueOf(roleName); // Validate enum
                        return roleRepository.findByRoleName(roleType)
                                .orElseThrow(() -> new CustomException("Role not found: " + roleName));
                    }
                    catch(IllegalArgumentException e) {
                        throw new CustomException("Role name not matched");
                    }
                })
                .collect(Collectors.toSet());

        user.setRoles(roles);
        userRepository.save(user);
    }

    @Override
    public String loginUser(@Valid LoginRequest user) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword())
            );
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            Users u = userRepository.getByFullName(userDetails.getUsername());
            String token = jwtUtils.generateToken(userDetails.getUsername(), u);
            return token;
        }
        catch(Exception e){
            throw new CustomException("Incorrect username or password");
        }
    }

    @Override
    public Users findAll() {

        return userRepository.findById(CurrentUser.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + CurrentUser.getUserId()));
    }
}
