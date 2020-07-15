package com.back.weins.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name="labelandblog")
public class LabelAndBlog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    @Column
    private Integer lid;

    @Column
    private Integer bid;
}
