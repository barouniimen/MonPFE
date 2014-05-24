package org.esprit.gestion.rapports.services.facades.Impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.esprit.gestion.rapports.persistence.Company;
import org.esprit.gestion.rapports.persistence.CompanyCoach;
import org.esprit.gestion.rapports.services.CRUD.Interfaces.IServiceLocal;
import org.esprit.gestion.rapports.services.CRUD.Util.CompanyCoachQualifier;
import org.esprit.gestion.rapports.services.facades.Interfaces.ICompanyCoachFacadeLocal;
import org.esprit.gestion.rapports.services.facades.Interfaces.ICompanyCoachFacadeRemote;

@Stateless
public class CompanyCoachFacade implements ICompanyCoachFacadeRemote,
		ICompanyCoachFacadeLocal {

	@Inject
	@CompanyCoachQualifier
	IServiceLocal<CompanyCoach> compCoachServ;

	@Override
	public List<CompanyCoach> compCoachSameCompany(Company company) {
		System.out.println("entrer methode!");
		List<CompanyCoach> returnList = new ArrayList<CompanyCoach>();
		List<CompanyCoach> allList = new ArrayList<CompanyCoach>();
		boolean equalName;
		boolean equalAdress;
		String name;
		String address;

		allList = compCoachServ.retrieveList(allList, "ALL");
		
		
		for (int i = 0; i < allList.size(); i++) {
			equalAdress = false;
			equalName = false;
		
			name = allList.get(i).getCompany().getName();
			address = allList.get(i).getCompany().getAdress();
			
			if(name.equals(company.getName())){
				equalName = true;
			}
			if(address.equals(company.getAdress())){
				equalAdress = true;
			}
			
			if(equalAdress && equalName){
				returnList.add(allList.get(i));
				
			}
			
			
		}

		return returnList;
	}
}
