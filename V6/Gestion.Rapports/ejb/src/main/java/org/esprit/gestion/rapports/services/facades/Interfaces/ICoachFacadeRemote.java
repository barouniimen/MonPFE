/**
 * 
 */
package org.esprit.gestion.rapports.services.facades.Interfaces;

import java.util.List;

import javax.ejb.Remote;

import org.esprit.gestion.rapports.persistence.Project;
import org.esprit.gestion.rapports.persistence.Teacher;

/**
 * @author Imen Barouni
 *
 * 
 */
@Remote
public interface ICoachFacadeRemote {

		public List<Teacher> listerCoachDisponibles(int coachingHoursMin);

		public boolean CoachProjectAccept(Teacher teacher, Project project);
	

}
