
package org.esprit.gestion.rapports.services.CRUD.Interfaces;


import java.util.List;

import javax.ejb.Local;

@Local
public interface IServiceLocal<T> {
	
	public void create(Object object);

	public Object retrieve(Object object, String searchBy);

	public List<T> retrieveList(Object object, String searchBy);

	public void update(Object object);

	public void delete(int id);
	
	public void delete(Object object);

}