package com.davidsir1.trabalhon2.controller.form;

import com.davidsir1.trabalhon2.exception.DadosInvalidosException;
import com.davidsir1.trabalhon2.model.Adotante;
import com.davidsir1.trabalhon2.service.AdotanteService;
import com.davidsir1.trabalhon2.utils.AlertaUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;

public class AdotanteFormController {
    @FXML private TextField inNomeAdotante;
    @FXML private TextField inTelefone;
    @FXML private TextField inEmailAdotante;
    @FXML private TextField inEndereco;

    @FXML private Button btnConfirmar;

    private final AdotanteService adotanteService = new AdotanteService();
    private Adotante adotanteEdicao;

    public void initialize() {}

    public void setAdotanteEdicao(Adotante adotanteEdicao) {
        this.adotanteEdicao = adotanteEdicao;

        if (adotanteEdicao != null) {
            inNomeAdotante.setText(adotanteEdicao.getNome_adotante());
            inTelefone.setText(adotanteEdicao.getTelefone());
            inEmailAdotante.setText(adotanteEdicao.getEmail_adotante());
            inEndereco.setText(adotanteEdicao.getEndereco());

            btnConfirmar.setText("Salvar Alteração");
        }
    }

    @FXML
    public void confirmar() {
        try {
            String nome = inNomeAdotante.getText();
            String telefone = inTelefone.getText();
            String email = inEmailAdotante.getText();
            String endereco = inEndereco.getText();

            if (adotanteEdicao == null) {
                Adotante novoAdotante = new Adotante(nome, telefone, email, endereco);
                adotanteService.cadastrar(novoAdotante);
                AlertaUtil.exibirAlerta(Alert.AlertType.INFORMATION, "Sucesso", "Adotante cadastrado com sucesso!");
            } else {
                adotanteEdicao.setNome_adotante(nome);
                adotanteEdicao.setEmail_adotante(email);
                adotanteEdicao.setTelefone(telefone);
                adotanteEdicao.setEndereco(endereco);
                adotanteService.atualizar(adotanteEdicao);
                AlertaUtil.exibirAlerta(Alert.AlertType.INFORMATION, "Sucesso", "Adotante atualizado com sucesso!");
            }

            cancelar();
        } catch (DadosInvalidosException e) {
            AlertaUtil.exibirAlerta(Alert.AlertType.WARNING, "Dados Inválidos", e.getMessage());
        } catch (SQLException e) {
            AlertaUtil.exibirAlerta(Alert.AlertType.ERROR, "Erro no Banco de Dados", "Ocorreu um erro ao cadastrar/atualizar o adotante.");
        }
    }

    @FXML
    public void cancelar() {
        Stage stage = (Stage) inNomeAdotante.getScene().getWindow();
        stage.close();
    }
}
