package com.back.weins.entity;

import com.back.weins.repository.UserRepository;
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

    @Column(name="lik")
    private Integer like;

    @Column(name = "reblog")
    private Integer reblog;

    @Column(name = "com_number")
    private Integer com_number;

    @Column(name = "uid")
    private Integer uid;

    @Column(name = "username")
    private String username;

    @Column(name = "post_day")
    private String post_day;

    @Column(name = "type")
    private Integer type;

    public Blog(){
        like = 0;
        reblog = 0;
        com_number = 0;

    }

    @Transient
    private BlogMongo blogMongo;
}
