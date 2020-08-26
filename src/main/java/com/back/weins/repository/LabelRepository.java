package com.back.weins.repository;

import com.back.weins.entity.Label;
import com.mongodb.lang.Nullable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LabelRepository extends JpaRepository<Label, Integer> {
    @Nullable
    @Query( value = "select * from label where label.content = ?1", nativeQuery = true)
    Label findByContent(String content);

    @Nullable
//    @Query(value="select * from label where label.content like ('%'+?1+'%')", nativeQuery = true)
    @Query(value="select * from label where label.content like ?1", nativeQuery = true)
    List<Label> findPuzzy(String lab);
}
