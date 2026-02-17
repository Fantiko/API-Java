package org.example.Repositories;

import org.example.Database.MySQLConnection;
import org.example.Model.Departamento;
import org.example.Model.Funcionario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DepartamentoRepository {

    public Optional<Departamento> getDepartamentoById(int id) {
        
        String sql = "SELECT * FROM departamento WHERE id = ?";
        
        try(Connection conn = MySQLConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setInt(1,id);
            ResultSet rs  = stmt.executeQuery();
            
            if(rs.next()){
                Funcionario gerenteProvisorio = new Funcionario();
                gerenteProvisorio.setId(rs.getInt("gerente"));


                Departamento d = new Departamento(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        gerenteProvisorio,
                        rs.getInt("Capacidade"),
                        rs.getBoolean("Ativo")
                );
                return Optional.of(d);
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
        return Optional.empty();
    }


    public List<Departamento> getDepartamentos(int page, int size) {
        List<Departamento> lista = new ArrayList<>();
        int offset = (page - 1) * size; // Lógica de paginação

        String sql = "SELECT * FROM departamento LIMIT ? OFFSET ?";

        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, size);
            stmt.setInt(2, offset);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                // Criamos o objeto gerente apenas com o ID vindo da coluna 'gerente'
                Funcionario gerenteProvisorio = new Funcionario();
                gerenteProvisorio.setId(rs.getInt("gerente"));

                Departamento d = new Departamento(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        gerenteProvisorio, // Passa o objeto com ID para o Controller resolver
                        rs.getInt("capacidade"),
                        rs.getBoolean("ativo")
                );
                lista.add(d);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public Optional<Departamento> adicionarDepartamento(Departamento departamento) {
        String sql = "INSERT INTO departamento (nome, gerente, capacidade, ativo) VALUES (?, ?, ?, ?)";

        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, departamento.getNome());
            stmt.setInt(2, departamento.getGerente().getId());
            stmt.setInt(3, departamento.getCapacidade());
            stmt.setBoolean(4, departamento.isAtivo());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                // Recupera o ID gerado pelo AUTO_INCREMENT do MySQL [cite: 76]
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        departamento.setId(generatedKeys.getInt(1));
                        return Optional.of(departamento);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
    public boolean atualizarDepartamento(Departamento departamento) {
        String sql = """
            UPDATE departamento
            SET nome = ?, gerente = ?, capacidade = ?, ativo = ?
            WHERE id = ?""";

        try (
            Connection conn = MySQLConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setString(1, departamento.getNome());
            stmt.setString(2, null);
            stmt.setInt(3, departamento.getCapacidade());
            stmt.setBoolean(4, departamento.isAtivo());
            stmt.setInt(5, departamento.getId());

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deletarDepartamento(int id) {
        String sql = "DELETE FROM departamento WHERE id = ?";

        try (
            Connection conn = MySQLConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setInt(1, id);
            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}