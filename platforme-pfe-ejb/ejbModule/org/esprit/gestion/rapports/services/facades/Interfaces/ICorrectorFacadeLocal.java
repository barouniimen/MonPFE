package org.esprit.gestion.rapports.services.facades.Interfaces;

import java.util.List;

import javax.ejb.Local;

import org.esprit.gestion.rapports.persistence.Teacher;

@Local
public interface ICorrectorFacadeLocal {

	public void  CorrectorProjectAccept(int idCorrector, int idMsg);

	List<Teacher> listerCorrectorDisponibles();
	
	public List<Teacher> listCorrectorsSameDom(List<String> projectDomains);

	public void correctorDeclineAssign(int id, int includedRef,
			String declineCause, int idAdmin, int idAssignMsg);
}
