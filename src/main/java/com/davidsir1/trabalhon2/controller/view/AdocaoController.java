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
import java.util.List;
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
        // Verificar se os campos foram selecionados
        // Exibir uma mensagem de confirmação da
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
    }

    @FXML
    public void atualizarResumoAdotante() {
        try {
            Adotante adotante = selAdotante.getValue() != null ? selAdotante.getValue() : null;
            if (adotante != null) {
                adotanteSelecionado.setText(adotante.getNome_adotante());
            } else {
                adotanteSelecionado.setText("Nenhum selecionado");
            }
        } catch (ClassCastException e) {
            System.err.println(e.getMessage());
            adotanteSelecionado.setText("Nenhum selecionado");
        }
    }

    @FXML
    public void atualizarResumoAnimal() {
        try {
            Animal animal = selAnimalDisponivel.getValue() != null ? selAnimalDisponivel.getValue() : null;
            if (animal != null) {
                animalSelecionado.setText(animal.getNome_animal());
            } else {
                animalSelecionado.setText("Nenhum selecionado");
            }
        } catch (ClassCastException e) {
            System.err.println(e.getMessage());
            animalSelecionado.setText("Nenhum selecionado");
        }
    }

    private void carregarListas() {
        List<Adotante> adotantes = adocaoService.listarAdotantes();
        if (!adotantes.isEmpty())
            selAdotante.setItems(FXCollections.observableArrayList(adotantes));
        else
            selAdotante.setDisable(true);

        List<Animal> animaisDisponiveis = adocaoService.listarAnimaisDisponiveis();
        if (!animaisDisponiveis.isEmpty())
            selAnimalDisponivel.setItems(FXCollections.observableArrayList(animaisDisponiveis));
        else
            selAnimalDisponivel.setDisable(true);
    }

    private void limparFormulario() {
        selAdotante.setValue(null);
        selAnimalDisponivel.setValue(null);
        dataAdocao.setValue(LocalDate.now());
        adotanteSelecionado.setText("Nenhum selecionado");
        animalSelecionado.setText("Nenhum selecionado");
    }

    private void exibirAlerta(Alert.AlertType tipo, String titulo, String mensagem) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}
