package org.example.Model;

public class Departamento {
    int id;
    String nome;
    int gerente;
    int capacidade;
    boolean ativo;


    public Departamento(int id, String nome, int gerente, int capacidade, boolean ativo) {
        this.id = id;
        this.nome = nome;
        this.gerente = gerente;
        this.capacidade = capacidade;
        this.ativo = ativo;
    }
    
    public Departamento() {
    }

    //--------------------------------------------------------------------------------------------------------------
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

    public int getGerente() {
        return gerente;
    }

    public void setGerente(int gerente) {
        this.gerente = gerente;
    }

    public int getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(int capacidade) {
        this.capacidade = capacidade;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
}
