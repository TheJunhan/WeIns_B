package com.back.weins.Utils.RequestUtils;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommentUtils {
    Integer uid;
    Integer to_uid;
    String post_time;
    Integer bid;
    Integer to_cid;
    String content;
    Integer root_cid;
}
