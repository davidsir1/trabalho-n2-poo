package com.davidsir1.trabalhon2.controller;

import com.davidsir1.trabalhon2.HelloApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class SidebarController {
    @FXML private Button btnDashboard;
    @FXML private Button btnAnimais;
    @FXML private Button btnAdotantes;
    @FXML private Button btnBaias;
    @FXML private Button btnEspecieRaca;
    @FXML private Button btnUsuarios;
    @FXML private Button btnSair;

    private MainController mainController;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @FXML
    public void trocarParaDashboardView() {
        trocarSubTela("DashboardView.fxml");
    }

    @FXML
    public void trocarParaAnimalView() {
        trocarSubTela("AnimalView.fxml");
    }

    @FXML
    public void trocarParaAdotanteView() {
        trocarSubTela("AdotanteView.fxml");
    }

    @FXML
    public void trocarParaBaiaView() {
        trocarSubTela("BaiaView.fxml");
    }

    @FXML
    public void trocarParaEspecieRacaView() {
        trocarSubTela("EspecieRacaView.fxml");
    }

    @FXML
    public void trocarParaAdocaoView() {
        trocarSubTela("AdocaoView.fxml");
    }

    @FXML
    private void realizarLogout () {
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("view/LoginView.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) btnSair.getScene().getWindow();
            Scene scene = new Scene(root);

            stage.setScene(scene);
            stage.setTitle("Hello!");
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            System.err.println("Erro ao retornar para a tela de login: " + e.getMessage());
        } catch (NullPointerException | IllegalStateException e) {
            System.err.println("Houve falha ao abrir a tela LoginView.fxml: " + e.getMessage());
        }
    }

    private void trocarSubTela(String fxml) {
        if (mainController != null) {
            mainController.carregarTela(fxml);
        }
    }
}
