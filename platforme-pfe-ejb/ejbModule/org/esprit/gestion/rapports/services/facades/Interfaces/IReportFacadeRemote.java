package org.esprit.gestion.rapports.services.facades.Interfaces;

import java.util.List;

import javax.ejb.Remote;

import org.esprit.gestion.rapports.persistence.Report;

@Remote
public interface IReportFacadeRemote {
	
	public String getLastVersion(int idStudent);
	
	public List<Report> listStudentReports(int idStudent);
	
	public void createReport(Report report, int idStudent);
	
	public boolean deleteReport(Report report);
	
	public void changeToFinal(Report selectedReport);

}
