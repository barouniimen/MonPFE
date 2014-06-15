package org.esprit.gestion.rapports.services.CRUD.Impl;

import java.util.List;

import org.esprit.gestion.rapports.persistence.PresentationEvent;
import org.esprit.gestion.rapports.services.CRUD.Interfaces.IServiceLocal;
import org.esprit.gestion.rapports.services.CRUD.Interfaces.IServiceRemote;

public class PresentationEventServ implements IServiceLocal<PresentationEvent>, IServiceRemote<PresentationEvent>{

	@Override
	public void create(Object object) {
		throw new UnsupportedOperationException("isn't implemented!!!!!!!");
		
	}

	@Override
	public Object retrieve(Object object, String searchBy) {
		throw new UnsupportedOperationException("isn't implemented!!!!!!!");
	}

	@Override
	public List<PresentationEvent> retrieveList(Object object, String searchBy) {
		
		return null;
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
		throw new UnsupportedOperationException("isn't implemented!!!!!!!");
	}
	

}
