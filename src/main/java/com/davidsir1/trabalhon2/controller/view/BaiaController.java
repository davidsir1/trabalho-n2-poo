package com.davidsir1.trabalhon2.controller.view;

import com.davidsir1.trabalhon2.HelloApplication;
import com.davidsir1.trabalhon2.controller.form.AlocacaoFormController;
import com.davidsir1.trabalhon2.controller.form.BaiaFormController;
import com.davidsir1.trabalhon2.model.AlocacaoAnimal;
import com.davidsir1.trabalhon2.model.Baia;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;

public class BaiaController {
    @FXML private Button btnAdicionarBaia;
    @FXML private Button btnEditarBaia;
    @FXML private Button btnExcluirBaia;
    @FXML private Button btnAlocarAnimal;

    @FXML private TableView<Baia> tabelaBaias;
    @FXML private TableView<AlocacaoAnimal> tabelaHistoricoAlocao;

    public void initialize() {
        // Inicializar as tabelas
    }

    @FXML
    public void adicionarBaia() {
        abrirFormularioBaia(null, "Adicionar Baia");
    }

    @FXML void editarBaia() {
        Baia baia = tabelaBaias.getSelectionModel().getSelectedItem();

        if (baia == null) {
            exibirAlerta(Alert.AlertType.WARNING, "Atenção", "Selecione uma baia na tabela para editar.");
            return;
        }

        abrirFormularioBaia(baia, "Editar Animal");
    }

    @FXML void alocarAnimal() {
        abrirFormularioAlocacao("Alocar Animal");
    }

    private void abrirFormularioBaia(Baia baia, String titulo) {
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("forms/BaiaFormView.fxml"));

            Stage stage = new Stage();
            stage.setTitle(titulo);
            stage.setScene(new Scene(loader.load()));

            BaiaFormController formController = loader.getController();
            formController.setBaiaEdicao(baia);

            stage.showAndWait();
        } catch (IOException e) {
            System.err.println("Erro ao carregar o formulário: " + e.getMessage());
        }
    }

    private void abrirFormularioAlocacao(String titulo) {
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("forms/AlocacaoFormView.fxml"));

            Stage stage = new Stage();
            stage.setTitle(titulo);
            stage.setScene(new Scene(loader.load()));

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
