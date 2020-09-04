package com.back.weins.Utils.RequestUtils;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChangeUtil {
    Integer uid;
    Integer bid;
    String content;
    Integer type;

//    ChangeUtil(Integer uid, Integer bid, String content, Integer type) {
//        this.uid = uid;
//        this.bid = bid;
//        this.content = content;
//        this.type = type;
//    }
}
