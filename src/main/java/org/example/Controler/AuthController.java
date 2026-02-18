package org.example.Controler;

import io.javalin.http.Context;
import org.example.Model.Administrador;
import org.example.Repositories.AdministradorRepository;
import org.example.Services.TokenService;

public class AuthController {

    private static final AdministradorRepository repository = new AdministradorRepository();

    public static void login(Context ctx) {

        // RECEBE DADOS DE LOGIN
        Administrador loginRequest = ctx.bodyAsClass(Administrador.class);

        // VERIFICA DADOS DE LOGIN NO BANCO
        Administrador adminAutenticado = repository.login(loginRequest.getEmail(), loginRequest.getSenha());

        // CASO VALIDE OS DADOS DE LOGIN -> GERA TOKEN
        if (adminAutenticado != null) {
            String token = TokenService.gerarToken(adminAutenticado);
            ctx.json("{\"Token\": \"" + token + "\"}");
        } else {
            // CASO NÃO VALIDE OS DADOS DE LOGIN -> NEGA ENTRADA
            ctx.status(401).result("Acesso Negado: Email ou senha inválidos!");
        }
    }
}