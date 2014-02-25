package edu.esprit.pfe.store.ejb.services;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import edu.esprit.pfe.store.ejb.domain.Option;
import edu.esprit.pfe.store.ejb.domain.Student;

@Stateless
public class RegistrationService implements RegistrationServiceRemote,
RegistrationServiceLocal {

	@PersistenceContext
	EntityManager em;

	public RegistrationService() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createStudent(Student student, int optionId) {

		Option option = em.find(Option.class, optionId);
		student.setOption(option);
		em.persist(student);

	}


}
