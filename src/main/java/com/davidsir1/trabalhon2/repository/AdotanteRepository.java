package com.davidsir1.trabalhon2.repository;

import com.davidsir1.trabalhon2.config.DatabaseConnection;
import com.davidsir1.trabalhon2.model.Adotante;
import com.davidsir1.trabalhon2.utils.AlertaUtil;
import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class AdotanteRepository {

    public void salvar(Adotante adotante) throws SQLException {

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


        }
    }

    public void atualizar(Adotante adotante) throws SQLException {

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

        }
    }

    public void excluir(int idAdotante) throws SQLException {

        String sql = "DELETE FROM adotante WHERE id_adotante = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idAdotante);

            stmt.executeUpdate();

        }
    }

    public List<Adotante> listarTodos() throws SQLException {

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

        }

        return adotantes;
    }

    public List<Adotante> buscarPorNome(String nome) throws SQLException {

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

        }

        return adotantes;
    }

    public boolean possuiAdocao(int idAdotante) throws SQLException {

        String sql = "SELECT 1 FROM adocao WHERE id_adotante = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idAdotante);

            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }

        }
    }
}


