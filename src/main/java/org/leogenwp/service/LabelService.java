package org.leogenwp.service;

import org.leogenwp.model.Label;
import org.leogenwp.repository.LabelRepository;

import java.util.List;

public class LabelService {
    private final LabelRepository labelRepository;

    public LabelService(LabelRepository labelRepository) {
        this.labelRepository = labelRepository;
    }

    public Label save (Label label) {
        return labelRepository.save(label);
    }
    public List<Label> getAll(){
        return labelRepository.getAll();
    }

    public void deleteById(Integer id) {
        labelRepository.deleteById(id);
    }

    public Label getById(Integer id) {
        return labelRepository.getById(id);
    }

    public Label update(Label label) {
        return labelRepository.update(label);
    }
}
