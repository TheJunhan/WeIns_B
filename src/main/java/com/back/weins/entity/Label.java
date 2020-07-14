package com.back.weins.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name="Label")
public class Label {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    @Column
    private String content;

    @Column
    private Integer delete;

    public Label() {
        this.delete = 0;
    }

    public Label(Integer id, String content) {
        this.id = id;
        this.content = content;
        this.delete = 0;
    }
}
