package org.esprit.gestion.rapports.services.facades.Interfaces;

import java.util.List;

import javax.ejb.Remote;

import org.esprit.gestion.rapports.persistence.Domain;
import org.esprit.gestion.rapports.persistence.Project;

@Remote
public interface IDomainFacadeRemote {

	public String addDomain(Domain domain);

	public List<Domain> listProjectDomain(Project project);
}
