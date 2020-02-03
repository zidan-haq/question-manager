package com.project.questionmanager.managers.html;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import com.project.questionmanager.gui.util.Alerts;

import javafx.scene.control.Alert.AlertType;

public class HTMLToQuestion {
	private String id;
	private String prova;
	private String ano;
	private String banca;
	private String orgao;
	private String materia;
	private String assunto;
	private String body;
	
	private String html;

	public String getId() {
		return id;
	}

	public String getProva() {
		return prova;
	}

	public String getAno() {
		return ano;
	}

	public String getBanca() {
		return banca;
	}

	public String getOrgao() {
		return orgao;
	}

	public String getMateria() {
		return materia;
	}

	public String getAssunto() {
		return assunto;
	}

	public String getBody() {
		return body;
	}

	public void readQuestion(File question) {
		try(BufferedReader br = new BufferedReader(new FileReader(question))){
			String aux = br.readLine();
			while(aux != null) {
				html += aux;
				aux = br.readLine();
			}
			populateAttributes(question);
		} catch(IOException e) {
			new Alerts().showAlert(AlertType.ERROR, "Erro", "IOException", e.getMessage(), false);
		}
	}
	
	private void populateAttributes(File question) {
		if(question.getName().toUpperCase().charAt(0) == 'Q') {
			treatDatabaseHtml(question);
		} else {
			treatSiteHtml();
		}
	}
	
	private void treatDatabaseHtml(File question) {
		body = html.substring(html.indexOf("q-question-body") - 12);

		id = question.getName().replace(".html", "");
		prova = find(html, "Prova:", 6, "/", 1).strip();
		ano = find(html, "/ Ano:", 6, "/", 1).strip();
		banca = find(html, "/ Banca:", 8, "/", 1).strip();
		orgao = find(html, "/ Órgão:", 8, "/", 1).strip();
		materia = find(html, "/ Matéria:", 10, "/", 1).strip();
		assunto = find(html, "/ Assunto:", 10, "<", 1).strip();
	}
	
	private void treatSiteHtml() {
		body = "<meta charset=\"utf-8\">" + html.substring(html.indexOf("q-question-body") - 12);
		String header = html.substring(0, html.indexOf("q-question-body") - 12);
		
		id = find(header, "q-id", 6, "<", 1).trim();// procura o id da questão e elimina os espaços em branco
		prova = find(header, "<strong>Prova: </strong>", 24, "<", 1).strip();// procura a prova e elimina espaçoes em braco no inicio e final da string
		ano = find(header, "<strong>Ano: </strong>", 22, "<", 1).strip();
		banca = find(header, "<strong>Banca: </strong>", 24, "<", 1).strip();
		orgao = find(header, "<strong>Órgão: </strong>", 24, "<", 1).strip();
		materia = find(header, "q-question-breadcrumb", 23, "<", 1).strip();
		assunto = findAssunto(header, "q-arrow-separator", 19, "q-question-info", 15);
	}
	
	/**
	 * @param text - é o texto que será tratado
	 * @param beginIndex - valor antes da string procurada
	 * @param beginLenght - tamanho da string acima mais as poluições que houverem ao lado dela, a sequencia definida não será retornada pelo método
	 * @param endIndex - valor depois do fim da string procurada
	 * @param endLenght - tamanho da string acima
	 * @return - retorna a string que fica entre o beginIndex (+ beginLenght) e o endIndex
	 */
	private String find(String text, String beginIndex, int beginLenght,  String endIndex, int endLenght) {
			int start = text.indexOf(beginIndex) + beginLenght;
			
			if(start == -1 || text.indexOf(endIndex) == -1) {
				return "";
			}
			
			String character = "";

			while (!character.equals(endIndex)) {
				start++;
				character = text.substring(start, start + endLenght);
			}
			
			return text.substring(text.indexOf(beginIndex) + beginLenght, start);
	}
	
	//A diferença deste método para o anterior está no fato deste, antes de retornar a string, limpar todos os campos que estão entre "<" e ">"
	private String findAssunto(String text, String beginIndex, int beginLenght,  String endIndex, int endLenght) {
		int start = text.indexOf(beginIndex) + beginLenght;
		
		if(start == -1 || text.indexOf(endIndex) == -1) {
			return "";
		}
		
		String character = "";

		while (!character.equals(endIndex)) {
			start++;
			character = text.substring(start, start + endLenght);
		}
		
		String pollutedString = text.substring(text.indexOf(beginIndex) + beginLenght, start) + ">";
		String cleanString = "";
		while(pollutedString.contains("<")) {
			cleanString = find(pollutedString, "<", 1, ">", 1);
			pollutedString = pollutedString.replace("<" + cleanString + ">", "");
		}
		return pollutedString.replace(" ,    ", ", ").strip();
	}
	
}
