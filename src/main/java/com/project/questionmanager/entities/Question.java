package com.project.questionmanager.entities;

import java.io.File;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

import com.project.questionmanager.managers.html.HTMLToQuestion;

@Entity
public class Question implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Transient
	HTMLToQuestion htmlToQuestion = new HTMLToQuestion();
	
	@Id
	private String id;
	private String prova;
	private String ano;
	private String banca;
	private String orgao;
	private String materia;
	private String assunto;
	
	@Column(nullable = true, columnDefinition = "TEXT")
	private String body;
	
	public Question() {
	}
	
	public Question(File question) {
		htmlToQuestion.readQuestion(question);
		id = htmlToQuestion.getId();
		prova = htmlToQuestion.getProva();
		ano = htmlToQuestion.getAno();
		banca = htmlToQuestion.getBanca();
		orgao = htmlToQuestion.getOrgao();
		materia = htmlToQuestion.getMateria();
		assunto = htmlToQuestion.getAssunto();
		body = htmlToQuestion.getBody();
	}
	
	public Question(String id, String prova, String ano, String banca, String orgao, String materia, String assunto, String body) {
		this.id = id;
		this.prova = prova;
		this.ano = ano;
		this.banca = banca;
		this.orgao = orgao;
		this.materia = materia;
		this.assunto = assunto;
		this.body = body;
	}

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

	@Override
	public String toString() {
		return String.format("Prova: %s / Ano: %s / Banca: %s / Órgão: %s / Matéria: %s / Assunto: %s", prova, ano, banca, orgao, materia, assunto);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Question other = (Question) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
