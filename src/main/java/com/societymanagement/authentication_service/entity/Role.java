package com.societymanagement.authentication_service.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.societymanagement.authentication_service.enums.RoleType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Entity
@Data
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private RoleType roleName;
    @ManyToMany(mappedBy = "roles")
    @JsonIgnore
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    List<Users> users;
}
