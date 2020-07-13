package com.back.weins.entity;

import lombok.Data;
import org.hibernate.annotations.Proxy;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "avatar")
@Proxy(lazy = false)
public class Avatar {
    @Id
    private Integer id; // 对应用户id
    private String base64;

    public Avatar(){}

    public Avatar(Integer id, String base64) {
        this.id = id;
        this.base64 = base64;
    }
}
