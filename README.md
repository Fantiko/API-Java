# Sistema de Gestão de Funcionários - Grupo 7

Projeto desenvolvido para a disciplina de Sistemas Distribuídos, focado na criação de uma API REST robusta utilizando **Java**, **Javalin** e **MySQL**.

---

## Instruções de Utilização

### 1. Pré-requisitos
Certifique-se de ter instalado:
* **Java JDK 17** ou superior
* **Maven 3.x**
* **MySQL Server 8.0**

### 2. Configuração do Banco de Dados (RNF02)
* Crie um banco de dados no MySQL chamado gestao_funcionarios (ou o nome definido no seu código).

* Execute os scripts SQL localizados na pasta /sql do projeto para criar as tabelas departamento e funcionario.

### 3. Configuração do Projeto
   No arquivo de configuração de conexão (ex: MySQLConnection.java), ajuste as credenciais de acesso ao seu banco de dados local:
```java
private static final String URL = "jdbc:mysql://localhost:3306/gestao_funcionarios";
private static final String USER = "seu_usuario";
private static final String PASSWORD = "sua_senha";
```

### 4. Execução da API
* Abra o terminal na pasta raiz do projeto.

*Compile o projeto com o Maven:

```Bash
mvn clean install
```
* Execute a classe App.java

## Endpoints Disponíveis

###  Entidade: Departamentos

#### 1. Listar Departamentos
- **URL:** `GET http://localhost:7000/departamentos?page=1&size=5`
- **Acesso:** Pública
- **Body:** Nenhum (vazio)

#### 2. Buscar por ID
- **URL:** `GET http://localhost:7000/departamentos/1`
- **Acesso:** Pública
- **Body:** Nenhum

#### 3. Criar Departamento
- **URL:** `POST http://localhost:7000/departamentos`
- **Acesso:** Privada (Requer Token JWT no Header)
- **Body (JSON):**

```json
{
  "nome": "Desenvolvimento de Software",
  "capacidade": 50,
  "ativo": true,
  "gerente": {
    "id": 1
  }
}
```
#### 4. Deletar Departamento
- **URL:** `DELETE http://localhost:7000/departamentos/1`
- **Acesso:** Privada
- **Body:** Nenhum

---

### Entidade: Funcionários

#### 1. Listar Funcionários (Com Filtro)
- **URL:** `GET http://localhost:7000/funcionarios?nome=Joao&page=1&size=10`
- **Acesso:** Pública
- **Body:** Nenhum

#### 2. Criar Funcionário
- **URL:** `POST http://localhost:7000/funcionarios`
- **Acesso:** Privada
- **Body (JSON):**

```json
{
  "nome": "Carlos Silva",
  "cargo": "Desenvolvedor Backend",
  "gestor": "Ana Souza",
  "dataContratacao": "2026-02-17",
  "departamento": 1
}
```

#### 3. Funcionários de um Departamento (RF08)
- **URL:** `GET http://localhost:7000/departamentos/1/funcionarios`
- **Acesso:** Pública
- **Body:** Nenhum


#### 4. Deletar Funcionário
- **URL:** `DELETE http://localhost:7000/funcionarios/1`
- **Acesso:** Privada
- **Body:** Nenhum

---

### 🔑 Autenticação (RF09)

#### 1. Login (Gerar Token)
- **URL:** `POST http://localhost:7000/login`
- **Acesso:** Pública
- **Body (JSON):**

```json
{
  "usuario": "admin@empresa.com",
  "senha": "1234"
}
```



















