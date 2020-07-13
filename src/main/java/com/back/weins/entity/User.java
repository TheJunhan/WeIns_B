package com.back.weins.entity;

import lombok.Data;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
@Table(name="user", schema="weins")
@Proxy(lazy = false)
public class user {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer uid;
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
    private BigDecimal reg_time;
    @Column
    private Integer type;

    @Transient
    private Avatar avatar;
}
