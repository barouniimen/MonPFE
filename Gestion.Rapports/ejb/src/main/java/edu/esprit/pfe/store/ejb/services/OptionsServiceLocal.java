package edu.esprit.pfe.store.ejb.services;

import java.util.List;

import javax.ejb.Local;

import edu.esprit.pfe.store.ejb.domain.Option;

@Local
public interface OptionsServiceLocal {
	
	List<Option> getAll();
	void createOption(Option option);

}
