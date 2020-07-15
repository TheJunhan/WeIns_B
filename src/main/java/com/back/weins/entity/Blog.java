package com.back.weins.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "blog")
public class Blog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;


    @Column(name = "uid")
    private Integer uid;

    @Column(name = "post_day")
    private String post_day;

    @Column(name = "type")
    private Integer type;

    @Transient
    private BlogMongo blogMongo;
}
