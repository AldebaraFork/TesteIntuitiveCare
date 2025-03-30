-- 3.3. Criação das tabelas
CREATE TABLE operadoras (
 registro_ans VARCHAR(20) PRIMARY KEY,
 cnpj VARCHAR(14),
 razao_social VARCHAR(255),
 nome_fantasia VARCHAR(255),
 modalidade VARCHAR(100),
 logradouro VARCHAR(255),
 numero VARCHAR(20),
 complemento VARCHAR(100),
 bairro VARCHAR(100),
 cidade VARCHAR(100),
 uf VARCHAR(2),
 cep VARCHAR(10),
 ddd VARCHAR(2),
 telefone VARCHAR(20),
 fax VARCHAR(20),
 email VARCHAR(100),
 representante VARCHAR(255),
 cargo_representante VARCHAR(100),
 data_registro_ans DATE
);

CREATE TABLE demonstracoes_contabeis (
    id SERIAL PRIMARY KEY,
    registro_ans VARCHAR(20) REFERENCES operadoras(registro_ans),
    data DATE,
    conta_contabil VARCHAR(50),
    descricao VARCHAR(255),
    valor NUMERIC(15,2)
);

-- 3.4. Importação dos dados
-- Importar operadoras (ajuste o caminho do arquivo)
COPY operadoras FROM '/caminho/para/operadoras.csv' DELIMITER ';' CSV HEADER ENCODING 'ISO-8859-1';

-- Importar demonstrações contábeis (ajuste o caminho)
COPY demonstracoes_contabeis(registro_ans, data, conta_contabil, descricao, valor)
    FROM '/caminho/para/demonstracoes.csv' DELIMITER ';' CSV HEADER ENCODING 'ISO-8859-1';

-- 3.5. Queries analíticas
-- 10 operadoras com maiores despesas no último trimestre
SELECT o.razao_social, SUM(d.valor) as total_despesas
FROM demonstracoes_contabeis d
         JOIN operadoras o ON d.registro_ans = o.registro_ans
WHERE d.descricao LIKE 'EVENTOS/ SINISTROS CONHECIDOS OU AVISADOS DE ASSISTÊNCIA A SAÚDE MEDICO HOSPITALAR'
  AND d.data >= (CURRENT_DATE - INTERVAL '3 months')
GROUP BY o.razao_social
ORDER BY total_despesas DESC
    LIMIT 10;

-- 10 operadoras com maiores despesas no último ano
SELECT o.razao_social, SUM(d.valor) as total_despesas
FROM demonstracoes_contabeis d
         JOIN operadoras o ON d.registro_ans = o.registro_ans
WHERE d.descricao LIKE 'EVENTOS/ SINISTROS CONHECIDOS OU AVISADOS DE ASSISTÊNCIA A SAÚDE MEDICO HOSPITALAR'
  AND d.data >= (CURRENT_DATE - INTERVAL '1 year')
GROUP BY o.razao_social
ORDER BY total_despesas DESC
    LIMIT 10;