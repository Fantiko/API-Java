CREATE DATABASE IF NOT EXISTS gestao_funcionarios;
USE gestao_funcionarios;

CREATE TABLE departamento (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    gestor VARCHAR(100),
    capacidade INT NOT NULL,
    ativo BOOLEAN NOT NULL
);
