package com.back.weins.repository;

import com.back.weins.entity.Comment;
import com.mongodb.lang.Nullable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    @Nullable
    @Query(value = "select * from comment where comment.to_cid = ?1", nativeQuery = true)
    List<Comment> findByTo_cid(Integer to_cid);

    @Nullable
    @Query(value = "select * from comment where comment.is_del = 0 and comment.bid = ?1 ", nativeQuery = true)
    List<Comment> findByBid(Integer bid);
}
