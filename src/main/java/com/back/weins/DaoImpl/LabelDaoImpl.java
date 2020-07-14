package com.back.weins.DaoImpl;

import com.back.weins.Dao.LabelDao;
import com.back.weins.entity.Label;
import com.back.weins.repository.LabelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class LabelDaoImpl implements LabelDao {
    @Autowired
    private LabelRepository labelRepository;

    @Override
    public Label getById(Integer id) {
        return labelRepository.getOne(id);
    }

    @Override
    public Label getByContent(String content) {
        return labelRepository.findByContent(content);
    }

    @Override
    public void save(Label label) {

        // insert new
        if (label.getId() == null) {
            System.out.println("insert new");
            Label label1 = labelRepository.findByContent(label.getContent());

            // exists and deleted, set non-deleted
            if (label1 != null && label1.getDelete() == 1) {
                label1.setDelete(0);
                labelRepository.save(label1);
                System.out.println("exits");
            }
        }

        // update
        else {
            labelRepository.save(label);
            System.out.println("update");
        }
    }

    @Override
    public void update(Label label) {
        save(label);
    }

    @Override
    public void deleteById(Integer id) {
        Label label = labelRepository.getOne(id);

        if (label.getDelete() == 0)
            return;

        label.setDelete(1);
        save(label);
    }

    @Override
    public void deleteByContent(String content) {
        Label label = labelRepository.findByContent(content);

        if (label != null && label.getDelete() == 1) {
            label.setDelete(1);
            save(label);
        }
    }
}
