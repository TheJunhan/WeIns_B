package com.back.weins.entity;


import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name="user")
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

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getType() {
        return type;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public void setReg_time(BigDecimal reg_time) {
        this.reg_time = reg_time;
    }

    public Integer getUid() {
        return uid;
    }

    public String getName() {
        return name;
    }

    public Integer getSex() {
        return sex;
    }

    public String getPhone() {
        return phone;
    }

    public String getPassword() {
        return password;
    }

    public String getBirthday() {
        return birthday;
    }

    public BigDecimal getReg_time() {
        return reg_time;
    }
}
