package com.devendrabrain.OrgMgr.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "user_role")
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
