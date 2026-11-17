package com.davidsir1.trabalhon2.controller.view;

import com.davidsir1.trabalhon2.HelloApplication;
import com.davidsir1.trabalhon2.controller.form.AdotanteFormController;
import com.davidsir1.trabalhon2.model.Adotante;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class AdotanteController {
    @FXML private Button btnCadastrarAdotante;
    @FXML private Button btnEditarAdotante;
    @FXML private Button btnExcluirAdotante;

    @FXML private TextField inBuscarAdotanteNome;
    @FXML private Button btnBuscar;

    @FXML private TableView<Adotante> tabelaAdotante;

    public void initialize() {}

    @FXML
    public void cadastrarAdotante() {
        abrirFormulario(null, "Cadastrar Adotante");
    }

    @FXML
    public void editarAdotante() {
        Adotante adotante = tabelaAdotante.getSelectionModel().getSelectedItem();

        if (adotante == null) {
            exibirAlerta(Alert.AlertType.WARNING, "Atenção", "Selecione um adotante da tabela para editar.");
            return;
        }

        abrirFormulario(adotante, "Editar Adotante");
    }

    private void abrirFormulario(Adotante adotante, String titulo) {
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("forms/AdotanteFormView.fxml"));

            Stage stage = new Stage();
            stage.setTitle(titulo);
            stage.setScene(new Scene(loader.load()));

            AdotanteFormController formController = loader.getController();
            formController.setAdotanteEdicao(adotante);

            stage.showAndWait();
        } catch (IOException e) {
            System.err.println("Erro ao carregar o formulário: " + e.getMessage());
        }
    }

    private void exibirAlerta(Alert.AlertType tipo, String titulo, String mensagem) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}
