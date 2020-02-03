package com.project.questionmanager.gui.util;

import java.io.IOException;

import com.project.questionmanager.QuestionManagerApplication;

import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ChangeView {
	public static Scene sceneAdicionarQuestoes;
	public static Scene sceneQuestoes;
	public static Scene sceneConfiguracoes;
	
	public void changeToAdicionarQuestoes() {
		if(sceneAdicionarQuestoes == null) {
			sceneAdicionarQuestoes = changeScene("/templates/ViewAdicionarQuestoes.fxml");
		} else {
			double width = QuestionManagerApplication.primaryStage.getWidth();
			double height = QuestionManagerApplication.primaryStage.getHeight();
			changeScene(sceneAdicionarQuestoes);
			sceneAdicionarQuestoes.getRoot().resize(width, height);;
		}
	}
	
	public void changeToQuestoes() {
		if(sceneQuestoes == null) {
			double width = QuestionManagerApplication.primaryStage.getWidth();
			double height = QuestionManagerApplication.primaryStage.getHeight();
			sceneQuestoes = changeScene("/templates/ViewQuestoes.fxml");
			sceneQuestoes.getRoot().resize(width, height);;
		} else {
			double width = QuestionManagerApplication.primaryStage.getWidth();
			double height = QuestionManagerApplication.primaryStage.getHeight();
			changeScene(sceneQuestoes);
			sceneQuestoes.getRoot().resize(width, height);;
		}
	}
	
	public void changeToConfiguracoes() {
		if(sceneConfiguracoes == null) {
			double width = QuestionManagerApplication.primaryStage.getWidth();
			double height = QuestionManagerApplication.primaryStage.getHeight();
			sceneConfiguracoes = changeScene("/templates/ViewConfiguracoes.fxml");
			sceneConfiguracoes.getRoot().resize(width, height);;
		} else {
			double width = QuestionManagerApplication.primaryStage.getWidth();
			double height = QuestionManagerApplication.primaryStage.getHeight();
			changeScene(sceneConfiguracoes);
			sceneConfiguracoes.getRoot().resize(width, height);;
		}
	}
	
	private void changeScene(Scene scene) {
		QuestionManagerApplication.primaryStage.setScene(scene);		
	}

	public Scene changeScene(String absolutePath) {
		try {
			Parent parent = FXMLLoader.load(getClass().getResource(absolutePath));
			Scene scene = new Scene(parent);

			QuestionManagerApplication.primaryStage.setScene(scene);
			QuestionManagerApplication.primaryStage.setResizable(true);

			return scene;
		} catch (IOException e) {
			new Alerts().showAlert(AlertType.ERROR, "Erro", "IOException", "Não foi possível mudar a scene\n" + e.getMessage(), false);
			return null;
		}
	}

	public Object openNewWindow(String title, String absolutePath, boolean subordinate) {
		Stage newWindow = new Stage();

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absolutePath));
			Parent parent = loader.load();

			newWindow.setTitle(title);
			newWindow.setScene(new Scene(parent));
			newWindow.setResizable(true);

			newWindow.initOwner(subordinate ? QuestionManagerApplication.primaryStage : null);
			newWindow.initModality(subordinate ? Modality.WINDOW_MODAL : null);
			
			newWindow.show();
			
			return loader.getController();

		} catch (IOException e) {
			new Alerts().showAlert(AlertType.ERROR, "Erro", "IOException", "Não foi possível abrir a nova janela.\n" + e.getMessage(), true);
			return null;
		}
	}
	
	public static void closeWindow(Event event) {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.close();
	}
}
