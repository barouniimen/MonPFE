package org.esprit.gestion.rapports.services.facades.Impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.esprit.gestion.rapports.persistence.Project;
import org.esprit.gestion.rapports.persistence.Report;
import org.esprit.gestion.rapports.persistence.Student;
import org.esprit.gestion.rapports.services.CRUD.Interfaces.IServiceLocal;
import org.esprit.gestion.rapports.services.CRUD.Util.ReportQualifier;
import org.esprit.gestion.rapports.services.CRUD.Util.StudentQualifier;
import org.esprit.gestion.rapports.services.facades.Interfaces.IStorageSpaceFacadeLocal;
import org.esprit.gestion.rapports.services.facades.Interfaces.IStorageSpaceFacadeRemote;

@Stateless
public class StorageSpaceFacade implements IStorageSpaceFacadeLocal,
		IStorageSpaceFacadeRemote {

	@Inject
	@StudentQualifier
	private IServiceLocal<Student> studentServ;

	@Inject
	@ReportQualifier
	private IServiceLocal<Report> reportServ;

	@Override
	public int allocatedSpace(int idStudent) {
		int space;
		Student st = new Student();
		st.setId(idStudent);

		st = (Student) studentServ.retrieve(st, "ID");

		space = st.getStorageSpace().getAllocatedSpace();

		return space;

	}

	@Override
	public int spaceInUse(int idStudent) {
		int space = 0;
		Student st = new Student();
		st.setId(idStudent);

		st = (Student) studentServ.retrieve(st, "ID");

		Project proj = new Project();
		proj = st.getProject();

		List<Report> listReport = new ArrayList<Report>();

		Report report = new Report();
		report.setProject(proj);

		listReport = reportServ.retrieveList(report, "proj");

		if (!(listReport.isEmpty())) {
			for (int i = 0; i < listReport.size(); i++) {
				space = (int) (space + listReport.get(i).getSize());
			}
		}

	return space;
	}

}
