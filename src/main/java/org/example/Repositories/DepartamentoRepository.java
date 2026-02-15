package org.example.Repositories;

import org.example.Database.MySQLConnection;
import org.example.Model.Departamento;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DepartamentoRepository {

    public Optional<Departamento> getDepartamentoById(int id) {
        
        String sql = "SELECT * FROM departamento WHERE id = ?";
        
        try(Connection conn = MySQLConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
        ) {
            stmt.setInt(1,id);
            ResultSet rs  = stmt.executeQuery();
            
            if(rs.next()){
                Departamento d = new Departamento(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        null,
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
        int offset = (page - 1) * size;


        String sql = "SELECT * FROM departamento LIMIT ? OFFSET ?";

        try (
            Connection conn = MySQLConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
        ) {
            stmt.setInt(1, size);   // Quantidade de itens
            stmt.setInt(2, offset); // Onde começar

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Departamento d = new Departamento(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        null,
                        rs.getInt("capacidade"),
                        rs.getBoolean("ativo")
                );
                lista.add(d);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

    public Optional<Departamento> adicionarDepartamento(Departamento departamento) {
		
	String sql = """
            INSERT INTO departamento (nome, gestor, capacidade, ativo)
            VALUES (?, ?, ?, ?) """;
        try (
            Connection conn = MySQLConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setString(1, departamento.getNome());
            stmt.setString(2, null);
            stmt.setInt(3, departamento.getCapacidade());
            stmt.setBoolean(4, departamento.isAtivo());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public boolean atualizarDepartamento(Departamento departamento) {
        String sql = """
            UPDATE departamento
            SET nome = ?, gestor = ?, capacidade = ?, ativo = ?
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