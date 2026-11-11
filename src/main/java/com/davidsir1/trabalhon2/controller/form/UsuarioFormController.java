package com.davidsir1.trabalhon2.controller.form;

import com.davidsir1.trabalhon2.model.Usuario;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class UsuarioFormController {
    @FXML
    private TextField inNomeUsuario;
    @FXML
    private TextField inEmailUsuario;
    @FXML
    private ChoiceBox<String> cargoUsuario;
    @FXML
    private TextField inSenhaUsuario;
    @FXML
    private Button btnConfirmar;

    private Usuario usuarioEdicao;

    public void initialize() {
        cargoUsuario.setItems(FXCollections.observableArrayList(
                "Admin", "Funcionário"
        ));
    }

    public void setUsuarioEdicao(Usuario usuarioEdicao) {
        this.usuarioEdicao = usuarioEdicao;

        if (usuarioEdicao != null) {
            inNomeUsuario.setText(usuarioEdicao.getNome_usuario());
            inEmailUsuario.setText(usuarioEdicao.getEmail_usuario());

            if (usuarioEdicao.getCargo() != null) {
                cargoUsuario.setValue(usuarioEdicao.getCargo());
            }

            inSenhaUsuario.clear();

            btnConfirmar.setText("Salvar");
        }
    }

    @FXML
    public void confirmar() {
        // Implementar funcionalidade de salvar alterações ou salvar novo usuário
    }

    @FXML
    public void cancelar() {
        Stage stage = (Stage) inNomeUsuario.getScene().getWindow();
        stage.close();
    }

}
