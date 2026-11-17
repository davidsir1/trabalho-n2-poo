package com.davidsir1.trabalhon2.controller.form;

import com.davidsir1.trabalhon2.model.Baia;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;

public class BaiaFormController {
    @FXML private TextField inCodigoBaia;
    @FXML private Spinner<Integer> spCapacidade;

    @FXML private Button btnConfirmar;

    private Baia baiaEdicao;

    public void initialize() {}

    public void setBaiaEdicao(Baia baiaEdicao) {
        this.baiaEdicao = baiaEdicao;

        if (baiaEdicao != null) {
            inCodigoBaia.setText(baiaEdicao.getCod_baia());
            spCapacidade.getValueFactory().setValue(baiaEdicao.getId_baia());

            btnConfirmar.setText("Salvar Alteração");
        }
    }
}
