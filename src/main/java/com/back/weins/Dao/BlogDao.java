package com.back.weins.Dao;

import com.back.weins.entity.Label;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.context.annotation.Bean;

import java.util.List;

@Mapper
public interface BlogDao {

    void setBlog(Integer uid, Integer type, String post_day, String video, List<String> imag, List<Label> label);
}
