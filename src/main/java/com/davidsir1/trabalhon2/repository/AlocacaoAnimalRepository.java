package com.davidsir1.trabalhon2.repository;

import com.davidsir1.trabalhon2.config.DatabaseConnection;
import com.davidsir1.trabalhon2.model.Animal;
import com.davidsir1.trabalhon2.model.AlocacaoAnimal;
import com.davidsir1.trabalhon2.model.Baia;
import com.davidsir1.trabalhon2.model.Especie;
import com.davidsir1.trabalhon2.model.Raca;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class AlocacaoAnimalRepository {
    public void salvar(AlocacaoAnimal alocacao) throws SQLException {
        String sql = "INSERT INTO alocacao_animal (id_animal, id_baia) VALUES (?, ?);";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, alocacao.getAnimal().getId_animal());
            stmt.setInt(2, alocacao.getBaia().getId_baia());
            stmt.executeUpdate();
        }
    }

    public List<AlocacaoAnimal> listarHistorico() throws SQLException {
        String sql = "SELECT aa.id_alocacao_animal, aa.data_transferencia, " +
                "a.id_animal, a.nome_animal, a.porte, a.sexo, a.status_adocao, " +
                "r.id_raca, r.raca, e.id_especie, e.especie, " +
                "b.id_baia, b.cod_baia, b.capacidade " +
                "FROM alocacao_animal aa " +
                "INNER JOIN animal a ON aa.id_animal = a.id_animal " +
                "INNER JOIN raca r ON a.id_raca = r.id_raca " +
                "INNER JOIN especie e ON r.id_especie = e.id_especie " +
                "INNER JOIN baia b ON aa.id_baia = b.id_baia " +
                "ORDER BY aa.data_transferencia DESC, aa.id_alocacao_animal DESC;";
        List<AlocacaoAnimal> historico = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                historico.add(mapearAlocacao(rs));
            }
        }

        return historico;
    }

    private AlocacaoAnimal mapearAlocacao(ResultSet rs) throws SQLException {
        Animal animal = mapearAnimal(rs);
        Baia baia = new Baia(rs.getInt("id_baia"), rs.getString("cod_baia"), rs.getInt("capacidade"));
        Timestamp dataTransferencia = rs.getTimestamp("data_transferencia");
        return new AlocacaoAnimal(
                rs.getInt("id_alocacao_animal"),
                dataTransferencia == null ? null : dataTransferencia.toLocalDateTime(),
                animal,
                baia
        );
    }

    private Animal mapearAnimal(ResultSet rs) throws SQLException {
        Especie especie = new Especie(rs.getInt("id_especie"), rs.getString("especie"));
        Raca raca = new Raca(rs.getInt("id_raca"), rs.getString("raca"), especie);
        return new Animal(
                rs.getInt("id_animal"),
                rs.getString("nome_animal"),
                rs.getString("porte"),
                rs.getString("sexo"),
                rs.getString("status_adocao"),
                raca
        );
    }
}
