package com.davidsir1.trabalhon2.controller.form;

import com.davidsir1.trabalhon2.exception.DadosInvalidosException;
import com.davidsir1.trabalhon2.model.Animal;
import com.davidsir1.trabalhon2.model.Baia;
import com.davidsir1.trabalhon2.repository.AlocacaoAnimalRepository;
import com.davidsir1.trabalhon2.service.BaiaService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

import java.sql.SQLException;

public class AlocacaoFormController {
    @FXML private ComboBox<Animal> cbAnimal;
    @FXML private ComboBox<Baia> cbBaia;

    @FXML private Button btnConfirmar;

    private final BaiaService baiaService = new BaiaService();

    public void initialize() {
        // Inicializar ChoiceBox Animel e Baia
        try {
            cbAnimal.setItems(
                    FXCollections.observableArrayList(baiaService.listarAnimais())
            );
        } catch (SQLException e) {
            System.err.println("Erro ao listar os animais: " + e.getMessage());
            exibirAlerta(Alert.AlertType.ERROR, "Erro no Banco de Dados", e.getMessage());
            cancelar();
        }
        /*
        try {
            cbBaia.setItems(
                    FXCollections.observableArrayList(baiaService.listarBaias())
            );
        } catch (SQLException e) {
            System.err.println("Erro ao listar as baias: " + e.getMessage());
            exibirAlerta(Alert.AlertType.ERROR, "Erro no Banco de Dados", e.getMessage());
            cancelar();
        }
         */
    }

    public void setCbBaia(Baia baia) {
        cbBaia.setValue(baia);
    }

    @FXML
    public void confirmar() {
        // O animal só pode ser alocado se ele não estiver
        // o status de adoção como ADOTADO
        try {
            baiaService.alocarAnimal(cbAnimal.getValue(), cbBaia.getValue());
            exibirAlerta(Alert.AlertType.INFORMATION, "Sucesso", "Animal alocado com sucesso.");
            cancelar();
        } catch (DadosInvalidosException e) {
            exibirAlerta(Alert.AlertType.WARNING, "Dados inválidos", e.getMessage());
        } catch (SQLException e) {
            exibirAlerta(Alert.AlertType.ERROR, "Erro no banco de dados", e.getMessage());
        }
    }

    @FXML
    public void cancelar() {
        Stage stage = (Stage) cbAnimal.getScene().getWindow();
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
