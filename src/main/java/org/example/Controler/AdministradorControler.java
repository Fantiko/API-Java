package org.example.Controler;

import io.javalin.http.Context;
import org.example.Model.Administrador;
import org.example.Repositories.AdministradorRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class AdministradorControler {

    private final AdministradorRepository repository = new AdministradorRepository();

    // GET - LISTAGEM DOS ADMINISTRADORES (NÃO LISTA SENHA)
    public void listar(Context ctx) {
        List<Administrador> lista = repository.getAdministrador();
        ctx.json(lista);
        ctx.status(200);
    }

    // POST - ADIÇÃO DE NOVOS ADMINISTRADORES
    public void cadastrar(Context ctx) {
        try {
            Administrador adm = ctx.bodyAsClass(Administrador.class);
            repository.postAdministrador(adm);

            ctx.status(201).result("Administrador cadastrado com sucesso! Favor alterar a senha padrão! (Senha padrão: 12345)");
        } catch (Exception e) {
            ctx.status(400).result("Erro ao cadastrar. Verifique os dados enviados no JSON.");
        }
    }

    // PUT - ATUALIZAR AS INFORMAÇÕES DE UM ADMINISTRADOR (NÃO ATUALIZA SENHA)
    public void atualizar(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));

            Optional<Administrador> adminExistenteOpt = repository.getAdministradorById(id);

            if (adminExistenteOpt.isEmpty()) {
                ctx.status(404).result("Administrador não encontrado!");
                return;
            }

            Administrador admExistente = adminExistenteOpt.get();
            Administrador admRecebidoJson = ctx.bodyAsClass(Administrador.class);

            if (admRecebidoJson.getNome() != null && !admRecebidoJson.getNome().isEmpty()) {
                admExistente.setNome(admRecebidoJson.getNome());
            }
            if (admRecebidoJson.getCargo() != null && !admRecebidoJson.getCargo().isEmpty()) {
                admExistente.setCargo(admRecebidoJson.getCargo());
            }
            if (admRecebidoJson.getEmail() != null && !admRecebidoJson.getEmail().isEmpty()) {
                admExistente.setEmail(admRecebidoJson.getEmail());
            }

            if (repository.updateAdministrador(admExistente)) {
                ctx.status(200).result("Dados atualizados com sucesso!");
            } else {
                ctx.status(404).result("Administrador não encontrado!");
            }
        } catch (NumberFormatException e) {
            ctx.status(400).result("ID inválido na URL!");
        }
    }

    // DELETE - EXCLUIR ADMINISTRADOR
    public void deletar(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));

            if (repository.deleteAdministrador(id)) {
                ctx.status(200).result("Administrador deletado!");
            } else {
                ctx.status(404).result("Administrador não encontrado!");
            }
        } catch (NumberFormatException e) {
            ctx.status(400).result("ID inválido na URL!");
        }
    }

    // PATCH - ATUALIZAÇÃO DA SENHA (NECESSÁRIO INFORMAR SENHA ATUAL)
    public void alterarSenha(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));

            Map<String, String> senhas = ctx.bodyAsClass(Map.class);
            String antiga = senhas.get("senhaAntiga");
            String nova = senhas.get("senhaNova");

            if (antiga == null || nova == null) {
                ctx.status(400).result("Para realizar a alteração da senha você precisa informar a 'senhaAntiga' e a 'senhaNova' no JSON!");
                return;
            }

            if (repository.updateSenha(id, antiga, nova)) {
                ctx.status(200).result("Senha alterada com sucesso!");
            } else {
                ctx.status(404).result("Acesso negado: Senha antiga incorreta ou ID não encontrado.");
            }
        } catch (NumberFormatException e) {
            ctx.status(400).result("ID inválido na URL!");
        } catch (Exception e) {
            ctx.status(400).result("Erro ao ler o JSON. Verifique a formatação!");
        }
    }
}