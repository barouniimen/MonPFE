package org.esprit.gestion.rapports.services.facades.Interfaces;

import java.util.List;

import javax.ejb.Local;

import org.esprit.gestion.rapports.persistence.Administrator;
import org.esprit.gestion.rapports.persistence.Report;
import org.esprit.gestion.rapports.persistence.SubmissionEvent;

@Local
public interface ISubmissionFacadeLocal {

	public SubmissionEvent sessionOpened();

	public List<SubmissionEvent> listAllSubmitEvent();

	public void createSubmitEvent(SubmissionEvent submitEvent, Administrator sender);

	public void updateSubmitEvent(SubmissionEvent submitEventToDB,
			Administrator user);

	public List<Report> listSubmittedReports(SubmissionEvent submitEvent);

	public void updateDates();

}
