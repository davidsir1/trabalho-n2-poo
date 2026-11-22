package com.davidsir1.trabalhon2.controller.view;

import com.davidsir1.trabalhon2.model.Adocao;
import com.davidsir1.trabalhon2.service.DashboardService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class DashboardController {
    @FXML private Label lblAnimaisDisponiveis;
    @FXML private Label lblAdocoesRealizadas;
    @FXML private Label lblAdotantesCadastrados;

    @FXML private TableView<Adocao> tabelaAdocoesRecentes;
    @FXML private TableColumn<Adocao, Integer> colId;
    @FXML private TableColumn<Adocao, String> colAnimal;
    @FXML private TableColumn<Adocao, String> colAdotante;
    @FXML private TableColumn<Adocao, String> colDataAdocao;

    private final DashboardService dashboardService = new DashboardService();

    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<Adocao, Integer>("id_adocao"));
        colAnimal.setCellValueFactory(new PropertyValueFactory<Adocao, String>("nomeAnimal"));
        colAdotante.setCellValueFactory(new PropertyValueFactory<Adocao, String>("nomeAdotante"));
        colDataAdocao.setCellValueFactory(new PropertyValueFactory<Adocao, String>("dataAdocaoFormatada"));

        atualizarDashboard();
    }

    public void atualizarDashboard() {
        lblAnimaisDisponiveis.setText(String.valueOf(dashboardService.getTotalAnimaisDisponiveis()));
        lblAdocoesRealizadas.setText(String.valueOf(dashboardService.getTotalAdocoesRealizadas()));
        lblAdotantesCadastrados.setText(String.valueOf(dashboardService.getTotalAdotantesCadastrados()));

        tabelaAdocoesRecentes.setItems(
                FXCollections.observableArrayList(dashboardService.listarAdocoesRecentes())
        );
    }

}
