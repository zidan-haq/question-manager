package com.project.questionmanager.gui;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.stereotype.Component;

import com.project.questionmanager.gui.listeners.DataChangeListener;
import com.project.questionmanager.gui.listeners.ListeningControllers;
import com.project.questionmanager.gui.util.Alerts;
import com.project.questionmanager.gui.util.ChangeView;
import com.project.questionmanager.managers.html.HTMLTreatment;
import com.project.questionmanager.managers.tabPane.TabPaneManager;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import net.rgielen.fxweaver.core.FxmlView;

@Component
@FxmlView("/templates/ViewAdicionarQuestoes.fxml")
public class ViewAdicionarQuestoesController implements Initializable, DataChangeListener {
	ChangeView changeView = new ChangeView();
	HTMLTreatment htmlTreatment = new HTMLTreatment();
	ViewNavegadorController viewNavegadorController;

	@FXML private TabPane tabPane;
		
	@FXML private TextField urlQconcursos;
	@FXML private TextField prova;
	@FXML private TextField ano;
	@FXML private TextField banca;
	@FXML private TextField orgao;
	@FXML private TextField materia;
	@FXML private TextField assunto; 
	
	@FXML private void onNavegarAction() {
		viewNavegadorController = (ViewNavegadorController) changeView.openNewWindow("Navegador", "/templates/ViewNavegador.fxml", false);
		viewNavegadorController.subscribeDataChangeListener(this);
	}
	
	@FXML private void onQuestoesAction() {
		changeView.changeToQuestoes();
	}
	
	@FXML private void onConfiguracoesAction() {
		changeView.changeToConfiguracoes();
	}
	
	@FXML private void onCapturarQuestaoAction() {
		try {
			boolean ok = htmlTreatment.getPage(new URL(urlQconcursos.getText()));
			
			if(ok) {
				new Alerts().showAlert(AlertType.INFORMATION, "Informação", "Operação realizada com sucesso", "Total de questões capturadas: " + htmlTreatment.getQuantityOfQuestions(), true);
				createTabs();
			} else {
				new Alerts().showAlert(AlertType.ERROR, "Erro", "Erro inesperado", "Verifique se você está no site do QConcursos.\nVerifique se a página atual contém questões.", false);
			}
			
		} catch(MalformedURLException e) {
			new Alerts().showAlert(AlertType.ERROR, "Erro", "URL inválida", e.getMessage(), false);
		}
	}
	
	public void createTabs() {
		TabPaneManager tabPaneManager = new TabPaneManager();
		tabPane.getTabs().clear();
		tabPaneManager.createTabs(tabPane);
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {	
		ListeningControllers.viewAdicionarQuestoesController = this;
	}

	@Override
	public void onDataChanged() {
		createTabs();
	}
}
