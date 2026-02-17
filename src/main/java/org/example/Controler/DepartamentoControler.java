package org.example.Controler;

import org.example.Model.Departamento;
import org.example.Model.Funcionario;
import org.example.Repositories.DepartamentoRepository;
import io.javalin.http.Context;
import org.example.Repositories.FuncionarioRepository;

import java.util.List;
import java.util.Optional;

public class DepartamentoControler {

    DepartamentoRepository departamentoRepository;
    FuncionarioRepository funcionarioRepository;


    public DepartamentoControler() {
        this.departamentoRepository = new DepartamentoRepository();
        this.funcionarioRepository = new FuncionarioRepository();
    }

    //GET /departamentos
    public void getAll(Context ctx) {
        // Captura os parâmetros de paginação via Query Params (RF06)
        int page = ctx.queryParamAsClass("page", Integer.class).getOrDefault(1);
        int size = ctx.queryParamAsClass("size", Integer.class).getOrDefault(10);

        // 1. Busca a lista básica de departamentos no banco
        List<Departamento> departamentos = departamentoRepository.getDepartamentos(page, size);

        for (Departamento d : departamentos) {

            if (d.getGerente() != null) {
                int idGerente = d.getGerente().getId();

                Optional<Funcionario> gerenteCompleto = funcionarioRepository.getFuncionarioById(idGerente);

                gerenteCompleto.ifPresent(d::setGerente);
            }
        }

        // Retorna a lista completa com Status 200 OK (Item 7.2)
        ctx.status(200).json(departamentos);
    }

    //GET /departamentos/{id}
    public void getOne(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));

        // 1. Busca o Departamento (RF02)
        departamentoRepository.getDepartamentoById(id).ifPresentOrElse(dept -> {
            int idGerente = dept.getGerente().getId();

            // Chamando o repositório de funcionários (RF08)
            Optional<Funcionario> gerenteCompleto = funcionarioRepository.getFuncionarioById(idGerente);

            // 3. Se o gerente existir, popula o objeto; se não, mantém o que está
            gerenteCompleto.ifPresent(dept::setGerente);

            ctx.status(200).json(dept);
        }, () -> {
            ctx.status(404).result("Departamento não encontrado");
        });
    }

    //POST /departamentos

    public void create(Context ctx) {
        // Converte o JSON recebido para objeto Java [cite: 10, 49]
        Departamento novoDept = ctx.bodyAsClass(Departamento.class);

        // Tenta salvar no banco
        Optional<Departamento> deptoSalvo = departamentoRepository.adicionarDepartamento(novoDept);

        deptoSalvo.ifPresentOrElse(
                dept -> ctx.status(201).json(dept), // 201 Created: Sucesso
                () -> ctx.status(400).result("Erro ao criar departamento") // 400 Bad Request [cite: 111]
        );
    }

    //PUT /departamentos/{id}
    public void update(Context context) {
        try {
            int id = Integer.parseInt(context.pathParam("id"));
            var departamentoExistenteOpt = departamentoRepository.getDepartamentoById(id);

            if (departamentoExistenteOpt.isEmpty()) {
                context.status(404).result("Departamento não encontrado!");
                return;
            }

            Departamento depExistente = departamentoExistenteOpt.get();
            var depRecebidoJson = context.bodyAsClass(org.example.Model.Departamento.class);
            java.util.Map<String, Object> jsonMap = context.bodyAsClass(java.util.Map.class);

            if (depRecebidoJson.getNome() != null && !depRecebidoJson.getNome().isEmpty()) {
                depExistente.setNome(depRecebidoJson.getNome());
            }

            if (depRecebidoJson.getGerente() != null) {
                depExistente.setGerente(depRecebidoJson.getGerente());
            }

            if (depRecebidoJson.getCapacidade() > 0) {
                depExistente.setCapacidade(depRecebidoJson.getCapacidade());
            }

            if (jsonMap.containsKey("ativo")) {
                depExistente.setAtivo(depRecebidoJson.isAtivo());
            }

            if (departamentoRepository.atualizarDepartamento(depExistente)) {
                context.status(200).result("Departamento atualizado com sucesso!");
            } else {
                context.status(500).result("Erro ao atualizar o departamento no banco.");
            }

        } catch (NumberFormatException e) {
            context.status(400).result("ID inválido na URL!");
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
