package com.davidsir1.trabalhon2.controller.view;

import com.davidsir1.trabalhon2.exception.DadosInvalidosException;
import com.davidsir1.trabalhon2.model.Animal;
import com.davidsir1.trabalhon2.model.AlocacaoAnimal;
import com.davidsir1.trabalhon2.model.Baia;
import com.davidsir1.trabalhon2.service.BaiaService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;

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
        TextInputDialog codigoDialog = new TextInputDialog();
        codigoDialog.setTitle("Cadastrar baia");
        codigoDialog.setHeaderText("Informe o código da baia");
        codigoDialog.setContentText("Código:");
        Optional<String> codigo = codigoDialog.showAndWait();
        if (!codigo.isPresent()) {
            return;
        }

        TextInputDialog capacidadeDialog = new TextInputDialog();
        capacidadeDialog.setTitle("Cadastrar baia");
        capacidadeDialog.setHeaderText("Informe a capacidade da baia");
        capacidadeDialog.setContentText("Capacidade:");
        Optional<String> capacidadeTexto = capacidadeDialog.showAndWait();
        if (!capacidadeTexto.isPresent()) {
            return;
        }

        try {
            int capacidade = Integer.parseInt(capacidadeTexto.get().trim());
            service.cadastrarBaia(codigo.get(), capacidade);
            atualizarTabelas();
        } catch (NumberFormatException e) {
            exibirAlerta(Alert.AlertType.WARNING, "Dados inválidos", "A capacidade deve ser um número inteiro.");
        } catch (DadosInvalidosException e) {
            exibirAlerta(Alert.AlertType.WARNING, "Dados inválidos", e.getMessage());
        } catch (SQLException e) {
            exibirAlerta(Alert.AlertType.ERROR, "Erro no banco de dados", e.getMessage());
        }
    }

    @FXML
    public void editarBaia() {
        Baia baia = tabelaBaias.getSelectionModel().getSelectedItem();
        if (baia == null) {
            exibirAlerta(Alert.AlertType.WARNING, "Atenção", "Selecione uma baia para editar.");
            return;
        }

        TextInputDialog codigoDialog = new TextInputDialog(baia.getCod_baia());
        codigoDialog.setTitle("Editar baia");
        codigoDialog.setHeaderText("Altere o código da baia");
        codigoDialog.setContentText("Código:");
        Optional<String> codigo = codigoDialog.showAndWait();
        if (!codigo.isPresent()) {
            return;
        }

        TextInputDialog capacidadeDialog = new TextInputDialog(String.valueOf(baia.getCapacidade()));
        capacidadeDialog.setTitle("Editar baia");
        capacidadeDialog.setHeaderText("Altere a capacidade da baia");
        capacidadeDialog.setContentText("Capacidade:");
        Optional<String> capacidadeTexto = capacidadeDialog.showAndWait();
        if (!capacidadeTexto.isPresent()) {
            return;
        }

        try {
            int capacidade = Integer.parseInt(capacidadeTexto.get().trim());
            service.atualizarBaia(baia, codigo.get(), capacidade);
            atualizarTabelas();
        } catch (NumberFormatException e) {
            exibirAlerta(Alert.AlertType.WARNING, "Dados inválidos", "A capacidade deve ser um número inteiro.");
        } catch (DadosInvalidosException e) {
            exibirAlerta(Alert.AlertType.WARNING, "Dados inválidos", e.getMessage());
        } catch (SQLException e) {
            exibirAlerta(Alert.AlertType.ERROR, "Erro no banco de dados", e.getMessage());
        }
    }

    @FXML
    public void excluirBaia() {
        Baia baia = tabelaBaias.getSelectionModel().getSelectedItem();
        if (baia == null) {
            exibirAlerta(Alert.AlertType.WARNING, "Atenção", "Selecione uma baia para excluir.");
            return;
        }

        Alert confirmacao = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacao.setTitle("Excluir baia");
        confirmacao.setHeaderText(null);
        confirmacao.setContentText("Deseja excluir a baia " + baia.getCod_baia() + "?");
        if (!confirmacao.showAndWait().filter(resposta -> resposta == ButtonType.OK).isPresent()) {
            return;
        }

        try {
            service.excluirBaia(baia);
            atualizarTabelas();
        } catch (DadosInvalidosException e) {
            exibirAlerta(Alert.AlertType.WARNING, "Dados inválidos", e.getMessage());
        } catch (SQLException e) {
            exibirAlerta(Alert.AlertType.ERROR, "Não foi possível excluir",
                    "A baia pode possuir registros de alocação vinculados.\n" + e.getMessage());
        }
    }

    @FXML
    public void alocarAnimal() {
        try {
            List<Animal> animais = service.listarAnimais();
            List<Baia> baias = service.listarBaias();
            if (animais.isEmpty()) {
                exibirAlerta(Alert.AlertType.INFORMATION, "Alocação", "Cadastre um animal antes de realizar uma alocação.");
                return;
            }
            if (baias.isEmpty()) {
                exibirAlerta(Alert.AlertType.INFORMATION, "Alocação", "Cadastre uma baia antes de realizar uma alocação.");
                return;
            }

            ChoiceDialog<Animal> animalDialog = new ChoiceDialog<>(animais.get(0), animais);
            animalDialog.setTitle("Alocar animal");
            animalDialog.setHeaderText("Selecione o animal");
            animalDialog.setContentText("Animal:");
            Optional<Animal> animal = animalDialog.showAndWait();
            if (!animal.isPresent()) {
                return;
            }

            ChoiceDialog<Baia> baiaDialog = new ChoiceDialog<>(baias.get(0), baias);
            baiaDialog.setTitle("Alocar animal");
            baiaDialog.setHeaderText("Selecione a baia de destino");
            baiaDialog.setContentText("Baia:");
            Optional<Baia> baia = baiaDialog.showAndWait();
            if (!baia.isPresent()) {
                return;
            }

            service.alocarAnimal(animal.get(), baia.get());
            atualizarTabelas();
        } catch (DadosInvalidosException e) {
            exibirAlerta(Alert.AlertType.WARNING, "Dados inválidos", e.getMessage());
        } catch (SQLException e) {
            exibirAlerta(Alert.AlertType.ERROR, "Erro no banco de dados", e.getMessage());
        }
    }

    private void atualizarTabelas() {
        try {
            tabelaBaias.setItems(FXCollections.observableArrayList(service.listarBaias()));
            tabelaHistoricoAlocacao.setItems(FXCollections.observableArrayList(service.listarHistoricoAlocacoes()));
        } catch (SQLException e) {
            exibirAlerta(Alert.AlertType.ERROR, "Erro no banco de dados", e.getMessage());
        }
    }

    private void exibirAlerta(Alert.AlertType tipo, String titulo, String mensagem) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}
