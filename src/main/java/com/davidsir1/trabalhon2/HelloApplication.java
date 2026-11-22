package com.davidsir1.trabalhon2;

import atlantafx.base.theme.PrimerLight;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("view/MainView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);

        Application.setUserAgentStylesheet(new PrimerLight().getUserAgentStylesheet());

        String css = HelloApplication.class.getResource("css/style.css").toExternalForm();
        scene.getStylesheets().add(css);

        InputStream iconStream = HelloApplication.class.getResourceAsStream("icons/Doghead48x48.png");
        if (iconStream != null) {
            stage.getIcons().add(new Image(iconStream));
        }

        stage.setTitle("Sistema de Adoção");
        stage.setScene(scene);
        stage.show();
    }
}
