package org.example.Controler;

import org.example.Repositories.DepartamentoRepository;
import io.javalin.http.Context;

public class DepartamentoControler {

    DepartamentoRepository departamentoRepository;

    public DepartamentoControler() {
        this.departamentoRepository = new DepartamentoRepository();
    }

    //GET /departamentos
    public void getAll(Context cxt){
        int page = cxt.queryParamAsClass("page", Integer.class).getOrDefault(1);
        int size = cxt.queryParamAsClass("size", Integer.class).getOrDefault(10);

        if (page<1)page = 1;
        if (size<1 || size > 50)size = 10;

        var departamentos = departamentoRepository.getDepartamentos(page, size);
        cxt.status(200).json(departamentos);
    }

    //GET /departamentos/{id}
    public void getOne(Context cxt){
        int id = Integer.parseInt(cxt.pathParam("id"));
        var departamento = departamentoRepository.getDepartamentoById(id);
        if(departamento.isPresent()){
            cxt.json(departamento.get());
        } else {
            cxt.status(404);
        }
    }

    //POST /departamentos

    public void create(Context cxt){
        var departamento = cxt.bodyAsClass(org.example.Model.Departamento.class);
        departamentoRepository.adicionarDepartamento(departamento);
        cxt.status(201);
    }

    //PUT /departamentos/{id}
    public void update(Context context) {
        var departamento = context.bodyAsClass(org.example.Model.Departamento.class);
        int id = Integer.parseInt(context.pathParam("id"));

        var departamentoExistente = departamentoRepository.getDepartamentoById(id);

        if(departamentoExistente.isPresent()){
            departamento.setId(id); // Garantir que o ID seja o mesmo
            departamentoRepository.atualizarDepartamento(departamento);
            context.status(200);
        } else {
            context.status(404).result("Departamento não encontrado");
        }
    }

    //DELETE /departamentos/{id}
    public void delete(Context context) {
        int id = Integer.parseInt(context.pathParam("id"));
        var departamentoExistente = departamentoRepository.getDepartamentoById(id);

        if(departamentoExistente.isPresent()){
            departamentoRepository.deletarDepartamento(id);
            context.status(204);
        } else {
            context.status(404).result("Departamento não encontrado");
        }
    }
}
