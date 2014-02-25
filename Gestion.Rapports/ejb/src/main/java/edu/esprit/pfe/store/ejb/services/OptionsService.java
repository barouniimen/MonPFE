package edu.esprit.pfe.store.ejb.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import edu.esprit.pfe.store.ejb.domain.Option;

@Stateless
public class OptionsService implements OptionsServiceRemote,
		OptionsServiceLocal {

	@PersistenceContext
	EntityManager em;

	public OptionsService() {
	}

	@Override
	public List<Option> getAll() {
		return em.createQuery("select o from Option o").getResultList();
	}

	@Override
	public void createOption(Option option) {
		em.persist(option);
	}

}
