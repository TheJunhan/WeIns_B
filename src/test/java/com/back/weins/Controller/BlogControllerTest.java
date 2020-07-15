package com.back.weins.Controller;

import com.alibaba.fastjson.JSON;
import com.back.weins.entity.Label;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BlogControllerTest {

    @Autowired
    private BlogController blogController;

    private MockMvc mockMvc;

    @Test
    public void ccc(){
        List<String> image = new ArrayList<String>();
        image.add("徐珺涵");
        image.add("敖宇晨");
        image.add("窦嘉伟");
        image.add("付玉晗");
        String ima = JSON.toJSONString(image);
        List<Label> labe = new ArrayList<Label>();
        Label la = new Label();
        la.setId(2);
        la.setContent("学习");
        la.setFlag(0);
        labe.add(la);
        la.setId(3);
        la.setContent("游戏");
        la.setFlag(0);
        labe.add(la);
        String lab = JSON.toJSONString(labe);
        blogController.setBlog(1, 1, "2020-7-15", "null", ima,
                lab, "徐珺涵", ":whale:");
    }

}
