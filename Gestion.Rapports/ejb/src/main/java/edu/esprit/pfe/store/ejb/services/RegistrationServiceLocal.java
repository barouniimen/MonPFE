package edu.esprit.pfe.store.ejb.services;

import javax.ejb.Local;

import edu.esprit.pfe.store.ejb.domain.Student;

@Local
public interface RegistrationServiceLocal {
	
	void createStudent(Student student, int optionId);
}
