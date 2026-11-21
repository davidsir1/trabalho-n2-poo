package com.davidsir1.trabalhon2.repository;

import com.davidsir1.trabalhon2.config.DatabaseConnection;
import com.davidsir1.trabalhon2.model.Animal;
import com.davidsir1.trabalhon2.model.Especie;
import com.davidsir1.trabalhon2.model.Raca;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AnimalRepository {

    public List<Animal> listarTodos() {
        return null;
    }

    public List<Animal> buscarPorStatusAdocao(String status_adocao) {
        String sql = "SELECT a.id_animal, a.nome_animal, a.porte, a.sexo, a.status_adocao, r.id_raca, r.raca, e.id_especie, e.especie " +
                "FROM animal a " +
                "INNER JOIN raca r ON a.id_raca = r.id_raca " +
                "INNER JOIN especie e ON r.id_especie = e.id_especie " +
                "WHERE a.status_adocao = ? " +
                "ORDER BY a.id_animal ASC;";

        List<Animal> animais = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setString(1, status_adocao);

            try (ResultSet rs = stmt.executeQuery()){
                while (rs.next()) {
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
                    animais.add(animal);
                }
            }
        } catch (SQLException e) {
            System.err.println("Falha ao listar animais por status de adoção '" + status_adocao + "': " + e.getMessage());
            return null;
        }

        return animais;
    }
}
