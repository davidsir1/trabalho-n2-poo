package com.davidsir1.trabalhon2.repository;

import com.davidsir1.trabalhon2.config.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DashboardRepository {
    public int contarAnimaisDisponiveis() {
        String sql = "SELECT COUNT(id_animal) AS total FROM animal WHERE status_adocao = 'DISPONIVEL';";
        return executarContagem(sql, "Erro ao contar animais disponíveis");
    }

    public int contarAdocoesRealizadas() {
        String sql = "SELECT COUNT(id_adocao) AS total FROM adocao;";
        return executarContagem(sql, "Erro ao contar adoções realizadas");
    }

    public int contarAdotantesCadastrados() {
        String sql = "SELECT COUNT(id_adotante) AS total FROM adotante;";
        return executarContagem(sql, "Erro ao contar adotantes cadastrados");
    }

    public int contarTotalBaias() {
        String sql = "SELECT COUNT(id_baia) AS total FROM baia;";
        return executarContagem(sql, "Erro ao contar baias cadastradas");
    }

    private int executarContagem(String sql, String mensagemErro) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next())
                return rs.getInt("total");
        } catch (SQLException e) {
            System.err.println(mensagemErro + ": " + e.getMessage());
        }

        return 0;
    }
}
