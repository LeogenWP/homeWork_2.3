package org.leogenwp.service;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.leogenwp.controller.LabelController;
import org.leogenwp.repository.LabelRepository;
import org.leogenwp.repository.io.JavaIOLabelRepository;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.leogenwp.model.Label;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;


class LabelServiceTest {

    @Test
    void saveLabel_Success() {
        Label label = new Label("Table");
        LabelRepository labelRepository = mock(LabelRepository.class);
        LabelService labelService_underTest = new LabelService(labelRepository);
        when(labelRepository.save(label)).thenReturn(label);
        assertEquals(label,labelService_underTest.save(label));
    }

    @Test
    void getAllLabels_Success() {
        List<Label> labels = getLabelList();
        LabelRepository labelRepository = mock(LabelRepository.class);
        LabelService labelService_underTest = new LabelService(labelRepository);
        when(labelRepository.getAll()).thenReturn(labels);
        assertEquals(labels,labelService_underTest.getAll());
    }

    @Test
    void deleteById_Success() {
        LabelService labelService_underTest = mock(LabelService.class);
        labelService_underTest.deleteById(1);
        verify(labelService_underTest).deleteById(1);

    }

    @Test
    void getLabelById_Success() {
        Label label = new Label("Table").setId(1);
        LabelRepository labelRepository = mock(LabelRepository.class);
        LabelService labelService_underTest = new LabelService(labelRepository);
        when(labelRepository.getById(1)).thenReturn(label);
        assertEquals(label,labelService_underTest.getById(1));

    }

    @Test
    void updateLabel_Success() {
        Label label = new Label("Table").setId(1);
        LabelRepository labelRepository = mock(LabelRepository.class);
        LabelService labelService_underTest = new LabelService(labelRepository);
        when(labelRepository.update(label)).thenReturn(label);
        assertEquals(label,labelService_underTest.update(label));
    }

    private List<Label> getLabelList() {
        List<Label> labels = Arrays.asList(
                new Label("Table"),
                new Label("Mable"),
                new Label("Cable"));
        return labels;
    }
}