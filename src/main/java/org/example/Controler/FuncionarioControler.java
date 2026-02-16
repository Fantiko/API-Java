package org.example.Controler;

import org.example.Model.Funcionario;
import org.example.Repositories.FuncionarioRepository;
import io.javalin.http.Context;

import java.util.List;

public class FuncionarioControler {

    FuncionarioRepository funcionarioRepository;

    public FuncionarioControler() {
        this.funcionarioRepository = new FuncionarioRepository();
    }

    // GET /funcionarios
    public void getAll(Context cxt){
        int page = cxt.queryParamAsClass("page", Integer.class).getOrDefault(1);
        int size = cxt.queryParamAsClass("size", Integer.class).getOrDefault(10);

        if (page<1)page = 1;
        if (size<1 || size > 50)size = 10;

        List<Funcionario > funcionarios = funcionarioRepository.getFuncionarios(page, size);

        cxt.status(200).json(funcionarios);
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


    public void update(Context context) {
        var funcionario = context.bodyAsClass(org.example.Model.Funcionario.class);
        int id = Integer.parseInt(context.pathParam("id"));

        var funcionarioExistente = funcionarioRepository.getFuncionarioById(id);
        if (funcionarioExistente.isPresent()) {
            funcionario.setId(id); // Garantir que o ID seja o mesmo
            funcionarioRepository.atualizarFuncionario(funcionario);
            context.status(200);
        } else {
            context.status(404).result("Funcionário não encontrado");
        }

    }

    public void delete(Context context) {
        int id = Integer.parseInt(context.pathParam("id"));
        var funcionarioExistente = funcionarioRepository.getFuncionarioById(id);
        if (funcionarioExistente.isPresent()) {
            funcionarioRepository.deletarFuncionario(id);
            context.status(204);
        } else {
            context.status(404).result("Funcionário não encontrado");
        }
    }
}
