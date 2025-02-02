package com.wizzy.Wizzy.Banking.Application.domain.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.time.LocalDateTime;
import java.util.Objects;

@MappedSuperclass
@EnableJpaAuditing
@Getter
@Setter
public abstract class BaseClass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime dateCreated;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime dateModified;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseClass that = (BaseClass) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @PrePersist
    @PreUpdate
    public void  prePersist(){
        if(dateCreated == null){
            dateCreated = LocalDateTime.now();
        }
        dateModified = LocalDateTime.now();
    }
}
