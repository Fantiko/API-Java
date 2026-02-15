package org.example;


import io.javalin.Javalin;
import org.example.Controler.DepartamentoControler;
import org.example.Controler.FuncionarioControler;
import org.example.Repositories.FuncionarioRepository;

import static io.javalin.apibuilder.ApiBuilder.*;

public class Main {
    public static void main(String[] args) {
        
        //IniDataBase.init();
        
        FuncionarioControler funcionarioControler = new FuncionarioControler();
        DepartamentoControler departamentoControler = new DepartamentoControler();


        var app = Javalin.create(config -> {

            config.router.apiBuilder(()->{

                path("funcionarios",() ->{
                    get(funcionarioControler::getAll);
                    post(funcionarioControler::create);

                    path("{id}", ()->{
                        get(funcionarioControler::getOne);
                        put(funcionarioControler::update);
                        delete(funcionarioControler::delete);
                    });
                });

                path("departamentos", () -> {
                    get(departamentoControler::getAll);
                    post(departamentoControler::create);

                    path("{id}", () -> {
                        get(departamentoControler::getOne);
                        put(departamentoControler::update);
                        delete(departamentoControler::delete);
                    });
                });


            });
        }).start(7000);

        // Tratamento de Erros Global para Status Codes
        app.exception(Exception.class, (e, ctx) -> {
            ctx.status(500).result("Erro interno no servidor: " + e.getMessage());
        });

    }
}