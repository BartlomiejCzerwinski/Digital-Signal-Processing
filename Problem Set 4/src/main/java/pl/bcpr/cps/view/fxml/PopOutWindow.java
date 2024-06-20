package pl.bcpr.cps.view.fxml;

import javafx.scene.control.Alert;

public class PopOutWindow {

    public static final void messageBox(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
    