package com.project.questionmanager.managers.tabPane;

import java.util.LinkedHashMap;
import java.util.Map;

import com.project.questionmanager.entities.Question;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class TabTextFields {
	private Question question;

	private TextField prova;
	private TextField ano;
	private TextField banca;
	private TextField orgao;
	private TextField materia;
	private TextField assunto;

	public TabTextFields(Question question) {
		this.question = question;
	}

	public TextField getProva() {
		return prova;
	}

	public TextField getAno() {
		return ano;
	}

	public TextField getBanca() {
		return banca;
	}

	public TextField getOrgao() {
		return orgao;
	}

	public TextField getMateria() {
		return materia;
	}

	public TextField getAssunto() {
		return assunto;
	}

	public HBox createHBox() {
		HBox hbox = new HBox();

		hbox.getStylesheets().add(getClass().getResource("/templates/styles.css").toExternalForm());

		Map<String, TextField> map = createTextFields();
		map.forEach((x, y) -> {
			y.getStyleClass().add("text-field");
			hbox.getChildren().add(createVBox(x, y));
		});

		hbox.setSpacing(10);
		hbox.setAlignment(Pos.CENTER);
		return hbox;
	}

	private VBox createVBox(String name, TextField textField) {
		Label label = new Label(name);
		label.getStyleClass().add("label");
		VBox vbox = new VBox(label, textField);
		vbox.setAlignment(Pos.CENTER);

		return vbox;
	}

	private Map<String, TextField> createTextFields() {
		prova = generateTextField(195, question.getProva());
		ano = generateTextField(55, question.getAno());
		banca = generateTextField(126, question.getBanca());
		orgao = generateTextField(195, question.getOrgao());
		materia = generateTextField(195, question.getMateria());
		assunto = generateTextField(195, question.getAssunto());

		Map<String, TextField> map = new LinkedHashMap<String, TextField>();
		map.put("Prova", prova);
		map.put("Ano", ano);
		map.put("Banca", banca);
		map.put("Orgao", orgao);
		map.put("Materia", materia);
		map.put("Assunto", assunto);

		return map;
	}

	private TextField generateTextField(int tamanho, String content) {
		TextField textField = new TextField();
		textField.setPrefWidth(195);
		textField.setText(content);
		return textField;
	}

	public Question questionInThisTab() {
		return new Question(question.getId(), prova.getText(), ano.getText(), banca.getText(), orgao.getText(),
				materia.getText(), assunto.getText(), question.getBody());
	}
}
