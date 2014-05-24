package org.esprit.gestion.rapports.services.facades.Impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.esprit.gestion.rapports.persistence.ClassGroup;
import org.esprit.gestion.rapports.persistence.Registration;
import org.esprit.gestion.rapports.persistence.RegistrationPK;
import org.esprit.gestion.rapports.persistence.Student;
import org.esprit.gestion.rapports.services.CRUD.Interfaces.IServiceLocal;
import org.esprit.gestion.rapports.services.CRUD.Util.ClassGroupeQualifier;
import org.esprit.gestion.rapports.services.CRUD.Util.RegistrationQualifier;
import org.esprit.gestion.rapports.services.facades.Interfaces.IClassGroupFacadeLocal;
import org.esprit.gestion.rapports.services.facades.Interfaces.IClassGroupFacadeRemote;

@Stateless
public class ClassGroupFacade implements IClassGroupFacadeLocal,
		IClassGroupFacadeRemote {

	@Inject
	@RegistrationQualifier
	IServiceLocal<Registration> registrationServ;

	@Inject
	@ClassGroupeQualifier
	IServiceLocal<ClassGroup> classGroupServ;

	@Override
	public ClassGroup findByAcademicYearForStudent(Student student,
			String academicYear) {
		System.out.println("find class group methos!!!");
		System.out.println("by student: " + student.getId());
		System.out.println("and by academic year: " + academicYear);

		ClassGroup returnClassGroup = new ClassGroup();
		returnClassGroup = null;

		RegistrationPK pk = new RegistrationPK();
		pk.setUserId(student.getId());

		Registration registration = new Registration();
		registration.setRegistrationPK(pk);
		registration.setAcademicYear(academicYear);
		System.out.println("retreive registration: ");
		registration = (Registration) registrationServ.retrieve(registration,
				"ST&AC");
		System.out.println("registration found!!! " + registration);

		ClassGroup classGroup = new ClassGroup();
		classGroup.setId(registration.getRegistrationPK().getClassGroupId());

		System.out.println("retreive classgroup: ");
		returnClassGroup = (ClassGroup) classGroupServ.retrieve(classGroup,
				"ID");
		System.out.println("classgroup found!! " + returnClassGroup);

		return returnClassGroup;
	}

	@Override
	public String addClassGroup(ClassGroup classGroupe) {
		String resultState = "unique";
		List<ClassGroup> allClassGroup = new ArrayList<ClassGroup>();

		allClassGroup = classGroupServ.retrieveList(null, "ALL");

		for (int i = 0; i < allClassGroup.size(); i++) {

			if (allClassGroup.get(i).getReference().toLowerCase()
					.equals(classGroupe.getReference().toLowerCase())) {

				resultState = "exist";
			}

		}

		if (resultState == "unique") {
			classGroupServ.create(classGroupe);
		}

		return resultState;
	}

}
