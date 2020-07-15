package com.back.weins.repository;

import com.back.weins.entity.LabelAndBlog;
import com.mongodb.lang.Nullable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface LabelAndBlogRepository extends JpaRepository<LabelAndBlog, Integer> {
    @Nullable
    @Query(value = "select bid from labelandblog where lid=?1", nativeQuery = true)
    List<Integer> findByLid(Integer l);
}
