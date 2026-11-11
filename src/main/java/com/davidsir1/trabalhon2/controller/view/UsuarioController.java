package com.davidsir1.trabalhon2.controller.view;

import com.davidsir1.trabalhon2.HelloApplication;
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
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("forms/UsuarioFormView.fxml"));

            // Carregar os dados do usuario no formulario

            Stage stage = new Stage();
            stage.setTitle("Adicionar Usuário");
            stage.setScene(new Scene(loader.load()));
            stage.show();

        } catch (IOException e) {
            System.err.println("Erro ao carregar o formulário: " + e.getMessage());
        }
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
}
