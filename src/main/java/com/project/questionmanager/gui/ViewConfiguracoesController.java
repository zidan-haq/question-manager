package com.project.questionmanager.gui;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

import com.project.questionmanager.gui.util.ChangeView;
import com.project.questionmanager.managers.Configuration;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

public class ViewConfiguracoesController implements Initializable {
	ChangeView changeView = new ChangeView();
	Configuration configuration = new Configuration();
	
	@FXML TextField localDoPrograma;
	@FXML ComboBox<String> tipoDeSistema;
	@FXML RadioButton temaEscuro;
	@FXML RadioButton temaClaro;
	
	@FXML private void onAdicionarQuestoesAction() {
		changeView.changeToAdicionarQuestoes();
	}
	
	@FXML private void onQuestoesAction() {
		changeView.changeToQuestoes();
	}
	
	@FXML private void onSavarAlteracoesAction() {

		//por enquanto o tema não será utilizado
		String theme = temaEscuro.isSelected() ? temaEscuro.getText() : temaClaro.getText();
		
		configuration.saveEditions(localDoPrograma.getText(), tipoDeSistema.getSelectionModel().getSelectedItem(), "Escuro");
	}
	
	@FXML private void onDescartarAlteracoesAction() {
		localDoPrograma.setText(configuration.getProgramPath());
		tipoDeSistema.getSelectionModel().select(configuration.getSystemEnvironment());
		if(configuration.getTheme().equals("Escuro")) {
			temaEscuroSelecionado();
		} else {
			temaClaroSelecionado();
		}
	}
	
	@FXML private void temaEscuroSelecionado() {
		temaEscuro.setSelected(true);
		temaClaro.setSelected(false);
	}
	@FXML private void temaClaroSelecionado() {
		temaClaro.setSelected(true);
		temaEscuro.setSelected(false);
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		localDoPrograma.setText(configuration.getProgramPath());
		tipoDeSistema.getItems().addAll(Arrays.asList("linux", "windows"));
		tipoDeSistema.getSelectionModel().select(configuration.getSystemEnvironment());
		if(configuration.getTheme().equals("Escuro")) {
			temaEscuroSelecionado();
		} else {
			temaClaroSelecionado();
		}
	}
}
