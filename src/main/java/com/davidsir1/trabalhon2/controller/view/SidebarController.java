package com.davidsir1.trabalhon2.controller.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

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

    private void trocarSubTela(String fxml) {
        if (mainController != null) {
            mainController.carregarTela(fxml);
        }
    }
}
