package com.project.questionmanager;

import java.io.IOException;

import com.project.questionmanager.gui.util.Alerts;
import com.project.questionmanager.gui.util.ChangeView;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
//https://www.qconcursos.com/questoes-do-enem/provas/inep-2019-enem-exame-nacional-do-ensino-medio-primeiro-dia-e-segundo-dia/questoes

//https://www.qconcursos.com/questoes-de-concursos/questoes

public class QuestionManagerApplication extends Application {
	public static Stage primaryStage;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) {
		primaryStage = stage;

		try {			
			Parent parent = FXMLLoader.load(getClass().getResource("/templates/ViewAdicionarQuestoes.fxml"));

			Scene scene = new Scene(parent);

			ChangeView.sceneAdicionarQuestoes = scene;

			stage.setTitle("Question manager");
			stage.setScene(scene);
			stage.setResizable(false);
			stage.show();
		} catch (IOException e) {
			new Alerts().showAlert(AlertType.ERROR, "Erro", "IOException", e.getMessage(), true);
		}
	}
}
