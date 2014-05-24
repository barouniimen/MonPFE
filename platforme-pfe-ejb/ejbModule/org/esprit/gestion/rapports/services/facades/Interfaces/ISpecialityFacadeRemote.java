package org.esprit.gestion.rapports.services.facades.Interfaces;

import javax.ejb.Remote;

import org.esprit.gestion.rapports.persistence.Speciality;

@Remote
public interface ISpecialityFacadeRemote {
	
	public String addSpeciality(Speciality speciality);

}
