package tn.esprit.pfeged.service.interfaces;

import javax.ejb.Local;

@Local
public interface IServiceLocal<T> {
	
	public void create(Object object);

	public Object retrieve(int id);

	public void update(Object object);

	public void delete(int id);

}
