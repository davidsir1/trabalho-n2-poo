package com.davidsir1.trabalhon2.controller.view;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class UsuarioController {
    @FXML private TextField inBuscarUsuario;

    @FXML
    public void adicionarUsuario() {
        // Chamar o formulário de adicionar o usuário
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
