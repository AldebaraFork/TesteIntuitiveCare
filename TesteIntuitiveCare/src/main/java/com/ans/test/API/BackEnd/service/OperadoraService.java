package com.ans.test.API.BackEnd.service;

import com.ans.test.API.BackEnd.model.Operadora;
import com.ans.test.API.BackEnd.repository.OperadoraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;

@Service
public class OperadoraService {
    @Value("${csv.file.path}")
    private String csvFilePath;

    @Autowired
    private CsvService csvService;

    @Autowired
    private OperadoraRepository repository;

    @PostConstruct
    public void init() throws IOException {
        List<Operadora> operadoras = csvService.readOperadorasFromCsv(csvFilePath);
        repository.saveAll(operadoras);
    }

    public List<Operadora> searchOperadoras(String termo) {
        return repository.searchByTermo(termo);
    }
}
