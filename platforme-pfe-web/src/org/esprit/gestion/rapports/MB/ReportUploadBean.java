package org.esprit.gestion.rapports.MB;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.esprit.gestion.rapports.persistence.KeyWord;
import org.esprit.gestion.rapports.persistence.KeyWordCategory;
import org.esprit.gestion.rapports.persistence.Report;
import org.esprit.gestion.rapports.persistence.ReportState;
import org.esprit.gestion.rapports.persistence.Student;
import org.esprit.gestion.rapports.services.facades.Interfaces.IKeyWordFacadeLocal;
import org.esprit.gestion.rapports.services.facades.Interfaces.IProjectFacadeLocal;
import org.esprit.gestion.rapports.services.facades.Interfaces.IReportFacadeLocal;
import org.esprit.gestion.rapports.services.facades.Interfaces.ISubmissionFacadeLocal;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.FlowEvent;
import org.primefaces.model.DualListModel;
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
	private boolean markAsFinal;
	private boolean onUpload;
	private SimpleDateFormat dateFormat;
	private String fileToUploadName;
	private InputStream inputStream;
	private long fileSize;
	private String fileDescription;
	private boolean renderAddKw;
	private List<String> listKeyWordSource;
	private List<String> listKeyWordTarget;
	private KeyWordCategory selectedCategory;
	private boolean kwfound;
	private List<KeyWord> listKeyWordFromDB;
	private List<String> listKeyWordToDB;
	private DualListModel<String> dualListKeyWord;
	private boolean newKeyWord;
	private String newCategoryToAdmin;
	private String newKeyWordToAdmin;
	private boolean submitSessionOpened;
	private boolean lastedSixMonth;

	// MBean-------------------------------------------
	@ManagedProperty(value = "#{tabViewIndexBean}")
	private TabViewIndexBean tabViewBean;

	public void setTabViewBean(TabViewIndexBean tabViewBean) {
		this.tabViewBean = tabViewBean;
	}

	@EJB
	IReportFacadeLocal reportFacade;

	@EJB
	IKeyWordFacadeLocal keyWordFacade;

	@EJB
	ISubmissionFacadeLocal submissionFacade;
	
	@EJB
	IProjectFacadeLocal projFacad;

	/****************************** init method ***************************/
	@PostConstruct
	public void init() {
		finalVersion = false;
		selectedReport = new Report();
		markAsFinal = false;
		reportToDB = new Report();
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
		selectedCategory = new KeyWordCategory();
		listKeyWordSource = new ArrayList<String>();
		listKeyWordTarget = new ArrayList<String>();

		renderAddKw = false;
		kwfound = false;
		listKeyWordToDB = new ArrayList<String>();

		newKeyWord = false;

		submitSessionOpened = submissionFacade.sessionOpened();
		lastedSixMonth = projFacad.lastedSixMonth(authBean.getUser().getId());
	}

	/*********************** listeners ********************************/
	public void askForNewKeyWord(ActionEvent event) {
		keyWordFacade.askNewKeyWord(newCategoryToAdmin, newKeyWordToAdmin,
				(Student) authBean.getUser());

		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
				"Message envoyé", "L'administrateur traitera votre requête");
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void onChangeCategory() {
		listKeyWordSource.clear();
		listKeyWordFromDB = keyWordFacade.keyWordsByCateg(selectedCategory);
		setDualListKeyWord(new DualListModel<>(listKeyWordSource,
				listKeyWordTarget));

		if (listKeyWordFromDB.isEmpty()) {
			kwfound = false;

		} else {
			for (int i = 0; i < listKeyWordFromDB.size(); i++) {
				listKeyWordSource.add(listKeyWordFromDB.get(i).getName());
			}
			kwfound = true;
		}

	}

	public void addSelectedKw(ActionEvent event) {

		listKeyWordToDB.addAll(listKeyWordTarget);

		// hashset to remove duplicated values!
		HashSet<String> hs = new HashSet<String>();
		hs.addAll(listKeyWordToDB);
		listKeyWordToDB.clear();
		listKeyWordToDB.addAll(hs);
		listKeyWordTarget.clear();
		listKeyWordSource.clear();
		setNewKeyWord(true);
		selectedCategory = new KeyWordCategory();
		kwfound = false;

	}

	public void handleClose() {
		int tabIndex;
		tabIndex = tabViewBean.getTabIndex();
		try {
			RequestContext.getCurrentInstance().execute("location.reload();");
		} catch (Exception e) {
		}
		tabViewBean.setTabIndex(tabIndex);
	}

	public String onFlowProcess(FlowEvent event) {
		if (event.getOldStep().equals("upload")) {
			if (fileToUploadName == null) {
				FacesMessage msg = new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "Aucun fichier",
						"Veuillez sélectionner un fichier à télécharger!");
				FacesContext.getCurrentInstance().addMessage(null, msg);
				return event.getOldStep();
			}

		}

		return event.getNewStep();

	}

	public void confirmFinalVersion(ActionEvent event) {
		if (submitSessionOpened && lastedSixMonth) {
			/*
			 * markAsFinal=true if button marquer comme finale is pressed and
			 * not on upload new file
			 */
			if (markAsFinal) {
				reportFacade.changeToFinal(selectedReport);
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
						"Depôt réussi",
						"Vous avez déposé la version finale de votre rapport");
				FacesContext.getCurrentInstance().addMessage(null, msg);
			} else {
				renderAddKw = true;
			}
		}

		else {
			
			if(!submitSessionOpened){
			FacesMessage msg = new FacesMessage(
					FacesMessage.SEVERITY_WARN,
					"Pas de session",
					"Aucune session de dépôt n'est ouverte en ce moment, vous ne pouvez pas déposer une version finale!");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			}
			if(!lastedSixMonth){
				FacesMessage msg = new FacesMessage(
						FacesMessage.SEVERITY_WARN,
						"Moins de 6 mois",
						"Votre projet a duré moins que 6 mois, vous ne pouvez pas le déposer maintenant!");
				FacesContext.getCurrentInstance().addMessage(null, msg);
			}
			markAsFinal = false;
			finalVersion = false;
		}

		markAsFinal = false;
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

	public void doUpload(ActionEvent event) {
		// TODO clear tmp directory
		
		/*
		 * String tmpDestination =
		 * "C:\\PFE_Tools\\jboss-as-7.1.1.Final\\tmpUpload"; File tmpDirectory =
		 * new File(tmpDestination); try {
		 * FileUtils.cleanDirectory(tmpDirectory);
		 * System.out.println("did clear!!!!!!!!!!!"); } catch (IOException e1)
		 * { System.out.println("didnt clear!!!!!!!!!!!!"); 
		 * Auto-generated catch block e1.printStackTrace(); }
		 */

		int tabIndex;
		tabIndex = tabViewBean.getTabIndex();

		reportFacade.createReport(reportToDB, authBean.getUser().getId(),
				listKeyWordToDB);
		Student student = new Student();
		student = (Student) authBean.getUser();
		String destination = "C:\\PFE_Tools\\jboss-as-7.1.1.Final\\UploadedFiles\\"
				+ student.getRegistrationNumber() + "\\";

		try {
			OutputStream out = new FileOutputStream(new File(destination
					+ fileToUploadName));
			int read = 0;
			byte[] bytes = new byte[1024];
			while ((read = inputStream.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}
			inputStream.close();
			out.flush();
			out.close();

		} catch (IOException e) {
			System.out.println(e.getMessage());

		}

		try {
			RequestContext.getCurrentInstance().execute("location.reload();");
		} catch (Exception e) {
		}
		tabViewBean.setTabIndex(tabIndex);

	}

	public void fileUpload(FileUploadEvent event) throws IOException {
		String createResult;

		fileToUploadName = event.getFile().getFileName();
		inputStream = event.getFile().getInputstream();

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

		boolean exist = fileExist(destination + fileName);
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
			fileSize = file.getSize() / 1000;
			reportToDB.setSize(fileSize);
			reportToDB.setUploadDate(uploadDate);

			reportToDB.setDescription(fileDescription);

			if (finalVersion) {
				reportToDB.setState(ReportState.DEPOSED);
				reportToDB.setVersion("final");

			} else {

				reportToDB.setKeyWords(null);
				reportToDB.setState(ReportState.WAITINGDEPO);
				reportToDB.setVersion(version);
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
		markAsFinal = false;
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

			markAsFinal = true;
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

	public String getFileToUploadName() {
		return fileToUploadName;
	}

	public void setFileToUploadName(String fileToUploadName) {
		this.fileToUploadName = fileToUploadName;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public long getFileSize() {
		return fileSize;
	}

	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}

	public String getFileDescription() {
		return fileDescription;
	}

	public void setFileDescription(String fileDescription) {
		this.fileDescription = fileDescription;
	}

	public boolean isMarkAsFinal() {
		return markAsFinal;
	}

	public void setMarkAsFinal(boolean markAsFinal) {
		this.markAsFinal = markAsFinal;
	}

	public boolean isRenderAddKw() {
		return renderAddKw;
	}

	public void setRenderAddKw(boolean renderAddKw) {
		this.renderAddKw = renderAddKw;
	}

	public KeyWordCategory getSelectedCategory() {
		return selectedCategory;
	}

	public void setSelectedCategory(KeyWordCategory selectedCategory) {
		this.selectedCategory = selectedCategory;
	}

	public boolean isKwfound() {
		return kwfound;
	}

	public void setKwfound(boolean kwfound) {
		this.kwfound = kwfound;
	}

	public boolean isNewKeyWord() {
		return newKeyWord;
	}

	public void setNewKeyWord(boolean newKeyWord) {
		this.newKeyWord = newKeyWord;
	}

	public List<KeyWord> getListKeyWordFromDB() {
		return listKeyWordFromDB;
	}

	public void setListKeyWordFromDB(List<KeyWord> listKeyWordFromDB) {
		this.listKeyWordFromDB = listKeyWordFromDB;
	}

	public DualListModel<String> getDualListKeyWord() {
		return dualListKeyWord;
	}

	public void setDualListKeyWord(DualListModel<String> dualListKeyWord) {
		this.dualListKeyWord = dualListKeyWord;
	}

	public List<String> getListKeyWordSource() {
		return listKeyWordSource;
	}

	public void setListKeyWordSource(List<String> listKeyWordSource) {
		this.listKeyWordSource = listKeyWordSource;
	}

	public List<String> getListKeyWordTarget() {
		return listKeyWordTarget;
	}

	public void setListKeyWordTarget(List<String> listKeyWordTarget) {
		this.listKeyWordTarget = listKeyWordTarget;
	}

	public List<String> getListKeyWordToDB() {
		return listKeyWordToDB;
	}

	public void setListKeyWordToDB(List<String> listKeyWordToDB) {
		this.listKeyWordToDB = listKeyWordToDB;
	}

	public String getNewCategoryToAdmin() {
		return newCategoryToAdmin;
	}

	public void setNewCategoryToAdmin(String newCategoryToAdmin) {
		this.newCategoryToAdmin = newCategoryToAdmin;
	}

	public String getNewKeyWordToAdmin() {
		return newKeyWordToAdmin;
	}

	public void setNewKeyWordToAdmin(String newKeyWordToAdmin) {
		this.newKeyWordToAdmin = newKeyWordToAdmin;
	}

	public boolean isSubmitSessionOpened() {
		return submitSessionOpened;
	}

	public void setSubmitSessionOpened(boolean submitSessionOpened) {
		this.submitSessionOpened = submitSessionOpened;
	}

	public boolean isLastedSixMonth() {
		return lastedSixMonth;
	}

	public void setLastedSixMonth(boolean lastedSixMonth) {
		this.lastedSixMonth = lastedSixMonth;
	}

}
