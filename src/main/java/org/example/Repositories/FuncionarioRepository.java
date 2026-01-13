package org.example.Repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.example.Model.Funcionario;

public class FuncionarioRepository {
    //dao
    private final List<Funcionario> funcionarios = new ArrayList<>();

    public FuncionarioRepository() {
        // Dados iniciais para teste
        funcionarios.add(new Funcionario(1, "João Silva", "Maria Souza", "Desenvolvedor", new java.util.Date()));
        funcionarios.add(new Funcionario(2, "Ana Costa", "Carlos Pereira", "Analista de Sistemas", new java.util.Date()));
    }

    public void adicionarFuncionario(Funcionario funcionario) {
        funcionarios.add(funcionario);
    }
    public List<Funcionario> getFuncionarios() {
        return funcionarios;
    }

    public Optional<Funcionario> getFuncionarioById(int id) {
        return funcionarios.stream()
                .filter(funcionario -> funcionario.getId() == id)
                .findFirst();
    }


}
