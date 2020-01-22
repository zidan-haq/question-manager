package com.project.questionmanager.gui.util;

import java.io.IOException;

import com.project.questionmanager.QuestionManagerApplication;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class ChangeView {
	public void changeScene(String absolutePath) {
		try {
			Parent parent = FXMLLoader.load(getClass().getResource(absolutePath));
			Scene scene = new Scene(parent);

			QuestionManagerApplication.primaryStage.setScene(scene);
			QuestionManagerApplication.primaryStage.setResizable(true);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Object openNewWindow(String title, String absolutePath) {
		Stage newWindow = new Stage();

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absolutePath));
			Parent parent = loader.load();

			newWindow.setTitle(title);
			newWindow.setScene(new Scene(parent));
			newWindow.setResizable(true);
			newWindow.show();
			
			return loader.getController();

		} catch (IOException e) {
			new Alerts().showAlert(AlertType.ERROR, "Erro", "IOException", "Não foi possível abrir a nova janela.\n" + e.getMessage(), true);
			return null;
		}
	}
}
