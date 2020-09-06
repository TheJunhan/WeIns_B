package com.back.weins.DaoImpl;

import com.back.weins.Dao.LabelDao;
import com.back.weins.entity.Label;
import com.back.weins.repository.LabelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

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
    public String save(Label label) {

        // insert new
        if (label.getId() == null) {
            System.out.println("insert new");
            Label label1 = labelRepository.findByContent(label.getContent());

            // exists and deleted, set non-deleted
            if (label1 != null) {
                if (label1.getFlag() == 1) {
                    label1.setFlag(0);
                    labelRepository.save(label1);
                }
                else {
                    return "existed";
                }
            }

            else
                labelRepository.save(label);

            return "success";
        }

        // update
        else {
            System.out.println("update");
            Label label1 = labelRepository.findByContent(label.getContent());

            if (label1 != null)
                return "existed";

            else {
                labelRepository.save(label);
                return "update";
            }
        }
    }

    @Override
    public void deleteById(Integer id) {
        Label label = labelRepository.getOne(id);

        if (label == null || label.getFlag() == 1)
            return;

        label.setFlag(1);
        labelRepository.save(label);
    }

    @Override
    public void deleteByContent(String content) {
        Label label = labelRepository.findByContent(content);

        if (label != null && label.getFlag() == 0) {
            label.setFlag(1);
            labelRepository.save(label);
        }
    }

    @Override
    public List<Label> getLabels() {
        return labelRepository.findAll();
    }

    @Override
    public List<Label> findLabels(String lab) {
        return labelRepository.findPuzzy('%' + lab + '%');
    }
}
