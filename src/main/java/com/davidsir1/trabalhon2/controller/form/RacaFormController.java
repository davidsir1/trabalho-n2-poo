package com.davidsir1.trabalhon2.controller.form;

import com.davidsir1.trabalhon2.model.Especie;
import com.davidsir1.trabalhon2.model.Raca;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class RacaFormController {
    @FXML private ComboBox<Especie> cbEspecie;
    @FXML private TextField inNomeRaca;

    @FXML private Button btnConfirmar;

    private Raca racaEdicao;

    public void initialize() {
        // Inicializar a cbEspecie com especies
    }

    public void setRacaEdicao(Raca racaEdicao) {
        this.racaEdicao = racaEdicao;

        if (racaEdicao != null) {
            cbEspecie.setValue(racaEdicao.getEspecie());
            inNomeRaca.setText(racaEdicao.getRaca());

            btnConfirmar.setText("Salvar Alteração");
        }
    }
}
