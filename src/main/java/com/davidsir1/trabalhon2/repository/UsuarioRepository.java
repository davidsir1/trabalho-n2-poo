package com.davidsir1.trabalhon2.repository;

import com.davidsir1.trabalhon2.config.DatabaseConnection;
import com.davidsir1.trabalhon2.model.Cargo;
import com.davidsir1.trabalhon2.model.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioRepository {
    public void salvar(Usuario usuario) {
        String sql = "INSERT INTO usuario (nome_usuario, email_usuario, senha, id_cargo) VALUES " +
                "(?,?,?,?);";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setString(1, usuario.getNome_usuario());
            stmt.setString(2, usuario.getEmail_usuario());
            stmt.setString(3, usuario.getSenha());
            stmt.setInt(4, usuario.getCargo().getId_cargo());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao tentar cadastrar usuario no banco de dados: " + e.getMessage());
        }
    }

    public void atualizar(Usuario usuario) {
        String sql = "UPDATE usuario SET nome_usuario = ?, email_usuario = ?, senha = ?, id_cargo = ? " +
                "WHERE id_usuario = ?;";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setString(1, usuario.getNome_usuario());
            stmt.setString(2, usuario.getEmail_usuario());
            stmt.setString(3, usuario.getSenha());
            stmt.setInt(4, usuario.getCargo().getId_cargo());
            stmt.setInt(5, usuario.getId_usuario());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao tentar salvar usuario no banco de dados: " + e.getMessage());
        }
    }

    public void excluir(int idUsuario) {
        String sql = "DELETE FROM usuario WHERE id_usuario = ?;";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setInt(1, idUsuario);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao excluir usuário. Verifique se não há registros vinculados a ele: " + e.getMessage());
        }
    }

    public List<Usuario> listarTodos() {
        return buscarPorNome("");
    }

    public List<Usuario> buscarPorNome(String nome) {
        String sql = "SELECT u.id_usuario, u.nome_usuario, u.email_usuario, u.senha, " +
                "c.id_cargo, c.cargo " +
                "FROM usuario u " +
                "INNER JOIN cargo c ON u.id_cargo = c.id_cargo " +
                "WHERE u.nome_usuario LIKE ? " +
                "ORDER BY u.id_usuario;";
        List<Usuario> usuarios = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            stmt.setString(1, "%" + (nome == null ? "" : nome.trim()) + "%");

            while (rs.next()) {
                Cargo cargo = new Cargo(rs.getInt("id_cargo"), rs.getString("cargo"));
                Usuario usuario = new Usuario(
                        rs.getInt("id_usuario"),
                        rs.getString("nome_usuario"),
                        rs.getString("email_usuario"),
                        rs.getString("senha"),
                        cargo
                );
                usuarios.add(usuario);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao consultar usuários no banco de dados: " + e.getMessage());
        }

        return usuarios;
    }

    public List<Cargo> listarCargos() {
        String sql = "SELECT id_cargo, cargo FROM cargo ORDER BY cargo;";
        List<Cargo> cargos = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                cargos.add(new Cargo(rs.getInt("id_cargo"), rs.getString("cargo")));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao consultar usuários no banco de dados: " + e.getMessage());
        }

        return cargos;
    }
}
