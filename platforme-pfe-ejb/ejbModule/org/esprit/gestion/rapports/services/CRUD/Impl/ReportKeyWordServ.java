package org.esprit.gestion.rapports.services.CRUD.Impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.esprit.gestion.rapports.persistence.ReportKeyWord;
import org.esprit.gestion.rapports.services.CRUD.Interfaces.IServiceLocal;
import org.esprit.gestion.rapports.services.CRUD.Interfaces.IServiceRemote;
import org.esprit.gestion.rapports.services.CRUD.Util.ReportKeyWordQualifier;

@ReportKeyWordQualifier
@Stateless
public class ReportKeyWordServ implements IServiceLocal<ReportKeyWord>,
		IServiceRemote<ReportKeyWord> {
	
	@PersistenceContext
	EntityManager em;

	@Override
	public void create(Object object) {
		em.persist(object);

	}

	@Override
	public Object retrieve(Object object, String searchBy) {
		throw new UnsupportedOperationException("isn't implemented!!!!!!!");
	}

	@Override
	public List<ReportKeyWord> retrieveList(Object object, String searchBy) {
		List<ReportKeyWord> found = new ArrayList<ReportKeyWord>();
		
		if(searchBy.equals("repID")){
			
			TypedQuery<ReportKeyWord> query = em.createNamedQuery(
					"ReportKeyWord.findByreportId",ReportKeyWord.class);
			
			query.setParameter("reportId",((ReportKeyWord) object).getPk().getReportId());
			
				found = query.getResultList();
			
		}
		
		return found;
	}

	@Override
	public void update(Object object) {
		throw new UnsupportedOperationException("isn't implemented!!!!!!!");

	}

	@Override
	public void delete(int id) {
		throw new UnsupportedOperationException("isn't implemented!!!!!!!");
	}

	@Override
	public void delete(Object object) {
		em.refresh(object);

	}

}
