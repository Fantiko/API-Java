package org.example.Repositories;

import org.example.Database.MySQLConnection;
import org.example.Model.Administrador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdministradorRepository {

    // ROTA UTILIZADO NA LISTAGEM DOS ADMINISTRADORES (NÃO LISTA SENHA)
    public List<Administrador> getAdministrador() {

        List<Administrador> lista = new ArrayList<>();

        String sql = "SELECT id, nome, cargo, email FROM administrador";

        try (
                Connection conn = MySQLConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery();
        ) {

            while (rs.next()) {
                Administrador adm = new Administrador(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("cargo"),
                        rs.getString("email")
                );
                lista.add(adm);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    // ROTA PARA ADIÇÃO DE NOVOS ADMINISTRADORES (O ADMINISTRADOR É ADICIONADO COM UMA SENHA PADRÃO QUE DEVE SER ALTERADA POSTERIORMENTE POR ELE)
    public void postAdministrador(Administrador adm) {
        String sql = """
        INSERT INTO administrador
        (nome, cargo, email, senha)
        VALUES (?, ?, ?, ?)
        """;

        try (Connection conn = MySQLConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, adm.getNome());
            stmt.setString(2, adm.getCargo());
            stmt.setString(3, adm.getEmail());

            //Definindo uma senha padrão para ser alterada posteriormente pelo próprio administrador
            String senhaPadrao = "12345";
            stmt.setString(4, senhaPadrao);

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ROTA PARA EXCLUIR UM ADMINISTRADOR
    public boolean deleteAdministrador(int id) {
        String sql = "DELETE FROM administrador WHERE id = ?";

        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int registros = stmt.executeUpdate();

            return registros > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ROTA PARA ATUALIZAR AS INFORMAÇÕES DE UM ADMINISTRADOR (NÃO ATUALIZA SENHA)

    // --- ajustar para não deixar null quem nao for preenchido ---
    public boolean updateAdministrador(Administrador adm) {
        String sql = "UPDATE administrador SET nome = ?, cargo = ?, email = ? WHERE id = ?";

        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, adm.getNome());
            stmt.setString(2, adm.getCargo());
            stmt.setString(3, adm.getEmail());
            stmt.setInt(4, adm.getId());

            int registros = stmt.executeUpdate();
            return registros > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ROTA PARA ATUALIZAÇÃO DA SENHA (NECESSÁRIO INFORMAR SENHA ATUAL)
    public boolean updateSenha(int id, String senhaAntiga, String senhaNova) {

        // SOMENTE ATUALIZA A SENHA SE O ID E A SENHA ATUAL  FOREM PREENCHIDOS
        String sql = "UPDATE administrador SET senha = ? WHERE id = ? AND senha = ?";

        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, senhaNova);
            stmt.setInt(2, id);
            stmt.setString(3, senhaAntiga);

            int registro = stmt.executeUpdate();
            return registro > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ROTA PARA LOGIN (VERIFICA SE O EMAIL E SENHA EXISTEM NO BANCO)
    public Administrador login(String email, String senha) {
        String sql = "SELECT id, nome, cargo, email FROM administrador WHERE email = ? AND senha = ?";

        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            stmt.setString(2, senha);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Administrador(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("cargo"),
                        rs.getString("email")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
