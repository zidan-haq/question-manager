package com.project.questionmanager.gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.project.questionmanager.gui.listeners.DataChangeListener;
import com.project.questionmanager.gui.listeners.ListeningControllers;
import com.project.questionmanager.gui.util.ChangeView;
import com.project.questionmanager.managers.html.DatabaseToHTML;
import com.project.questionmanager.managers.viewquestoes.ManagerViewQuestion;
import com.project.questionmanager.managers.viewquestoes.ReturnQuestion;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

public class ViewQuestoesController implements Initializable, DataChangeListener {
	ChangeView changeView = new ChangeView();
	DatabaseToHTML databaseToHtml = new DatabaseToHTML();	
	List<DataChangeListener> dataChangeListeners = new ArrayList<>();
	ManagerViewQuestion managerViewQuestion = new ManagerViewQuestion(dataChangeListeners);

	public void subscribeDataChangeListener(DataChangeListener listener) {
		dataChangeListeners.add(listener);
	}

	@FXML private TextField id;
	@FXML private TextField prova;
	@FXML private TextField ano;
	@FXML private TextField banca;
	@FXML private TextField orgao;
	@FXML private TextField materia;
	@FXML private TextField assunto;

	@FXML private ComboBox<ReturnQuestion> retornarQuestoescomo;

	@FXML private RadioButton todasQuestoesEncontradas;
	@FXML private RadioButton digiteOValor;
	@FXML private TextField valor;
	
	@FXML private Label quantQuestoesCadastradas;

	@FXML
	private void onAdicionarQuestoesAction() {
		changeView.changeToAdicionarQuestoes();
	}

	@FXML
	private void onConfiguracoesAction() {
		changeView.changeToConfiguracoes();
	}

	@FXML
	private void onVerQuestoesCadastradasAction() {
		ViewQuestoesCadastradasController x = (ViewQuestoesCadastradasController) changeView.openNewWindow("Questões Cadastradas", "/templates/ViewQuestoesCadastradas.fxml", true);
		x.setDataChangeListeners(dataChangeListeners);
	}
	
	@FXML private void todasQuestoesEncontradasSelecionado() {
		todasQuestoesEncontradas.setSelected(true);
		digiteOValor.setSelected(false);
		valor.setEditable(false);
		valor.setStyle("-fx-background-color: #263238");
	}
	@FXML private void digiteOValorSelecionado() {
		digiteOValor.setSelected(true);
		valor.setEditable(true);
		valor.setStyle("-fx-background-color: #dddddd");
		todasQuestoesEncontradas.setSelected(false);
	}

	@FXML
	private void onPesquisarAction() {		
		ReturnQuestion modo = retornarQuestoescomo.getSelectionModel().getSelectedItem();
		String limite = "";
		if(digiteOValor.isSelected()) {
			limite = valor.getText();
		}
		managerViewQuestion.pesquisar(id.getText(), prova.getText(), ano.getText(), banca.getText(), orgao.getText(), materia.getText(), assunto.getText(), limite, modo);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		subscribeDataChangeListener(ListeningControllers.viewAdicionarQuestoesController);
		subscribeDataChangeListener(this);
		quantQuestoesCadastradas.setText(String.valueOf(managerViewQuestion.quantQuestoesCadastradas()));
		retornarQuestoescomo.setItems(FXCollections.observableArrayList(ReturnQuestion.values()));
		retornarQuestoescomo.getSelectionModel().select(ReturnQuestion.ALL_QUESTIONS_ON_ONE_PAGE);
		todasQuestoesEncontradasSelecionado();
	}
	
	@Override
	public void onDataChanged() {
		onAdicionarQuestoesAction();
	}
}
