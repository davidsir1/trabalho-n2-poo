package com.davidsir1.trabalhon2.controller.view;

import com.davidsir1.trabalhon2.HelloApplication;
import com.davidsir1.trabalhon2.controller.form.AdotanteFormController;
import com.davidsir1.trabalhon2.exception.DadosInvalidosException;
import com.davidsir1.trabalhon2.model.Adotante;
import com.davidsir1.trabalhon2.service.AdotanteService;
import com.davidsir1.trabalhon2.utils.AlertaUtil;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class AdotanteController {
    @FXML private Button btnCadastrarAdotante;
    @FXML private Button btnEditarAdotante;
    @FXML private Button btnExcluirAdotante;

    @FXML private TextField inBuscarAdotanteNome;
    @FXML private Button btnBuscar;

    @FXML private TableView<Adotante> tabelaAdotante;
    @FXML private TableColumn<Adotante, Integer> idColuna;
    @FXML private TableColumn<Adotante, String> nomeColuna;
    @FXML private TableColumn<Adotante, String> telefoneColuna;
    @FXML private TableColumn<Adotante, String> emailColuna;
    @FXML private TableColumn<Adotante, String> enderecoColuna;

    private final AdotanteService adotanteService = new AdotanteService();

    public void initialize() {
        idColuna.setCellValueFactory(new PropertyValueFactory<Adotante, Integer>("id_adotante"));
        nomeColuna.setCellValueFactory(new PropertyValueFactory<Adotante, String>("nome_adotante"));
        telefoneColuna.setCellValueFactory(new PropertyValueFactory<Adotante, String>("telefone"));
        emailColuna.setCellValueFactory(new PropertyValueFactory<Adotante, String>("email_adotante"));
        enderecoColuna.setCellValueFactory(new PropertyValueFactory<Adotante, String>("endereco"));

        carregarAdotantes();
    }

    @FXML
    public void cadastrarAdotante() {
        abrirFormulario(null, "Cadastrar Adotante");
        carregarAdotantes();
    }

    @FXML
    public void editarAdotante() {
        Adotante adotante = tabelaAdotante.getSelectionModel().getSelectedItem();

        if (adotante == null) {
            AlertaUtil.exibirAlerta(Alert.AlertType.WARNING, "Atenção", "Selecione um adotante da tabela para editar.");
            return;
        }

        abrirFormulario(adotante, "Editar Adotante");
        carregarAdotantes();
    }

    @FXML
    public void excluirAdotante() {
        Adotante adotante = tabelaAdotante.getSelectionModel().getSelectedItem();

        if (adotante == null) {
            AlertaUtil.exibirAlerta(Alert.AlertType.WARNING, "Atenção", "Selecione um adotante da tabela para excluir.");
            return;
        }

        boolean confirmado = AlertaUtil.exibirConfirmacao(
                "Confirmar Exclusão",
                "Excluir adotante: " + adotante,
                "Tem certeza que deseja excluir este adotante?");

        if (!confirmado) {
            return;
        }

        try {
            adotanteService.excluir(adotante.getId_adotante());
            AlertaUtil.exibirAlerta(Alert.AlertType.INFORMATION, "Sucesso", "Adotante excluído com sucesso.");
            carregarAdotantes();
        } catch (DadosInvalidosException e) {
            AlertaUtil.exibirAlerta(Alert.AlertType.WARNING, "Não permitido", e.getMessage());
        }
    }

    @FXML
    public void buscarAdotante() {
        String nome = inBuscarAdotanteNome.getText();
        tabelaAdotante.setItems(FXCollections.observableArrayList(adotanteService.buscarPorNome(nome)));
    }

    private void carregarAdotantes() {
        tabelaAdotante.setItems(FXCollections.observableArrayList(adotanteService.listarTodos()));
    }

    private void abrirFormulario(Adotante adotante, String titulo) {
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("forms/AdotanteFormView.fxml"));

            Stage stage = new Stage();
            stage.setTitle(titulo);
            stage.setScene(new Scene(loader.load()));

            AdotanteFormController formController = loader.getController();
            formController.setAdotanteEdicao(adotante);

            stage.showAndWait();
        } catch (IOException e) {
            System.err.println("Erro ao carregar o formulário: " + e.getMessage());
        }
    }
}
