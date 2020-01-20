package managers;

import gui.util.Alerts;
import javafx.scene.control.Alert.AlertType;

public class Configuration {
	private String systemEnvironment = "unix";
	
	private String programPath = "/home/manjaro/Área de trabalho/question-manager/";
	
	public String getQuestionStorage() {
		return generatePath("questionStorage");
	}
	
	private String generatePath(String folderName) {
		String path = programPath;
		if(systemEnvironment == "unix") {
			path += path.substring(path.length() - 1) == "/" ? folderName + "/" : "/" + folderName + "/";
		} else if(systemEnvironment == "windows") {
			path += path.substring(path.length() - 1) == "\\" ? folderName + "\\" : "\\" + folderName + "\\";
		} else {
			new Alerts().showAlert(AlertType.ERROR, "Erro inesperado", "Error: managers.Configuration.generatePath(folderName)", "Não foi possível identificar o seu ambiente de sistema", false);
		}
		
		return path;
	}
}
