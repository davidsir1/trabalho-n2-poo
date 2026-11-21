package com.davidsir1.trabalhon2.repository;

import com.davidsir1.trabalhon2.config.DatabaseConnection;
import com.davidsir1.trabalhon2.model.Adotante;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdotanteRepository {

    public List<Adotante> listarTodos() {
        String sql = "SELECT id_adotante, nome_adotante, telefone, email_adotante, endereco, data_cadastro " +
                "FROM adotante " +
                "ORDER BY nome_adotante;";

        List<Adotante> adotantes = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                adotantes.add(new Adotante(
                        rs.getInt("id_adotante"),
                        rs.getString("nome_adotante"),
                        rs.getString("telefone"),
                        rs.getString("email_adotante"),
                        rs.getString("endereco"),
                        rs.getDate("data_cadastro") != null ? rs.getDate("data_cadastro").toLocalDate() : null
                ));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar todos os adotantes: " + e.getMessage());
            return null;
        }

        return adotantes;
    }
}
