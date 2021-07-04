package org.leogenwp.controller;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.leogenwp.model.Post;
import org.leogenwp.model.Writer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class WriterControllerTest {

    @Test
    void saveWriter_Success() {
        WriterController writerController_underTest = mock(WriterController.class);
        Writer writer = new Writer().setFirstName("Bob").setLastName("White");
        when(writerController_underTest.save("Bob","White")).thenReturn(writer);
        assertEquals(writer,writerController_underTest.save("Bob","White"));
    }

    @Test
    void getAllWriters_Success() {
        WriterController writerController_underTest = mock(WriterController.class);
        List<Writer> writers = getWriterList();
        when(writerController_underTest.getAll()).thenReturn(writers);
        assertEquals(writers,writerController_underTest.getAll());
    }

    @Test
    void deleteWriterById_Success() {
        WriterController writerController_underTest = mock(WriterController.class);
        writerController_underTest.deleteById(1);
        verify(writerController_underTest).deleteById(1);
    }

    @Test
    void getWriterById_Success() {
        WriterController writerController_underTest = mock(WriterController.class);
        Writer writer = new Writer().setId(1);
        when(writerController_underTest.getById(1)).thenReturn(writer);
        assertEquals(writer,writerController_underTest.getById(1));
    }

    @Test
    @Disabled
    void updateWriterById_Success() {
        WriterController writerController_underTest = mock(WriterController.class);
        writerController_underTest.updateById( new BufferedReader(new InputStreamReader(System.in)));
        verify(writerController_underTest).updateById(new BufferedReader(new InputStreamReader(System.in)));
    }

    private List<Writer> getWriterList() {
        List<Writer> writers = Arrays.asList(
                new Writer(),
                new Writer()
        );
        return writers;
    }
}