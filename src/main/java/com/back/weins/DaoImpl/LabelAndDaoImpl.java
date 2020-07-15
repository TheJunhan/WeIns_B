package com.back.weins.DaoImpl;

import com.back.weins.Dao.LabelAndBlogDao;
import com.back.weins.entity.LabelAndBlog;
import com.back.weins.repository.LabelAndBlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class LabelAndDaoImpl implements LabelAndBlogDao {
    @Autowired
    LabelAndBlogRepository labelAndBlogRepository;

    @Override
    public void setLAB(Integer lid, Integer bid) {
        LabelAndBlog labelAndBlog = new LabelAndBlog();
        labelAndBlog.setLid(lid);
        labelAndBlog.setBid(bid);
        labelAndBlogRepository.save(labelAndBlog);
    }
}
