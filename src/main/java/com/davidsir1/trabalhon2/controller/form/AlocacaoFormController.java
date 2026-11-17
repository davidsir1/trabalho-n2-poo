package com.davidsir1.trabalhon2.controller.form;

import com.davidsir1.trabalhon2.model.Animal;
import com.davidsir1.trabalhon2.model.Baia;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

public class AlocacaoFormController {
    @FXML private ComboBox<Animal> cbAnimal;
    @FXML private ComboBox<Baia> cbBaia;

    @FXML private Button btnConfirmar;

    public void initialize() {
        // Inicializar ChoiceBox Animel e Baia
        //cbAnimal.setItems();
        //cbBaia.setItems();
    }

    @FXML
    public void confirmar() {
        // O animal só pode ser alocado se ele não estiver
        // o status de adoção como ADOTADO
    }
}
