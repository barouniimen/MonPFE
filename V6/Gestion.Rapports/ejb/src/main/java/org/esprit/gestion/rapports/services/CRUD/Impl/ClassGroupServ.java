package org.esprit.gestion.rapports.services.CRUD.Impl;

import java.util.List;

import javax.ejb.Stateless;

import org.esprit.gestion.rapports.persistence.ClassGroup;
import org.esprit.gestion.rapports.services.CRUD.Interfaces.IServiceLocal;
import org.esprit.gestion.rapports.services.CRUD.Interfaces.IServiceRemote;
import org.esprit.gestion.rapports.services.CRUD.Util.ClassGroupeQualifier;

@ClassGroupeQualifier
@Stateless
public class ClassGroupServ implements IServiceLocal<ClassGroup>, IServiceRemote<ClassGroup>{

	@Override
	public void delete(Object object) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("pas encore implémentée");
	}

	@Override
	public void create(Object object) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("pas encore implémentée");
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
	public List<ClassGroup> retrieveList(Object object, String searchBy) {
		// TODO Auto-generated method stub
		return null;
	}

}
