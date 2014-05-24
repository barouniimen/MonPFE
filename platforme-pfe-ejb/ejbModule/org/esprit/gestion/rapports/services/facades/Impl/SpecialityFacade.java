package org.esprit.gestion.rapports.services.facades.Impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.esprit.gestion.rapports.persistence.Speciality;
import org.esprit.gestion.rapports.services.CRUD.Interfaces.IServiceLocal;
import org.esprit.gestion.rapports.services.CRUD.Util.SpecialityQualifier;
import org.esprit.gestion.rapports.services.facades.Interfaces.ISpecialityFacadeLocal;
import org.esprit.gestion.rapports.services.facades.Interfaces.ISpecialityFacadeRemote;

@Stateless
public class SpecialityFacade implements ISpecialityFacadeLocal, ISpecialityFacadeRemote {

	@Inject
	@SpecialityQualifier
	IServiceLocal<Speciality> specialityServ;
	
	@Override
	public String addSpeciality(Speciality speciality) {
		String resultState = "unique";
		List<Speciality> allSpecialities = new ArrayList<Speciality>();

		allSpecialities = specialityServ.retrieveList(null, "ALL");

		for (int i = 0; i < allSpecialities.size(); i++) {

			if (allSpecialities.get(i).getTitle().toLowerCase()
					.equals(speciality.getTitle().toLowerCase())) {

				resultState = "exist";
			}

		}

		if (resultState == "unique") {
			specialityServ.create(speciality);
		}

		return resultState;
	}

}
