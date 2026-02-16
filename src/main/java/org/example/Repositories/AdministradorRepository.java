package org.example.Repositories;

import org.example.Database.MySQLConnection;
import org.example.Model.Administrador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.mindrot.jbcrypt.BCrypt;

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

            // Definindo uma senha padrão (1234) para ser alterada posteriormente pelo próprio administrador
            String senhaPadrao = BCrypt.hashpw("1234", BCrypt.gensalt());
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

    public boolean updateAdministrador(Administrador adm) {
        String sqlBusca = "SELECT nome, cargo, email FROM administrador WHERE id = ?";
        String nomeAtual = null, cargoAtual = null, emailAtual = null;

        try (Connection conn = MySQLConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sqlBusca)) {

            stmt.setInt(1, adm.getId());
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                nomeAtual = rs.getString("nome");
                cargoAtual = rs.getString("cargo");
                emailAtual = rs.getString("email");
            } else {
                return false;
            }

            String sqlUpdate = "UPDATE administrador SET nome = ?, cargo = ?, email = ? WHERE id = ?";
            try (PreparedStatement stmtUpdate = conn.prepareStatement(sqlUpdate)) {
                stmtUpdate.setString(1, (adm.getNome() != null && !adm.getNome().isEmpty()) ? adm.getNome() : nomeAtual);
                stmtUpdate.setString(2, (adm.getCargo() != null && !adm.getCargo().isEmpty()) ? adm.getCargo() : cargoAtual);
                stmtUpdate.setString(3, (adm.getEmail() != null && !adm.getEmail().isEmpty()) ? adm.getEmail() : emailAtual);
                stmtUpdate.setInt(4, adm.getId());

                return stmtUpdate.executeUpdate() > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ROTA PARA ATUALIZAÇÃO DA SENHA (NECESSÁRIO INFORMAR SENHA ATUAL)
    public boolean updateSenha(int id, String senhaAntiga, String senhaNova) {

        String sql = "SELECT senha FROM administrador WHERE id = ?";

        try (Connection conn = MySQLConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String senhaBanco = rs.getString("senha");

                // VERIFICA SE SENHA DIGITADA É IGUAL HASH DO BANCO
                if (BCrypt.checkpw(senhaAntiga, senhaBanco)) {

                    // ENCRIPTA NOVA SENHA E ATUALIZA
                    String sqlUpdate = "UPDATE administrador SET senha = ? WHERE id = ?";
                    try (PreparedStatement stmtUpdate = conn.prepareStatement(sqlUpdate)) {
                        stmtUpdate.setString(1, BCrypt.hashpw(senhaNova, BCrypt.gensalt()));
                        stmtUpdate.setInt(2, id);
                        return stmtUpdate.executeUpdate() > 0;
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    // ROTA PARA LOGIN (VERIFICA SE O EMAIL E SENHA EXISTEM NO BANCO)
    public Administrador login(String email, String inputSenha) {
        String sql = "SELECT id, nome, cargo, email, senha FROM administrador WHERE email = ?";

        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                String senhaBanco = rs.getString("senha");
                if (BCrypt.checkpw(inputSenha, senhaBanco)) {
                    return new Administrador(
                            rs.getInt("id"),
                            rs.getString("nome"),
                            rs.getString("cargo"),
                            rs.getString("email")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
