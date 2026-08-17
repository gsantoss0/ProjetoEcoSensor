package com.example.proj_escosensor;

import javafx.scene.control.Alert;

public class Utils {

    private Utils() {}

    public static void mostrarAlerta(String MensagemTitulo, String Mensagem){

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(MensagemTitulo);
        alert.setHeaderText(Mensagem);
        alert.showAndWait();
    }
}
