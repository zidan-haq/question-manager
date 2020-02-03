package com.project.questionmanager.managers.viewquestoes;

import java.io.File;
import java.util.List;

import com.project.questionmanager.database.DatabaseOperations;
import com.project.questionmanager.entities.Question;
import com.project.questionmanager.gui.listeners.DataChangeListener;
import com.project.questionmanager.gui.util.Alerts;
import com.project.questionmanager.managers.Configuration;
import com.project.questionmanager.managers.DesktopManager;
import com.project.questionmanager.managers.html.DatabaseToHTML;

import javafx.scene.control.Alert.AlertType;

public class ManagerViewQuestion {
	DatabaseOperations database = new DatabaseOperations();
	DatabaseToHTML databaseToHTML = new DatabaseToHTML();
	Configuration configuration = new Configuration();

	List<DataChangeListener> dataChangeListeners;
	
	public ManagerViewQuestion(List<DataChangeListener> dataChangeListeners) {
		this.dataChangeListeners = dataChangeListeners;
	}
	
	public void pesquisar(String id, String prova, String ano, String banca, String orgao, String materia, String assunto, String limite, ReturnQuestion modo) {
		List<Question> listQuestions = database.findDetailed(id, prova, ano, banca, orgao, materia, assunto, limite);	
		database.closeConnection();
		
		if(listQuestions.isEmpty() || listQuestions == null) {
			new Alerts().showAlert(AlertType.INFORMATION, "Informação", "Nenhuma questão encontrada", "Tente pesquisar novamente com menos especificações", false);
		} else if(modo.equals(ReturnQuestion.ALL_QUESTIONS_ON_ONE_PAGE)) {
			databaseToHTML.toFolderQuestionStorageOnePage(listQuestions);
		} else if(modo.equals(ReturnQuestion.MULTIPLE_PAGES_WITH_ONE_QUESTION)) {
			databaseToHTML.toFolderQuestionStorageManyPages(listQuestions);
			DesktopManager.openManyInBrowser(new File(configuration.getQuestionStorage()).listFiles());
		} else if(modo.equals(ReturnQuestion.ON_EDIT_MENU)) {
			databaseToHTML.toFolderQuestionStorageManyPages(listQuestions);
			dataChangeListeners.forEach(x -> x.onDataChanged());
		}
	}
	
	public int quantQuestoesCadastradas() {
		int quantity = database.findAll().size();
		database.closeConnection();
		return quantity;
	}
}
