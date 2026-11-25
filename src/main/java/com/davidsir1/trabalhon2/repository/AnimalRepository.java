package com.davidsir1.trabalhon2.repository;

import com.davidsir1.trabalhon2.config.DatabaseConnection;
import com.davidsir1.trabalhon2.model.Animal;
import com.davidsir1.trabalhon2.model.Especie;
import com.davidsir1.trabalhon2.model.Raca;
import com.davidsir1.trabalhon2.utils.AlertaUtil;
import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AnimalRepository {

    public void salvar(Animal animal) throws SQLException {
        String sql = "INSERT INTO animal (nome_animal, porte, sexo, status_adocao, id_raca) " +
                "VALUES (?, ?, ?, ?, ?);";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, animal.getNome_animal());
            stmt.setString(2, animal.getPorte());
            stmt.setString(3, animal.getSexo());
            stmt.setString(4, animal.getStatus_adocao());
            stmt.setInt(5, animal.getRaca().getId_raca());
            stmt.executeUpdate();
        }
    }

    public void atualizar(Animal animal) throws SQLException {
        String sql = "UPDATE animal SET nome_animal = ?, porte = ?, sexo = ?, " +
                "status_adocao = ?, id_raca = ? WHERE id_animal = ?;";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, animal.getNome_animal());
            stmt.setString(2, animal.getPorte());
            stmt.setString(3, animal.getSexo());
            stmt.setString(4, animal.getStatus_adocao());
            stmt.setInt(5, animal.getRaca().getId_raca());
            stmt.setInt(6, animal.getId_animal());
            stmt.executeUpdate();
        }
    }

    public void excluir(int idAnimal) throws SQLException {
        String sql = "DELETE FROM animal WHERE id_animal = ?;";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idAnimal);
            stmt.executeUpdate();
        }
    }

    public List<Animal> listarTodos() throws SQLException {
        return consultar(null, null);
    }

    public List<Animal> buscarPorNomeEStatus(String nome, String status) throws SQLException{
        return consultar(nome, status);
    }

    public List<Animal> buscarPorStatusAdocao(String status) throws SQLException {
        return consultar(null, status);
    }

    private List<Animal> consultar(String nome, String status) throws SQLException {
        StringBuilder sql = new StringBuilder(
                "SELECT a.id_animal, a.nome_animal, a.porte, a.sexo, a.status_adocao, " +
                "r.id_raca, r.raca, e.id_especie, e.especie " +
                "FROM animal a " +
                "INNER JOIN raca r ON a.id_raca = r.id_raca " +
                "INNER JOIN especie e ON r.id_especie = e.id_especie "
        );

        boolean temNome = nome != null && !nome.trim().isEmpty();
        boolean temStatus = status != null && !status.trim().isEmpty();

        if (temNome) {
            sql.append("WHERE a.nome_animal LIKE ? ");
        }
        if (temStatus) {
            sql.append(temNome ? "AND " : "WHERE ");
            sql.append("a.status_adocao = ? ");
        }
        sql.append("ORDER BY a.id_animal;");

        List<Animal> animais = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql.toString())) {

            int parametro = 1;
            if (temNome) {
                stmt.setString(parametro++, "%" + nome.trim() + "%");
            }
            if (temStatus) {
                stmt.setString(parametro, status.trim());
            }

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Especie especie = new Especie(
                            rs.getInt("id_especie"),
                            rs.getString("especie")
                    );

                    Raca raca = new Raca(
                            rs.getInt("id_raca"),
                            rs.getString("raca"),
                            especie
                    );

                    Animal animal = new Animal(
                            rs.getInt("id_animal"),
                            rs.getString("nome_animal"),
                            rs.getString("porte"),
                            rs.getString("sexo"),
                            rs.getString("status_adocao"),
                            raca
                    );

                    animais.add(animal);
                }
            }
        }

        return animais;
    }
}
