package com.davidsir1.trabalhon2.repository;

import com.davidsir1.trabalhon2.config.DatabaseConnection;
import com.davidsir1.trabalhon2.model.Adotante;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class AdotanteRepository {

    public void salvar(Adotante adotante) {

        String sql = "INSERT INTO adotante " +
                "(nome_adotante, telefone, email_adotante, endereco) " +
                "VALUES (?, ?, ?, ?);";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, adotante.getNome_adotante());
            stmt.setString(2, adotante.getTelefone());
            stmt.setString(3, adotante.getEmail_adotante());
            stmt.setString(4, adotante.getEndereco());

            stmt.executeUpdate();


        } catch (SQLException e) {
            System.err.println("erro ao cadastrar adotante no banco de dados " + e.getMessage());

        }

    }

    public void atualizar(Adotante adotante) {

        String sql = "UPDATE adotante SET " +
                "nome_adotante = ?, " +
                "telefone = ?, " +
                "email_adotante = ?, " +
                "endereco = ? " +
                "WHERE id_adotante = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, adotante.getNome_adotante());
            stmt.setString(2, adotante.getTelefone());
            stmt.setString(3, adotante.getEmail_adotante());
            stmt.setString(4, adotante.getEndereco());
            stmt.setInt(5, adotante.getId_adotante());

            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Erro ao atualizar adotante: " + e.getMessage());
        }
    }

    public void excluir(int idAdotante) {

        String sql = "DELETE FROM adotante WHERE id_adotante = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idAdotante);

            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Erro ao excluir adotante: " + e.getMessage());
        }

    }

    public List<Adotante> listarTodos() {

        List<Adotante> adotantes = new ArrayList<>();

        String sql = "SELECT id_adotante, nome_adotante, telefone, " +
                "email_adotante, endereco, data_cadastro " +
                "FROM adotante ORDER BY id_adotante";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                Adotante adotante = new Adotante(
                        rs.getInt("id_adotante"),
                        rs.getString("nome_adotante"),
                        rs.getString("telefone"),
                        rs.getString("email_adotante"),
                        rs.getString("endereco"),
                        rs.getDate("data_cadastro").toLocalDate()
                );

                adotantes.add(adotante);
            }

        } catch (SQLException e) {
            System.err.println("Erro ao listar adotantes: " + e.getMessage());
        }

        return adotantes;
    }

    public List<Adotante> buscarPorNome(String nome) {

        List<Adotante> adotantes = new ArrayList<>();

        String sql = "SELECT id_adotante, nome_adotante, telefone, " +
                "email_adotante, endereco, data_cadastro " +
                "FROM adotante WHERE nome_adotante LIKE ? " +
                "ORDER BY id_adotante";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + nome + "%");

            try (ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {

                    Adotante adotante = new Adotante(
                            rs.getInt("id_adotante"),
                            rs.getString("nome_adotante"),
                            rs.getString("telefone"),
                            rs.getString("email_adotante"),
                            rs.getString("endereco"),
                            rs.getDate("data_cadastro").toLocalDate()
                    );

                    adotantes.add(adotante);
                }
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar adotantes: " + e.getMessage());
        }

        return adotantes;
    }

    public boolean possuiAdocao(int idAdotante) {

        String sql = "SELECT 1 FROM adocao WHERE id_adotante = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idAdotante);

            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException e) {
            System.err.println("Erro ao verificar adoções: " + e.getMessage());
            throw new RuntimeException("Não foi possível verificar as adoções.", e);
        }
    }

}


