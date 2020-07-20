package com.back.weins.Utils.RequestUtils;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommentUtils {
    Integer uid;
    String username;
    Integer to_uid;
    String to_username;
    Integer bid;
    String content;
}
