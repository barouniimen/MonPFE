package org.esprit.gestion.rapports.services.facades.Interfaces;

import java.util.List;

import javax.ejb.Local;

import org.esprit.gestion.rapports.persistence.Domain;
import org.esprit.gestion.rapports.persistence.TeachingUnit;

@Local
public interface ITeachingUnitFacadeLocal {
	
	public String addTeachingUnit(TeachingUnit tUnit, List<Domain> listDom) ;
	
	public List<TeachingUnit> listAllTeachUnit();

}
