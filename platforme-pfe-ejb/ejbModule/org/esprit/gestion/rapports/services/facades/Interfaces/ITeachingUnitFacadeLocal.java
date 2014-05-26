package org.esprit.gestion.rapports.services.facades.Interfaces;

import javax.ejb.Local;

import org.esprit.gestion.rapports.persistence.TeachingUnit;

@Local
public interface ITeachingUnitFacadeLocal {
	
	public String addTeachingUnit(TeachingUnit tUnit);

}
