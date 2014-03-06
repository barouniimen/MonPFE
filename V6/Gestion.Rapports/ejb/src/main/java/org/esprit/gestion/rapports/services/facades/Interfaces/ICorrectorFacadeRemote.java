package org.esprit.gestion.rapports.services.facades.Interfaces;

import javax.ejb.Remote;

import org.esprit.gestion.rapports.persistence.Project;
import org.esprit.gestion.rapports.persistence.Teacher;

@Remote
public interface ICorrectorFacadeRemote {

	public boolean CorrectorProjectAccept(Teacher teacher, Project project);
}
