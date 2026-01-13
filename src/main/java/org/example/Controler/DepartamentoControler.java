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
        cxt.json(departamentoRepository.getDepartamentos());
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


}
