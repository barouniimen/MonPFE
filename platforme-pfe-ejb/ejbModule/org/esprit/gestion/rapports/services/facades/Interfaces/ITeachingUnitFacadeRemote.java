package org.esprit.gestion.rapports.services.facades.Interfaces;

import java.util.List;

import javax.ejb.Remote;

import org.esprit.gestion.rapports.persistence.Domain;
import org.esprit.gestion.rapports.persistence.TeachingUnit;

@Remote
public interface ITeachingUnitFacadeRemote {
	
	public String addTeachingUnit(TeachingUnit tUnit, List<Domain> listDom) ;

	public List<TeachingUnit> listAllTeachUnit();

}
