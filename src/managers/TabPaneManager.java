package managers;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import entity.Question;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;

public class TabPaneManager {
	Configuration configuration = new Configuration();

	public List<Tab> createTabs() {
		File[] arrayFile = new File(configuration.getQuestionStorage()).listFiles();
		List<Tab> listTab = new ArrayList<>();
		
		for(File x : arrayFile) {
			Question question = new Question(x);

			Tab tab = new Tab(question.getId());
			
			VBox vbox = new VBox();
			vbox.setAlignment(Pos.TOP_CENTER);
			vbox.setSpacing(10);
			vbox.getChildren().add(createHBox(question));

			WebView webView = new WebView();
			webView.getEngine().loadContent(question.getBody());
			vbox.getChildren().add(webView);
			
			tab.setContent(vbox);
			
			listTab.add(tab);
		}
		
		return listTab;
	}
	
	private HBox createHBox(Question question) {
		HBox hbox = new HBox();
		
		hbox.getStylesheets().add(getClass().getResource("/application/Styles.css").toExternalForm());
		
		Map<String, TextField> map = createTextFields(question);
		map.forEach((x, y) -> {
			y.getStyleClass().add("text-field");
			hbox.getChildren().add(createVBox(x, y));	
		});
		
		hbox.setSpacing(10);
		return hbox;
	}
	
	private VBox createVBox(String name, TextField textField) {
		Label label = new Label(name);
		label.getStyleClass().add("label");
		VBox vbox = new VBox(label, textField);
		vbox.setAlignment(Pos.CENTER);
		
		return vbox;
	}
	
	private Map<String, TextField> createTextFields(Question question) {
		TextField prova = new TextField();
		prova.setPrefWidth(195);
		prova.setText(question.getProva());
		
		TextField ano = new TextField();
		ano.setPrefWidth(55);
		ano.setText(question.getAno());
		
		TextField banca = new TextField();
		banca.setPrefWidth(126);
		banca.setText(question.getBanca());
		
		TextField orgao = new TextField();
		orgao.setPrefWidth(195);
		orgao.setText(question.getOrgao());
		
		TextField materia = new TextField();
		materia.setPrefWidth(195);
		materia.setText(question.getMateria());

		TextField assunto = new TextField();
		assunto.setPrefWidth(195);
		assunto.setText(question.getAssunto());
		
		Map<String, TextField> map = new LinkedHashMap<String, TextField>();
		map.put("Prova", prova);
		map.put("Ano", ano);
		map.put("Banca", banca);
		map.put("Orgao", orgao);
		map.put("Materia", materia);
		map.put("Assunto", assunto);

		return map;
	}
}
