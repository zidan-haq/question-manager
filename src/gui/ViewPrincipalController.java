package gui;

import java.net.URL;
import java.util.ResourceBundle;

import gui.util.ChangeView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class ViewPrincipalController implements Initializable{
	ChangeView changeView = new ChangeView();
	
	@FXML private void onAdicionarQuestoesAction() {
		changeView.changeScene("/gui/ViewAdicionarQuestoes.fxml");
	}
	
	@FXML private void onVerQuestoesAction() {
		
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {		
	}

}
