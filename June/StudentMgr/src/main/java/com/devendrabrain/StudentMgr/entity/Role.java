package com.devendrabrain.StudentMgr.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String rolename;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
