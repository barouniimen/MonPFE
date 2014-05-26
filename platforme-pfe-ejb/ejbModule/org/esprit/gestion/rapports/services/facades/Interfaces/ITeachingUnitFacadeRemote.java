package org.esprit.gestion.rapports.services.facades.Interfaces;

import javax.ejb.Remote;

import org.esprit.gestion.rapports.persistence.TeachingUnit;

@Remote
public interface ITeachingUnitFacadeRemote {
	
	public String addTeachingUnit(TeachingUnit tUnit);

}
