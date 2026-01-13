package org.example.Model;

import java.util.Date;

public class Funcionario {
    int id;
    String nome;

    String gestor;

    String cargo;

    Date dataContratacao;

    Departamento departamento;


    public Funcionario(int id, String nome, String gestor, String cargo, Date dataContratacao) {
        this.id = id;
        this.nome = nome;
        this.gestor = gestor;
        this.cargo = cargo;
        this.dataContratacao = dataContratacao;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getGestor() {
        return gestor;
    }

    public void setGestor(String gestor) {
        this.gestor = gestor;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public Date getDataContratacao() {
        return dataContratacao;
    }

    public void setDataContratacao(Date dataContratacao) {
        this.dataContratacao = dataContratacao;
    }

    public Departamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }
}
