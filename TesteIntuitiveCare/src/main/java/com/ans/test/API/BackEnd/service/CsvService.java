package com.ans.test.API.BackEnd.service;

import com.ans.test.API.BackEnd.model.Operadora;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Service
public class CsvService {
    public List<Operadora> readOperadorasFromCsv(String filePath) throws IOException {
        try (Reader reader = Files.newBufferedReader(Paths.get(filePath))) {
            CsvToBean<Operadora> csvToBean = new CsvToBeanBuilder<Operadora>(reader)
                    .withType(Operadora.class)
                    .withSeparator(';')
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            return csvToBean.parse();
        }
    }
}
