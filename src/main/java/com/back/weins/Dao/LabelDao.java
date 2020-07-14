package com.back.weins.Dao;

import com.back.weins.entity.Label;

public interface LabelDao {
    Label getById(Integer id);

    Label getByContent(String content);

    void save(Label label);

    void update(Label label);

    void deleteById(Integer id);

    void deleteByContent(String content);
}
