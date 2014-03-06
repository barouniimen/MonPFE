package org.esprit.gestion.rapports.services.facades.Interfaces;

import javax.ejb.Local;

import org.esprit.gestion.rapports.persistence.Project;
import org.esprit.gestion.rapports.persistence.Teacher;

@Local
public interface ICorrectorFacadeLocal {

	public boolean CorrectorProjectAccept(Teacher teacher, Project project);
}
