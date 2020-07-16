package com.back.weins.entity;

public class Comment {

    private String content;
    private Integer uid;
    private String username;
    private Integer to_uid;
    private String to_username;
    private Integer is_del;

    public Comment(Integer ui, String usernam, Integer to_ui,String to_usernam, String con){
        this.uid = ui;
        this.username = usernam;
        this.content = con;
        this.to_uid = ui;
        this.to_username = to_usernam;
        this.is_del = 0;
    }

}
