package org.esprit.gestion.rapports.services.facades.Impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.esprit.gestion.rapports.persistence.Student;
import org.esprit.gestion.rapports.services.CRUD.Interfaces.IServiceLocal;
import org.esprit.gestion.rapports.services.CRUD.Util.StudentQualifier;
import org.esprit.gestion.rapports.services.facades.Interfaces.IStudentFacadeLocal;
import org.esprit.gestion.rapports.services.facades.Interfaces.IStudentFacadeRemote;

@Stateless
public class StudentFacade implements IStudentFacadeLocal, IStudentFacadeRemote{

	@Inject
	@StudentQualifier
	IServiceLocal<Student> studentServ;
	
	
	@Override
	public List<Student> listStudentsWithoutProject() {
		//init vars----------------------------------------------------
		List<Student> returnList = new ArrayList<Student>();
		List<Student> allStudentsList = new ArrayList<Student>();
		int j=0;
		
		
		//retrieve all students----------------------------------------
		allStudentsList = studentServ.retrieveList(null, "ALL");
		
		//find students without projects
		for (int i = 0; i < allStudentsList.size(); i++) {
			if(allStudentsList.get(i).getProject() == null){
			returnList.add(allStudentsList.get(i));
			j++;
			}
		}
		if(j==0){
			returnList = null;
		}
		
		return returnList;
	}

}
