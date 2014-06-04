package org.esprit.gestion.rapports.MB;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;

import org.esprit.gestion.rapports.persistence.ClassGroup;
import org.esprit.gestion.rapports.persistence.StorageSpace;
import org.esprit.gestion.rapports.persistence.Student;
import org.esprit.gestion.rapports.services.CRUD.Interfaces.IServiceLocal;
import org.esprit.gestion.rapports.services.CRUD.Util.ClassGroupeQualifier;
import org.esprit.gestion.rapports.services.facades.Interfaces.IStudentFacadeLocal;
import org.primefaces.context.RequestContext;

@ManagedBean
@ViewScoped
public class AddStudentBean {

	private Student studentToDB;
	private int classId;
	private String academicYear;
	private StorageSpace space;
	private ClassGroup selectedClass;
	private List<ClassGroup> listClassFromDB;

	@EJB
	private IStudentFacadeLocal studentFacade;

	@Inject
	@ClassGroupeQualifier
	IServiceLocal<ClassGroup> classServ;

	/************************************* init method *****************************************/
	@PostConstruct
	public void init() {
		studentToDB = new Student();
		space = new StorageSpace();
		selectedClass = new ClassGroup();
		listClassFromDB = new ArrayList<ClassGroup>();
		listClassFromDB = classServ.retrieveList(null, "ALL");

	}

	/*********************************** actionListener *************************************/

	public void addStudent(ActionEvent event) {
		// TODO verifications de saisie!!!

		String resultCreate;
		studentToDB.setStorageSpace(space);
		classId = selectedClass.getId();

		resultCreate = studentFacade.addStudent(studentToDB, classId,
				academicYear);

		if (resultCreate.equals("reussi")) {
			try {
				RequestContext.getCurrentInstance().execute(
						" location.reload();");
			} catch (Exception e) {
			}
		} else if (resultCreate.equals("regNbreExist")) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Existe!!", "Le code d'inscription existe!");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		} else if (resultCreate.equals("loginExist")) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Existe!!", "Le login est déjà utilisé!");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		} else if (resultCreate.equals("passExist")) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Existe!!", "Le mot de passe est déjà utilisé!");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}

	}

	/************************************* constructor ***************************************/
	public AddStudentBean() {
		super();
	}

	public Student getStudentToDB() {
		return studentToDB;
	}

	public void setStudentToDB(Student studentToDB) {
		this.studentToDB = studentToDB;
	}

	public int getClassId() {
		return classId;
	}

	public void setClassId(int classId) {
		this.classId = classId;
	}

	public String getAcademicYear() {
		return academicYear;
	}

	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}

	public StorageSpace getSpace() {
		return space;
	}

	public void setSpace(StorageSpace space) {
		this.space = space;
	}

	public ClassGroup getSelectedClass() {
		return selectedClass;
	}

	public void setSelectedClass(ClassGroup selectedClass) {
		this.selectedClass = selectedClass;
	}

	public List<ClassGroup> getListClassFromDB() {
		return listClassFromDB;
	}

	public void setListClassFromDB(List<ClassGroup> listClassFromDB) {
		this.listClassFromDB = listClassFromDB;
	}

	/*********************************** getter & setter ***************************************/

}
