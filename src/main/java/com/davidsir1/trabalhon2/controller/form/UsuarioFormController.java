package com.davidsir1.trabalhon2.controller.form;

import com.davidsir1.trabalhon2.exception.DadosInvalidosException;
import com.davidsir1.trabalhon2.model.Cargo;
import com.davidsir1.trabalhon2.model.Usuario;
import com.davidsir1.trabalhon2.service.UsuarioService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
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
    private ChoiceBox<Cargo> cargoUsuario;
    @FXML
    private TextField inSenhaUsuario;
    @FXML
    private Button btnConfirmar;

    private final UsuarioService usuarioService = new UsuarioService();
    private Usuario usuarioEdicao;

    public void initialize() {
        cargoUsuario.setItems(FXCollections.observableArrayList(usuarioService.listarCargos()));
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
        String nome = inNomeUsuario.getText();
        String email = inEmailUsuario.getText();
        Cargo cargo = cargoUsuario.getValue();
        String senha = inSenhaUsuario.getText();

        if (usuarioEdicao == null) {
            try {
                usuarioService.cadastrar(nome, email, cargo, senha);
                exibirAlerta(Alert.AlertType.INFORMATION, "Sucesso", "Usuário cadastrado com sucesso.");
                cancelar();
            } catch (DadosInvalidosException e) {
                exibirAlerta(Alert.AlertType.ERROR, "Error", e.getMessage());
            }
        } else {
            try {
                usuarioService.atualizar(usuarioEdicao, nome, email, cargo, senha);
                exibirAlerta(Alert.AlertType.INFORMATION, "Sucesso", "Usuário atualizado com sucesso.");
                cancelar();
            } catch (DadosInvalidosException e) {
                exibirAlerta(Alert.AlertType.ERROR, "Error", e.getMessage());
            }
        }
    }

    @FXML
    public void cancelar() {
        Stage stage = (Stage) inNomeUsuario.getScene().getWindow();
        stage.close();
    }

    private void exibirAlerta(Alert.AlertType tipo, String titulo, String mensagem) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}
