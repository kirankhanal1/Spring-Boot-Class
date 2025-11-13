package com.cosmo.training.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name="users")
@EntityListeners(AuditingEntityListener.class)
//@Where(clause = "isDeleted= false") Its deprecated now we use @SQLRestriction
@SQLRestriction("is_deleted = false")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", nullable = false)
    private Integer id;

    @Column(name = "username", nullable = false, length = 50)
    private String username;

    @Column(name="full_name", nullable = false, length = 100)
    private String fullName;

    @Column(name="email", nullable = false, length = 30)
    private String email;

    @Column(name="password", nullable = false,length = 15)
    private String  password;

    @Column(name="is_deleted", nullable = false)
    private Boolean isDeleted = false;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name="updated_at", nullable = false)
    private LocalDateTime updatedAt;


}
