CREATE DATABASE IF NOT EXISTS gestao_funcionarios;
USE gestao_funcionarios;

CREATE TABLE IF NOT EXISTS departamento (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    gestor VARCHAR(100),
    capacidade INT NOT NULL,
    ativo BOOLEAN NOT NULL
);

CREATE TABLE IF NOT EXISTS funcionario (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    gestor VARCHAR(100),
    cargo VARCHAR(100),
    data_contratacao DATE,
    departamento_id INT,
    FOREIGN KEY (departamento_id) REFERENCES departamento(id)
);
