package tn.esprit.pfeged.service.interfaces;

import javax.ejb.Remote;

@Remote
public interface IServiceRemote<T> {

	public void create(Object object);

	public Object retrieve(int id);

	public void update(Object object);

	public void delete(int id);
	
	public void delete(Object object);

}
