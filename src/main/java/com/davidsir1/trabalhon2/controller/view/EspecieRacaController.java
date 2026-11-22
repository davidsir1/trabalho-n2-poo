package com.davidsir1.trabalhon2.controller.view;

import com.davidsir1.trabalhon2.HelloApplication;
import com.davidsir1.trabalhon2.controller.form.EspecieFormController;
import com.davidsir1.trabalhon2.controller.form.RacaFormController;
import com.davidsir1.trabalhon2.exception.DadosInvalidosException;
import com.davidsir1.trabalhon2.model.Especie;
import com.davidsir1.trabalhon2.model.Raca;
import com.davidsir1.trabalhon2.service.EspecieRacaService;
import com.davidsir1.trabalhon2.utils.AlertaUtil;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

public class EspecieRacaController {
    @FXML private TableView<Especie> tabelaEspecies;
    @FXML private TableColumn<Especie, Integer> idEspecieColuna;
    @FXML private TableColumn<Especie, String> especieColuna;
    @FXML private TableView<Raca> tabelaRacas;
    @FXML private TableColumn<Raca, Integer> idRacaColuna;
    @FXML private TableColumn<Raca, String> racaColuna;
    @FXML private TableColumn<Raca, String> especieRacaColuna;

    private final EspecieRacaService service = new EspecieRacaService();

    @FXML
    public void initialize() {
        idEspecieColuna.setCellValueFactory(new PropertyValueFactory<>("id_especie"));
        especieColuna.setCellValueFactory(new PropertyValueFactory<>("especie"));
        idRacaColuna.setCellValueFactory(new PropertyValueFactory<>("id_raca"));
        racaColuna.setCellValueFactory(new PropertyValueFactory<>("raca"));
        especieRacaColuna.setCellValueFactory(new PropertyValueFactory<>("nomeEspecie"));
        atualizarTabelas();
    }

    @FXML
    public void adicionarEspecie() {
        abrirFormularioEspecie(null, "Cadastrar Espécie");
        atualizarTabelas();
    }

    @FXML
    public void editarEspecie() {
        Especie especie = tabelaEspecies.getSelectionModel().getSelectedItem();
        if (especie == null) {
            AlertaUtil.exibirAlerta(Alert.AlertType.WARNING, "Atenção", "Selecione uma espécie para editar.");
            return;
        }

        abrirFormularioEspecie(especie, "Editar Espécie");
        atualizarTabelas();
    }

    @FXML
    public void excluirEspecie() {
        Especie especie = tabelaEspecies.getSelectionModel().getSelectedItem();
        if (especie == null) {
            AlertaUtil.exibirAlerta(Alert.AlertType.WARNING, "Atenção", "Selecione uma espécie para excluir.");
            return;
        }
        if (!AlertaUtil.exibirConfirmacao("Excluir espécie", null, "Deseja excluir a espécie " + especie.getEspecie() + "?")) {
            return;
        }

        try {
            service.excluirEspecie(especie);
            atualizarTabelas();
        } catch (DadosInvalidosException e) {
            AlertaUtil.exibirAlerta(Alert.AlertType.WARNING, "Dados inválidos", e.getMessage());
        } catch (SQLException e) {
            AlertaUtil.exibirAlerta(Alert.AlertType.ERROR, "Não foi possível excluir",
                    "A espécie pode estar vinculada a raças.\n" + e.getMessage());
        }
    }

    @FXML
    public void adicionarRaca() {
        if (tabelaEspecies.getItems().isEmpty()) {
            AlertaUtil.exibirAlerta(Alert.AlertType.INFORMATION, "Espécies",
                    "Cadastre uma espécie antes de cadastrar uma raça.");
            return;
        }
        abrirFormularioRaca(null, "Cadastrar Raça");
        atualizarTabelas();
    }

    @FXML
    public void editarRaca() {
        Raca raca = tabelaRacas.getSelectionModel().getSelectedItem();
        if (raca == null) {
            AlertaUtil.exibirAlerta(Alert.AlertType.WARNING, "Atenção", "Selecione uma raça para editar.");
            return;
        }

        abrirFormularioRaca(raca, "Editar raça");
        atualizarTabelas();
    }

    @FXML
    public void excluirRaca() {
        Raca raca = tabelaRacas.getSelectionModel().getSelectedItem();
        if (raca == null) {
            AlertaUtil.exibirAlerta(Alert.AlertType.WARNING, "Atenção", "Selecione uma raça para excluir.");
            return;
        }
        if (!AlertaUtil.exibirConfirmacao("Excluir raça", null, "Deseja excluir a raça " + raca.getRaca() + "?")) {
            return;
        }

        try {
            service.excluirRaca(raca);
            atualizarTabelas();
        } catch (DadosInvalidosException e) {
            AlertaUtil.exibirAlerta(Alert.AlertType.WARNING, "Dados inválidos", e.getMessage());
        } catch (SQLException e) {
            AlertaUtil.exibirAlerta(Alert.AlertType.ERROR, "Não foi possível excluir",
                    "A raça pode estar vinculada a animais.\n" + e.getMessage());
        }
    }

    private void abrirFormularioEspecie(Especie especie, String titulo) {
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("forms/EspecieFormView.fxml"));

            Stage stage = new Stage();
            stage.setTitle(titulo);
            stage.setScene(new Scene(loader.load()));

            EspecieFormController formController = loader.getController();
            formController.setEspecieEdicao(especie);

            InputStream iconStream = HelloApplication.class.getResourceAsStream("icons/Doghead48x48.png");
            if (iconStream != null) {
                stage.getIcons().add(new Image(iconStream));
            }

            stage.showAndWait();
        } catch (IOException e) {
            System.err.println("Erro ao carregar o formulário: " + e.getMessage());
        }
    }

    private void abrirFormularioRaca(Raca raca, String titulo) {
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("forms/RacaFormView.fxml"));

            Stage stage = new Stage();
            stage.setTitle(titulo);
            stage.setScene(new Scene(loader.load()));

            RacaFormController formController = loader.getController();
            formController.setRacaEdicao(raca);

            InputStream iconStream = HelloApplication.class.getResourceAsStream("icons/Doghead48x48.png");
            if (iconStream != null) {
                stage.getIcons().add(new Image(iconStream));
            }

            stage.showAndWait();
        } catch (IOException e) {
            System.err.println("Erro ao carregar o formulário: " + e.getMessage());
        }
    }

    private void atualizarTabelas() {
        try {
            tabelaEspecies.setItems(FXCollections.observableArrayList(service.listarEspecies()));
            tabelaRacas.setItems(FXCollections.observableArrayList(service.listarRacas()));
        } catch (SQLException e) {
            AlertaUtil.exibirAlerta(Alert.AlertType.ERROR, "Erro no banco de dados", e.getMessage());
        }
    }
}