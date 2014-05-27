package org.esprit.gestion.rapports.services.facades.Impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.esprit.gestion.rapports.persistence.TeachingUnit;
import org.esprit.gestion.rapports.persistence.TeachingUnitDomain;
import org.esprit.gestion.rapports.persistence.TeachingUnitDomainPK;
import org.esprit.gestion.rapports.services.CRUD.Interfaces.IServiceLocal;
import org.esprit.gestion.rapports.services.CRUD.Util.TeachUnitDomainQualifier;
import org.esprit.gestion.rapports.services.CRUD.Util.TeachingUnitQualifier;
import org.esprit.gestion.rapports.services.facades.Interfaces.ITeachingUnitFacadeLocal;
import org.esprit.gestion.rapports.services.facades.Interfaces.ITeachingUnitFacadeRemote;

@Stateless
public class TeachingUnitFacade implements ITeachingUnitFacadeLocal,
		ITeachingUnitFacadeRemote {

	@Inject
	@TeachingUnitQualifier
	IServiceLocal<TeachingUnit> tUnitServ;

	@Inject
	@TeachUnitDomainQualifier
	IServiceLocal<TeachingUnitDomain> teachUnitDomServ;

	@Override
	public String addTeachingUnit(TeachingUnit tUnit, int idDom) {
		String addResult = "init";
		System.out.println("id dom: "+idDom);
		List<TeachingUnit> allTeachUnit = new ArrayList<TeachingUnit>();

		allTeachUnit = tUnitServ.retrieveList(null, "ALL");
		if (allTeachUnit.isEmpty()) {

			tUnitServ.create(tUnit);
			TeachingUnitDomainPK pk = new TeachingUnitDomainPK(tUnit.getId(),
					idDom);
			TeachingUnitDomain tudom = new TeachingUnitDomain();
			tudom.setPk(pk);
			teachUnitDomServ.create(tudom);
			addResult = "success";
		}

		else {
			for (int i = 0; i < allTeachUnit.size(); i++) {
				if (allTeachUnit.get(i).getName().toLowerCase()
						.equals(tUnit.getName().toLowerCase())) {
					addResult = "name exist";
					return addResult;
				} else if (allTeachUnit.get(i).getLocalization().toLowerCase()
						.equals(tUnit.getLocalization().toLowerCase())) {
					addResult = "local exist";
					return addResult;
				}

			}
			if (addResult == "init") {
				tUnitServ.create(tUnit);
				System.out.println("id t unit created!!! " + tUnit.getId());
				TeachingUnitDomainPK pk = new TeachingUnitDomainPK(
						tUnit.getId(), idDom);
				TeachingUnitDomain tudom = new TeachingUnitDomain();
				tudom.setPk(pk);
				teachUnitDomServ.create(tudom);
				addResult = "success";
			}

		}

		return addResult;
	}

	@Override
	public List<TeachingUnit> listAllTeachUnit() {
		List<TeachingUnit> returnList = new ArrayList<TeachingUnit>();

		returnList = tUnitServ.retrieveList(null, "ALL");

		if (returnList.isEmpty()) {
			return null;
		}

		return returnList;
	}

}
