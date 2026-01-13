package org.example.Repositories;

import org.example.Model.Departamento;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DepartamentoRepository {
    //DAO
    private final List<Departamento> departamentos = new ArrayList<>();
    public DepartamentoRepository() {
        // Dados iniciais para teste
        departamentos.add(new Departamento(1, "Recursos Humanos", null, 10, true));
        departamentos.add(new Departamento(2, "Desenvolvimento", null, 25, true));
    }

    public Optional<Departamento> getDepartamentoById(int id) {
        return departamentos.stream()
                .filter(departamento -> departamento.getId() == id)
                .findFirst();
    }

    public List<Departamento> getDepartamentos() {
        return departamentos;
    }

    public Optional<Departamento> adicionarDepartamento(Departamento departamento) {
        departamentos.add(departamento);
        return Optional.of(departamento);
    }



}
