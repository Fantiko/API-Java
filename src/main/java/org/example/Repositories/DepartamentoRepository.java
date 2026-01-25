package org.example.Repositories;

import org.example.Model.Departamento;

import org.example.Database.MySQLConnection;
import org.example.Model.Departamento;

import java.sql.*;
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
    
    
    public List<Departamento> getDepartamentos() {
        List<Departamento> lista = new ArrayList<>();

        String sql = "SELECT * FROM departamento";

        try (
            Connection conn = MySQLConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()
        ) {

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
}
