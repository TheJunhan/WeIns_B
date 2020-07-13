package com.back.weins.servicesImpl;

import com.back.weins.entity.Label;
import com.back.weins.repository.LabelRepository;
import com.back.weins.services.LabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class LabelServiceImpl implements LabelService {
    @Autowired
    private LabelRepository labelRepository;

    @Override
    public Integer InsertLabel(String label){
        Label label1 = new Label();
        label1.setContent(label);
        System.out.print(label);
        System.out.print(label1);
        labelRepository.save(label1);
        return 1;
    }
}
