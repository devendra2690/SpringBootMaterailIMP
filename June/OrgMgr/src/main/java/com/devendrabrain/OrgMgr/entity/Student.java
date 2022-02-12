package com.devendrabrain.OrgMgr.entity;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Table
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="name")
    private String name;

    @Column(name = "city")
    private String city;

    @Column(name="classroom")
    private String classroom;

    public Student() {

    }
}
