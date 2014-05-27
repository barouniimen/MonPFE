package org.esprit.gestion.rapports.services.facades.Interfaces;

import java.util.List;

import javax.ejb.Local;

import org.esprit.gestion.rapports.persistence.Domain;
import org.esprit.gestion.rapports.persistence.Project;

@Local
public interface IDomainFacadeLocal {

	public String addDomain(Domain domain);
	
	public List<Domain> listProjectDomain(Project project);
	
	public List<Domain> allDomains();
}
