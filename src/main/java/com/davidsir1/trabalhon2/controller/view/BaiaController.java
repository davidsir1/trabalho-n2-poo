package com.davidsir1.trabalhon2.controller.view;

import com.davidsir1.trabalhon2.HelloApplication;
import com.davidsir1.trabalhon2.controller.form.AlocacaoFormController;
import com.davidsir1.trabalhon2.controller.form.BaiaFormController;
import com.davidsir1.trabalhon2.exception.DadosInvalidosException;
import com.davidsir1.trabalhon2.model.Animal;
import com.davidsir1.trabalhon2.model.AlocacaoAnimal;
import com.davidsir1.trabalhon2.model.Baia;
import com.davidsir1.trabalhon2.service.BaiaService;
import com.davidsir1.trabalhon2.utils.AlertaUtil;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class BaiaController {
    @FXML private TableView<Baia> tabelaBaias;
    @FXML private TableColumn<Baia, Integer> idBaiaColuna;
    @FXML private TableColumn<Baia, String> codigoBaiaColuna;
    @FXML private TableColumn<Baia, Integer> capacidadeColuna;
    @FXML private TableView<AlocacaoAnimal> tabelaHistoricoAlocacao;
    @FXML private TableColumn<AlocacaoAnimal, Integer> idAlocacaoColuna;
    @FXML private TableColumn<AlocacaoAnimal, String> animalColuna;
    @FXML private TableColumn<AlocacaoAnimal, String> baiaColuna;
    @FXML private TableColumn<AlocacaoAnimal, String> dataTransferenciaColuna;

    private final BaiaService service = new BaiaService();

    @FXML
    public void initialize() {
        idBaiaColuna.setCellValueFactory(new PropertyValueFactory<>("id_baia"));
        codigoBaiaColuna.setCellValueFactory(new PropertyValueFactory<>("cod_baia"));
        capacidadeColuna.setCellValueFactory(new PropertyValueFactory<>("capacidade"));
        idAlocacaoColuna.setCellValueFactory(new PropertyValueFactory<>("id_alocacao_animal"));
        animalColuna.setCellValueFactory(new PropertyValueFactory<>("nomeAnimal"));
        baiaColuna.setCellValueFactory(new PropertyValueFactory<>("descricaoBaia"));
        dataTransferenciaColuna.setCellValueFactory(new PropertyValueFactory<>("dataTransferenciaFormatada"));
        atualizarTabelas();
    }

    @FXML
    public void adicionarBaia() {
        abrirFormularioBaia(null, "Cadastrar Baia");
        atualizarTabelas();
    }

    @FXML
    public void editarBaia() {
        Baia baia = tabelaBaias.getSelectionModel().getSelectedItem();
        if (baia == null) {
            AlertaUtil.exibirAlerta(Alert.AlertType.WARNING, "Atenção", "Selecione uma baia para editar.");
            return;
        }

        abrirFormularioBaia(baia, "Editar Baia");
        atualizarTabelas();
    }

    @FXML
    public void excluirBaia() {
        Baia baia = tabelaBaias.getSelectionModel().getSelectedItem();
        if (baia == null) {
            AlertaUtil.exibirAlerta(Alert.AlertType.WARNING, "Atenção", "Selecione uma baia para excluir.");
            return;
        }

        if (!AlertaUtil.exibirConfirmacao("Excluir Baia", null, "Deseja excluir a baia " + baia.getCod_baia() + "?")) {
            return;
        }

        try {
            service.excluirBaia(baia);
            atualizarTabelas();
        } catch (DadosInvalidosException e) {
            AlertaUtil.exibirAlerta(Alert.AlertType.WARNING, "Dados inválidos", e.getMessage());
        } catch (SQLException e) {
            AlertaUtil.exibirAlerta(Alert.AlertType.ERROR, "Não foi possível excluir",
                    "A baia pode possuir registros de alocação vinculados.");
        }
    }

    @FXML
    public void alocarAnimal() {
        Baia baiaSelecionada = tabelaBaias.getSelectionModel().getSelectedItem();

        if (tabelaBaias.getItems().isEmpty()) {
            AlertaUtil.exibirAlerta(Alert.AlertType.INFORMATION, "Alocação", "Cadastre uma baia antes de realizar uma alocação.");
            return;
        }

        if (baiaSelecionada == null) {
            AlertaUtil.exibirAlerta(Alert.AlertType.WARNING, "Atenção", "Selecione uma baia.");
            return;
        }

        abrirFormularioAlocacao(baiaSelecionada, "Registrar Alocação");
        atualizarTabelas();
    }

    private void abrirFormularioBaia(Baia baia, String titulo) {
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("forms/BaiaFormView.fxml"));

            Stage stage = new Stage();
            stage.setTitle(titulo);
            stage.setScene(new Scene(loader.load()));

            BaiaFormController formController = loader.getController();
            formController.setBaiaEdicao(baia);

            InputStream iconStream = HelloApplication.class.getResourceAsStream("icons/Doghead48x48.png");
            if (iconStream != null) {
                stage.getIcons().add(new Image(iconStream));
            }

            stage.showAndWait();
        } catch (IOException e) {
            System.err.println("Erro ao carregar o formulário: " + e.getMessage());
        }
    }

    private void abrirFormularioAlocacao(Baia baia, String titulo) {
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("forms/AlocacaoFormView.fxml"));

            Stage stage = new Stage();
            stage.setTitle(titulo);
            stage.setScene(new Scene(loader.load()));

            AlocacaoFormController formController = loader.getController();
            formController.setCbBaia(baia);

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
            tabelaBaias.setItems(FXCollections.observableArrayList(service.listarBaias()));
            tabelaHistoricoAlocacao.setItems(FXCollections.observableArrayList(service.listarHistoricoAlocacoes()));
        } catch (SQLException e) {
            AlertaUtil.exibirAlerta(Alert.AlertType.ERROR, "Erro no banco de dados", e.getMessage());
        }
    }
}
