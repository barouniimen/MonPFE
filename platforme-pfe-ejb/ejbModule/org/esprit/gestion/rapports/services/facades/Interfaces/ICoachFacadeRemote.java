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

		public List<Teacher> listerCoachSameDom(int coachingHoursMax,List<String> projectDomains);
		
		public List<Teacher> listAllCoach(int coachingHoursMax);

		public void CoachProjectAccept(int idCoach, int idMsg);
	
		public void coachDeclineAssign(int idCoach, int includedRef, String declineCause, int idAdmin, int idAssignMsg);
		
		public List<Project> listProjectsCoached(int idCoach);

}
