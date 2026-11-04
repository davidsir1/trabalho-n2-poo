package com.davidsir1.trabalhon2.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class MainController {
    @FXML
    private StackPane conteudoArea;

    public void carregarTela(String nomeFxml) {
        try {
            Parent fxml = FXMLLoader.load(getClass().getResource("/view/" + nomeFxml));
            conteudoArea.getChildren().clear();
            conteudoArea.getChildren().add(fxml);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
