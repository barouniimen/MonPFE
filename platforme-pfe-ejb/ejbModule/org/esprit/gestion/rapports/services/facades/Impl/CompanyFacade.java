package org.esprit.gestion.rapports.services.facades.Impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.esprit.gestion.rapports.persistence.Company;
import org.esprit.gestion.rapports.persistence.CompanyCoach;
import org.esprit.gestion.rapports.services.CRUD.Interfaces.IServiceLocal;
import org.esprit.gestion.rapports.services.CRUD.Util.CompanyCoachQualifier;
import org.esprit.gestion.rapports.services.facades.Interfaces.ICompanyFacadeLocal;
import org.esprit.gestion.rapports.services.facades.Interfaces.ICompanyFacadeRemote;

@Stateless
public class CompanyFacade implements ICompanyFacadeLocal, ICompanyFacadeRemote {

	// EJB services------------------------------------
	@Inject
	@CompanyCoachQualifier
	IServiceLocal<CompanyCoach> compCoachServ;

	public List<Company> listAllComapnies() {

		boolean doublant;
		int j;
		List<Company> returnList = new ArrayList<Company>();

		// retrive all company coachs
		List<CompanyCoach> compCoachlist = new ArrayList<CompanyCoach>();
		compCoachlist = compCoachServ.retrieveList(compCoachlist, "ALL");

		
		// retirer les doublants
		for (int i = 0; i < compCoachlist.size(); i++) {
			doublant = false;
			j = i + 1;
			while (j < compCoachlist.size()) {
				if (compCoachlist.get(i).getCompany().getName()
						.equals(compCoachlist.get(j).getCompany().getName())) {
					doublant = true;
					j = compCoachlist.size();
				} else {
					j = j + 1;
				}
			}

			if (doublant == false) {
				returnList.add(compCoachlist.get(i).getCompany());
			}
		}

		return returnList;

	}

}
