package com.davidsir1.trabalhon2.repository;

import com.davidsir1.trabalhon2.config.DatabaseConnection;
import com.davidsir1.trabalhon2.model.Especie;
import com.davidsir1.trabalhon2.model.Raca;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RacaRepository {
    public void salvar(Raca raca) throws SQLException {
        String sql = "INSERT INTO raca (raca, id_especie) VALUES (?, ?);";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, raca.getRaca());
            stmt.setInt(2, raca.getEspecie().getId_especie());
            stmt.executeUpdate();
        }
    }

    public void atualizar(Raca raca) throws SQLException {
        String sql = "UPDATE raca SET raca = ?, id_especie = ? WHERE id_raca = ?;";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, raca.getRaca());
            stmt.setInt(2, raca.getEspecie().getId_especie());
            stmt.setInt(3, raca.getId_raca());
            stmt.executeUpdate();
        }
    }

    public void excluir(int idRaca) throws SQLException {
        String sql = "DELETE FROM raca WHERE id_raca = ?;";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idRaca);
            stmt.executeUpdate();
        }
    }

    public List<Raca> listarTodos() throws SQLException {
        String sql = "SELECT r.id_raca, r.raca, e.id_especie, e.especie " +
                "FROM raca r " +
                "INNER JOIN especie e ON r.id_especie = e.id_especie " +
                "ORDER BY r.id_raca;";
        List<Raca> racas = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Especie especie = new Especie(rs.getInt("id_especie"), rs.getString("especie"));
                racas.add(new Raca(rs.getInt("id_raca"), rs.getString("raca"), especie));
            }
        }

        return racas;
    }

    public List<Raca> listarRacaPorEspecie(String especie) throws SQLException {
        String sql = "SELECT r.id_raca, r.raca, e.id_especie, e.especie FROM raca r\n" +
                "INNER JOIN especie e ON r.id_especie = e.id_especie\n" +
                "WHERE especie = ?;";

        List<Raca> racas = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, especie);

            try (ResultSet rs = stmt.executeQuery()){
                while (rs.next()) {
                    Especie e = new Especie(rs.getInt("id_especie"), rs.getString("especie"));
                    Raca raca = new Raca(rs.getInt("id_raca"), rs.getString("raca"), e);
                    racas.add(raca);
                }
            }
        }

        return racas;
    }
}
