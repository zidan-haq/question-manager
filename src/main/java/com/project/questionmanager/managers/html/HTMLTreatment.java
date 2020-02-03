package com.project.questionmanager.managers.html;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.project.questionmanager.gui.util.Alerts;
import com.project.questionmanager.managers.Configuration;

import javafx.scene.control.Alert.AlertType;
import javafx.scene.web.WebView;

public class HTMLTreatment {
	Configuration configuration = new Configuration();
	private List<String> questionList = new ArrayList<>();
	private List<String> feedbackList = new ArrayList<>();
	private String generalFeedback;
	private int quantityOfQuestions;

	int auxFeedback;

	public List<String> getQuestionList() {
		return questionList;
	}

	public List<String> getFeedbackQuestion() {
		return feedbackList;
	}

	public int getQuantityOfQuestions() {
		return quantityOfQuestions;
	}

	public boolean getPage(URL url) {
		questionList.clear();
		feedbackList.clear();
		
		try (BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()))) {
			String inputLine;
			String htmlCode = "";

			while ((inputLine = br.readLine()) != null) {
				htmlCode += inputLine;
			}

			customizeCode(htmlCode);// Método para simplificar o HTML do site
			generateFeedbackList();// gera a lista com os gabaritos
			cleanQuestionList();// limpa a poluição das questões
			createHtmlArchive();

			return true;
		} catch (IOException e) {
			new Alerts().showAlert(AlertType.ERROR, "Erro", "IOException", e.getMessage(), false);
			return false;
		} catch(RuntimeException e) {
			return false;
		}
	}

	public boolean getPage(WebView webView) {
		questionList.clear();
		feedbackList.clear();
		
		try {
			String htmlCode = (String) webView.getEngine().executeScript("document.documentElement.innerHTML");
			
			customizeCode(htmlCode);// Método para simplificar o HTML do site
			generateFeedbackList();// gera a lista com os gabaritos
			cleanQuestionList();// limpa a poluição das questões
			createHtmlArchive();
			
			return true;
		} catch(RuntimeException e) {
			return false;
		}
	}
	
	// método para identar o código
		public void customizeCode(String line) {
			line = line.substring((line.indexOf("js-question-item") - 12), (line.indexOf("</fieldset>") + 11)); //linha que contém as questões
			
			while(line.contains("js-question-item")) {
				String question = "";
				
				question = line.substring((line.indexOf("js-question-item") - 12), (line.indexOf("q-question-belt") - 12));
				
				questionList.add(question);
				
				line = line.replace(question, "");
				if(line.contains("js-question-item")) {
					line = line.replace(line.substring(0, (line.indexOf("js-question-item") - 12)), "");
				} else {
					line = line.replace(line.substring(0, line.indexOf("<fieldset")), "");
					generalFeedback = line;
				}
			}
		}
		
		/*
		 * Transforma o generalFeedback em uma lista de feedbacks
		 */
		public void generateFeedbackList() {
			while (generalFeedback.contains("<div")) {
				String feedback = generalFeedback.substring(generalFeedback.indexOf("<div"),
						generalFeedback.indexOf("</div>") + 6);// retira do gabarito geral o gabarito específico

				int start = feedback.indexOf("</span>");
				;
				String character = "";
				while (!character.equals(">")) {
					start--;
					character = feedback.substring(start, start + 1);
				}

				generalFeedback = generalFeedback.replace(feedback, "");// excluí o gabarito já adicionado

				character = feedback.substring(start + 1, feedback.indexOf("</span>"));
				feedback = feedback.replace(character, "RESPOSTA:");

				feedbackList.add(feedback + "<br />");// adiciona o gabarito específico na lista de gabaritos

			}
		}
		
		// limpa a poluição restante que ainda continuam nas questões
		public void cleanQuestionList() {
			auxCleanQuestionList("<input", ">", 1);// retira todas as caixas de seleção input do HTML
			auxCleanQuestionList("<i class=", "</i>", 4);// retira todos os elementos <i> do HTML
			auxCleanQuestionList("<a ", ">", 1);// retira todas as referências <href> do HTML

			for (int question = 0; question < questionList.size(); question++) {
				String x = questionList.get(question);
				
				// retira o código que contém o botão resposta
				questionList.set(question, x.substring(0, x.indexOf("</ul>") + 5));
			}
		}
		
		private void auxCleanQuestionList(String beginIndex, String endIndex, int lenght) {
			for (int question = 0; question < questionList.size(); question++) {
				String x = questionList.get(question);

				while (x.contains(beginIndex)) {
					int start = x.indexOf(beginIndex);
					String character = "";

					while (!character.equals(endIndex)) {
						start++;
						character = x.substring(start, start + lenght);
					}
					questionList.set(question, x.replace(x.substring(x.indexOf(beginIndex), start + lenght), ""));
					x = x.replace(x.substring(x.indexOf(beginIndex), start + lenght), "");
				}
			}
		}
		
	// esse método criar o arquivo .html
	public void createHtmlArchive() {
		auxFeedback = 0;
		quantityOfQuestions = 0;
		
		deleteArchives();

		// Grava pagina no arquivo
		questionList.forEach(x -> {
			try(BufferedWriter bw = new BufferedWriter(new FileWriter(new File(nameOfQuestions(quantityOfQuestions))))) {
				bw.write("<meta charset='UTF-8'>\n");// define que o arquivo utiliza caractéres do tipo UTF8
				bw.write(x);
				bw.newLine();
				bw.write(feedbackList.get(auxFeedback));
				bw.newLine();
				auxFeedback++;
				quantityOfQuestions++;
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}

	//apaga todos as questoes que estiverem presentes na pasta storageQuestions
	private void deleteArchives() {
		File file = new File(configuration.getQuestionStorage());
		for(File x : file.listFiles()) {
			x.delete();
		}
	}
	
	private String nameOfQuestions(int number) {
		String name =  configuration.getQuestionStorage() + number + ".html";
		return name;
	}
}
