package com.back.weins.entity;

import javax.persistence.*;

@Entity
@Table(name = "blog")
public class Blog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    @Column(name="label_id")
    private Integer label_id;

    @Column(name = "uid")
    private Integer uid;

    @Column(name = "post_day")
    private String post_day;

    @Column(name = "type")
    private Integer type;

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public void setPost_day(String post_day) {
        this.post_day = post_day;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public Integer getUid() {
        return uid;
    }

    public String getPost_day() {
        return post_day;
    }

    public Integer getType() {
        return type;
    }
}
