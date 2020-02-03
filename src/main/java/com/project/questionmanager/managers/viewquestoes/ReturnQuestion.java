package com.project.questionmanager.managers.viewquestoes;

public enum ReturnQuestion {
	ALL_QUESTIONS_ON_ONE_PAGE("Todas as questões em apenas uma página"),
	MULTIPLE_PAGES_WITH_ONE_QUESTION("Várias páginas com uma questão cada"),
	ON_EDIT_MENU("Enviar questões encontradas para edição");

	private String description;
	ReturnQuestion(String description) {
		this.description = description;
	}
	public String getDescription() {
		return description;
	}
	
	@Override
	public String toString() {
		return description;
	}
}
