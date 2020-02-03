package com.project.questionmanager.gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.project.questionmanager.entities.Question;
import com.project.questionmanager.gui.listeners.DataChangeListener;
import com.project.questionmanager.gui.util.ChangeView;
import com.project.questionmanager.managers.viewquestoes.QuestoesCadastradas;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

public class ViewQuestoesCadastradasController implements Initializable {
	QuestoesCadastradas questoesCadastradas = new QuestoesCadastradas();
	
	List<DataChangeListener> dataChangeListeners = new ArrayList<>();

	@FXML ListView<Question> listView;
	@FXML Label quantQuestoesCadastradas;
	
	public void setDataChangeListeners(List<DataChangeListener> dataChangeListeners) {
		this.dataChangeListeners = dataChangeListeners;
	}
	
	@FXML void onApagarAction() {
		questoesCadastradas.apagar(listView);
		questoesCadastradas.populateListView(listView);
		quantQuestoesCadastradas.setText(String.valueOf(questoesCadastradas.quantQuestoesCadastradas()));
	}
	
	@FXML void onEditarAction(Event event) {
		questoesCadastradas.editar(listView, event, dataChangeListeners);
		ChangeView.closeWindow(event);
	}

	@FXML void onVisualizarAction() {
		questoesCadastradas.visualizar(listView);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		questoesCadastradas.populateListView(listView);
		quantQuestoesCadastradas.setText(String.valueOf(questoesCadastradas.quantQuestoesCadastradas()));
	}

}
