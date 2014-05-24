package org.esprit.gestion.rapports.services.facades.Impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.esprit.gestion.rapports.persistence.Domain;
import org.esprit.gestion.rapports.persistence.Project;
import org.esprit.gestion.rapports.persistence.ProjectDomain;
import org.esprit.gestion.rapports.persistence.ProjectDomainPK;
import org.esprit.gestion.rapports.services.CRUD.Interfaces.IServiceLocal;
import org.esprit.gestion.rapports.services.CRUD.Util.DomainQualifier;
import org.esprit.gestion.rapports.services.CRUD.Util.ProjectDomainQualifier;
import org.esprit.gestion.rapports.services.facades.Interfaces.IDomainFacadeLocal;
import org.esprit.gestion.rapports.services.facades.Interfaces.IDomainFacadeRemote;

@Stateless
public class DomainFacade implements IDomainFacadeLocal, IDomainFacadeRemote{

	@Inject
	@DomainQualifier
	IServiceLocal<Domain> domainServ;
	
	@Inject
	@ProjectDomainQualifier
	IServiceLocal<ProjectDomain> projDomServ;
	
	
	@Override
	public String addDomain(Domain domain) {
		String addResult;
		addResult = "unique";
		List<Domain> allDomains = new ArrayList<Domain>();
		
		allDomains = domainServ.retrieveList(null, "ALL");
		
		for (int i = 0; i < allDomains.size(); i++) {
			if(allDomains.get(i).getDomainName().toLowerCase().equals(domain.getDomainName().toLowerCase())){
				addResult = "exist";
			}
		}
		
		if(addResult == "unique"){
			domainServ.create(domain);
		}
		return addResult;
	}


	@Override
	public List<Domain> listProjectDomain(Project project) {
		List<Domain> returnList = new ArrayList<Domain>();
		List<ProjectDomain> allProjDom = new ArrayList<ProjectDomain>();
		
		ProjectDomain projectDom = new ProjectDomain();
		ProjectDomainPK pk = new ProjectDomainPK();
		pk.setProjId(project.getId());
		projectDom.setPk(pk);
		
		allProjDom = projDomServ.retrieveList(projectDom, "projID");
		
		for (int i = 0; i < allProjDom.size(); i++) {
			returnList.add(allProjDom.get(i).getDomain());
		}
		
		return returnList;
	}

}
