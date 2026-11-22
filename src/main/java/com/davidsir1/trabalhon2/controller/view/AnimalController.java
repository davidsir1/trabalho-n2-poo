package com.davidsir1.trabalhon2.controller.view;

import com.davidsir1.trabalhon2.HelloApplication;
import com.davidsir1.trabalhon2.controller.form.AnimalFormController;
import com.davidsir1.trabalhon2.exception.DadosInvalidosException;
import com.davidsir1.trabalhon2.model.Animal;
import com.davidsir1.trabalhon2.model.Especie;
import com.davidsir1.trabalhon2.model.Raca;
import com.davidsir1.trabalhon2.service.AnimalService;
import com.davidsir1.trabalhon2.utils.AlertaUtil;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;

public class AnimalController {
    @FXML private TextField inBuscarAnimalNome;
    @FXML private ComboBox<String> selStatusAnimal;
    @FXML private Button btnFiltrar;

    @FXML private Button btnAdicionarAnimal;
    @FXML private Button btnEditarAnimal;
    @FXML private Button btnExcluirAnimal;

    @FXML private TableView<Animal> tabelaAnimal;
    @FXML private TableColumn<Animal, Integer> idColuna;
    @FXML private TableColumn<Animal, String> nomeColuna;
    @FXML private TableColumn<Animal, String> porteColuna;
    @FXML private TableColumn<Animal, String> sexoColuna;
    @FXML private TableColumn<Animal, Raca> racaColuna;
    @FXML private TableColumn<Animal, Especie> especieColuna;
    @FXML private TableColumn<Animal, String> statusColuna;

    private final AnimalService animalService = new AnimalService();

    public void initialize() {
        idColuna.setCellValueFactory(new PropertyValueFactory<>("id_animal"));
        nomeColuna.setCellValueFactory(new PropertyValueFactory<>("nome_animal"));
        porteColuna.setCellValueFactory(new PropertyValueFactory<>("porte"));
        sexoColuna.setCellValueFactory(new PropertyValueFactory<>("sexo"));
        racaColuna.setCellValueFactory(new PropertyValueFactory<>("raca"));
        especieColuna.setCellValueFactory(new PropertyValueFactory<>("especie"));
        statusColuna.setCellValueFactory(new PropertyValueFactory<>("status_adocao"));

        selStatusAnimal.setItems(FXCollections.observableArrayList(
                "TODOS", "DISPONIVEL", "ADOTADO", "RESERVADO", "EM TRATAMENTO", "INDISPONIVEL"
        ));
        selStatusAnimal.setValue("TODOS");

        carregarAnimais();
    }

    @FXML
    public void adicionarAnimal() {
        abrirFormulario(null, "Adicionar Animal");
        carregarAnimais();
    }

    @FXML
    public void editarAnimal() {
        Animal animal = tabelaAnimal.getSelectionModel().getSelectedItem();

        if (animal == null) {
            AlertaUtil.exibirAlerta(Alert.AlertType.WARNING, "Atenção", "Selecione um animal na tabela para editar.");
            return;
        }

        abrirFormulario(animal, "Editar Animal");
        carregarAnimais();
    }

    @FXML
    public void excluirAnimal() {
        Animal animal = tabelaAnimal.getSelectionModel().getSelectedItem();

        if (animal == null) {
            AlertaUtil.exibirAlerta(Alert.AlertType.WARNING, "Atenção", "Selecione um animal na tabela para excluir.");
            return;
        }


        if (!AlertaUtil.exibirConfirmacao("Confirmar Exclusão",
                "Excluir animal: " + animal.getNome_animal(),
                "Tem certeza que deseja excluir este registro?")) {
            return;
        }

        try {
            animalService.excluir(animal);
            AlertaUtil.exibirAlerta(Alert.AlertType.INFORMATION, "Sucesso", "Animal excluĩdo com sucesso!");
            carregarAnimais();
        } catch (DadosInvalidosException e) {
            AlertaUtil.exibirAlerta(Alert.AlertType.WARNING, "Não permitido", e.getMessage());
        }
    }

    @FXML
    public void filtrarAnimais() {
        String nome = inBuscarAnimalNome.getText();
        String status = selStatusAnimal.getValue();

        if ("TODOS".equals(status)) {
            status = null;
        }

        tabelaAnimal.setItems(FXCollections.observableArrayList(
                animalService.buscarPorNomeEStatus(nome, status)
        ));
    }

    private void carregarAnimais() {
        tabelaAnimal.setItems(FXCollections.observableArrayList(animalService.listarTodos()));
    }

    private void abrirFormulario(Animal animal, String titulo) {
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("forms/AnimalFormView.fxml"));

            Stage stage = new Stage();
            stage.setTitle(titulo);
            stage.setScene(new Scene(loader.load()));

            AnimalFormController formController = loader.getController();
            formController.setAnimalEdicao(animal);

            InputStream iconStream = HelloApplication.class.getResourceAsStream("icons/Doghead48x48.png");
            if (iconStream != null) {
                stage.getIcons().add(new Image(iconStream));
            }

            stage.showAndWait();
        } catch (IOException e) {
            System.err.println("Erro ao carregar o formulário: " + e.getMessage());
        }
    }
}
