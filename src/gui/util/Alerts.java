package gui.util;

import java.util.Optional;

import application.Main;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;

public class Alerts {
	public Optional<ButtonType> showAlert(AlertType alertType, String title, String header, String content,  boolean subordinate) {
		Alert alert = new Alert(alertType);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);
		alert.getDialogPane().getStylesheets().add(getClass().getResource("/application/Styles.css").toExternalForm());
		alert.getDialogPane().getStyleClass().add("dialog-pane");
		
		alert.initOwner(subordinate ? Main.primaryStage : null);
		alert.initModality(subordinate ? Modality.WINDOW_MODAL : null);
		
		return alert.showAndWait();
	}
}
