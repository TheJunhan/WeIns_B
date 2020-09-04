package com.back.weins.Utils.RequestUtils;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BlogUtil {
    private Integer uid;
    private Integer type;
    private String content;
    private String post_day;
    private String video;
    private String imag;
    private String label;
    private String username;
}
