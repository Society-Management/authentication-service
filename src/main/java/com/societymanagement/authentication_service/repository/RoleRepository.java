package com.societymanagement.authentication_service.repository;

import com.societymanagement.authentication_service.entity.Role;
import com.societymanagement.authentication_service.enums.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    public Optional<Role> findByRoleName(RoleType roleType);
}
