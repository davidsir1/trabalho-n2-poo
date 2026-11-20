package com.davidsir1.trabalhon2.controller.view;

import com.davidsir1.trabalhon2.exception.DadosInvalidosException;
import com.davidsir1.trabalhon2.model.Especie;
import com.davidsir1.trabalhon2.model.Raca;
import com.davidsir1.trabalhon2.service.EspecieRacaService;
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
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Cadastrar espécie");
        dialog.setHeaderText("Informe o nome da espécie");
        dialog.setContentText("Espécie:");
        Optional<String> resultado = dialog.showAndWait();
        if (!resultado.isPresent()) {
            return;
        }

        try {
            service.cadastrarEspecie(resultado.get());
            atualizarTabelas();
        } catch (DadosInvalidosException e) {
            exibirAlerta(Alert.AlertType.WARNING, "Dados inválidos", e.getMessage());
        } catch (SQLException e) {
            exibirAlerta(Alert.AlertType.ERROR, "Erro no banco de dados", e.getMessage());
        }
    }

    @FXML
    public void editarEspecie() {
        Especie especie = tabelaEspecies.getSelectionModel().getSelectedItem();
        if (especie == null) {
            exibirAlerta(Alert.AlertType.WARNING, "Atenção", "Selecione uma espécie para editar.");
            return;
        }

        TextInputDialog dialog = new TextInputDialog(especie.getEspecie());
        dialog.setTitle("Editar espécie");
        dialog.setHeaderText("Altere o nome da espécie");
        dialog.setContentText("Espécie:");
        Optional<String> nome = dialog.showAndWait();
        if (!nome.isPresent()) {
            return;
        }

        try {
            service.atualizarEspecie(especie, nome.get());
            atualizarTabelas();
        } catch (DadosInvalidosException e) {
            exibirAlerta(Alert.AlertType.WARNING, "Dados inválidos", e.getMessage());
        } catch (SQLException e) {
            exibirAlerta(Alert.AlertType.ERROR, "Erro no banco de dados", e.getMessage());
        }
    }

    @FXML
    public void excluirEspecie() {
        Especie especie = tabelaEspecies.getSelectionModel().getSelectedItem();
        if (especie == null) {
            exibirAlerta(Alert.AlertType.WARNING, "Atenção", "Selecione uma espécie para excluir.");
            return;
        }
        if (!confirmarExclusao("Excluir espécie", "Deseja excluir a espécie " + especie.getEspecie() + "?")) {
            return;
        }

        try {
            service.excluirEspecie(especie);
            atualizarTabelas();
        } catch (DadosInvalidosException e) {
            exibirAlerta(Alert.AlertType.WARNING, "Dados inválidos", e.getMessage());
        } catch (SQLException e) {
            exibirAlerta(Alert.AlertType.ERROR, "Não foi possível excluir",
                    "A espécie pode estar vinculada a raças.\n" + e.getMessage());
        }
    }

    @FXML
    public void adicionarRaca() {
        try {
            List<Especie> especies = service.listarEspecies();
            if (especies.isEmpty()) {
                exibirAlerta(Alert.AlertType.INFORMATION, "Espécies",
                        "Cadastre uma espécie antes de cadastrar uma raça.");
                return;
            }

            ChoiceDialog<Especie> escolhaEspecie = new ChoiceDialog<>(especies.get(0), especies);
            escolhaEspecie.setTitle("Cadastrar raça");
            escolhaEspecie.setHeaderText("Selecione a espécie da raça");
            escolhaEspecie.setContentText("Espécie:");
            Optional<Especie> especieSelecionada = escolhaEspecie.showAndWait();
            if (!especieSelecionada.isPresent()) {
                return;
            }

            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Cadastrar raça");
            dialog.setHeaderText("Informe o nome da raça");
            dialog.setContentText("Raça:");
            Optional<String> nome = dialog.showAndWait();
            if (!nome.isPresent()) {
                return;
            }

            service.cadastrarRaca(nome.get(), especieSelecionada.get());
            atualizarTabelas();
        } catch (DadosInvalidosException e) {
            exibirAlerta(Alert.AlertType.WARNING, "Dados inválidos", e.getMessage());
        } catch (SQLException e) {
            exibirAlerta(Alert.AlertType.ERROR, "Erro no banco de dados", e.getMessage());
        }
    }

    @FXML
    public void editarRaca() {
        Raca raca = tabelaRacas.getSelectionModel().getSelectedItem();
        if (raca == null) {
            exibirAlerta(Alert.AlertType.WARNING, "Atenção", "Selecione uma raça para editar.");
            return;
        }

        try {
            List<Especie> especies = service.listarEspecies();
            if (especies.isEmpty()) {
                exibirAlerta(Alert.AlertType.INFORMATION, "Espécies",
                        "Cadastre uma espécie antes de editar uma raça.");
                return;
            }

            Especie especieAtual = especies.stream()
                    .filter(especie -> especie.getId_especie() == raca.getEspecie().getId_especie())
                    .findFirst()
                    .orElse(especies.get(0));
            ChoiceDialog<Especie> escolhaEspecie = new ChoiceDialog<>(especieAtual, especies);
            escolhaEspecie.setTitle("Editar raça");
            escolhaEspecie.setHeaderText("Selecione a espécie da raça");
            escolhaEspecie.setContentText("Espécie:");
            Optional<Especie> especieSelecionada = escolhaEspecie.showAndWait();
            if (!especieSelecionada.isPresent()) {
                return;
            }

            TextInputDialog dialog = new TextInputDialog(raca.getRaca());
            dialog.setTitle("Editar raça");
            dialog.setHeaderText("Altere o nome da raça");
            dialog.setContentText("Raça:");
            Optional<String> nome = dialog.showAndWait();
            if (!nome.isPresent()) {
                return;
            }

            service.atualizarRaca(raca, nome.get(), especieSelecionada.get());
            atualizarTabelas();
        } catch (DadosInvalidosException e) {
            exibirAlerta(Alert.AlertType.WARNING, "Dados inválidos", e.getMessage());
        } catch (SQLException e) {
            exibirAlerta(Alert.AlertType.ERROR, "Erro no banco de dados", e.getMessage());
        }
    }

    @FXML
    public void excluirRaca() {
        Raca raca = tabelaRacas.getSelectionModel().getSelectedItem();
        if (raca == null) {
            exibirAlerta(Alert.AlertType.WARNING, "Atenção", "Selecione uma raça para excluir.");
            return;
        }
        if (!confirmarExclusao("Excluir raça", "Deseja excluir a raça " + raca.getRaca() + "?")) {
            return;
        }

        try {
            service.excluirRaca(raca);
            atualizarTabelas();
        } catch (DadosInvalidosException e) {
            exibirAlerta(Alert.AlertType.WARNING, "Dados inválidos", e.getMessage());
        } catch (SQLException e) {
            exibirAlerta(Alert.AlertType.ERROR, "Não foi possível excluir",
                    "A raça pode estar vinculada a animais.\n" + e.getMessage());
        }
    }

    private void atualizarTabelas() {
        try {
            tabelaEspecies.setItems(FXCollections.observableArrayList(service.listarEspecies()));
            tabelaRacas.setItems(FXCollections.observableArrayList(service.listarRacas()));
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

    private boolean confirmarExclusao(String titulo, String mensagem) {
        Alert confirmacao = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacao.setTitle(titulo);
        confirmacao.setHeaderText(null);
        confirmacao.setContentText(mensagem);
        return confirmacao.showAndWait().filter(resposta -> resposta == ButtonType.OK).isPresent();
    }
}