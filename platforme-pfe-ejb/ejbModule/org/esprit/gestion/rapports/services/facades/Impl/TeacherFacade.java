package org.esprit.gestion.rapports.services.facades.Impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.esprit.gestion.rapports.persistence.Teacher;
import org.esprit.gestion.rapports.services.CRUD.Interfaces.IServiceLocal;
import org.esprit.gestion.rapports.services.CRUD.Util.TeacherQualifier;
import org.esprit.gestion.rapports.services.facades.Interfaces.ITeacherFacadeLocal;
import org.esprit.gestion.rapports.services.facades.Interfaces.ITeacherFacadeRemote;

@Stateless
public class TeacherFacade implements ITeacherFacadeLocal, ITeacherFacadeRemote{

	@Inject
	@TeacherQualifier
	IServiceLocal<Teacher> teacherServ;
	
	@Override
	public String addTeacher(Teacher teacher) {
		String addResult = "init";
		List<Teacher> allTeachers = new ArrayList<Teacher>();
		
		allTeachers = teacherServ.retrieveList(null, "ALL");
		
		if(allTeachers.isEmpty()){
			teacherServ.create(teacher);
			addResult = "success";
			return addResult;
		}
		else {
			for (int i = 0; i < allTeachers.size(); i++) {
				if(allTeachers.get(i).getLogin().toLowerCase().equals(teacher.getLogin().toLowerCase())){
					addResult = "loginExist";
					return addResult;
				}
				else if(allTeachers.get(i).getPassword().toLowerCase().equals(teacher.getPassword().toLowerCase())){
					addResult = "passExist";
					return addResult;
				}
			}
			if(addResult=="init"){
				teacherServ.create(teacher);
				addResult = "success";
			}
		}
		
		
		return addResult;
	}
	
	

}
