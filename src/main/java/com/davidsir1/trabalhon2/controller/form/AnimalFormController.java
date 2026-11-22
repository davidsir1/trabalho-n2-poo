package com.davidsir1.trabalhon2.controller.form;

import com.davidsir1.trabalhon2.exception.DadosInvalidosException;
import com.davidsir1.trabalhon2.model.Animal;
import com.davidsir1.trabalhon2.model.Especie;
import com.davidsir1.trabalhon2.model.Raca;
import com.davidsir1.trabalhon2.service.AnimalService;
import com.davidsir1.trabalhon2.service.EspecieRacaService;
import com.davidsir1.trabalhon2.utils.AlertaUtil;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AnimalFormController {
    @FXML private TextField inNomeAnimal;
    @FXML private ComboBox<String> cbPorte;
    @FXML private ComboBox<String> cbSexo;
    @FXML private ComboBox<String> cbStatusAdocao;
    @FXML private ComboBox<Raca> cbRaca;
    @FXML private ComboBox<Especie> cbEspecie;

    @FXML private Button btnConfirmar;

    private final AnimalService animalService = new AnimalService();
    private final EspecieRacaService especieRacaService = new EspecieRacaService();

    private List<Raca> todasRacas;
    private Animal animalEdicao;

    public void initialize() {
        // Inicializar os choicebox
        cbPorte.setItems(FXCollections.observableArrayList(
                "GRANDE", "MÉDIO", "PEQUENO"
        ));
        cbSexo.setItems(FXCollections.observableArrayList(
                "MACHO", "FÊMEA"
        ));
        cbStatusAdocao.setItems(FXCollections.observableArrayList(
                "DISPONIVEL", "ADOTADO", "RESERVADO", "EM TRATAMENTO", "INDISPONIVEL"
        ));

        // Listar as raças e especies utilizando o método de busca no banco de dados
        try {
            cbEspecie.setItems(FXCollections.observableArrayList(especieRacaService.listarEspecies()));
        } catch (SQLException e) {
            System.err.println("Erro ao listar as especies: " + e.getMessage());
            AlertaUtil.exibirAlerta(Alert.AlertType.ERROR, "Erro no banco de dados", "Ocorreu um erro ao listar as espécies.");
            cancelar();
        }

        try {
            todasRacas = especieRacaService.listarRacas();
            cbRaca.setItems(FXCollections.observableArrayList(todasRacas));
        } catch (SQLException e) {
            System.err.println("Erro ao listar as raças: " + e.getMessage());
            AlertaUtil.exibirAlerta(Alert.AlertType.ERROR, "Erro no banco de dados", "Ocorreu um erro ao listar as raças.");
            cancelar();
        }
    }

    public void setAnimalEdicao(Animal animalEdicao) {
        this.animalEdicao = animalEdicao;

        if (animalEdicao != null) {
            inNomeAnimal.setText(animalEdicao.getNome_animal());
            cbSexo.setValue(animalEdicao.getSexo());
            cbPorte.setValue(animalEdicao.getPorte());
            cbStatusAdocao.setValue(animalEdicao.getStatus_adocao());
            cbEspecie.setValue(animalEdicao.getEspecie());

            try {
                cbRaca.setItems(FXCollections.observableArrayList(
                        especieRacaService.listarRacaPorEspecie(animalEdicao.getEspecie().getEspecie())
                ));
                cbRaca.setValue(animalEdicao.getRaca());
            } catch (SQLException e) {
                System.err.println("Erro ao filtrar as raças por especie: " + e.getMessage());
                AlertaUtil.exibirAlerta(Alert.AlertType.WARNING, "Erro no banco de dados",
                        "Ocorreu um erro ao filtar as raças por espécie.");
                cancelar();
            }

            btnConfirmar.setText("Salvar Alteração");
        }
    }

    @FXML
    public void filtrarRacasPorEspecie() {
        Especie especieSelecionada = cbEspecie.getValue();
        if (especieSelecionada == null || todasRacas == null) {
            cbRaca.setItems(FXCollections.observableArrayList(todasRacas));
            return;
        }

        List<Raca> racasFiltradas = new ArrayList<>();
        try {
            racasFiltradas = especieRacaService.listarRacaPorEspecie(especieSelecionada.getEspecie());
        } catch (SQLException e) {
            System.err.println("Erro ao filtrar as raças por especie: " + e.getMessage());
            AlertaUtil.exibirAlerta(Alert.AlertType.WARNING, "Erro no banco de dados",
                    "Ocorreu um erro ao filtar as raças por espécie.");
            cancelar();
        }

        cbRaca.setItems(FXCollections.observableArrayList(racasFiltradas));
        cbRaca.setValue(null);
    }

    @FXML
    public void confirmar() {
        try {
            String nome = inNomeAnimal.getText();
            String porte = cbPorte.getValue();
            String sexo = cbSexo.getValue();
            String status = cbStatusAdocao.getValue();
            Raca raca = cbRaca.getValue();

            if (animalEdicao == null) {
                Animal novoAnimal = new Animal(nome, porte, sexo, status, raca);
                animalService.cadastrar(novoAnimal);
                AlertaUtil.exibirAlerta(Alert.AlertType.INFORMATION, "Sucesso", "Animal cadastrado com sucesso.");
            } else {
                animalEdicao.setNome_animal(nome);
                animalEdicao.setPorte(porte);
                animalEdicao.setSexo(sexo);
                animalEdicao.setStatus_adocao(status);
                animalEdicao.setRaca(raca);
                animalService.atualizar(animalEdicao);
                AlertaUtil.exibirAlerta(Alert.AlertType.INFORMATION, "Sucesso", "Animal atualizado com sucesso!");
            }
            cancelar();
        } catch (DadosInvalidosException e) {
            AlertaUtil.exibirAlerta(Alert.AlertType.WARNING, "Dados Inválidos", e.getMessage());
        }
    }

    @FXML
    public void cancelar() {
        Stage stage = (Stage) inNomeAnimal.getScene().getWindow();
        stage.close();
    }
}
