package com.project.questionmanager.managers.html;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import com.project.questionmanager.entities.Question;
import com.project.questionmanager.gui.util.Alerts;
import com.project.questionmanager.managers.Configuration;

import javafx.scene.control.Alert.AlertType;

public class DatabaseToHTML {
	String html; //variável usada no método writeOnOnePage();

	
	public String toFolderQuestionStorage(Question question) {
		String path = new Configuration().getQuestionStorage();
		deleteAllFiles(path);
		write(new File(path + question.getId() + ".html"), question);
		return path;
	}
	public String toFolderQuestionStorageOnePage(List<Question> list) {
		String path = new Configuration().getQuestionStorage();
		deleteAllFiles(path);
		writeOnOnePage(new File(path + "all questions.html"), list);
		return path;
	}
	public String toFolderQuestionStorageManyPages(List<Question> list) {
		String path = new Configuration().getQuestionStorage();
		deleteAllFiles(path);
		list.forEach(x -> {
			write(new File(path + x.getId() + ".html"), x);
		});
		return path;
	}
	
	private void write(File file, Question question) {
		try(BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
			bw.write(question.toString());
			bw.newLine();
			bw.write(question.getBody());
		} catch (IOException e) {
			new Alerts().showAlert(AlertType.ERROR, "Erro", "IOException", e.getMessage(), false);
		}
	}
	
	private void writeOnOnePage(File file, List<Question> list) {
		html = "";
		
		list.forEach(question -> {
			html += "\n" + question.toString() + "\n" + question.getBody();
		});
		
		try(BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
			bw.write(html);			
		} catch (IOException e) {
			new Alerts().showAlert(AlertType.ERROR, "Erro", "IOException", e.getMessage(), false);
		}
	}
	
	private void deleteAllFiles(String path) {
		for(File x : new File(path).listFiles()) {
			x.delete();
		}
	}
}
