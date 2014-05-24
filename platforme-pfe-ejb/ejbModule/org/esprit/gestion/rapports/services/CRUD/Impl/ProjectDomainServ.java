package org.esprit.gestion.rapports.services.CRUD.Impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.esprit.gestion.rapports.persistence.ProjectDomain;
import org.esprit.gestion.rapports.services.CRUD.Interfaces.IServiceLocal;
import org.esprit.gestion.rapports.services.CRUD.Interfaces.IServiceRemote;
import org.esprit.gestion.rapports.services.CRUD.Util.ProjectDomainQualifier;

@Stateless
@ProjectDomainQualifier
public class ProjectDomainServ implements IServiceLocal<ProjectDomain>,
		IServiceRemote<ProjectDomain> {

	@PersistenceContext
	EntityManager em;

	@Override
	public void create(Object object) {
		em.persist(object);

	}

	@Override
	public Object retrieve(Object object, String searchBy) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ProjectDomain> retrieveList(Object object, String searchBy) {
		List<ProjectDomain> returnList = new ArrayList<ProjectDomain>();

		/******************** search by project id ******************************/
		if (searchBy == "projID") {
			TypedQuery<ProjectDomain> query = em.createNamedQuery(
					"ProjectDomain.findByProjId", ProjectDomain.class);
			query.setParameter("projId", ((ProjectDomain) object).getPk()
					.getProjId());
			returnList = query.getResultList();
		}

		return returnList;
	}

	@Override
	public void update(Object object) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Object object) {
		em.remove(object);

	}

}
