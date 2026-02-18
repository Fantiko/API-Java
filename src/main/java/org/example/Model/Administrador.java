package org.example.Model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Administrador {

    private int id;
    private String nome;
    private String cargo;
    private String email;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) private String senha;


    public Administrador() {}

    public Administrador(int id, String nome, String cargo, String email) {
        this.id = id;
        this.nome = nome;
        this.cargo = cargo;
        this.email = email;
    }

    public Administrador(int id, String nome, String cargo, String email, String senha) {
        this.id = id;
        this.nome = nome;
        this.cargo = cargo;
        this.email = email;
        this.senha = senha;
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

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getEmail() {return email;}

    public void setEmail(String email) {this.email = email;}

    public String getSenha() {return senha;}

    public void setSenha(String senha) {this.senha = senha;}
}
