package com.back.weins.Controller;

import com.back.weins.entity.Blog;
import com.back.weins.entity.Label;
import com.back.weins.services.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/blog")
public class BlogController {

    @Autowired
    BlogService blogService;

    @GetMapping(value="/setLabel")
    public void setLabel(@RequestParam("label") String label) {
        blogService.setLabel(label);
    }

    @RequestMapping("/setBlog")
    public Integer setBlog(Integer uid, Integer type, String post_day, String video,
                           String imag, String label, String username, String useravatar){
        return blogService.setBlog(uid, type, post_day, video, imag, label, username, useravatar);

    }
}
