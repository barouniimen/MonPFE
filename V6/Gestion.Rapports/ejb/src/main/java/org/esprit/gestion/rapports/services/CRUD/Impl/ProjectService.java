package org.esprit.gestion.rapports.services.CRUD.Impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.esprit.gestion.rapports.persistence.Project;
import org.esprit.gestion.rapports.services.CRUD.Interfaces.IServiceLocal;
import org.esprit.gestion.rapports.services.CRUD.Interfaces.IServiceRemote;
import org.esprit.gestion.rapports.services.CRUD.Util.ProjectQualifier;

@ProjectQualifier
@Stateless
public class ProjectService implements IServiceLocal<Project>, IServiceRemote<Project>{

	@PersistenceContext
	EntityManager em;
	
	@Override
	public void delete(Object object) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("pas encore implémentée");
	}

	@Override
	public void create(Object object) {
		try {
			em.persist(object);
			} catch (EntityExistsException exist) {
			System.out.println("!!!!!!!!!!!!!!!!!!!!!User existe!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		}
	}

	@Override
	public Object retrieve(Object object, String searchBy) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("pas encore implémentée");
	}

	@Override
	public void update(Object object) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("pas encore implémentée");
	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("pas encore implémentée");
	}

	/* (non-Javadoc)
	 * @see org.esprit.gestion.rapports.services.CRUD.Interfaces.IServiceLocal#retrieveList(java.lang.Object, java.lang.String)
	 */
	@Override
	public List<Project> retrieveList(Object object, String searchBy) {
		// TODO Auto-generated method stub
		return null;
	}

}
