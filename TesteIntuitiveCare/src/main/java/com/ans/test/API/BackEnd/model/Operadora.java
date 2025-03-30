package com.ans.test.API.BackEnd.model;

import lombok.Data;
import javax.persistence.*;

@Data
@Entity
@Table(name = "operadoras")
public class Operadora {
    @Id
    private String registroAns;

    private String cnpj;
    private String razaoSocial;
    private String nomeFantasia;
    private String modalidade;
    private String logradouro;
    private String numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String uf;
    private String cep;
    private String ddd;
    private String telefone;
    private String fax;
    private String email;
    private String representante;
    private String cargoRepresentante;
    private String dataRegistroAns;
}
