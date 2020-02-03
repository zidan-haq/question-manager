package com.project.questionmanager.managers.tabPane;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.project.questionmanager.database.DatabaseOperations;
import com.project.questionmanager.entities.Question;
import com.project.questionmanager.managers.Configuration;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;

public class TabPaneManager implements ClipboardOwner {
	Configuration configuration = new Configuration();
	
	public TabPaneManager() {
	}

	public void createTabs(TabPane tabPane) {
		File[] arrayFile = new File(configuration.getQuestionStorage()).listFiles();
		List<Tab> listTab = new ArrayList<>();
		
		for(File x : arrayFile) {
			Question question = new Question(x);
			Tab tab = new Tab(question.getId());
			TabTextFields textFields = new TabTextFields(question);
			VBox vbox = new VBox();
			vbox.setAlignment(Pos.TOP_CENTER);
			vbox.setSpacing(10);
			vbox.getChildren().add(textFields.createHBox());

			WebView webView = new WebView();
			webView.getEngine().loadContent(question.getBody());
			vbox.getChildren().add(webView);
			
			HBox botoes = new HBox(createBtDelete(tabPane), createBtCopy(textFields), createBtSave(textFields));
			botoes.setSpacing(15);
			botoes.setAlignment(Pos.BOTTOM_RIGHT);
			vbox.getChildren().add(botoes);
			
			tab.setContent(vbox);
			
			listTab.add(tab);
		}
		
		tabPane.getTabs().addAll(listTab);
	}
	

	
	private Button createBtCopy(TabTextFields textFields) {
		Button button = new Button("Copiar");
		button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				copy(textFields.questionInThisTab());
			}	
		});
		return button;
	}
	private Button createBtSave(TabTextFields textFields) {
		Button button = new Button("Salvar");
		button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				DatabaseOperations db = new DatabaseOperations();
				db.save(textFields.questionInThisTab());
				db.closeConnection();
			}
		});
		return button;
	}
	private Button createBtDelete(TabPane tabPane) {
		Button button = new Button("Deletar");
		button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				tabPane.getTabs().remove(tabPane.getSelectionModel().getSelectedItem());				
			}
		});
		return button;
	}
	
	private void copy(Question question) {
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		StringSelection selection = new StringSelection(question.toString());
		clipboard.setContents(selection, this);
	}

	@Override
	public void lostOwnership(Clipboard arg0, Transferable arg1) {
	}
}
