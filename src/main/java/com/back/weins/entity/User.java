package com.back.weins.entity;

import lombok.Data;
import org.hibernate.annotations.Proxy;
import javax.persistence.*;

@Data
@Entity
@Table(name="user", schema="weins")
@Proxy(lazy = false)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;
    @Column
    private String name;
    @Column
    private Integer sex;
    @Column
    private String phone;
    @Column
    private String password;
    @Column
    private String birthday;
    @Column
    private String reg_time;
    @Column
    private Integer type;

    @Transient
    private UserMongo userMongo;
}
