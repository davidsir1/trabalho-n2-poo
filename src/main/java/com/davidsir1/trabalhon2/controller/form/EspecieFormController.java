package com.davidsir1.trabalhon2.controller.form;

import com.davidsir1.trabalhon2.model.Especie;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class EspecieFormController {
    @FXML private TextField inNomeEspecie;
    @FXML private Button btnConfirmar;

    private Especie especieEdicao;

    public void initialize() {}

    public void setEspecieEdicao(Especie especieEdicao) {
        this.especieEdicao = especieEdicao;

        if (especieEdicao != null) {
            inNomeEspecie.setText(especieEdicao.getEspecie());

            btnConfirmar.setText("Editar Especie");
        }
    }
}
