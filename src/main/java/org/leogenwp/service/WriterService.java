package org.leogenwp.service;

import org.leogenwp.model.Writer;
import org.leogenwp.repository.WriterRepository;
import org.leogenwp.repository.io.JavaIOPostRepository;
import org.leogenwp.repository.io.JavaIOWriterRepository;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

public class WriterService {
    private final WriterRepository writerRepository;

    public WriterService(WriterRepository writerRepository) {
        this.writerRepository = writerRepository;
    }

    public Writer save (Writer writer) {
        return writerRepository.save(writer);
    }

    public List<Writer> getAll() {
        return writerRepository.getAll();
    }

    public void deleteById( Integer id) {
        writerRepository.deleteById(id);

    }

    public Writer getById(Integer id) {
        return writerRepository.getById(id);
    }

    public Writer update(Writer writer) {
        return writerRepository.update(writer);
    }
}
