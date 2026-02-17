package org.example.Repositories;

import org.example.Database.MySQLConnection;
import org.example.Model.Funcionario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.example.Model.Funcionario;

public class FuncionarioRepository {
    
    public List<Funcionario> getFuncionarios(int page, int size) {

    List<Funcionario> lista = new ArrayList<>();
    int offset = (page - 1) * size;

    String sql = "SELECT * FROM funcionario LIMIT ? OFFSET ?";

    try (
        Connection conn = MySQLConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);
    ) {

        stmt.setInt(1, size);   // Quantidade de itens
        stmt.setInt(2, offset); // Onde começar

        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {

            Funcionario f = new Funcionario(
                rs.getInt("id"),
                rs.getString("nome"),
                rs.getInt("gestor"),
                rs.getString("cargo"),
                rs.getDate("data_contratacao")
            );
            f.setDepartamento(rs.getInt("departamento_id"));
            lista.add(f);
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }
    return lista;
}

public Optional<Funcionario> getFuncionarioById(int id) {

    String sql = "SELECT * FROM funcionario WHERE id = ?";

    try (
        Connection conn = MySQLConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);
    ) {

        stmt.setInt(1, id);

        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {

            Funcionario f = new Funcionario(
                rs.getInt("id"),
                rs.getString("nome"),
                rs.getInt("gestor"),
                rs.getString("cargo"),
                rs.getDate("data_contratacao")
            );
            f.setDepartamento(rs.getInt("departamento_id"));
            return Optional.of(f);
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return Optional.empty();
}
 
    public void adicionarFuncionario(Funcionario f) {
    String sql = """
        INSERT INTO funcionario
        (nome, gestor, cargo, data_contratacao, departamento_id)
        VALUES (?, ?, ?, ?, ?)
    """;

    try (Connection conn = MySQLConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setString(1, f.getNome());
        stmt.setInt(2, f.getGestor());
        stmt.setString(3, f.getCargo());
        /*
        Trocado para pegar o horario do computador
        stmt.setDate(4, new java.sql.Date(f.getDataContratacao().getTime()));

        */
        
        java.util.Date data = f.getDataContratacao();

        if (data == null) {
            data = new java.util.Date();
        }
        stmt.setDate(4, new java.sql.Date(data.getTime()));

        stmt.setInt(5, f.getDepartamento());
        stmt.executeUpdate();

    } catch (SQLException e) {
        e.printStackTrace();
        }
    }

    public boolean atualizarFuncionario(Funcionario funcionario) {
        String sql = """
            UPDATE funcionario
            SET nome = ?, gestor = ?, cargo = ?, data_contratacao = ?, departamento_id = ?
            WHERE id = ?
        """;

        try {
            Connection conn = MySQLConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, funcionario.getNome());
            stmt.setInt(2, funcionario.getGestor());
            stmt.setString(3, funcionario.getCargo());
            stmt.setDate(4, new java.sql.Date(funcionario.getDataContratacao().getTime()));
            stmt.setInt(5, funcionario.getDepartamento());
            stmt.setInt(6, funcionario.getId());

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deletarFuncionario(int id) {
        String sql = "DELETE FROM funcionario WHERE id = ?";

        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public Optional<List<Funcionario>> getFuncionariosPorDepartamento(int departamentoId, int page, int size) {
        String sql = "SELECT * FROM funcionario WHERE departamento_id = ? LIMIT ? OFFSET ?";

        List<Funcionario> funcionarios = new ArrayList<>();


        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, departamentoId);
            stmt.setInt(2, size);
            stmt.setInt(3, (page - 1) * size);

            ResultSet rs = stmt.executeQuery();

           while (rs.next()) {
                Funcionario f = new Funcionario(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getInt("gestor"),
                    rs.getString("cargo"),
                    rs.getDate("data_contratacao")
                );
                funcionarios.add(f);
            }

            return Optional.of(funcionarios);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

}
