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
    gestor INT,
    cargo VARCHAR(100),
    data_contratacao DATE,
    departamento_id INT,
    FOREIGN KEY (departamento_id) REFERENCES departamento(id)
);

-- Criar um Departamento (obrigatório ter um antes de criar funcionário)
INSERT INTO departamento (nome, gestor, capacidade, ativo) 
VALUES ('Tecnologia', 'Rafael', 10, true);

-- Criar Funcionários vinculados a esse departamento (ID 1)
INSERT INTO funcionario (nome, gestor, cargo, data_contratacao, departamento_id) 
VALUES ('Ruan Ribeiro', '', 'Dev Java', '2025-02-12', 1);

INSERT INTO funcionario (nome, gestor, cargo, data_contratacao, departamento_id) 
VALUES ('Kaio Stefan', '1', 'Analista', '2025-02-10', 1);

USE gestao_funcionarios;

-- Criando tabela para administradores
CREATE TABLE IF NOT EXISTS administrador()
    id INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    cargo VARCHAR(100),
    email VARCHAR(100) UNIQUE,
    senha VARCHAR(100)
);

-- Cria Administrador primário
INSERT INTO administrador (nome, cargo, email, senha)
VALUES ('Administrador', 'Ruan', 'admin@empresa.com', '1234');

SELECT * FROM funcionario;