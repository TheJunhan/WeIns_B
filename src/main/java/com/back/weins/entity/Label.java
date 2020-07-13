package com.back.weins.entity;

import javax.persistence.*;

@Entity
@Table(name="Label")
public class Label {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer lid;

    @Column(name="content")
    private String content;

    public Integer getLid() {
        return lid;
    }

    public String getContent() {
        return content;
    }

    public void setLid(Integer lid) {
        this.lid = lid;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
