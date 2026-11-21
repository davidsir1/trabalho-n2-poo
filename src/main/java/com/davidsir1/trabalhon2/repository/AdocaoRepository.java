package com.davidsir1.trabalhon2.repository;

import com.davidsir1.trabalhon2.config.DatabaseConnection;
import com.davidsir1.trabalhon2.model.*;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AdocaoRepository {
    public void registrarAdocao(Adocao adocao) {
        String sql_insert = "INSERT INTO adocao (id_usuario, id_adotante, id_animal, data_adocao) VALUES " +
                "(?, ?, ?, ?);";
        String sql_update_animal = "UPDATE animal SET status_adocao = 'ADOTADO' WHERE id_animal = ?;";

        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement stmtInsert = conn.prepareStatement(sql_insert);
                 PreparedStatement stmtUpdate = conn.prepareStatement(sql_update_animal)){

                stmtInsert.setInt(1, adocao.getUsuario().getId_usuario());
                stmtInsert.setInt(2, adocao.getAdotante().getId_adotante());
                stmtInsert.setInt(3, adocao.getAnimal().getId_animal());
                stmtInsert.setTimestamp(4, Timestamp.valueOf(adocao.getData_adocao()));
                stmtInsert.executeUpdate();

                stmtUpdate.setInt(1, adocao.getAnimal().getId_animal());
                stmtUpdate.executeUpdate();

                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            } finally {
                conn.setAutoCommit(true);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao registrar adoção no banco de dados: " + e.getMessage());
        }
    }

    public List<Adocao> listarAdocoesRecentes() {
        String sql = "SELECT ad.id_adocao, ad.data_adocao, " +
                "u.id_usuario, u.nome_usuario, u.email_usuario, " +
                "c.id_cargo, c.cargo, " +
                "adt.id_adotante, adt.nome_adotante, adt.telefone, adt.email_adotante, adt.endereco, adt.data_cadastro, " +
                "an.id_animal, an.nome_animal, an.porte, an.sexo, an.status_adocao, " +
                "r.id_raca, r.raca, " +
                "e.id_especie, e.especie " +
                "FROM adocao ad " +
                "INNER JOIN usuario u ON ad.id_usuario = u.id_usuario " +
                "INNER JOIN cargo c ON u.id_cargo = c.id_cargo " +
                "INNER JOIN adotante adt ON ad.id_adotante = adt.id_adotante " +
                "INNER JOIN animal an ON ad.id_animal = an.id_animal " +
                "INNER JOIN raca r ON an.id_raca = r.id_raca " +
                "INNER JOIN especie e ON r.id_especie = e.id_especie " +
                "ORDER BY ad.data_adocao DESC;";

        List<Adocao> adocoes = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Cargo cargo = new Cargo(rs.getInt("id_cargo"), rs.getString("cargo"));
                Usuario usuario = new Usuario(
                        rs.getInt("id_usuario"),
                        rs.getString("nome_usuario"),
                        rs.getString("email_usuario"),
                        rs.getString("senha"),
                        cargo
                );

                Adotante adotante = new Adotante(
                        rs.getInt("id_adotante"),
                        rs.getString("nome_adotante"),
                        rs.getString("telefone"),
                        rs.getString("email_adotante"),
                        rs.getString("endereco"),
                        rs.getDate("data_cadastro") != null ? rs.getDate("data_cadastro").toLocalDate() : null
                );

                Especie especie = new Especie(rs.getInt("id_especie"), rs.getString("especie"));
                Raca raca = new Raca(rs.getInt("id_raca"), rs.getString("raca"), especie);
                Animal animal = new Animal(
                        rs.getInt("id_animal"),
                        rs.getString("nome_animal"),
                        rs.getString("porte"),
                        rs.getString("sexo"),
                        rs.getString("status_adocao"),
                        raca
                );

                Timestamp ts = rs.getTimestamp("data_adocao");
                LocalDateTime dataAdocao = (ts != null) ? ts.toLocalDateTime() : null;

                adocoes.add(new Adocao(
                        rs.getInt("id_adocao"),
                        usuario,
                        adotante,
                        animal,
                        dataAdocao
                ));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar adoções recentes: " + e.getMessage());
            return null;
        }

        return adocoes;
    }
}
