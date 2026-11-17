package com.davidsir1.trabalhon2.controller.form;

import com.davidsir1.trabalhon2.model.Adotante;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class AdotanteFormController {
    @FXML private TextField inNomeAdotante;
    @FXML private TextField inTelefone;
    @FXML private TextField inEmailAdotante;
    @FXML private TextField inEndereco;

    @FXML private Button btnConfirmar;

    private Adotante adotanteEdicao;

    public void initialize() {}

    public void setAdotanteEdicao(Adotante adotanteEdicao) {
        this.adotanteEdicao = adotanteEdicao;

        if (adotanteEdicao != null) {
            inNomeAdotante.setText(adotanteEdicao.getNome_adotante());
            inTelefone.setText(adotanteEdicao.getTelefone());
            inEmailAdotante.setText(adotanteEdicao.getEmail_adotante());
            inEndereco.setText(adotanteEdicao.getEndereco());

            btnConfirmar.setText("Salvar Alteração");
        }
    }
}
