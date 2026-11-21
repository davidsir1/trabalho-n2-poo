package com.davidsir1.trabalhon2.repository;

import com.davidsir1.trabalhon2.config.DatabaseConnection;
import com.davidsir1.trabalhon2.model.Especie;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EspecieRepository {
    public void salvar(Especie especie) throws SQLException {
        String sql = "INSERT INTO especie (especie) VALUES (?);";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, especie.getEspecie());
            stmt.executeUpdate();
        }
    }

    public void atualizar(Especie especie) throws SQLException {
        String sql = "UPDATE especie SET especie = ? WHERE id_especie = ?;";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, especie.getEspecie());
            stmt.setInt(2, especie.getId_especie());
            stmt.executeUpdate();
        }
    }

    public void excluir(int idEspecie) throws SQLException {
        String sql = "DELETE FROM especie WHERE id_especie = ?;";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idEspecie);
            stmt.executeUpdate();
        }
    }

    public List<Especie> listarTodos() throws SQLException {
        String sql = "SELECT id_especie, especie FROM especie ORDER BY especie;";
        List<Especie> especies = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                especies.add(new Especie(rs.getInt("id_especie"), rs.getString("especie")));
            }
        }

        return especies;
    }
}
