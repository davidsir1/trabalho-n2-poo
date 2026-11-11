package com.davidsir1.trabalhon2.controller.view;

import com.davidsir1.trabalhon2.HelloApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    @FXML
    private Button btnEntrar;

    @FXML
    private void entrar(){
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("view/MainView.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) btnEntrar.getScene().getWindow();

            Scene scene = new Scene(root, 1280, 720);
            stage.setScene(scene);
            stage.setTitle("Sistema de Adoção");
            stage.centerOnScreen();
            stage.show();

        } catch (IOException e) {
            System.err.println("Erro ao carregar a MainView.fxml: " + e.getMessage());
        }
    }
}
