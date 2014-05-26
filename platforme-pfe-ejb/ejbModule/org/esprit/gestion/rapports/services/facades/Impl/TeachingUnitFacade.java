package org.esprit.gestion.rapports.services.facades.Impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.esprit.gestion.rapports.persistence.TeachingUnit;
import org.esprit.gestion.rapports.services.CRUD.Interfaces.IServiceLocal;
import org.esprit.gestion.rapports.services.CRUD.Util.TeachingUnitQualifier;
import org.esprit.gestion.rapports.services.facades.Interfaces.ITeachingUnitFacadeLocal;
import org.esprit.gestion.rapports.services.facades.Interfaces.ITeachingUnitFacadeRemote;

@Stateless
public class TeachingUnitFacade implements ITeachingUnitFacadeLocal,
		ITeachingUnitFacadeRemote {
	
	@Inject
	@TeachingUnitQualifier
	IServiceLocal<TeachingUnit> tUnitServ;

	@Override
	public String addTeachingUnit(TeachingUnit tUnit) {
		String addResult = "init";
		List<TeachingUnit> allTeachUnit = new ArrayList<TeachingUnit>();
		
		allTeachUnit = tUnitServ.retrieveList(null, "ALL");
		if(allTeachUnit.isEmpty()){
			addResult = "success";
			tUnitServ.create(tUnit);
		}
		
		else{
		for (int i = 0; i < allTeachUnit.size(); i++) {
			if(allTeachUnit.get(i).getName().toLowerCase().equals(tUnit.getName().toLowerCase())){
				addResult = "name exist";
				return addResult;
			}
			else if(allTeachUnit.get(i).getLocalization().toLowerCase().equals(tUnit.getLocalization().toLowerCase())){
				addResult = "local exist";
				return addResult;
			}
			
			
		}
		if(addResult=="init"){
			addResult="success";
			tUnitServ.create(tUnit);
		}
		
		}
		
		return addResult;
	}

}
