package com.davidsir1.trabalhon2.controller.view;

import com.davidsir1.trabalhon2.exception.DadosInvalidosException;
import com.davidsir1.trabalhon2.model.Adotante;
import com.davidsir1.trabalhon2.model.Animal;
import com.davidsir1.trabalhon2.service.AdocaoService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;

public class AdocaoController {
    @FXML private ComboBox<Adotante> selAdotante;
    @FXML private ComboBox<Animal> selAnimalDisponivel;
    @FXML private DatePicker dataAdocao;

    @FXML private Label adotanteSelecionado;
    @FXML private Label animalSelecionado;

    @FXML private Button btnRegistrarAdocao;

    private final AdocaoService adocaoService = new AdocaoService();

    public void initialize() {
        // Inicializar a data da adoção para a data atual e hora
        dataAdocao.setValue(LocalDate.now());

        // Inicializar o combobox adotante e animal
        carregarListas();
    }

    @FXML
    public void registrarAdocao() {
        Adotante adotante = selAdotante.getValue();
        Animal animal = selAnimalDisponivel.getValue();
        LocalDate data = dataAdocao.getValue();

        Alert confirmacao = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacao.setTitle("Confirmar Adoção");
        confirmacao.setHeaderText("Deseja confirmar o registro desta adoção?");
        confirmacao.setContentText(
                "Adotante: " + (adotante != null ? adotante.getNome_adotante() : "-") + "\n" +
                "Animal: " + (animal != null ? animal.getNome_animal() : "-")
        );

        Optional<ButtonType> resposta = confirmacao.showAndWait();
        if (resposta.isEmpty() || resposta.get() != ButtonType.OK) {
            return;
        }

        try {
            adocaoService.registrarAdocao(animal, adotante, data);
            exibirAlerta(Alert.AlertType.INFORMATION, "Sucesso", "Adoção registrada com sucesso!");
            limparFormulario();
            carregarListas();
        } catch (DadosInvalidosException e) {
            exibirAlerta(Alert.AlertType.WARNING, "Dados Inválidos", e.getMessage());
        }

        // Verificar se os campos foram selecionados

        // Exibir uma mensagem de confirmação da adoção

    }

    private void carregarListas() {
        selAdotante.setItems(FXCollections.observableArrayList(adocaoService.listarAdotantes()));
        selAnimalDisponivel.setItems(FXCollections.observableArrayList(adocaoService.listarAnimaisDisponiveis()));
    }

    private void limparFormulario() {
        selAdotante.setValue(null);
        selAnimalDisponivel.setValue(null);
        dataAdocao.setValue(LocalDate.now());
    }

    private void exibirAlerta(Alert.AlertType tipo, String titulo, String mensagem) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}
