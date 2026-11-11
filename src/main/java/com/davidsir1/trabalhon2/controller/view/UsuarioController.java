package com.davidsir1.trabalhon2.controller.view;

import com.davidsir1.trabalhon2.HelloApplication;
import com.davidsir1.trabalhon2.controller.form.UsuarioFormController;
import com.davidsir1.trabalhon2.model.Usuario;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class UsuarioController {
    @FXML private TextField inBuscarUsuario;
    @FXML private TableView tabelaUsuarios;

    @FXML
    public void adicionarUsuario() {
        // Chamar o formulário de adicionar o usuário
        abrirFormulario(null, "Adicionar Usuário");
    }

    @FXML
    public void editarUsuario() {
        // Chamar o mesmo formulário de adicionar o usuário
        // o formulário terá um botão com o nome "Salvar Alteração"
    }

    @FXML
    public void buscarUsuario() {
        // Consultar o nome do usuario no banco e atualizar a tabela
    }

    private void abrirFormulario(Usuario usuario, String titulo) {
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("forms/UsuarioFormView.fxml"));

            Stage stage = new Stage();
            stage.setTitle(titulo);
            stage.setScene(new Scene(loader.load()));

            UsuarioFormController formController = loader.getController();
            formController.setUsuarioEdicao(usuario);

            stage.showAndWait();
        } catch (IOException e) {
            System.err.println("Erro ao carregar o formulário: " + e.getMessage());
        }
    }
}
