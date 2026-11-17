package com.davidsir1.trabalhon2.controller.view;

import com.davidsir1.trabalhon2.HelloApplication;
import com.davidsir1.trabalhon2.controller.form.EspecieFormController;
import com.davidsir1.trabalhon2.controller.form.RacaFormController;
import com.davidsir1.trabalhon2.model.Especie;
import com.davidsir1.trabalhon2.model.Raca;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;

public class EspecieRacaController {
    @FXML private Button btnAdicionarEspecie;
    @FXML private Button btnEditarEspecie;
    @FXML private Button btnExcluirEspecie;
    @FXML private Button btnAdicionarRaca;
    @FXML private Button btnEditarRaca;
    @FXML private Button btnExcluirRaca;

    @FXML private TableView<Especie> tabelaEspecies;
    @FXML private TableView<Raca> tabelaRacas;

    public void initialize() {
        // Inicializar as tabelas
    }

    @FXML
    public void adicionarEspecie(){
        abrirFormularioEspecie(null, "Adicionar Especie");
    }

    @FXML
    public void editarEspecie() {
        Especie especie = tabelaEspecies.getSelectionModel().getSelectedItem();

        if (especie == null) {
            exibirAlerta(Alert.AlertType.WARNING, "Atenção", "Selecione uma especia na tabela para editar.");
            return;
        }

        abrirFormularioEspecie(especie, "Editar Especie");
    }

    @FXML
    public void adicionarRaca() {
        abrirFormularioRaca(null, "Adicionar Raça");
    }

    @FXML
    public void editarRaca() {
        Raca raca = tabelaRacas.getSelectionModel().getSelectedItem();

        if (raca == null) {
            exibirAlerta(Alert.AlertType.WARNING, "Atenção", "Seleciona uma raça na tabela para editar.");
            return;
        }

        abrirFormularioRaca(raca, "Editar Raça");
    }

    private void abrirFormularioRaca(Raca raca, String titulo) {
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("forms/RacaFormView.fxml"));

            Stage stage = new Stage();
            stage.setTitle(titulo);
            stage.setScene(new Scene(loader.load()));

            RacaFormController formController = loader.getController();
            formController.setRacaEdicao(raca);

            stage.showAndWait();
        } catch (IOException e) {
            System.err.println("Erro ao carregar o formulário: " + e.getMessage());
        }
    }

    private void abrirFormularioEspecie(Especie especie, String titulo) {
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("forms/EspecieFormView.fxml"));

            Stage stage = new Stage();
            stage.setTitle(titulo);
            stage.setScene(new Scene(loader.load()));

            EspecieFormController formController = loader.getController();
            formController.setEspecieEdicao(especie);

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
