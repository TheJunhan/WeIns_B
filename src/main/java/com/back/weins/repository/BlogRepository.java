package com.back.weins.repository;

import com.alibaba.fastjson.JSONObject;
import com.back.weins.entity.Blog;
import com.mongodb.lang.Nullable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BlogRepository extends JpaRepository<Blog, Integer> {
    @Nullable
    @Query(value = "select * from blog limit ?1,?2", nativeQuery = true)
    List<Blog> findPage(Integer index, Integer num);
}
