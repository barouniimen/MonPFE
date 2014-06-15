package org.esprit.gestion.rapports.MB;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.esprit.gestion.rapports.persistence.Report;
import org.esprit.gestion.rapports.persistence.ReportState;
import org.esprit.gestion.rapports.persistence.Student;
import org.esprit.gestion.rapports.services.facades.Interfaces.IReportFacadeLocal;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

@ManagedBean
@ViewScoped
public class ReportUploadBean {

	@ManagedProperty(value = "#{authenticationBean}")
	private AuthenticationBean authBean;
	private List<Report> listReports;
	private boolean finalVersion;
	private Report reportToDB;
	private boolean renderUpload;
	private Report selectedReport;
	private boolean onMarkAsFinal;
	private boolean onUpload;
	private SimpleDateFormat dateFormat;

	@EJB
	IReportFacadeLocal reportFacade;

	/****************************** init method ***************************/
	@PostConstruct
	public void init() {
		finalVersion = false;
		selectedReport = new Report();
		onMarkAsFinal = false;
		setOnUpload(false);
		String lastVersion = reportFacade.getLastVersion(authBean.getUser()
				.getId());

		if (lastVersion == null) {
			renderUpload = true;
		} else if (lastVersion.equals("final")) {
			renderUpload = false;
		} else {
			renderUpload = true;
		}

		listReports = new ArrayList<Report>();
		listReports = reportFacade.listStudentReports(authBean.getUser()
				.getId());

		setDateFormat(new SimpleDateFormat("dd/mm/yyyy"));
	}

	/*********************** listeners ********************************/
	public void confirmFinalVersion(ActionEvent event) {
		if (onMarkAsFinal) {
			reportFacade.changeToFinal(selectedReport);
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Depôt réussi",
					"Vous avez déposé la version finale de votre rapport");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}

		onMarkAsFinal = false;
		onUpload = false;
	}

	public void deleteReport(ActionEvent event) {
		boolean deleted;

		if (selectedReport.getVersion().equals("final")) {

			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Error! ",
					"Vous ne pouvez pas supprimer une version déposée!");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}

		else {
			if (selectedReport.getFilePath() == null) {
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
						"N'existe pas", "Le document a déjà été supprimé!");
				FacesContext.getCurrentInstance().addMessage(null, msg);
			} else {
				deleted = reportFacade.deleteReport(selectedReport);

				if (deleted) {
					FacesMessage msg = new FacesMessage(
							FacesMessage.SEVERITY_INFO, "Supprimé",
							"Le document a été supprimé.");
					FacesContext.getCurrentInstance().addMessage(null, msg);
				} else {
					FacesMessage msg = new FacesMessage(
							FacesMessage.SEVERITY_ERROR, "Echec",
							"La suppression du document a échoué!");
					FacesContext.getCurrentInstance().addMessage(null, msg);
				}
			}
		}

		try {
			RequestContext.getCurrentInstance().execute(
					"confirmationDeleteDoc.hide();");
		} catch (Exception e) {
		}

	}

	public void fileUpload(FileUploadEvent event) {
		String createResult;

		try {
			createResult = copyFile(event.getFile().getFileName(), event
					.getFile().getInputstream(), event.getFile());

			if (createResult.equalsIgnoreCase("success")) {

				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
						"Success! ", event.getFile().getFileName()
								+ " is uploaded.");
				FacesContext.getCurrentInstance().addMessage(null, msg);
				listReports = reportFacade.listStudentReports(authBean
						.getUser().getId());

			}

			else if (createResult.equals("exist")) {
				FacesMessage msg = new FacesMessage(
						FacesMessage.SEVERITY_ERROR,
						"Error! ",
						"Vous avez déjà téléchargé un doc du même nom, renommez votre fichier et réessayez!");
				FacesContext.getCurrentInstance().addMessage(null, msg);

				createResult = copyFile(event.getFile().getFileName(), event
						.getFile().getInputstream(), event.getFile());

			} else if (createResult.equals("directoryNotCreated")) {
				FacesMessage msg = new FacesMessage(
						FacesMessage.SEVERITY_FATAL, "Error",
						"Le fichier n'a pas pu être crée!");
				FacesContext.getCurrentInstance().addMessage(null, msg);

			}

		} catch (IOException e) {

			e.printStackTrace();
		}

	}

	public String copyFile(String fileName, InputStream in, UploadedFile file) {

		Student student = new Student();
		student = (Student) authBean.getUser();
		String destination = "C:\\PFE_Tools\\jboss-as-7.1.1.Final\\UploadedFiles\\"
				+ student.getRegistrationNumber() + "\\";

		boolean exist = fileExist(destination + fileName);
		File folderLocation = new File(destination);

		if (folderLocation.exists()) {
			if (!folderLocation.isDirectory()) {
				return "directoryNotCreated";
			}
		} else {
			if (!folderLocation.mkdirs()) {
				return "directoryNotCreated";
			}
		}

		if (exist) {

			return "exist";
		}

		else {

			// create object report
			Date uploadDate = new Date();
			String version = createVersion();
			reportToDB = new Report();
			reportToDB.setFileName(fileName);
			reportToDB.setFilePath(destination);
			reportToDB.setProject(student.getProject());
			reportToDB.setSize((file.getSize() / 1000));
			reportToDB.setUploadDate(uploadDate);

			if (finalVersion) {
				reportToDB.setState(ReportState.DEPOSED);
				reportToDB.setVersion("final");

			} else {

				reportToDB.setKeyWords(null);
				reportToDB.setState(ReportState.WAITINGDEPO);
				reportToDB.setVersion(version);
			}

			reportFacade.createReport(reportToDB, authBean.getUser().getId());

			try {
				OutputStream out = new FileOutputStream(new File(destination
						+ fileName));
				int read = 0;
				byte[] bytes = new byte[1024];
				while ((read = in.read(bytes)) != -1) {
					out.write(bytes, 0, read);
				}
				in.close();
				out.flush();
				out.close();

			} catch (IOException e) {
				System.out.println(e.getMessage());
				return "fileNotCreated";
			}

			return "success";
		}

	}

	/************ util methods ****************/
	private String createVersion() {
		String version;
		String lastVersion = reportFacade.getLastVersion(authBean.getUser()
				.getId());
		if (lastVersion == null) {
			version = "0";
		} else {
			int lastVersionInt = Integer.parseInt(lastVersion);
			int actualVersion = lastVersionInt + 1;
			version = String.valueOf(actualVersion);
		}
		return version;
	}

	public void toUpload(ActionEvent event) {
		onUpload = true;
		onMarkAsFinal = false;
	}

	public void markAsFinal(ActionEvent event) {
		if (selectedReport == null && onUpload == false) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Error! ", "Veuillez sélectionner un document!");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		} else {

			try {
				RequestContext.getCurrentInstance().execute(
						"confirmationFinal.show();");
			} catch (Exception e) {
			}

			onMarkAsFinal = true;
			onUpload = false;
		}
	}

	public void cancelFinalVersion(ActionEvent event) {
		finalVersion = false;
	}

	public void toDeleteReport(ActionEvent event) {
		if (selectedReport == null) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Error! ", "Veuillez sélectionner un document!");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		} else
			try {
				RequestContext.getCurrentInstance().execute(
						"confirmationDeleteDoc.show();");
			} catch (Exception e) {
			}
	}

	private boolean fileExist(String file_path) {
		File output_file = new File(file_path);
		return output_file.exists();
	}

	/********************** constructor ********************************/

	public ReportUploadBean() {
		super();

	}

	/************************** getter and setter ****************************/

	public void setAuthBean(AuthenticationBean authBean) {
		this.authBean = authBean;
	}

	public List<Report> getListReports() {
		return listReports;
	}

	public void setListReports(List<Report> listReports) {
		this.listReports = listReports;
	}

	public Report getReportToDB() {
		return reportToDB;
	}

	public void setReportToDB(Report reportToDB) {
		this.reportToDB = reportToDB;
	}

	public boolean isRenderUpload() {
		return renderUpload;
	}

	public void setRenderUpload(boolean renderUpload) {
		this.renderUpload = renderUpload;
	}

	public boolean isFinalVersion() {
		return finalVersion;
	}

	public void setFinalVersion(boolean finalVersion) {
		this.finalVersion = finalVersion;
	}

	public Report getSelectedReport() {
		return selectedReport;
	}

	public void setSelectedReport(Report selectedReport) {
		this.selectedReport = selectedReport;
	}

	public boolean isOnMarkAsFinal() {
		return onMarkAsFinal;
	}

	public void setOnMarkAsFinal(boolean onMarkAsFinal) {
		this.onMarkAsFinal = onMarkAsFinal;
	}

	public boolean isOnUpload() {
		return onUpload;
	}

	public void setOnUpload(boolean onUpload) {
		this.onUpload = onUpload;
	}

	public SimpleDateFormat getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(SimpleDateFormat dateFormat) {
		this.dateFormat = dateFormat;
	}

}
