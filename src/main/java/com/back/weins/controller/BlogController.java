package com.back.weins.controller;

import com.back.weins.services.LabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/blog")
public class BlogController {
    @Autowired
    private LabelService labelService;

    @RequestMapping("/insertLabel")
    public Integer InsertLabel(String label){
        System.out.print(label);
        return labelService.InsertLabel(label);
    }
}
