package org.esprit.gestion.rapports.services.facades.Interfaces;

import java.util.List;

import javax.ejb.Remote;

import org.esprit.gestion.rapports.persistence.Company;
import org.esprit.gestion.rapports.persistence.CompanyCoach;

@Remote
public interface ICompanyCoachFacadeRemote {
	
	public List<CompanyCoach> compCoachSameCompany(Company company);

}
