package com.davidsir1.trabalhon2.controller.view;

import com.davidsir1.trabalhon2.HelloApplication;
import com.davidsir1.trabalhon2.controller.form.UsuarioFormController;
import com.davidsir1.trabalhon2.model.Usuario;
import com.davidsir1.trabalhon2.service.UsuarioService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class UsuarioController {
    @FXML private TextField inBuscarUsuario;
    @FXML private TableView<Usuario> tabelaUsuarios;

    @FXML private TableColumn<Usuario, Integer> idColuna;
    @FXML private TableColumn<Usuario, String> nomeColuna;
    @FXML private TableColumn<Usuario, String> emailColuna;
    @FXML private TableColumn<Usuario, String> cargoColuna;
    @FXML private TableColumn<Usuario, String> senhaColuna;

    private final UsuarioService usuarioService = new UsuarioService();

    public void initialize() {
        idColuna.setCellValueFactory(new PropertyValueFactory<Usuario, Integer>("id_usuario"));
        nomeColuna.setCellValueFactory(new PropertyValueFactory<Usuario, String>("nome_usuario"));
        emailColuna.setCellValueFactory(new PropertyValueFactory<Usuario, String>("email_usuario"));
        cargoColuna.setCellValueFactory(new PropertyValueFactory<Usuario, String>("cargo"));
        senhaColuna.setCellValueFactory(new PropertyValueFactory<Usuario, String>("senha"));

        carregarUsuarios();
    }

    @FXML
    public void adicionarUsuario() {
        // Chamar o formulário de adicionar o usuário
        abrirFormulario(null, "Adicionar Usuário");
    }

    @FXML
    public void editarUsuario() {
        // Chamar o mesmo formulário de adicionar o usuário
        // o formulário terá um botão com o nome "Salvar Alteração"
        Usuario usuario = tabelaUsuarios.getSelectionModel().getSelectedItem();

        if (usuario == null) {
            exibirAlerta(Alert.AlertType.WARNING, "Atenção", "Selecione um usuário na tabela para editar.");
            return;
        }

        abrirFormulario(usuario, "Editar Usuário");
    }

    @FXML
    public void excluirUsuario() {
        Usuario usuario = tabelaUsuarios.getSelectionModel().getSelectedItem();

        if (usuario == null) {
            exibirAlerta(Alert.AlertType.WARNING, "Atenção", "Selecione um usuário na tabela para excluir.");
            return;
        }

        Alert confirmacao = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacao.setTitle("Confirmar Exclusão");
        confirmacao.setHeaderText("Excluir usuário: " + usuario.getNome_usuario());
        confirmacao.setContentText("Tem certeza que deseja excluir este usuário? Esta ação não pode ser desfeita.");

        Optional<ButtonType> resultado = confirmacao.showAndWait();
        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            usuarioService.excluir(usuario);
            exibirAlerta(Alert.AlertType.INFORMATION, "Sucesso", "Usuário excluído com sucesso.");
        }
    }

    @FXML
    public void buscarUsuario() {
        // Consultar o nome do usuario no banco e atualizar a tabela
        String nomeBusca = inBuscarUsuario.getText();
        tabelaUsuarios.setItems(FXCollections.observableArrayList(usuarioService.buscarPorNome(nomeBusca)));
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

    private void carregarUsuarios() {
        tabelaUsuarios.setItems(FXCollections.observableArrayList(usuarioService.listarTodos()));
    }

    private void exibirAlerta(Alert.AlertType tipo, String titulo, String mensagem) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}
