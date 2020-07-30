package com.back.weins.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name="comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    Integer cid;
    @Column
     String content;
    @Column
     Integer uid;

    @Column
     Integer to_uid;

    @Column
    Integer bid;

    @Column
    Integer to_cid;

    @Column
     Integer is_del;

    @Column
    String post_time;

    @Column
    Integer root_cid;

     public Comment(){
         is_del = 0;
     }

    public Comment(Integer uid, Integer to_uid, Integer bid, String content, String post_time, Integer to_cid, Integer root_cid){
        this.uid = uid;
        this.bid = bid;
        this.content = content;
        this.to_uid = to_uid;
        this.post_time = post_time;
        this.is_del = 0;
        this.to_cid = to_cid;
        this.root_cid = root_cid;
    }

}
