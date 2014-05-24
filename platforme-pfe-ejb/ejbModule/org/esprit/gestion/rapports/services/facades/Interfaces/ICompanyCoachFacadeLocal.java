package org.esprit.gestion.rapports.services.facades.Interfaces;

import java.util.List;

import javax.ejb.Local;

import org.esprit.gestion.rapports.persistence.Company;
import org.esprit.gestion.rapports.persistence.CompanyCoach;

@Local
public interface ICompanyCoachFacadeLocal {
	
	public List<CompanyCoach> compCoachSameCompany(Company company);

}
