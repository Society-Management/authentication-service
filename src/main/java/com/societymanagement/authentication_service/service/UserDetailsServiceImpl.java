package com.societymanagement.authentication_service.service;

import com.societymanagement.authentication_service.entity.Users;
import com.societymanagement.authentication_service.exception.CustomException;
import com.societymanagement.authentication_service.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users u = userRepository.getByFullName(username);
        if(u != null){
            return org.springframework.security.core.userdetails.User.builder()
                    .username(u.getFullName())
                    .password(u.getPassword())
                    .roles(
                            u.getRoles().stream()
                                    .map(role -> role.getRoleName().name())
                                    .toArray(String[]::new)
                    )
                    .build();
        }
        throw new CustomException("User not found with username: " + username);
    }
}
