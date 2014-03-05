package org.esprit.persistence;

import javax.inject.Inject;

import org.esprit.gestion.rapports.persistence.StorageSpace;
import org.esprit.gestion.rapports.persistence.Student;
import org.esprit.gestion.rapports.persistence.User;
import org.esprit.gestion.rapports.services.CRUD.Interfaces.IServiceLocal;
import org.esprit.gestion.rapports.services.CRUD.Util.UserQualifier;
import org.junit.Test;

public class TestKeyWordServ {

	@Inject
	@UserQualifier
	IServiceLocal<User> servUser;

	@Test
	public void testUpdate() {
		/*System.out.println("retreive key wor!!!!!!!!!!!!!!!!!!!!!!!");
		kw = (KeyWord) servKeyW.retrieve(kw, "searchBy");
		kw.setName("newname");*/
		/*if ((kw.getCategory()) != null) {
			categ = kw.getCategory();
			categ.setCategoryName("mycateg");
			kw.setCategory(categ);
			System.out.println("updating!!!!!!!!!!!!!!!!!!!!!!!");
			servKeyW.update(kw);
			System.out.println("update success!!!!!!!!!!!!!!!!!!!!!!!");
		} else
			System.out.println("failllllllllllllllllll");*/
		Student user = new Student();
		user.setEmail("mailll");
		user.setFirstName("nameuuu");
		user.setLastName("lastNameyyy");
		user.setLogin("loginrrrr");
		user.setPassword("passwordgff");
		user.setPhoneNumber(236523);
		StorageSpace st = new StorageSpace();
		st.setAllocatedSpace(12);
		user.setStorageSpace(st);
		servUser.create(user);
	}

}
