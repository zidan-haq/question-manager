package com.project.questionmanager.gui;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.project.questionmanager.gui.listeners.DataChangeListener;
import com.project.questionmanager.gui.util.Alerts;
import com.project.questionmanager.managers.HTMLTreatment;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;

public class ViewNavegadorController implements Initializable, ClipboardOwner{
	WebView webView = new WebView();
	HTMLTreatment htmlTreatment = new HTMLTreatment();
	List<DataChangeListener> dataChangeListeners = new ArrayList<>();
	
	@FXML private VBox screen;
	@FXML private TextField urlSearch;
	
	public void subscribeDataChangeListener(DataChangeListener listener) {
		dataChangeListeners.add(listener);
	}
	
	@FXML private void onVoltarAction() {
		Platform.runLater(() -> {
	        webView.getEngine().executeScript("history.back()");
	    });
		urlSearch.setText(webView.getEngine().getLocation());
	}
	
	@FXML private void onAvancarAction() {
		Platform.runLater(() -> {
	        webView.getEngine().executeScript("history.forward()");
	    });
		urlSearch.setText(webView.getEngine().getLocation());
	}
	
	@FXML private void onRecarregarAction() {
		webView.getEngine().reload();
		urlSearch.setText(webView.getEngine().getLocation());
	}
	
	@FXML private void onPesquisarAction() {
		webView.getEngine().load(urlSearch.getText());
		urlSearch.setText(webView.getEngine().getLocation());
	}
	
	@FXML private void onKeyEnterAction(KeyEvent event) {
		if(event.getCode() == KeyCode.ENTER) {
			onPesquisarAction();
		}
	}
	
	@FXML private void onCopiarURLAction() {
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		StringSelection selection = new StringSelection(webView.getEngine().getLocation());
		clipboard.setContents(selection, this);
	}
	
	@FXML private void onCapturarQuestoesAction() throws MalformedURLException {
		boolean ok = htmlTreatment.getPage(webView);
		if(ok) {
			new Alerts().showAlert(AlertType.INFORMATION, "Informação", "Operação realizada com sucesso", "Total de questões capturadas: " + htmlTreatment.getQuantityOfQuestions(), true);
			notifyDataChangeListeners();
		} else {
			new Alerts().showAlert(AlertType.ERROR, "Erro", "Erro inesperado", "Verifique se você está no site do QConcursos.\nVerifique se a página atual contém questões.", false);
		}
	}
	
	private void notifyDataChangeListeners() {
		for(DataChangeListener listener : dataChangeListeners) {
			listener.onDataChaged();
		}
	}

	@FXML private void onGerarURLAction() {
		urlSearch.setText(webView.getEngine().getLocation());
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {		
		webView.getEngine().load("https://www.qconcursos.com/conta/entrar");
		screen.getChildren().add(1, webView);
		urlSearch.setText(webView.getEngine().getLocation());	
	}

	@Override
	public void lostOwnership(Clipboard arg0, Transferable arg1) {		
	}
}
