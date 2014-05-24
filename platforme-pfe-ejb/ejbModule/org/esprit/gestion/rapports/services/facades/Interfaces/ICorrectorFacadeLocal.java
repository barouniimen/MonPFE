package org.esprit.gestion.rapports.services.facades.Interfaces;

import java.util.List;

import javax.ejb.Local;

import org.esprit.gestion.rapports.persistence.Message;
import org.esprit.gestion.rapports.persistence.Teacher;

@Local
public interface ICorrectorFacadeLocal {

	public void CorrectorProjectAccept(Teacher teacher,Message message);

	List<Teacher> listerCorrectorDisponibles();
}
