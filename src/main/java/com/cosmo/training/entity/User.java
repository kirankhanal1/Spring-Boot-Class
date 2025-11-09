package com.cosmo.training.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @Column(name = "username")
    private String username;

    @Column(name="full_name")
    private String fullName;

    private String email;
    private String  password;

}
