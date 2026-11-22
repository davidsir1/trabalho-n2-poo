package com.davidsir1.trabalhon2.controller.form;

import com.davidsir1.trabalhon2.exception.DadosInvalidosException;
import com.davidsir1.trabalhon2.model.Especie;
import com.davidsir1.trabalhon2.service.EspecieRacaService;
import com.davidsir1.trabalhon2.utils.AlertaUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;

public class EspecieFormController {
    @FXML private TextField inNomeEspecie;
    @FXML private Button btnConfirmar;

    private final EspecieRacaService especieRacaService = new EspecieRacaService();
    private Especie especieEdicao;

    public void initialize() {}

    public void setEspecieEdicao(Especie especieEdicao) {
        this.especieEdicao = especieEdicao;

        if (especieEdicao != null) {
            inNomeEspecie.setText(especieEdicao.getEspecie());

            btnConfirmar.setText("Editar Especie");
        }
    }

    @FXML
    public void confirmar() {
        String nomeEspecie = inNomeEspecie.getText();

        if (especieEdicao == null) {
            try {
                especieRacaService.cadastrarEspecie(nomeEspecie);
                AlertaUtil.exibirAlerta(Alert.AlertType.INFORMATION, "Sucesso", "Espécie cadastrado com sucesso.");
                cancelar();
            } catch (DadosInvalidosException e) {
                AlertaUtil.exibirAlerta(Alert.AlertType.WARNING, "Dados inválidos", e.getMessage());
            } catch (SQLException e) {
                AlertaUtil.exibirAlerta(Alert.AlertType.ERROR, "Erro no banco de dados", e.getMessage());
            }
        } else {
            try {
                especieRacaService.atualizarEspecie(especieEdicao, inNomeEspecie.getText());
                AlertaUtil.exibirAlerta(Alert.AlertType.INFORMATION, "Sucesso", "Espécie atualizado com sucesso.");
                cancelar();
            } catch (DadosInvalidosException e) {
                AlertaUtil.exibirAlerta(Alert.AlertType.WARNING, "Dados inválidos", e.getMessage());
            } catch (SQLException e) {
                AlertaUtil.exibirAlerta(Alert.AlertType.ERROR, "Erro no banco de dados", e.getMessage());
            }
        }
    }

    @FXML
    public void cancelar() {
        Stage stage = (Stage) inNomeEspecie.getScene().getWindow();
        stage.close();
    }
}
