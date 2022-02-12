package com.devendrabrain.StudentMgr.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name="is_enabled")
    private boolean isEnabled;

    @OneToMany(mappedBy = "user")
    private List<Role> roles;

    public User() {

    }
}

