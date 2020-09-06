package com.back.weins.repository;

import com.back.weins.entity.Blog;
import com.mongodb.lang.Nullable;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.Column;
import java.util.List;
import java.util.Optional;

public interface BlogRepository extends JpaRepository<Blog, Integer> {

    @Nullable
    @Query(value = "select * from blog limit ?1,?2", nativeQuery = true)
    List<Blog> findPage(Integer index, Integer num);

    @Query(value = "select count(*) from blog", nativeQuery = true)
    Integer getTotal();
}
