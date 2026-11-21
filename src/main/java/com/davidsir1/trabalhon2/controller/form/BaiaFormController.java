package com.davidsir1.trabalhon2.controller.form;

import com.davidsir1.trabalhon2.exception.DadosInvalidosException;
import com.davidsir1.trabalhon2.model.Baia;
import com.davidsir1.trabalhon2.service.BaiaService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.SQLException;

public class BaiaFormController {
    @FXML private TextField inCodigoBaia;
    @FXML private Spinner<Integer> spCapacidade;

    @FXML private Button btnConfirmar;

    private final BaiaService baiaService = new BaiaService();
    private Baia baiaEdicao;

    public void initialize() {
        spCapacidade.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1)
        );
    }

    public void setBaiaEdicao(Baia baiaEdicao) {
        this.baiaEdicao = baiaEdicao;

        if (baiaEdicao != null) {
            inCodigoBaia.setText(baiaEdicao.getCod_baia());
            spCapacidade.getValueFactory().setValue(baiaEdicao.getCapacidade());

            btnConfirmar.setText("Salvar Alteração");
        }
    }

    @FXML
    public void confirmar() {
        String codBaia = inCodigoBaia.getText();
        int capacidade = spCapacidade.getValue();

        if (baiaEdicao == null) {
            try {
                baiaService.cadastrarBaia(codBaia, capacidade);
                exibirAlerta(Alert.AlertType.INFORMATION, "Sucesso", "Baia cadastrada com sucesso.");
                cancelar();
            } catch (NumberFormatException e) {
                exibirAlerta(Alert.AlertType.WARNING, "Dados inválidos", "A capacidade deve ser um número inteiro.");
            } catch (DadosInvalidosException e) {
                exibirAlerta(Alert.AlertType.WARNING, "Dados inválidos", e.getMessage());
            } catch (SQLException e) {
                exibirAlerta(Alert.AlertType.ERROR, "Erro no banco de dados", e.getMessage());
            }
        } else {
            try {
                baiaService.atualizarBaia(baiaEdicao, codBaia, capacidade);
                exibirAlerta(Alert.AlertType.WARNING, "Sucesso", "Baia atualizada com sucesso.");
                cancelar();
            } catch (NumberFormatException e) {
                exibirAlerta(Alert.AlertType.WARNING, "Dados inválidos", "A capacidade deve ser um número inteiro.");
            } catch (DadosInvalidosException e) {
                exibirAlerta(Alert.AlertType.WARNING, "Dados inválidos", e.getMessage());
            } catch (SQLException e) {
                exibirAlerta(Alert.AlertType.ERROR, "Erro no banco de dados", e.getMessage());
            }
        }
    }

    @FXML
    public void cancelar() {
        Stage stage = (Stage) inCodigoBaia.getScene().getWindow();
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
