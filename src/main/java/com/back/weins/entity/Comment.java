package com.back.weins.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name="comment")
public class Comment {

    @Id
    Integer cid;
    @Column
     String content;
    @Column
     Integer uid;
    @Column
     String username;
    @Column
     Integer to_uid;
    @Column
     String to_username;
    @Column
     Integer is_del;

     public Comment(){
         is_del = 0;
     }

    public Comment(Integer uid, String username, Integer to_uid,String to_username, String content){
        this.uid = uid;
        this.username = username;
        this.content = content;
        this.to_uid = to_uid;
        this.to_username = to_username;
        this.is_del = 0;
    }

}
