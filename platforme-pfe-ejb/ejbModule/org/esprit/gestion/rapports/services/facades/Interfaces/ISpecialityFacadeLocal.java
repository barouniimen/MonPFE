package org.esprit.gestion.rapports.services.facades.Interfaces;

import javax.ejb.Local;

import org.esprit.gestion.rapports.persistence.Speciality;

@Local
public interface ISpecialityFacadeLocal {
	
	public String addSpeciality(Speciality speciality);
}
