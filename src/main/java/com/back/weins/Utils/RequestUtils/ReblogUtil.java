package com.back.weins.Utils.RequestUtils;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReblogUtil {
    Integer uid;
    Integer bid;
    Integer type;
    String content;
    String post_day;
    String username;
}
