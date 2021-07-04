package org.leogenwp.service;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.leogenwp.model.Post;
import org.leogenwp.model.Writer;
import org.leogenwp.repository.PostRepository;
import org.leogenwp.repository.WriterRepository;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class WriterServiceTest {

    @Test
    void saveWriterTest_Success() {
        Writer writer = new Writer("Bob","White");
        WriterRepository writerRepository = mock(WriterRepository.class);
        WriterService writerService_underTest = new WriterService(writerRepository);
        when(writerRepository.save(writer)).thenReturn(writer);
        assertEquals(writer,writerService_underTest.save(writer));
    }

    @Test
    void getAllWriters_Success() {
        List<Writer> writers = getWriterList();
        WriterRepository writerRepository = mock(WriterRepository.class);
        WriterService writerService_underTest = new WriterService(writerRepository);
        when(writerRepository.getAll()).thenReturn(writers);
        assertEquals(writers,writerService_underTest.getAll());
    }

    @Test
    void deleteWriterById_Success() {
        WriterService writerService_underTest = mock(WriterService.class);
        writerService_underTest.deleteById(1);
        verify(writerService_underTest).deleteById(1);
    }

    @Test
    void getWriterById_Success() {
        Writer writer = new Writer("Bob","White").setId(1);
        WriterRepository writerRepository = mock(WriterRepository.class);
        WriterService writerService_underTest = new WriterService(writerRepository);
        when(writerRepository.getById(1)).thenReturn(writer);
        assertEquals(writer,writerService_underTest.getById(1));
    }

    @Test
    void updateWriter_Success() {
        Writer writer = new Writer("Bob","White").setId(1);
        WriterRepository writerRepository = mock(WriterRepository.class);
        WriterService writerService_underTest = new WriterService(writerRepository);
        when(writerRepository.update(writer)).thenReturn(writer);
        assertEquals(writer,writerService_underTest.update(writer));
    }

    private List<Writer> getWriterList() {
        List<Writer> writers = Arrays.asList(
                new Writer("Bob","White"),
                new Writer("Rob","Black"),
                new Writer("Bob","White"));
        return writers;
    }
}