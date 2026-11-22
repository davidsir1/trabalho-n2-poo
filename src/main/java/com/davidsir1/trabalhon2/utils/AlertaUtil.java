package com.davidsir1.trabalhon2.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public final class AlertaUtil {
    public static void exibirAlerta(Alert.AlertType tipo, String titulo, String mensagem) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    public static boolean exibirConfirmacao(String titulo, String cabecalho, String mensagem) {
        Alert confirmacao = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacao.setTitle(titulo);
        confirmacao.setHeaderText(cabecalho);
        confirmacao.setContentText(mensagem);

        Optional<ButtonType> resposta = confirmacao.showAndWait();
        return resposta.isPresent() && resposta.get() == ButtonType.OK;
    }
}
