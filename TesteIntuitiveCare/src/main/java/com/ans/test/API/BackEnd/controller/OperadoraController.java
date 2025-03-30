package com.ans.test.API.BackEnd.controller;

import com.ans.test.API.BackEnd.model.Operadora;
import com.ans.test.API.BackEnd.service.OperadoraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/operadoras")
public class OperadoraController {
    @Autowired
    private OperadoraService operadoraService;

    @GetMapping("/search")
    public List<Operadora> search(@RequestParam String termo) {
        return operadoraService.searchOperadoras(termo);
    }
}
