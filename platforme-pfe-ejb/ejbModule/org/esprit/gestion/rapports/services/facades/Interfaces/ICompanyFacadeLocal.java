package org.esprit.gestion.rapports.services.facades.Interfaces;

import java.util.List;

import javax.ejb.Local;

import org.esprit.gestion.rapports.persistence.Company;

@Local
public interface ICompanyFacadeLocal {

	public List<Company> listAllComapnies();
	
}
