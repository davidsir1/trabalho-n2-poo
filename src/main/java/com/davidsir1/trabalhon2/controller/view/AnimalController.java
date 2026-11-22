package com.davidsir1.trabalhon2.controller.view;

import com.davidsir1.trabalhon2.HelloApplication;
import com.davidsir1.trabalhon2.controller.form.AnimalFormController;
import com.davidsir1.trabalhon2.model.Animal;
import com.davidsir1.trabalhon2.utils.AlertaUtil;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;

public class AnimalController {
    @FXML private Button btnAdicionarAnimal;
    @FXML private Button btnEditarAnimal;

    @FXML private TableView<Animal> tabelaAnimal;

    @FXML
    public void adicionarAnimal() {
        abrirFormulario(null, "Adicionar Animal");
    }

    @FXML
    public void editarAnimal() {
        Animal animal = tabelaAnimal.getSelectionModel().getSelectedItem();

        if (animal == null) {
            AlertaUtil.exibirAlerta(Alert.AlertType.WARNING, "Atenção", "Selecione um animal na tabela para editar.");
            return;
        }

        abrirFormulario(animal, "Editar Animal");
    }

    private void abrirFormulario(Animal animal, String titulo) {
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("forms/AnimalFormView.fxml"));

            Stage stage = new Stage();
            stage.setTitle(titulo);
            stage.setScene(new Scene(loader.load()));

            AnimalFormController formController = loader.getController();
            formController.setAnimalEdicao(animal);

            stage.showAndWait();
        } catch (IOException e) {
            System.err.println("Erro ao carregar o formulário: " + e.getMessage());
        }
    }
}
