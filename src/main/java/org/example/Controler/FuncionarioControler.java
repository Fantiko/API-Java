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
        try {
            int id = Integer.parseInt(context.pathParam("id"));

            var funcionarioExistenteOpt = funcionarioRepository.getFuncionarioById(id);

            if (funcionarioExistenteOpt.isEmpty()) {
                context.status(404).result("Funcionário não encontrado!");
                return;
            }

            Funcionario funcExistente = funcionarioExistenteOpt.get();

            var funcRecebidoJson = context.bodyAsClass(org.example.Model.Funcionario.class);

            if (funcRecebidoJson.getNome() != null && !funcRecebidoJson.getNome().isEmpty()) {
                funcExistente.setNome(funcRecebidoJson.getNome());
            }
            if (funcRecebidoJson.getGestor() > 0) {
                funcExistente.setGestor(funcRecebidoJson.getGestor());
            }
            if (funcRecebidoJson.getCargo() != null && !funcRecebidoJson.getCargo().isEmpty()) {
                funcExistente.setCargo(funcRecebidoJson.getCargo());
            }
            if (funcRecebidoJson.getDataContratacao() != null) {
                funcExistente.setDataContratacao(funcRecebidoJson.getDataContratacao());
            }
            if (funcRecebidoJson.getDepartamento() > 0) {
                funcExistente.setDepartamento(funcRecebidoJson.getDepartamento());
            }

            if (funcionarioRepository.atualizarFuncionario(funcExistente)) {
                context.status(200).result("Funcionário atualizado com sucesso!");
            } else {
                context.status(500).result("Erro ao atualizar o funcionário no banco.");
            }

        } catch (NumberFormatException e) {
            context.status(400).result("ID inválido na URL!");
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
