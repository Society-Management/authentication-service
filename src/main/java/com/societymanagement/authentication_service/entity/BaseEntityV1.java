package com.societymanagement.authentication_service.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;

@Getter
@Setter
//* Self Note * : MappedSuperClass - This class is a base entity that can be extended by other entities, and not a standalone entity which means,
// it has no table in the database.
@MappedSuperclass
@FilterDef(name = "societyFilter", parameters = @ParamDef(name = "societyId", type = Long.class))
@Filter(name = "societyFilter", condition = "society_id = :societyId")
public class BaseEntityV1 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long societyId;
    private String createdBy;
    private String updatedBy;
    private String createdAt;
    private String updatedAt;
    private String deletedAt; // This field is used for soft deletion

    @PrePersist //* Self Note * : Automatically called before the entity is created
    public void prePersist() {
        long currentTime = System.currentTimeMillis();
        this.createdAt = String.valueOf(currentTime);
        this.updatedAt = String.valueOf(currentTime);
    }

    @PreUpdate //* Self Note * : Automatically called before the entity is updated
    public void preUpdate() {
        this.updatedAt = String.valueOf(System.currentTimeMillis());
    }
}