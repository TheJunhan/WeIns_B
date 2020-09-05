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
//    private Integer like;
//
//    @Column(name = "reblog")
//    private Integer reblog;
//
//    @Column(name = "com_number")
//    private Integer com_number;
//
//    @Column(name = "coll_number")
//    private Integer coll_number;
//
//    @Column(name = "is_del")
//    private Integer is_del;
//
//    @Column(name = "uid")
//    private Integer uid;
//
//
//    @Column(name = "post_day")
//    private String post_day;
//
//    @Column(name = "type")
//    private Integer type;
//
//    @Column(name = "reblog_id")
//    private Integer reblog_id;

    @Nullable
    @Query(value = "select * from blog limit ?1,?2", nativeQuery = true)
    List<Blog> findPage(Integer index, Integer num);

//    @Nullable
//    @Cacheable(value = "blogs")
//    @Query(value = "select * from blog where id=?1", nativeQuery = true)
//    Optional<Blog> findById(Integer id);
//
//    @Query(value="update blog set like=?1.like, reblog=?1.reblog", nativeQuery = true)
//    Object saveAndFlush(Blog blog)
}
