package com.societymanagement.authentication_service.repository;

import com.societymanagement.authentication_service.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
    public Users getByFullName(String userName);
}
