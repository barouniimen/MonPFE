package org.esprit.gestion.rapports.MB;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;

import org.esprit.gestion.rapports.Model.ManagedProjects;
import org.esprit.gestion.rapports.persistence.Domain;
import org.esprit.gestion.rapports.persistence.Project;
import org.esprit.gestion.rapports.persistence.ProjectDomain;
import org.esprit.gestion.rapports.persistence.Student;
import org.esprit.gestion.rapports.persistence.Teacher;
import org.esprit.gestion.rapports.services.CRUD.Interfaces.IServiceLocal;
import org.esprit.gestion.rapports.services.CRUD.Util.DomainQualifier;
import org.esprit.gestion.rapports.services.facades.Interfaces.ICoachFacadeLocal;
import org.esprit.gestion.rapports.services.facades.Interfaces.ICorrectorFacadeLocal;
import org.esprit.gestion.rapports.services.facades.Interfaces.IProjectFacadeLocal;
import org.esprit.gestion.rapports.services.facades.Interfaces.IStudentFacadeLocal;
import org.primefaces.context.RequestContext;
import org.primefaces.model.DualListModel;

@ManagedBean
@RequestScoped
public class AddProjectFormBean {

	// var----------------------------
	private List<Student> studentList;
	private List<Teacher> listCoach;
	private Student studentToProject;
	private List<Teacher> listCorrectors;
	private List<Domain> listDomFromDB;
	private List<String> listDomSource;
	private List<String> listDomTarget;
	private DualListModel<String> dualListDomains;
	private String regNbre;
	private ManagedProjects projToAdd = new ManagedProjects();
	private List<ProjectDomain> projDomToDB;
	private Project projToDB;
	
	private String compCoachEmail;
	

	// EJB------------------------------
	@EJB
	private ICoachFacadeLocal coachFacade;
	@EJB
	private IStudentFacadeLocal studentFacade;
	@EJB
	private ICorrectorFacadeLocal correctorFacade;
	@EJB
	private IProjectFacadeLocal projFacade;
	
	
	// EJB service-------------------------
	@Inject
	@DomainQualifier
	IServiceLocal<Domain> domainServ;
	
	
	
/****************************************  init method  **********************************************/
	@PostConstruct
	public void init() {
		// init projectToAdd--------------------------------------
		projToAdd = new ManagedProjects();

		// init student to attach to project---------------------
		studentToProject = new Student();

		// init regNbre-------------------------------------------
		regNbre = null;

		// retrieve students list (not affected to project)-------
		studentList = new ArrayList<Student>();
		studentList = studentFacade.listStudentsWithoutProject();

		// retrieve all domains-----------------------------------
		listDomFromDB = new ArrayList<Domain>();
		listDomFromDB = domainServ.retrieveList(null, "ALL");
		listDomSource = new ArrayList<String>();
		listDomTarget = new ArrayList<String>();
		/** init list of domains to display on the selectManyListBox **/
		for (int i = 0; i < listDomFromDB.size(); i++) {
			listDomSource.add(listDomFromDB.get(i).getDomainName());
		}
		/** init Dual List **/
		dualListDomains = new DualListModel<String>(listDomSource,
				listDomTarget);

		//init var to DB--------------------------------------------------
		projDomToDB = new ArrayList<ProjectDomain>();
		projToDB =new Project();
		
		
		
	}

	/***************************** listners ************************************/
	
	
	public void addProject(ActionEvent event) {
		RequestContext context = RequestContext.getCurrentInstance();
	        FacesMessage msg = null;
	         boolean valid = false;
	         System.out.println(projToAdd.getProjTopic());
	         
	         /**************!!!!!!!!!!!!!!!!!!!!!!!!!!!! Conditions de validation à développer!!!!!!!!!!!!!!!!!!!!!! ******************/
	        if(projToAdd.getProjTopic().length()>5 ) {
	        	valid = false;
	            msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Erreur", "Veuillez vérifier les données saisies");
	            System.out.println(msg.getDetail());
	        } else {
	        	valid = true;
	            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Succès" , "Projet Ajouté");
	            System.out.println(msg.getDetail());
	        }
	         
	        FacesContext.getCurrentInstance().addMessage(null, msg);
	        context.addCallbackParam("valid", valid);
		
		
		
		/*//test!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		System.out.println("topic: " + projToAdd.getProjTopic());
		System.out.println("state: "
				+ projToAdd.getProjState().getDescription());
		System.out.println("student: " + studentToProject.getId());
		System.out.println("tab: " + listDomTarget.get(0));
		
		//init listDom---------------------------------------------------
		for (int i = 0; i < listDomTarget.size(); i++) {
			Domain d = new Domain();
			d.setDomainName(listDomTarget.get(i));
			ProjectDomain pd =  new ProjectDomain();
			pd.setDomain(d);
			projDomToDB.add(i, pd);
		
		}
		
		//init project-----------------------------------------------
		//projToDB.setAcademicYear(projToAdd.getAcademicYear());
		projToDB.setProjectDomains(projDomToDB);
		projToDB.setStartDate(projToAdd.getStartDate());
		projToDB.setTopic(projToAdd.getProjTopic());
		
		System.out.println("projFacade: "+projFacade);
		
		//add project to DB-----------------------------------------
		projFacade.addProjectToDB(projToDB, studentToProject);
*/
		//RESET----------------------------------------------------
		projToAdd = new ManagedProjects();
		studentToProject = new Student();
		listDomTarget = new ArrayList<String>();
		regNbre = null;
		projDomToDB = null;
		compCoachEmail = null;
	}

	public void populateStudentInfo(ValueChangeEvent event) {
		int id = (int) event.getNewValue();
		studentToProject.setId(id);
		for (int i = 0; i < studentList.size(); i++) {
			if (studentList.get(i).getId() == id) {
				studentToProject = studentList.get(i);
				
			}
		}
		
		regNbre = studentToProject.getRegistrationNumber();

	}
	
	public void onDomainChange(AjaxBehaviorEvent event) {
		// retrieve coach list--------------------------------
		
		listCoach = coachFacade.listerCoachDisponibles(10, listDomTarget);
		System.out.println("domtarget: "+listDomTarget.get(0));

	}
	
	public void test(){
		System.out.println("tests");
	}

	/************************ constructor *****************************/

	public AddProjectFormBean() {
		super();
		// TODO Auto-generated constructor stub
	}

	/*********************** getter && setter **************************/
	public List<Student> getStudentList() {
		return studentList;
	}

	public void setStudentList(List<Student> studentList) {
		this.studentList = studentList;
	}

	public List<Teacher> getListCoach() {
		return listCoach;
	}

	public void setListCoach(List<Teacher> listCoach) {
		this.listCoach = listCoach;
	}

	public Student getStudentToProject() {
		return studentToProject;
	}

	public void setStudentToProject(Student studentToProject) {
		this.studentToProject = studentToProject;
	}

	public List<Teacher> getListCorrectors() {
		return listCorrectors;
	}

	public void setListCorrectors(List<Teacher> listCorrectors) {
		this.listCorrectors = listCorrectors;
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

	public String getRegNbre() {
		return regNbre;
	}

	public void setRegNbre(String regNbre) {
		this.regNbre = regNbre;
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

}
