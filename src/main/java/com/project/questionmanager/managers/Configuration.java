package com.project.questionmanager.managers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.project.questionmanager.gui.util.Alerts;

import javafx.scene.control.Alert.AlertType;

public class Configuration {
	private String systemEnvironment;
	private String programPath;
	private String theme;
	
	public Configuration() {		
		try(BufferedReader br = new BufferedReader(new FileReader(new File("conf.txt")))){
			programPath = br.readLine();
			systemEnvironment = br.readLine();
			theme = br.readLine();
		} catch (FileNotFoundException e) {
			new Alerts().showAlert(AlertType.WARNING, "Atenção", "Programa não configurado", "Reinicie o programa após o configurar", false);
		} catch (IOException e) {
			new Alerts().showAlert(AlertType.WARNING, "Atenção", "Programa não configurado", "Reinicie o programa após o configurar", false);
		}
	}
	
	public String getQuestionStorage() {
		return generatePath("question storage");
	}
	
	public String getTmp() {
		return generatePath("tmp");
	}	
	
	private String generatePath(String folderName) {
		String path = getProgramPath();
		if(getSystemEnvironment().equals("linux")) {
			path += path.substring(path.length() - 1).equals("/") ? folderName + "/" : "/" + folderName + "/";
		} else if(getSystemEnvironment().equals("windows")) {
			path += path.substring(path.length() - 1).equals("\\") ? folderName + "\\" : "\\" + folderName + "\\";
		} else {
			new Alerts().showAlert(AlertType.ERROR, "Erro inesperado", "Não foi possível identificar o seu ambiente de sistema", "Error: managers.Configuration.generatePath(folderName)", false);
		}
		return path;
	}
	
	public String getProgramPath() {
		File file = new File(".");
		if(programPath == null || programPath.isEmpty()) {
			try {
				programPath = file.getCanonicalPath();
			} catch (IOException e) {
				new Alerts().showAlert(AlertType.ERROR, "Erro", "Não foi possível encontrar o local onde o programa está instalado", e.getMessage(), false);
			}
		}		
		return programPath;
	}
	
	public String getSystemEnvironment() {
		if(systemEnvironment == null || systemEnvironment.isEmpty()) {
			if(System.getProperty("os.name").toLowerCase().contains("windows")) {
				systemEnvironment = "windows";
			} else {
				systemEnvironment = "linux";
			}
		}
		return systemEnvironment;
	}
	
	public String getTheme() {
		if(theme == null || theme.isEmpty()) {
			theme = "Escuro";
		}
		return theme;
	}
	
	public void saveEditions(String programPath, String systemEnvironment, String theme) {
		this.programPath = programPath;
		this.systemEnvironment = systemEnvironment;
		this.theme = theme;
		
		try(BufferedWriter bw = new BufferedWriter(new FileWriter(new File("conf.txt")))) {
			bw.write(programPath);
			bw.newLine();
			bw.write(systemEnvironment);
			bw.newLine();
			bw.write(theme);
			
			new Alerts().showAlert(AlertType.INFORMATION, "Informação", "As alterações foram salvas com sucesso", null, false);
		} catch (IOException e) {
			new Alerts().showAlert(AlertType.ERROR, "Erro", "Não foi possível salvar as alterações", e.getMessage(), false);
		}
	}
}
