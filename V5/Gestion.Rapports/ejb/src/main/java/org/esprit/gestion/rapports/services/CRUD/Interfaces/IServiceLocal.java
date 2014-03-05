
package org.esprit.gestion.rapports.services.CRUD.Interfaces;


import javax.ejb.Local;

@Local
public interface IServiceLocal<T> {
	
	public void create(Object object);

	public Object retrieve(Object object, String searchBy);

	public void update(Object object);

	public void delete(int id);

}