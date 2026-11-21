package com.davidsir1.trabalhon2.controller.form;

import com.davidsir1.trabalhon2.exception.DadosInvalidosException;
import com.davidsir1.trabalhon2.model.Especie;
import com.davidsir1.trabalhon2.model.Raca;
import com.davidsir1.trabalhon2.service.EspecieRacaService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.List;

public class RacaFormController {
    @FXML private ComboBox<Especie> cbEspecie;
    @FXML private TextField inNomeRaca;

    @FXML private Button btnConfirmar;

    private final EspecieRacaService especieRacaService = new EspecieRacaService();
    private Raca racaEdicao;

    public void initialize() {
        // Inicializar a cbEspecie com especies
        try {
            cbEspecie.setItems(FXCollections.observableArrayList(especieRacaService.listarEspecies()));
        } catch (SQLException e) {
            System.err.println("Erro ao listar as especies: " + e.getMessage());
            cancelar();
        }
    }

    public void setRacaEdicao(Raca racaEdicao) {
        this.racaEdicao = racaEdicao;

        if (racaEdicao != null) {
            cbEspecie.setValue(racaEdicao.getEspecie());
            inNomeRaca.setText(racaEdicao.getRaca());

            btnConfirmar.setText("Salvar Alteração");
        }
    }

    @FXML
    public void confirmar() {
        String nomeRaca = inNomeRaca.getText();
        Especie especieSelecionada = cbEspecie.getValue();

        if (racaEdicao == null) {
            try {
                especieRacaService.cadastrarRaca(nomeRaca, especieSelecionada);
                exibirAlerta(Alert.AlertType.INFORMATION, "Sucesso", "Raça cadastrada com sucesso.");
                cancelar();
            } catch (DadosInvalidosException e) {
                exibirAlerta(Alert.AlertType.WARNING, "Dados inválidos", e.getMessage());
            } catch (SQLException e) {
                exibirAlerta(Alert.AlertType.ERROR, "Erro no banco de dados", e.getMessage());
            }
        } else {
            try {
                especieRacaService.atualizarRaca(racaEdicao, nomeRaca, especieSelecionada);
                exibirAlerta(Alert.AlertType.INFORMATION, "Sucesso", "Raça atualizada com sucesso.");
                cancelar();
            } catch (DadosInvalidosException e) {
                exibirAlerta(Alert.AlertType.WARNING, "Dados inválidos", e.getMessage());
            } catch (SQLException e) {
                exibirAlerta(Alert.AlertType.ERROR, "Erro no banco de dados", e.getMessage());
            }
        }
    }

    @FXML
    public void cancelar() {
        Stage stage = (Stage) inNomeRaca.getScene().getWindow();
        stage.close();
    }

    private void exibirAlerta(Alert.AlertType tipo, String titulo, String mensagem) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}
