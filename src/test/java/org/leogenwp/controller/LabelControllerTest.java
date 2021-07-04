package org.leogenwp.controller;

import org.junit.jupiter.api.Test;
import org.leogenwp.model.Label;
import org.leogenwp.model.Post;
import org.leogenwp.repository.LabelRepository;
import org.leogenwp.service.LabelService;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LabelControllerTest {

    @Test
    void saveLabel_Success() {
        LabelController labelController_underTest = new LabelController();
        assertEquals(Label.class,labelController_underTest.save("Table").getClass());
    }

    @Test
    void getAllLabels_Success() {
        LabelController labelController_underTest = mock(LabelController.class);
        List<Label> labels = getLabelList();
        when(labelController_underTest.getAll()).thenReturn(labels);
        assertEquals(labels,labelController_underTest.getAll());
    }

    @Test
    void deleteLabelById_Success() {
        LabelController labelController_underTest = mock(LabelController.class);
        labelController_underTest.deleteById(1);
        verify(labelController_underTest).deleteById(1);
    }

    @Test
    void getLabelById_Success() {
        LabelController labelController_underTest = mock(LabelController.class);
        Label label = new Label(1,"Bom");
        when(labelController_underTest.getById(1)).thenReturn(label);
        assertEquals(label,labelController_underTest.getById(1));
    }

    @Test
    void updateLabelById_Success() {
        LabelController labelController_underTest = mock(LabelController.class);
        Label label = new Label(1,"Bom");
        when(labelController_underTest.updateById(1,"Bom")).thenReturn(label);
        assertEquals(label,labelController_underTest.updateById(1,"Bom"));
    }

    private List<Label> getLabelList() {
        List<Label> labels = Arrays.asList(
                new Label("Bom"),
                new Label("Tom")
        );
        return labels;
    }
}