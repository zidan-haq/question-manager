package application;
//https://www.qconcursos.com/questoes-do-enem/provas/inep-2019-enem-exame-nacional-do-ensino-medio-primeiro-dia-e-segundo-dia/questoes

//https://www.qconcursos.com/questoes-de-concursos/questoes

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main  extends Application{
	public static Stage primaryStage;
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		primaryStage = stage;
		
		Parent parent = FXMLLoader.load(getClass().getResource("/gui/ViewPrincipal.fxml"));
		Scene scene = new Scene(parent);
		
		stage.setTitle("Questões");
		stage.setScene(scene);
		stage.setResizable(false);
		stage.show();
	}
}