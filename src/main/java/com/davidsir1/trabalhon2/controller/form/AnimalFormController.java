package com.davidsir1.trabalhon2.controller.form;

import com.davidsir1.trabalhon2.model.Animal;
import com.davidsir1.trabalhon2.model.Especie;
import com.davidsir1.trabalhon2.model.Raca;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class AnimalFormController {
    @FXML private TextField inNomeAnimal;
    @FXML private ComboBox<String> cbPorte;
    @FXML private ComboBox<String> cbSexo;
    @FXML private ComboBox<String> cbStatusAdocao;
    @FXML private ComboBox<Raca> cbRaca;
    @FXML private ComboBox<Especie> cbEspecie;

    @FXML private Button btnConfirmar;

    private Animal animalEdicao;

    public void initialize() {
        // Inicializar os choicebox
        cbPorte.setItems(FXCollections.observableArrayList(
                "GRANDE", "MEDIO", "PEQUENO"
        ));
        cbSexo.setItems(FXCollections.observableArrayList(
                "MACHO", "FEMEA"
        ));
        cbStatusAdocao.setItems(FXCollections.observableArrayList(
                "DISPONIVEL", "ADOTADO", "RESERVADO", "EM TRATAMENTO", "INDISPONIVEL"
        ));

        // Listar as raças e especies utilizando o método de busca no banco de dados
        //cbRaca.setItems();
        //cbEspecie.setItems();
    }

    public void setAnimalEdicao(Animal animalEdicao) {
        this.animalEdicao = animalEdicao;

        if (animalEdicao != null) {
            inNomeAnimal.setText(animalEdicao.getNome_animal());
            cbRaca.setValue(animalEdicao.getRaca());
            cbSexo.setValue(animalEdicao.getSexo());
            cbPorte.setValue(animalEdicao.getPorte());
            cbStatusAdocao.setValue(animalEdicao.getStatus_adocao());

            btnConfirmar.setText("Salvar Alteração");
        }
    }
}
