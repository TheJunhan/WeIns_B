package com.back.weins.Dao;

import java.util.List;

public interface LabelAndBlogDao {
    List<Integer> getByLid(Integer lid);

    void setLAB(Integer lid, Integer bid);
}
