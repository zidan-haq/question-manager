package com.project.questionmanager.database;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.project.questionmanager.entities.Question;
import com.project.questionmanager.gui.util.Alerts;

import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

public class DatabaseOperations {
	EntityManagerFactory emf;
	EntityManager em;
	
	// Variável usada pelo método findDetailed
	boolean useAndCommercial = false;
	boolean where = true;
	
	private void openConnection() {
		emf = Persistence.createEntityManagerFactory("question-manager");
		em = emf.createEntityManager();
	}
	
	public void closeConnection() {
		emf.close();
		em.close();
	}
	
	public void save(Question question) {
		openConnection();
		if(em.find(Question.class, question.getId()) == null) {
			em.getTransaction().begin();
			em.persist(question);
			em.getTransaction().commit();
		} else {
			Optional<ButtonType> ok = new Alerts().showAlert(AlertType.CONFIRMATION, "Atenção", "Essa questão já está cadastrada no banco de dados", "Deseja atualizá-la?", false);
			if(ok.get() == ButtonType.OK) {
				closeConnection();
				remove(question.getId());
				closeConnection();
				openConnection();
				em.getTransaction().begin();
				em.persist(question);
				em.getTransaction().commit();
			}
		}
		new Alerts().showAlert(AlertType.INFORMATION, "Informação", "Operação realizada com sucesso", "A questão foi salva no banco de dados", false);
	}
	
	@SuppressWarnings("unchecked")
	public List<Question> findAll() {
		openConnection();
		return em.createQuery("FROM Question").getResultList();
	}
	
	public Question findByid(String id) {
		openConnection();
		Question question = em.find(Question.class, id);
		if(question == null) {
			new Alerts().showAlert(AlertType.INFORMATION, "Informação", "Questão não encontrada", "Esta questão não foi encontrada no banco de dados", false);
			return null;
		}
		return question;
	}

	@SuppressWarnings("unchecked")
	public List<Question> findDetailed(String id, String prova, String ano, String banca, String orgao, String materia, String assunto, String limite) {
		openConnection();
		
		String hql = "FROM Question ";

		hql = addHql(id, "id", hql);
		hql = addHql(prova, "prova", hql);
		hql = addHql(ano, "ano", hql);
		hql = addHql(banca, "banca", hql);
		hql = addHql(orgao, "orgao", hql);
		hql = addHql(materia, "materia", hql);
		hql = addHql(assunto, "assunto", hql);
				
		Query query = em.createQuery(hql);
		querySetParameter(query, "id", id);
		querySetParameter(query, "prova", prova);
		querySetParameter(query, "ano", ano);
		querySetParameter(query, "banca", banca);
		querySetParameter(query, "orgao", orgao);
		querySetParameter(query, "materia", materia);
		querySetParameter(query, "assunto", assunto);
		
		clearVariables();
		
		if(limite != null && limite.trim() != "") {
			query.setMaxResults(Integer.parseInt(limite));
		}
		
		return query.getResultList();
	}
	
	private String addHql(String parametro, String campo, String hql) {
		if(parametro != null && !parametro.trim().isEmpty()) {
			if(useAndCommercial) {
				hql += " AND " + campo + " = :" + campo;
			} else {
				if(where) {
					hql += "WHERE ";
					where = false;
				}
				hql += campo + " = :" + campo + " ";
				useAndCommercial = true;
			}
		}
		return hql;
	}
	private void querySetParameter(Query query, String campo, String parametro) {
		if(parametro != null && !parametro.trim().isEmpty()) {
			query.setParameter(campo, parametro);
		}
	}
	
	private void clearVariables() {
		useAndCommercial = false;
		where = true;
	}
	
	public void remove(String id) {
		openConnection();
		Question question = em.find(Question.class, id);
		em.getTransaction().begin();
		em.remove(question);
		em.getTransaction().commit();
	}
	
	/**
	 * Use o método find desta classe para encontrar o parâmetro question para ser repassado para este método
	 * pois para uma questão ser atualizada no banco de dados ela precisa está no mesmo contexto de persistência
	 * que o parâmetro passado aqui;
	 */
	public void update(Question question) {
		em.getTransaction().begin();
		em.merge(question);
		em.getTransaction().commit();
	}
}
