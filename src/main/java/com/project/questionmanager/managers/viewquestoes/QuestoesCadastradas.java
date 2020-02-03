package com.project.questionmanager.managers.viewquestoes;

import java.util.List;

import com.project.questionmanager.database.DatabaseOperations;
import com.project.questionmanager.entities.Question;
import com.project.questionmanager.gui.listeners.DataChangeListener;
import com.project.questionmanager.managers.DesktopManager;
import com.project.questionmanager.managers.html.DatabaseToHTML;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.scene.control.ListView;

public class QuestoesCadastradas {
	DatabaseOperations database = new DatabaseOperations();
	DatabaseToHTML databaseToHtml = new DatabaseToHTML();

	public void populateListView(ListView<Question> listView) {
		ObservableList<Question> obsList = FXCollections.observableArrayList(database.findAll());
		listView.setItems(obsList);
		database.closeConnection();
	}
	
	public void apagar(ListView<Question> listView) {
		Question question = listView.getSelectionModel().getSelectedItem();
		if(question != null) {
			database.remove(question.getId());
			database.closeConnection();
		}
	}
	
	public void editar(ListView<Question> listView, Event event, List<DataChangeListener> dataChangeListeners) {
		Question question = listView.getSelectionModel().getSelectedItem();
		if(question != null) {
			databaseToHtml.toFolderQuestionStorage(question);
			dataChangeListeners.forEach(x -> x.onDataChanged());
		}
	}
	
	public void visualizar(ListView<Question> listView) {
		Question question = listView.getSelectionModel().getSelectedItem();
		if(question != null) {
			String path = databaseToHtml.toFolderQuestionStorage(question);
			DesktopManager.openInBrowser(path);
		}
	}
	
	public int quantQuestoesCadastradas() {
		int quantity = database.findAll().size();
		database.closeConnection();
		return quantity;
	}
}
