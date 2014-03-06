package org.esprit.gestion.rapports.services.facades.Interfaces;

import java.util.List;

import javax.ejb.Local;

import org.esprit.gestion.rapports.persistence.Project;
import org.esprit.gestion.rapports.persistence.Teacher;

/**
 * @author Imen Barouni
 */

@Local
public interface ICoachFacadeLocal {

	public List<Teacher> listerCoachDisponibles(int coachingHoursMin);

	public boolean CoachProjectAccept(Teacher teacher, Project project);
	

	

}
