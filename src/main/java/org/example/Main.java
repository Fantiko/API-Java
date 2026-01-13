package org.example;


import io.javalin.Javalin;
import org.example.Controler.DepartamentoControler;
import org.example.Controler.FuncionarioControler;
import org.example.Repositories.FuncionarioRepository;

public class Main {
    public static void main(String[] args) {
        FuncionarioControler funcionarioControler = new FuncionarioControler();
        DepartamentoControler departamentoControler = new DepartamentoControler();


        var app = Javalin.create().start(7000);

        // Define as rotas (Endpoints)
        app.get("/funcionarios", funcionarioControler::getAll);
        app.get("/funcionarios/{id}", funcionarioControler::getOne);
        app.post("/funcionarios", funcionarioControler::create);

        app.get("/departamento", departamentoControler::getAll);
        app.get("/departamento/{id}", departamentoControler::getOne);
        app.post("/departamento", departamentoControler::create);

    }
}