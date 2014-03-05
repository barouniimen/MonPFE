package org.esprit.gestion.rapports.services.facades.Interfaces;

import java.util.Date;
import java.util.List;

import org.esprit.gestion.rapports.persistence.Message;
import org.esprit.gestion.rapports.persistence.Project;
import org.esprit.gestion.rapports.persistence.Teacher;

public interface ICoachFacadeServ {

	public boolean assignCoachToProject(Teacher teacher, Project project,
			Date date);

	/*
	 * Un message est envoyé à l’enseignant avec réponse obligatoire
	 *  le champ encadrant au niveau de la base de données reste vide, les variables
	 * teacher et project servent seulement pour envoyer le message la méthode
	 * retourne si opération réussie ou pas
	 */

	public boolean acceptRole(Teacher teacher, Project project, Message message);
	/*
	 * affecter teacher à projet marquer le message comme répondu (ce flag sert
	 * à gérer les messages à réponse obligatoire)
	 * 
	 */

	public boolean declineRole(Teacher teacher, Message message);
	
	public boolean cancelCoachAssign(Teacher teacher);
	public List<Teacher> listCoachs();
	
}
