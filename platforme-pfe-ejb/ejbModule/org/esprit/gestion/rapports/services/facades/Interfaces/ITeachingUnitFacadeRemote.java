package org.esprit.gestion.rapports.services.facades.Interfaces;

import java.util.List;

import javax.ejb.Remote;

import org.esprit.gestion.rapports.persistence.TeachingUnit;
import org.esprit.gestion.rapports.persistence.TeachingUnitDomain;

@Remote
public interface ITeachingUnitFacadeRemote {
	
	public String addTeachingUnit(TeachingUnit tUnit, int idDom) ;

	public List<TeachingUnit> listAllTeachUnit();

}
