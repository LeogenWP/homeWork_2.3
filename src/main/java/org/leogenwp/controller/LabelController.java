package org.leogenwp.controller;

import org.leogenwp.model.Label;
import org.leogenwp.repository.LabelRepository;
import org.leogenwp.repository.io.JavaIOLabelRepository;
import org.leogenwp.service.LabelService;

import java.util.List;

public class LabelController {
    //private final LabelRepository labelRepository;
    private final LabelService labelService = new LabelService(new JavaIOLabelRepository());

    public Label save (String labelName) {
        return labelService.save(new Label(labelName));
    }
    public List<Label> getAll(){
        for (Label label : labelService.getAll()) {
            System.out.println("Label id: " + label.getId() + " Label name: " + label.getName());
        }
        return labelService.getAll();
    }

    public void deleteById(Integer id) {
        labelService.deleteById(id);
    }

    public Label getById(Integer id) {
        try {
            Label label = labelService.getById(id);
            System.out.println("Label id: " + label.getId() + " Label name: " + label.getName());
        } catch (NullPointerException e) {
            System.out.println("Label not found");
        }
        return labelService.getById(id);
    }

    public Label updateById(Integer id, String labelName) {
        Label label = labelService.getById(id);
        label.setName(labelName);
        return labelService.update(label);
    }

}
