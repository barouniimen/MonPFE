package org.esprit.gestion.rapports.services.facades.Interfaces;

import java.util.List;

import javax.ejb.Remote;

import org.esprit.gestion.rapports.persistence.Company;

@Remote
public interface ICompanyFacadeRemote {

	public List<Company> listAllComapnies();
}
