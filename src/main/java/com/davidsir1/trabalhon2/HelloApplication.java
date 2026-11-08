package com.davidsir1.trabalhon2;

import atlantafx.base.theme.PrimerLight;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("view/LoginView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);

        Application.setUserAgentStylesheet(new PrimerLight().getUserAgentStylesheet());

        String css = HelloApplication.class.getResource("css/style.css").toExternalForm();
        scene.getStylesheets().add(css);

        stage.setTitle("Sistema de Adoção");
        stage.setScene(scene);
        stage.show();
    }
}
