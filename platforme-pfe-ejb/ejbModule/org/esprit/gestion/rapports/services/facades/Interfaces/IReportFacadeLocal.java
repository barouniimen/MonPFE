package org.esprit.gestion.rapports.services.facades.Interfaces;

import java.util.List;

import javax.ejb.Local;

import org.esprit.gestion.rapports.persistence.Report;

@Local
public interface IReportFacadeLocal {

	public String getLastVersion(int idStudent);

	public List<Report> listStudentReports(int idStudent);

	public void createReport(Report report, int idStudent,List<String> keyWordNames);

	public boolean deleteReport(Report report);

	public void changeToFinal(Report selectedReport);

}
