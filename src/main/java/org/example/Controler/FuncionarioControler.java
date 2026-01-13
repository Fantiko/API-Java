package org.example.Controler;

import org.example.Repositories.FuncionarioRepository;
import io.javalin.http.Context;

public class FuncionarioControler {

    FuncionarioRepository funcionarioRepository;

    public FuncionarioControler() {
        this.funcionarioRepository = new FuncionarioRepository();
    }

    // GET /funcionarios
    public void getAll(Context cxt){
        cxt.json(funcionarioRepository.getFuncionarios());
    }

    // GET /funcionarios/{id}
    public void getOne(Context cxt){
        int id = Integer.parseInt(cxt.pathParam("id"));
        var funcionario = funcionarioRepository.getFuncionarioById(id);
        if(funcionario.isPresent()){
            cxt.json(funcionario.get());
        } else {
            cxt.status(404);
        }
    }


    //POST /funcionarios
    public void create(Context cxt){
        var funcionario = cxt.bodyAsClass(org.example.Model.Funcionario.class);
        funcionarioRepository.adicionarFuncionario(funcionario);
        cxt.status(201);
    }


}
