package org.esprit.gestion.rapports.MB;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;

import org.esprit.gestion.rapports.Model.ManagedProjects;
import org.esprit.gestion.rapports.persistence.ClassGroup;
import org.esprit.gestion.rapports.persistence.Company;
import org.esprit.gestion.rapports.persistence.CompanyCoach;
import org.esprit.gestion.rapports.persistence.Domain;
import org.esprit.gestion.rapports.persistence.Project;
import org.esprit.gestion.rapports.persistence.ProjectDomain;
import org.esprit.gestion.rapports.persistence.Student;
import org.esprit.gestion.rapports.persistence.ValidationState;
import org.esprit.gestion.rapports.services.CRUD.Interfaces.IServiceLocal;
import org.esprit.gestion.rapports.services.CRUD.Util.DomainQualifier;
import org.esprit.gestion.rapports.services.facades.Interfaces.IClassGroupFacadeLocal;
import org.esprit.gestion.rapports.services.facades.Interfaces.ICompanyCoachFacadeLocal;
import org.esprit.gestion.rapports.services.facades.Interfaces.ICompanyFacadeLocal;
import org.esprit.gestion.rapports.services.facades.Interfaces.IProjectFacadeLocal;
import org.esprit.gestion.rapports.services.facades.Interfaces.IStudentFacadeLocal;
import org.primefaces.event.FlowEvent;
import org.primefaces.model.DualListModel;

@ManagedBean
@ViewScoped
public class AddProjectFormBean {

	/***************************** VAR *************************************/
	// var add student------------------------------------------------------
	private List<Student> studentList;
	private Student studentToProject;
	private String selectedStudentName;
	private ClassGroup selectedStudentClass;

	// var add project-------------------------------------------------------
	private List<Domain> listDomFromDB;
	private List<String> listDomSource;
	private List<String> listDomTarget;
	private DualListModel<String> dualListDomains;
	private ManagedProjects projToAdd = new ManagedProjects();

	// var add company------------------------------------------------------
	private List<Company> existingCompanies;
	private boolean newCompany;
	private boolean notNewCompany;
	private String companyNameFromlist;
	private String companyAdressFromList;
	private String companyNameFromInput;
	private String companyAdressFromInput;

	// var add company coach------------------------------------------------
	private String compCoachEmail;
	private CompanyCoach compCoachFromInput;
	private CompanyCoach compCoachFromList;
	private boolean newCompCoach;
	private boolean notNewCompCoach;
	private List<CompanyCoach> existingCompCoach;
	private String compCoachPositionForList;
	private int compCoachPhoneForList;
	private String compCoachEmailForList;

	// var to Database-------------------------------------------------------
	private List<ProjectDomain> projDomToDB;
	private Project projToDB;
	private Company compToDB;
	private CompanyCoach compCoachToDB;
	private Student studentToDB;
	
	// var Update project
	private ManagedProjects selectedProj;

	/***************************** EJB Facade ******************************/
	@EJB
	private IStudentFacadeLocal studentFacade;
	@EJB
	private IProjectFacadeLocal projFacade;
	@EJB
	private ICompanyFacadeLocal companyFacade;
	@EJB
	private ICompanyCoachFacadeLocal compCoachFacade;
	@EJB
	private IClassGroupFacadeLocal classGpeFacade;

	/***************************** EJB Service ******************************/
	@Inject
	@DomainQualifier
	IServiceLocal<Domain> domainServ;

	/**********************************************************************************************************************/
	/************************************************ init method *********************************************************/
	/**********************************************************************************************************************/

	@PostConstruct
	public void init() {
		// ----------------------------------------------------------------------
		// PROJECT:
		// ----------------------------------------------------------------------
		projToAdd = new ManagedProjects();
		listDomFromDB = new ArrayList<Domain>();
		
		listDomFromDB = domainServ.retrieveList(null, "ALL");
		listDomSource = new ArrayList<String>();
		listDomTarget = new ArrayList<String>();

		for (int i = 0; i < listDomFromDB.size(); i++) {
			listDomSource.add(listDomFromDB.get(i).getDomainName());
		}

		dualListDomains = new DualListModel<String>(listDomSource,
				listDomTarget);

		// ----------------------------------------------------------------------
		// STUDENT:
		// ----------------------------------------------------------------------
		studentToProject = new Student();
		selectedStudentName = null;
		studentList = new ArrayList<Student>();
		studentList = studentFacade.listStudentsWithoutProject();

		// ----------------------------------------------------------------------
		// COMPANIES:
		// ----------------------------------------------------------------------
		existingCompanies = new ArrayList<Company>();
		existingCompanies = companyFacade.listAllComapnies();
		newCompany = false;
		notNewCompany = true;

		// ----------------------------------------------------------------------
		// COMPANY COACH
		// ----------------------------------------------------------------------
		compCoachFromInput = new CompanyCoach();
		compCoachFromList = new CompanyCoach();
		newCompCoach = false;
		notNewCompany = true;
		existingCompCoach = new ArrayList<CompanyCoach>();

		// ----------------------------------------------------------------------
		// INIT VAR TO DB
		// ----------------------------------------------------------------------
		projDomToDB = new ArrayList<ProjectDomain>();
		projToDB = new Project();
		studentToDB = new Student();
		compToDB = new Company();
		compCoachToDB = new CompanyCoach();

		//
		// INIT VAR Update Project
		//

	}

	/**********************************************************************************************************************/
	/******************************************** action listners *********************************************************/
	/**********************************************************************************************************************/

	/********************************** dialog events **********************************/
	// ----------------------------------------------------------------------
	// button close
	// ----------------------------------------------------------------------
	

	// ----------------------------------------------------------------------
	// button Cancel
	// ----------------------------------------------------------------------
	public void handelCancel() {
		projToAdd = new ManagedProjects();
		studentToProject = new Student();
		listDomTarget = new ArrayList<String>();
		selectedStudentName = null;
		projDomToDB = null;
		compCoachEmail = null;
	}

	// ----------------------------------------------------------------------
	// button add
	// ----------------------------------------------------------------------
	public void addProject(ActionEvent event) {
		// init listDom
		for (int i = 0; i < listDomTarget.size(); i++) {
			Domain d = new Domain();
			d.setDomainName(listDomTarget.get(i));
			ProjectDomain pd = new ProjectDomain();
			pd.setDomain(d);
			projDomToDB.add(i, pd);
		}

		// init company && company coach
		if (newCompany) {

			if (companyAdressFromInput.isEmpty()
					&& companyNameFromInput.isEmpty()) {

				compToDB = null;
				compCoachToDB = null;

			} else {

				compToDB.setAdress(companyAdressFromInput);
				compToDB.setName(companyNameFromInput);

				// compcoach init
				if (newCompCoach) {

					if (compCoachFromInput.getLastName().isEmpty()
							&& compCoachFromList.getFirstName().isEmpty()) {

						compCoachToDB = null;

					} else {

						if (compCoachEmail.isEmpty()) {
							compCoachToDB.setEmail(null);
						} else {
							compCoachToDB.setEmail(compCoachEmail);
						}

						compCoachToDB.setFirstName(compCoachFromInput
								.getFirstName());

						compCoachToDB.setLastName(compCoachFromInput
								.getLastName());

						if (compCoachFromInput.getPosition().isEmpty()) {
							compCoachToDB.setPosition(null);
						} else {
							compCoachToDB.setPosition(compCoachFromInput
									.getPosition());
						}

						if (!(String.valueOf(compCoachFromInput
								.getPhoneNumber()).isEmpty())
								|| !(compCoachFromInput.getPhoneNumber() == 0)) {

							compCoachToDB.setPhoneNumber(compCoachFromInput
									.getPhoneNumber());
						}

						compCoachToDB.setCompany(compToDB);

					}

				} else {

					if (compCoachFromList.getId() == -1) {

						compCoachToDB = null;
					} else {

						for (int i = 0; i < existingCompCoach.size(); i++) {
							if (existingCompCoach.get(i).getId() == compCoachFromList
									.getId()) {
								compCoachToDB = existingCompCoach.get(i);

							}
						}
					}

				}
				// end compCoach init
			}

		} else {

			if (companyNameFromlist.equals("nothing")) {

				compToDB = null;
				compCoachToDB = null;

			} else {

				compToDB.setAdress(companyAdressFromList);
				compToDB.setName(companyNameFromlist);

				// compcoach init
				if (newCompCoach) {

					if (compCoachFromInput.getLastName().isEmpty()
							&& compCoachFromList.getFirstName().isEmpty()) {

						compCoachToDB = null;

					} else {

						if (compCoachEmail.isEmpty()) {
							compCoachToDB.setEmail(null);
						} else {
							compCoachToDB.setEmail(compCoachEmail);
						}

						compCoachToDB.setFirstName(compCoachFromInput
								.getFirstName());

						compCoachToDB.setLastName(compCoachFromInput
								.getLastName());

						if (compCoachFromInput.getPosition().isEmpty()) {
							compCoachToDB.setPosition(null);
						} else {
							compCoachToDB.setPosition(compCoachFromInput
									.getPosition());
						}

						if (!(String.valueOf(compCoachFromInput
								.getPhoneNumber()).isEmpty())
								|| !(compCoachFromInput.getPhoneNumber() == 0)) {

							compCoachToDB.setPhoneNumber(compCoachFromInput
									.getPhoneNumber());
						}

						compCoachToDB.setCompany(compToDB);

					}

				} else {

					if (compCoachFromList.getId() == -1) {

						compCoachToDB = null;
					} else {

						for (int i = 0; i < existingCompCoach.size(); i++) {
							if (existingCompCoach.get(i).getId() == compCoachFromList
									.getId()) {
								compCoachToDB = existingCompCoach.get(i);

							}
						}
					}

				}
				// end compCoach init
			}

		}
		// end company && compcoach init

		// Student init
		if (studentToProject.getId() == -1) {

			studentToDB = null;

		} else {

			studentToDB = studentToProject;

		}
		// end student init

		// init project-----------------------------------------------

		projToDB.setAcademicYear(projToAdd.getAcademicYear());
		projToDB.setTopic(projToAdd.getProjTopic());
		projToDB.setStartDate(projToAdd.getStartDate());
		projToDB.setProjectDomains(projDomToDB);
		projToDB.setValidationState(projToAdd.getProjState());
		projToDB.setCompanycoach(compCoachToDB);
		projToDB.setStudent(studentToDB);
		// end init project
		for (int j = 0; j < projToDB.getProjectDomains().size(); j++) {
			System.out.println("proj dom: "+j+" "+projToDB.getProjectDomains().get(j));
		}

		// add project to DB-----------------------------------------
		projFacade.addProjectToDB(projToDB, newCompCoach);
	}

	/****************************** add student form events **********************************/
	// ----------------------------------------------------------------------
	// when selecting a student
	// ----------------------------------------------------------------------
	public void populateStudentInfo(ValueChangeEvent event) {
		int id = (int) event.getNewValue();
		studentToProject.setId(id);
		for (int i = 0; i < studentList.size(); i++) {
			if (studentList.get(i).getId() == id) {
				studentToProject = studentList.get(i);
			}
		}
		setSelectedStudentName(studentToProject.getFirstName() + " "
				+ studentToProject.getLastName());
		selectedStudentClass = classGpeFacade.findByAcademicYearForStudent(
				studentToProject, projToAdd.getAcademicYear());

	}

	/******************************** wizard events ****************************************/
	// ----------------------------------------------------------------------
	// onFlowProcess
	// ----------------------------------------------------------------------
	public String onFlowProcess(FlowEvent event) {

		// PROJECT
		if (event.getOldStep().equals("project")) {
			if (isAlpha(projToAdd.getProjTopic()) == false) {
				FacesMessage msg = new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "Format invalide",
						"Le sujet ne doit pas contenir de chiffres ou de caractères spéciaux!");
				FacesContext.getCurrentInstance().addMessage(null, msg);
				return event.getOldStep();
			}

			else if (validAcademicYear(projToAdd.getAcademicYear()) == false) {
				FacesMessage msg = new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "Format invalide",
						"L'anné universitaire doit être de la forme [année1 - année+1]");
				FacesContext.getCurrentInstance().addMessage(null, msg);
				return event.getOldStep();
			}

			else if (validStartDate(projToAdd.getStartDate()) == false) {
				FacesMessage msg = new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "Date invalide",
						"La date de début ne correspond pas à l'année scolaire!");
				FacesContext.getCurrentInstance().addMessage(null, msg);
				return event.getOldStep();
			}
		}

		// COMPANY FROM INPUT
		else if (event.getOldStep().equals("company") && newCompany == true) {
			if (companyNameFromInput.length() > 10) {
				FacesMessage msg = new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "Taille invalide",
						"La taille du nom de la company doit être < 10");
				FacesContext.getCurrentInstance().addMessage(null, msg);
				return event.getOldStep();
			}

			else if (!companyNameFromInput.isEmpty()
					&& companyAdressFromInput.isEmpty()) {
				FacesMessage msg = new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "Donnée incomplète",
						"Veuillez indiquer l'adresse");
				FacesContext.getCurrentInstance().addMessage(null, msg);
				return event.getOldStep();
			}

			else if (companyNameFromInput.isEmpty()
					&& !companyAdressFromInput.isEmpty()) {
				FacesMessage msg = new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "Donnée incomplète",
						"Veuillez indiquer le nom de l'entreprise");
				FacesContext.getCurrentInstance().addMessage(null, msg);
				return event.getOldStep();
			}

			else if (!companyNameFromInput.isEmpty()
					&& !companyAdressFromInput.isEmpty()) {

				projToAdd.setCompanyAdress(companyAdressFromInput);
				projToAdd.setCompanyName(companyNameFromInput);
				Company company = new Company();
				company.setAdress(companyAdressFromInput);
				company.setName(companyNameFromlist);
				existingCompCoach = compCoachFacade
						.compCoachSameCompany(company);
				return event.getNewStep();
			} else if (companyNameFromInput.isEmpty()
					&& companyAdressFromInput.isEmpty()) {

				compCoachFromInput = null;
				compCoachFromList = null;
				return "confirm";
			}
		}

		// COMPANY FROM LIST
		else if (event.getOldStep().equals("company") && notNewCompany == true) {

			if (companyNameFromlist.equals("nothing")) {
				projToAdd.setCompanyAdress(null);
				projToAdd.setCompanyName(null);
				return "confirm";
			} else {

				projToAdd.setCompanyAdress(companyAdressFromList);
				projToAdd.setCompanyName(companyNameFromlist);
				Company company = new Company();

				company.setAdress(companyAdressFromList);
				company.setName(companyNameFromlist);

				existingCompCoach = compCoachFacade
						.compCoachSameCompany(company);

				return event.getNewStep();
			}
		}

		// COMPANY COACH
		else if (event.getOldStep().equals("compCoach") && newCompCoach == true) {
			if (compCoachFromInput.getFirstName().isEmpty()) {
				FacesMessage msg = new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "Donnée incomplète: ",
						"Le nom est obligatoire!");
				FacesContext.getCurrentInstance().addMessage(null, msg);
				return event.getOldStep();
			} else if (compCoachFromInput.getLastName().isEmpty()) {
				FacesMessage msg = new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "Donnée incomplète: ",
						"Le prénom est obligatoire!");
				FacesContext.getCurrentInstance().addMessage(null, msg);
				return event.getOldStep();
			}

			else if (validPhoneNbre(compCoachFromInput.getPhoneNumber()) == false) {
				FacesMessage msg = new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "Format invalide: ",
						"Le numéro de tél doit être composé de 8 chiffres");
				FacesContext.getCurrentInstance().addMessage(null, msg);
				return event.getOldStep();
			}

			else if (compCoachFromInput.getPhoneNumber() == 0
					&& compCoachEmail.isEmpty()) {
				FacesMessage msg = new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "Données incomplètes: ",
						"Vous devez renseigner l'email ou le numéro de téléphone!");
				FacesContext.getCurrentInstance().addMessage(null, msg);
				return event.getOldStep();
			}

			else if (isAlpha(compCoachFromInput.getFirstName()) == false) {
				FacesMessage msg = new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "Format invalide: ",
						"Le prénom ne peut pas contenir de chiffres!");
				FacesContext.getCurrentInstance().addMessage(null, msg);
				return event.getOldStep();
			}

			else if (isAlpha(compCoachFromInput.getLastName()) == false) {
				FacesMessage msg = new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "Format invalide: ",
						"Le nom ne peut pas contenir de chiffres!");
				FacesContext.getCurrentInstance().addMessage(null, msg);
				return event.getOldStep();
			}

			else if (isAlpha(compCoachFromInput.getPosition()) == false) {
				FacesMessage msg = new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "Format invalide: ",
						"Le poste ne peut pas contenir de chiffres!");
				FacesContext.getCurrentInstance().addMessage(null, msg);
				return event.getOldStep();
			}

		}

		// DEFAULT
		return event.getNewStep();
	}

	/****************************** add company form events ************************************/
	// ----------------------------------------------------------------------
	// add new company commandButton
	// ----------------------------------------------------------------------
	public void addCompanyRendered() {
		newCompany = true;
		notNewCompany = false;
	}

	// ----------------------------------------------------------------------
	// add company or select company rendered
	// ----------------------------------------------------------------------
	public void selectCompanyRendered() {
		newCompany = false;
		notNewCompany = true;
	}

	// ----------------------------------------------------------------------
	// update adress from selcted company
	// ----------------------------------------------------------------------
	public void populateCompanyInfo(ValueChangeEvent event) {
		String name = (String) event.getNewValue();
		for (int i = 0; i < existingCompanies.size(); i++) {
			if (existingCompanies.get(i).getName().equals(name)) {
				companyAdressFromList = existingCompanies.get(i).getAdress();

			}

		}

	}

	/****************************** add company coach form events ************************************/

	public void addCompCoachRendered() {
		newCompCoach = true;
		notNewCompCoach = false;
	}

	public void selectCompCoachRendered() {
		newCompCoach = false;
		notNewCompCoach = true;
	}

	// ----------------------------------------------------------------------
	// update compCoach Infos from selected compCoach
	// ----------------------------------------------------------------------
	public void populateCompCoachInfo(ValueChangeEvent event) {
		int id = (int) event.getNewValue();
		for (int i = 0; i < existingCompCoach.size(); i++) {
			if (existingCompCoach.get(i).getId() == id) {
				compCoachEmailForList = existingCompCoach.get(i).getEmail();
				compCoachPhoneForList = existingCompCoach.get(i)
						.getPhoneNumber();
				compCoachPositionForList = existingCompCoach.get(i)
						.getPosition();
			}

		}

	}

	
	/********************************** validation methods *************************************/
	// ----------------------------------------------------------------------
	// String with only carcters
	// ----------------------------------------------------------------------

	public boolean isAlpha(String string) {
		boolean valid = false;
		if (string.matches("[a-zA-Zéèçàôêû ]+")) {
			valid = true;
		}
		return valid;
	}

	// ----------------------------------------------------------------------
	// project validation state
	// ----------------------------------------------------------------------
	public boolean validVstate(ValidationState state) {
		boolean valid = false;

		return valid;
	}

	// ----------------------------------------------------------------------
	// domain list with no conflicts
	// ----------------------------------------------------------------------
	public boolean validDomainList(List<ProjectDomain> domList) {
		boolean valid = false;

		return valid;
	}

	// ----------------------------------------------------------------------
	// start date within the academic year
	// ----------------------------------------------------------------------
	public boolean validStartDate(Date startDate) {
		boolean valid = false;

		String date = startDate.toString();
		int beginIndex = date.length() - 4;
		int EndIndex = date.length();

		String year = date.substring(beginIndex, EndIndex);
		int yearInt = Integer.parseInt(year);

		if (projToAdd.getAcademicYear() == null) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Champ requis", "Vous devez renseigner l'année scolaire!");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			valid = false;
		} else if (validAcademicYear(projToAdd.getAcademicYear())) {
			String year2 = projToAdd.getAcademicYear().substring(7, 11);
			int y2 = Integer.parseInt(year2);

			if ((yearInt == y2) || (yearInt == (y2 - 1))) {
				valid = true;
			}

		}

		return valid;
	}

	// ----------------------------------------------------------------------
	// company adress
	// ----------------------------------------------------------------------
	public boolean validCompAdress(String compAdress) {
		boolean valid = false;

		return valid;

	}

	// ----------------------------------------------------------------------
	// academic year (y - y+2)
	// ----------------------------------------------------------------------
	public boolean validAcademicYear(String academicYear) {
		boolean valid = false;

		String year1 = academicYear.substring(0, 4);
		String year2 = academicYear.substring(7, 11);
		int y1 = Integer.parseInt(year1);
		int y2 = Integer.parseInt(year2);
		int compareYears;
		compareYears = y2 - y1;

		if (compareYears == 1) {
			valid = true;
		}

		return valid;
	}

	// ----------------------------------------------------------------------
	// phone number
	// ----------------------------------------------------------------------
	private boolean validPhoneNbre(int phoneNumber) {
		boolean valid = false;
		if (String.valueOf(phoneNumber).matches("([0-9]{8})?")) {
			valid = true;
		} else if (phoneNumber == 0) {
			valid = true;
		}

		return valid;
	}

	/**********************************************************************************************************************/
	/************************************************ constructor *********************************************************/
	/**********************************************************************************************************************/
	public AddProjectFormBean() {
		super();
	}

	/**********************************************************************************************************************/
	/********************************************** getter && setter ******************************************************/
	/**********************************************************************************************************************/
	public List<Student> getStudentList() {
		return studentList;
	}

	public void setStudentList(List<Student> studentList) {
		this.studentList = studentList;
	}

	public Student getStudentToProject() {
		return studentToProject;
	}

	public void setStudentToProject(Student studentToProject) {
		this.studentToProject = studentToProject;
	}

	public List<Domain> getListDomFromDB() {
		return listDomFromDB;
	}

	public void setListDomFromDB(List<Domain> listDomFromDB) {
		this.listDomFromDB = listDomFromDB;
	}

	public List<String> getListDomSource() {
		return listDomSource;
	}

	public void setListDomSource(List<String> listDomSource) {
		this.listDomSource = listDomSource;
	}

	public List<String> getListDomTarget() {
		return listDomTarget;
	}

	public void setListDomTarget(List<String> listDomTarget) {
		this.listDomTarget = listDomTarget;
	}

	public DualListModel<String> getDualListDomains() {
		return dualListDomains;
	}

	public void setDualListDomains(DualListModel<String> dualListDomains) {
		this.dualListDomains = dualListDomains;
	}

	public ManagedProjects getProjToAdd() {
		return projToAdd;
	}

	public void setProjToAdd(ManagedProjects projToAdd) {
		this.projToAdd = projToAdd;
	}

	public List<ProjectDomain> getProjDomToDB() {
		return projDomToDB;
	}

	public void setProjDomToDB(List<ProjectDomain> projDomToDB) {
		this.projDomToDB = projDomToDB;
	}

	public Project getProjToDB() {
		return projToDB;
	}

	public void setProjToDB(Project projToDB) {
		this.projToDB = projToDB;
	}

	public String getCompCoachEmail() {
		return compCoachEmail;
	}

	public void setCompCoachEmail(String compCoachEmail) {
		this.compCoachEmail = compCoachEmail;
	}

	public List<Company> getExistingCompanies() {
		return existingCompanies;
	}

	public void setExistingCompanies(List<Company> existingCompanies) {
		this.existingCompanies = existingCompanies;
	}

	public boolean isNewCompany() {
		return newCompany;
	}

	public void setNewCompany(boolean newCompany) {
		this.newCompany = newCompany;
	}

	public boolean isNotNewCompany() {
		return notNewCompany;
	}

	public void setNotNewCompany(boolean notNewCompany) {
		this.notNewCompany = notNewCompany;
	}

	public String getCompanyNameFromlist() {
		return companyNameFromlist;
	}

	public void setCompanyNameFromlist(String companyNameFromlist) {
		this.companyNameFromlist = companyNameFromlist;
	}

	public String getCompanyAdressFromList() {
		return companyAdressFromList;
	}

	public void setCompanyAdressFromList(String companyAdressFromList) {
		this.companyAdressFromList = companyAdressFromList;
	}

	public String getCompanyNameFromInput() {
		return companyNameFromInput;
	}

	public void setCompanyNameFromInput(String companyNameFromInput) {
		this.companyNameFromInput = companyNameFromInput;
	}

	public String getCompanyAdressFromInput() {
		return companyAdressFromInput;
	}

	public void setCompanyAdressFromInput(String companyAdressFromInput) {
		this.companyAdressFromInput = companyAdressFromInput;
	}

	public CompanyCoach getCompCoachFromInput() {
		return compCoachFromInput;
	}

	public void setCompCoachFromInput(CompanyCoach compCoachFromInput) {
		this.compCoachFromInput = compCoachFromInput;
	}

	public CompanyCoach getCompCoachFromList() {
		return compCoachFromList;
	}

	public void setCompCoachFromList(CompanyCoach compCoachFromList) {
		this.compCoachFromList = compCoachFromList;
	}

	public boolean isNewCompCoach() {
		return newCompCoach;
	}

	public void setNewCompCoach(boolean newCompCoach) {
		this.newCompCoach = newCompCoach;
	}

	public boolean isNotNewCompCoach() {
		return notNewCompCoach;
	}

	public void setNotNewCompCoach(boolean notNewCompCoach) {
		this.notNewCompCoach = notNewCompCoach;
	}

	public List<CompanyCoach> getExistingCompCoach() {
		return existingCompCoach;
	}

	public void setExistingCompCoach(List<CompanyCoach> existingCompCoach) {
		this.existingCompCoach = existingCompCoach;
	}

	public String getCompCoachEmailForList() {
		return compCoachEmailForList;
	}

	public void setCompCoachEmailForList(String compCoachEmailForList) {
		this.compCoachEmailForList = compCoachEmailForList;
	}

	public String getCompCoachPositionForList() {
		return compCoachPositionForList;
	}

	public void setCompCoachPositionForList(String compCoachPositionForList) {
		this.compCoachPositionForList = compCoachPositionForList;
	}

	public int getCompCoachPhoneForList() {
		return compCoachPhoneForList;
	}

	public void setCompCoachPhoneForList(int compCoachPhoneForList) {
		this.compCoachPhoneForList = compCoachPhoneForList;
	}

	public Company getCompToDB() {
		return compToDB;
	}

	public void setCompToDB(Company compToDB) {
		this.compToDB = compToDB;
	}

	public CompanyCoach getCompCoachToDB() {
		return compCoachToDB;
	}

	public void setCompCoachToDB(CompanyCoach compCoachToDB) {
		this.compCoachToDB = compCoachToDB;
	}

	public Student getStudentToDB() {
		return studentToDB;
	}

	public void setStudentToDB(Student studentToDB) {
		this.studentToDB = studentToDB;
	}

	public String getSelectedStudentName() {
		return selectedStudentName;
	}

	public void setSelectedStudentName(String selectedStudentName) {
		this.selectedStudentName = selectedStudentName;
	}

	public ClassGroup getSelectedStudentClass() {
		return selectedStudentClass;
	}

	public void setSelectedStudentClass(ClassGroup selectedStudentClass) {
		this.selectedStudentClass = selectedStudentClass;
	}

	public ManagedProjects getSelectedProj() {
		return selectedProj;
	}

	public void setSelectedProj(ManagedProjects selectedProj) {
		this.selectedProj = selectedProj;
	}

}
