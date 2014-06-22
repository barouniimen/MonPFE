package org.esprit.gestion.rapports.services.CRUD.Impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.esprit.gestion.rapports.persistence.Report;
import org.esprit.gestion.rapports.services.CRUD.Interfaces.IServiceLocal;
import org.esprit.gestion.rapports.services.CRUD.Interfaces.IServiceRemote;
import org.esprit.gestion.rapports.services.CRUD.Util.ReportQualifier;

@ReportQualifier
@Stateless
public class ReportService implements IServiceLocal<Report>,
		IServiceRemote<Report> {

	@PersistenceContext
	EntityManager em;

	@Override
	public void delete(Object object) {
		em.remove(object);
	}

	@Override
	public void create(Object object) {
		em.persist(object);
	}

	@Override
	public Object retrieve(Object object, String searchBy) {
		Report report = new Report();

		if (searchBy.equals("ID")) {

			TypedQuery<Report> query = em.createNamedQuery("Report.findById",
					Report.class);
			query.setParameter("id", ((Report) object).getId());

			report = query.getSingleResult();
		}
		
		return report;
	}

	@Override
	public void update(Object object) {
		em.merge(object);
	}

	@Override
	public void delete(int id) {
		throw new UnsupportedOperationException("isn't implemented!!!!!!!");
	}

	@Override
	public List<Report> retrieveList(Object object, String searchBy) {
		List<Report> returnList = new ArrayList<Report>();

		/*************************** search ALL *************************************/
		if (searchBy.equals("state")) {
			TypedQuery<Report> query = em.createNamedQuery(
					"Report.findByState", Report.class);
			query.setParameter("state", ((Report) object).getState());

			returnList = query.getResultList();

		}
		
		else if(searchBy.equals("proj")){
			TypedQuery<Report> query = em.createNamedQuery(
					"Report.findByProject", Report.class);
			query.setParameter("project", ((Report) object).getProject());

			returnList = query.getResultList();
		}

		return returnList;
	}
}
