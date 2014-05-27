package org.esprit.gestion.rapports.services.facades.Interfaces;

import java.util.List;

import javax.ejb.Local;

import org.esprit.gestion.rapports.persistence.TeachingUnit;
import org.esprit.gestion.rapports.persistence.TeachingUnitDomain;

@Local
public interface ITeachingUnitFacadeLocal {
	
	public String addTeachingUnit(TeachingUnit tUnit, int idDom) ;
	
	public List<TeachingUnit> listAllTeachUnit();

}
