package org.esprit.gestion.rapports.MB;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;

import org.esprit.gestion.rapports.persistence.Report;
import org.esprit.gestion.rapports.persistence.Student;
import org.esprit.gestion.rapports.services.CRUD.Interfaces.IServiceLocal;
import org.esprit.gestion.rapports.services.CRUD.Util.StudentQualifier;
import org.esprit.gestion.rapports.services.facades.Interfaces.IProjectFacadeLocal;
import org.esprit.gestion.rapports.services.facades.Interfaces.IReportFacadeLocal;
import org.esprit.gestion.rapports.services.facades.Interfaces.IStudentFacadeLocal;
import org.primefaces.context.RequestContext;

@ManagedBean
@ViewScoped
public class ManageReportsByCoachBean {

	@ManagedProperty(value = "#{authenticationBean}")
	private AuthenticationBean authBean;

	@ManagedProperty(value = "#{tabViewIndexBean}")
	private TabViewIndexBean tabViewBean;

	private List<Student> allStudentCoached;

	private List<Student> allStudentCorrector;

	private String selectedProfile;

	private List<Student> studentList;
	private Student selectedStudent;
	private Student studentTogetPath;

	private boolean renderTableReports;
	private boolean renderNoReports;

	private List<Report> listStudentReports;
	private Report selectedReport;
	private int nbreReports;

	private SimpleDateFormat dateFormate;
	private String fileComments;

	private String filePath;
	private String sourcePath;
	private String sourceFileName;

	private boolean showDocument;

	private boolean disableDownload;

	@Inject
	@StudentQualifier
	IServiceLocal<Student> studentServ;

	@EJB
	IProjectFacadeLocal projFacade;

	@EJB
	IStudentFacadeLocal studentFacade;

	@EJB
	IReportFacadeLocal reportFacad;

	/******************************* init method *******************************/
	@PostConstruct
	public void init() {

		dateFormate = new SimpleDateFormat("dd-MM-yyyy");

		allStudentCoached = new ArrayList<Student>();
		allStudentCorrector = new ArrayList<Student>();
		selectedStudent = new Student();
		renderTableReports = false;
		renderNoReports = false;
		showDocument = false;
		studentTogetPath = new Student();

		disableDownload = false;
	}

	/****************************** action listeners ****************************/
	public void readComment(ActionEvent event) {
		if (selectedReport == null) {
			int tabIndex = tabViewBean.getTabIndex();

			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN,
					"Aucun document sélectionné",
					"Veuillez sélectionner un document");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			try {
				RequestContext.getCurrentInstance().execute(
						"location.reload();");
			} catch (Exception e) {
			}
			tabViewBean.setTabIndex(tabIndex);
		} else {

			selectedReport = reportFacad.findReport(selectedReport);

			try {
				RequestContext.getCurrentInstance().execute(
						"readCommentDialog.show();");
			} catch (Exception e) {
			}
		}
	}

	public void cancelAddComment(ActionEvent event) {
		fileComments = null;
		try {
			RequestContext.getCurrentInstance().execute("location.reload();");
		} catch (Exception e) {
		}
	}

	public void doAddComment(ActionEvent event) {
		System.out.println("on add comment!!!!!!!!!!!");

		reportFacad.addComment(selectedReport, authBean.getUser().getId(),
				fileComments);
		try {
			RequestContext.getCurrentInstance().execute("location.reload();");
		} catch (Exception e) {
		}

	}

	public void handleClose() {
		selectedReport = new Report();

		try {
			RequestContext.getCurrentInstance().execute(
					"location.reload();");
		} catch (Exception e) {
		}


	}

	public void addComment(ActionEvent event) {
		if (selectedReport == null) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN,
					"Aucun document sélectionné",
					"Veuillez sélectionner un document");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		} else {

			try {
				RequestContext.getCurrentInstance().execute(
						"addCommentDialog.show();");
			} catch (Exception e) {
			}

		}
	}

	public void onChangeProfile() {
		studentList = new ArrayList<Student>();

		renderNoReports = false;
		renderTableReports = false;
		if (selectedProfile.equals("coach")) {
			studentList = studentFacade.listCoachStudents(authBean.getUser()
					.getId());

			System.out.println("regnbre "
					+ studentList.get(0).getRegistrationNumber());

		} else if (selectedProfile.equals("corrector")) {

			studentList = studentFacade.listCorrectorStudents(authBean
					.getUser().getId());

		} else {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Aucun profil sélectionné",
					"Vous n'avez pas sélectionné de profil");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			studentList = new ArrayList<Student>();
		}

		if (studentList.isEmpty() && selectedProfile.equals("coach")) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Aucun étudiant", "Vous n'avez aucun étudiant à encadrer");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			studentList = new ArrayList<Student>();
		}

		else if (studentList.isEmpty() && selectedProfile.equals("corrector")) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Aucun étudiant", "Vous n'avez aucun rapport en cours");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			studentList = new ArrayList<Student>();
		}
	}

	public void onChangeStudent() {
		// find student reports

		if (selectedStudent.getId() != -1) {

			selectedStudent = (Student) studentServ.retrieve(selectedStudent,
					"ID");

			studentTogetPath = selectedStudent;

			listStudentReports = new ArrayList<Report>();
			listStudentReports = reportFacad.listStudentReports(selectedStudent
					.getId());

			if (listStudentReports.isEmpty()) {
				FacesMessage msg = new FacesMessage(
						FacesMessage.SEVERITY_INFO,
						"Aucun rapport",
						"L'étudiant(e): "
								+ selectedStudent.getLastName()
								+ " "
								+ selectedStudent.getFirstName()
								+ " n'a téléchargé aucun rapport pour le moment.");
				FacesContext.getCurrentInstance().addMessage(null, msg);
				renderNoReports = true;
				renderTableReports = false;

			} else {
				renderNoReports = false;
				renderTableReports = true;
				selectedReport = new Report();
				nbreReports = listStudentReports.size();

			}
		}

		else {
			renderNoReports = false;
			renderTableReports = false;
			selectedReport = new Report();
			selectedStudent = new Student();

		}

	}

	/******************************* constructor ******************************/
	public ManageReportsByCoachBean() {
		super();
	}

	/************************* getter && setter ********************************/
	public void setAuthBean(AuthenticationBean authBean) {
		this.authBean = authBean;
	}

	public void setTabViewBean(TabViewIndexBean tabViewBean) {
		this.tabViewBean = tabViewBean;
	}

	public List<Student> getAllStudentCoached() {
		return allStudentCoached;
	}

	public void setAllStudentCoached(List<Student> allStudentCoached) {
		this.allStudentCoached = allStudentCoached;
	}

	public List<Student> getAllStudentCorrector() {
		return allStudentCorrector;
	}

	public void setAllStudentCorrector(List<Student> allStudentCorrector) {
		this.allStudentCorrector = allStudentCorrector;
	}

	public String getSelectedProfile() {
		return selectedProfile;
	}

	public void setSelectedProfile(String selectedProfile) {
		this.selectedProfile = selectedProfile;
	}

	public List<Student> getStudentList() {
		return studentList;
	}

	public void setStudentList(List<Student> studentList) {
		this.studentList = studentList;
	}

	public Student getSelectedStudent() {
		return selectedStudent;
	}

	public void setSelectedStudent(Student selectedStudent) {
		this.selectedStudent = selectedStudent;
	}

	public boolean isRenderTableReports() {
		return renderTableReports;
	}

	public void setRenderTableReports(boolean renderTableReports) {
		this.renderTableReports = renderTableReports;
	}

	public boolean isRenderNoReports() {
		return renderNoReports;
	}

	public void setRenderNoReports(boolean renderNoReports) {
		this.renderNoReports = renderNoReports;
	}

	public List<Report> getListStudentReports() {
		return listStudentReports;
	}

	public void setListStudentReports(List<Report> listStudentReports) {
		this.listStudentReports = listStudentReports;
	}

	public Report getSelectedReport() {
		return selectedReport;
	}

	public void setSelectedReport(Report selectedReport) {
		this.selectedReport = selectedReport;
	}

	public int getNbreReports() {
		return nbreReports;
	}

	public void setNbreReports(int nbreReports) {
		this.nbreReports = nbreReports;
	}

	public SimpleDateFormat getDateFormate() {
		return dateFormate;
	}

	public void setDateFormate(SimpleDateFormat dateFormate) {
		this.dateFormate = dateFormate;
	}

	public String getFileComments() {
		return fileComments;
	}

	public void setFileComments(String fileComments) {
		this.fileComments = fileComments;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public boolean isShowDocument() {
		return showDocument;
	}

	public void setShowDocument(boolean showDocument) {
		this.showDocument = showDocument;
	}

	public Student getStudentTogetPath() {
		return studentTogetPath;
	}

	public void setStudentTogetPath(Student studentTogetPath) {
		this.studentTogetPath = studentTogetPath;
	}

	public boolean isDisableDownload() {
		return disableDownload;
	}

	public void setDisableDownload(boolean disableDownload) {
		this.disableDownload = disableDownload;
	}

	public String getSourcePath() {
		return sourcePath;
	}

	public void setSourcePath(String sourcePath) {
		this.sourcePath = sourcePath;
	}

	public String getSourceFileName() {
		return sourceFileName;
	}

	public void setSourceFileName(String sourceFileName) {
		this.sourceFileName = sourceFileName;
	}

}
