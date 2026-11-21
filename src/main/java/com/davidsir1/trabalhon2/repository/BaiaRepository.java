package com.davidsir1.trabalhon2.repository;

import com.davidsir1.trabalhon2.config.DatabaseConnection;
import com.davidsir1.trabalhon2.model.Baia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BaiaRepository {
    public void salvar(Baia baia) throws SQLException {
        String sql = "INSERT INTO baia (cod_baia, capacidade) VALUES (?, ?);";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, baia.getCod_baia());
            stmt.setInt(2, baia.getCapacidade());
            stmt.executeUpdate();
        }
    }

    public void atualizar(Baia baia) throws SQLException {
        String sql = "UPDATE baia SET cod_baia = ?, capacidade = ? WHERE id_baia = ?;";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, baia.getCod_baia());
            stmt.setInt(2, baia.getCapacidade());
            stmt.setInt(3, baia.getId_baia());
            stmt.executeUpdate();
        }
    }

    public void excluir(int idBaia) throws SQLException {
        String sql = "DELETE FROM baia WHERE id_baia = ?;";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idBaia);
            stmt.executeUpdate();
        }
    }

    public List<Baia> listarTodos() throws SQLException {
        String sql = "SELECT id_baia, cod_baia, capacidade FROM baia ORDER BY cod_baia;";
        List<Baia> baias = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                baias.add(new Baia(
                        rs.getInt("id_baia"),
                        rs.getString("cod_baia"),
                        rs.getInt("capacidade")
                ));
            }
        }

        return baias;
    }

    public int contarAnimaisAlocados(int idBaia) throws SQLException {
        String sql = "SELECT COUNT(aa.id_alocacao_animal) AS total " +
                "FROM alocacao_animal aa " +
                "WHERE aa.id_baia = ? " +
                "AND aa.id_alocacao_animal = (" +
                "SELECT MAX(aa_atual.id_alocacao_animal) " +
                "FROM alocacao_animal aa_atual " +
                "WHERE aa_atual.id_animal = aa.id_animal" +
                ");";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idBaia);

            try (ResultSet rs = stmt.executeQuery()) {
                rs.next();
                return rs.getInt("total");
            }
        }
    }
}
