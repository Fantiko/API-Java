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
import static io.javalin.apibuilder.ApiBuilder.*;


public class Main {
    public static void main(String[] args) {
        
        //IniDataBase.init();
        
        FuncionarioControler funcionarioControler = new FuncionarioControler();
        DepartamentoControler departamentoControler = new DepartamentoControler();
        AdministradorControler administradorControler = new AdministradorControler();


        var app = Javalin.create(config -> {

            config.router.apiBuilder(()->{
              
                // Rota de Login (Pública)
                path("auth", () -> {
                    post("login", AuthController::login);
                });

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

                        path("funcionarios", () -> {
                            get(departamentoControler::getFuncionariosPorDepartamento);
                        });

                    });
                });
              
                path("administrador", () -> {
                    get(administradorControler::listar);
                    post(administradorControler::cadastrar);
                    path("{id}", () -> {
                        put(administradorControler::atualizar);
                        delete(administradorControler::deletar);
                        patch("senha", administradorControler::alterarSenha);
                    });
                });  

            });
        }).start(7000);
      
        // Autenticação
        app.before(ctx -> {String rota = ctx.path();

            if (rota.equals("/auth/login")) {
                return;
            }

            // ROTA GET (PÚBLICA)
            if (ctx.method().equals(HandlerType.GET)) {
                return;
            }

           // ROTAS: POST, PUT, PATCH e DELETE (PRIVADAS | EXIGEM O TOKEN)
            String header = ctx.header("Authorization");

            if (header == null || TokenService.validarToken(header) == null) {
                throw new UnauthorizedResponse("Acesso negado: Token inválido ou ausente. Faça login primeiro.");
            }
        });

        // Tratamento de Erros Global para Status Codes
        app.exception(Exception.class, (e, ctx) -> {
            ctx.status(500).result("Erro interno no servidor: " + e.getMessage());
        });
       

    }
}