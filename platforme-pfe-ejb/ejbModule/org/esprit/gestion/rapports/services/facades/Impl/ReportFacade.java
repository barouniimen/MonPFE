package org.esprit.gestion.rapports.services.facades.Impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.esprit.gestion.rapports.persistence.KeyWord;
import org.esprit.gestion.rapports.persistence.Project;
import org.esprit.gestion.rapports.persistence.Report;
import org.esprit.gestion.rapports.persistence.ReportKeyWord;
import org.esprit.gestion.rapports.persistence.ReportKeyWordPk;
import org.esprit.gestion.rapports.persistence.ReportState;
import org.esprit.gestion.rapports.persistence.Student;
import org.esprit.gestion.rapports.services.CRUD.Interfaces.IServiceLocal;
import org.esprit.gestion.rapports.services.CRUD.Util.KeyWordsQualifier;
import org.esprit.gestion.rapports.services.CRUD.Util.ProjectQualifier;
import org.esprit.gestion.rapports.services.CRUD.Util.ReportKeyWordQualifier;
import org.esprit.gestion.rapports.services.CRUD.Util.ReportQualifier;
import org.esprit.gestion.rapports.services.CRUD.Util.StudentQualifier;
import org.esprit.gestion.rapports.services.facades.Interfaces.IReportFacadeLocal;
import org.esprit.gestion.rapports.services.facades.Interfaces.IReportFacadeRemote;

@Stateless
public class ReportFacade implements IReportFacadeLocal, IReportFacadeRemote {

	@Inject
	@StudentQualifier
	IServiceLocal<Student> studentServ;

	@Inject
	@ProjectQualifier
	IServiceLocal<Project> projServ;

	@Inject
	@ReportQualifier
	IServiceLocal<Report> reportServ;
	
	@Inject
	@KeyWordsQualifier
	IServiceLocal<KeyWord> keyWordServ;
	
	@Inject
	@ReportKeyWordQualifier
	IServiceLocal<ReportKeyWord> reportKwServ;

	@Override
	public String getLastVersion(int idStudent) {
		List<Report> reportsList = new ArrayList<Report>();
		String version;

		// getall reports
		reportsList = listStudentReports(idStudent);

		if (reportsList.isEmpty()) {
			return null;
		}

		else {
			version = reportsList.get(0).getVersion();

			Date lastDate = reportsList.get(0).getUploadDate();

			for (int i = 1; i < reportsList.size(); i++) {
				int compare = lastDate.compareTo(reportsList.get(i)
						.getUploadDate());

				if (compare < 0) {
					version = reportsList.get(i).getVersion();
					lastDate = reportsList.get(i).getUploadDate();
				}
			}

			return version;
		}
	}

	/*********************************** getter && setter ************************************/

	@Override
	public List<Report> listStudentReports(int idStudent) {

		List<Report> reportsList = new ArrayList<Report>();

		// retrieve student
		Student st = new Student();
		st.setId(idStudent);
		st = (Student) studentServ.retrieve(st, "ID");

		// find project
		Project proj = new Project();
		proj = st.getProject();

		if (proj != null) {
			proj = (Project) projServ.retrieve(proj, "ID");

			// find all reports linked to project
			reportsList = proj.getReports();
		}
		return reportsList;

	}

	@Override
	public void createReport(Report report, int idStudent,
			List<String> keyWordNames) {
		Student st = new Student();
		st.setId(idStudent);
		st = (Student) studentServ.retrieve(st, "ID");

		// create report
		reportServ.create(report);

		Project proj = new Project();
		proj = st.getProject();

		List<Report> projReports = new ArrayList<Report>();
		projReports = proj.getReports();

		projReports.add(report);

		// update proj
		projServ.update(proj);

		if (report.getState().equals(ReportState.DEPOSED)) {
			// ajouter les mots clés
			List<ReportKeyWord> reportKws = new ArrayList<ReportKeyWord>();
			
			for (int i = 0; i < keyWordNames.size(); i++) {
				KeyWord kw  = new KeyWord();
				kw.setName(keyWordNames.get(i));
				kw = (KeyWord) keyWordServ.retrieve(kw, "NAME");
				ReportKeyWordPk pk = new ReportKeyWordPk();
				pk.setKeyWordId(kw.getId());
				pk.setReportId(report.getId());
				ReportKeyWord reportKw = new ReportKeyWord();
				reportKw.setPk(pk);
				report.setKeyWords(reportKws);
				reportServ.update(report);
				reportKwServ.create(reportKw);
			}			
			
			
			// TODO notifier encadrant + administration
		}

	}

	@Override
	public boolean deleteReport(Report report) {
		// delete document
		// path == null
		// size = 0

		String path = report.getFilePath() + "\\" + report.getFileName();
		System.out.println("path: " + path);
		File file = new File(path);
		if (file.delete()) {
			report.setFilePath(null);
			report.setSize(0);
			reportServ.update(report);
			return true;
		} else {
			return false;
		}

	}

	@Override
	public void changeToFinal(Report selectedReport) {
		selectedReport.setVersion("final");
		selectedReport.setState(ReportState.DEPOSED);
		Date date = new Date();
		selectedReport.setUploadDate(date);

		reportServ.update(selectedReport);
		
		// TODO informer encadrant + admin pour affecter rapporteur

	}

}
