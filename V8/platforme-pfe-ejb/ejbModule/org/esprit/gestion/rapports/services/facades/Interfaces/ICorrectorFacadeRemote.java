package org.esprit.gestion.rapports.services.facades.Interfaces;

import java.util.List;

import javax.ejb.Remote;

import org.esprit.gestion.rapports.persistence.Message;
import org.esprit.gestion.rapports.persistence.Teacher;

@Remote
public interface ICorrectorFacadeRemote {

	public void CorrectorProjectAccept(Teacher teacher,Message message);
	
	public List<Teacher> listerCorrectorDisponibles();
}
