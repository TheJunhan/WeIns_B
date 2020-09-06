package com.back.weins.Dao;

import com.back.weins.entity.Label;

import java.util.List;

public interface LabelDao {

    Label getById(Integer id);

    Label getByContent(String content);

    String save(Label label);

    void deleteById(Integer id);

    void deleteByContent(String content);

    List<Label> getLabels();

    List<Label> findLabels(String lab);
}
