package com.davidsir1.trabalhon2.controller.view;

import com.davidsir1.trabalhon2.HelloApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.util.Objects;

public class MainController {
    @FXML
    private StackPane conteudoArea;

    @FXML
    private SidebarController sidebarComponenteController;

    @FXML
    public void initialize() {
        if (sidebarComponenteController != null) {
            sidebarComponenteController.setMainController(this);
        }

        carregarTela("DashboardView.fxml");
    }

    public void carregarTela(String nomeFxml) {
        try {
            Parent fxml = FXMLLoader.load(Objects.requireNonNull(HelloApplication.class.getResource("view/" + nomeFxml)));
            String css = HelloApplication.class.getResource("css/style.css").toExternalForm();
            conteudoArea.getChildren().clear();
            conteudoArea.getStylesheets().add(css);
            conteudoArea.getChildren().add(fxml);
        } catch (IOException e) {
            System.err.println("Falha ao abrir o arquivo " + nomeFxml + ": " + e.getMessage());
        } catch (NullPointerException e) {
            System.err.println("Houve falha ao abrir a tela " + nomeFxml + ": " + e.getMessage());
        }
    }
}
