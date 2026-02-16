package org.example;


import io.javalin.Javalin;
import org.example.Controler.AdministradorControler;
import org.example.Controler.DepartamentoControler;
import org.example.Controler.FuncionarioControler;
import org.example.Repositories.FuncionarioRepository;


import org.example.Services.TokenService;
import org.example.Controler.AuthController;
import io.javalin.http.HandlerType;
import io.javalin.http.UnauthorizedResponse;

public class Main {
    public static void main(String[] args) {
        
        //IniDataBase.init();
        
        FuncionarioControler funcionarioControler = new FuncionarioControler();
        DepartamentoControler departamentoControler = new DepartamentoControler();
        AdministradorControler administradorControler = new AdministradorControler();


        var app = Javalin.create().start(7000);

        // AUTENTICAÇÃO
        // ROTA DE LOGIN (PÚBLICA)
        app.post("/auth/login", AuthController::login);

        app.before(ctx -> {String rota = ctx.path();

            if (rota.equals("/auth/login")) {
                return;
            }

            // ROTAS: GET, POST, PUT, PATCH e DELETE (PRIVADAS | EXIGEM O TOKEN)
            String header = ctx.header("Authorization");

            if (header == null || TokenService.validarToken(header) == null) {
                throw new UnauthorizedResponse("Acesso negado: Token inválido ou ausente. Faça login primeiro.");
            }
        });

        // Define as rotas (Endpoints)

        // Rotas de Funcionários ---
        app.get("/funcionarios", funcionarioControler::getAll);
        app.get("/funcionarios/{id}", funcionarioControler::getOne);
        app.post("/funcionarios", funcionarioControler::create);

        // --- Rotas de Departamento ---
        app.get("/departamento", departamentoControler::getAll);
        app.get("/departamento/{id}", departamentoControler::getOne);
        app.post("/departamento", departamentoControler::create);

        // --- Rotas de Administrador ---
        app.get("/administrador", administradorControler::listar);
        app.post("/administrador", administradorControler::cadastrar);
        app.put("/administrador/{id}", administradorControler::atualizar);
        app.delete("/administrador/{id}", administradorControler::deletar);
        app.patch("/administrador/{id}/senha", administradorControler::alterarSenha);

    }
}