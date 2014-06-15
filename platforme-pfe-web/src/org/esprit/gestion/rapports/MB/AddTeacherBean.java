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

import org.esprit.gestion.rapports.persistence.Teacher;
import org.esprit.gestion.rapports.persistence.TeacherGrade;
import org.esprit.gestion.rapports.persistence.TeachingUnit;
import org.esprit.gestion.rapports.services.facades.Interfaces.ITeacherFacadeLocal;
import org.esprit.gestion.rapports.services.facades.Interfaces.ITeachingUnitFacadeLocal;
import org.primefaces.context.RequestContext;
import org.primefaces.event.ToggleEvent;

@ManagedBean
@ViewScoped
public class AddTeacherBean {
	private Teacher teacherToDB;
	private List<TeachingUnit> allTeachingUnit;
	private final static TeacherGrade[] listGrades;
	private boolean affectToUP;
	private boolean notAffectToUP;
	private List<TeachingUnit> listTeachUnitFromDB;
	private boolean noUPdispo;
	private boolean upDispo;
	private TeachingUnit selectedTeachUnit;

	// intit listStates--------------------------------
	static {
		listGrades = new TeacherGrade[6];
		listGrades[0] = TeacherGrade.AD;
		listGrades[1] = TeacherGrade.AT;
		listGrades[2] = TeacherGrade.PES;
		listGrades[3] = TeacherGrade.ST;
		listGrades[4] = TeacherGrade.T;
		listGrades[5] = TeacherGrade.VAC;
	}

	/****************** EJB facade ******************/
	@EJB
	ITeacherFacadeLocal teacherFacade;
	@EJB
	ITeachingUnitFacadeLocal tUnitFacade;

	/************************************* init method *****************************************/
	@PostConstruct
	public void init() {
		teacherToDB = new Teacher();
		allTeachingUnit = new ArrayList<TeachingUnit>();
		affectToUP = false;
		notAffectToUP = false;
		noUPdispo = false;
		upDispo = false;
		selectedTeachUnit = new TeachingUnit();

	}

	/*********************************** actionListener *************************************/
	public void renderAffectToUP(ActionEvent event) {
		affectToUP = true;
		notAffectToUP = false;
		//retreive list of UP
		listTeachUnitFromDB = new ArrayList<TeachingUnit>();
		listTeachUnitFromDB = tUnitFacade.listAllTeachUnit();
		if(listTeachUnitFromDB == null){
			noUPdispo = true;
			upDispo = false;
		}
		else{
			noUPdispo = false;
			upDispo = true;
		}
	}

	public void notRenderAffectToUP(ActionEvent event) {
		affectToUP = false;
		notAffectToUP = true;
	}

	public void addTeacher(ActionEvent event) {
		String addResult;
		teacherToDB.setTeachingUnit(selectedTeachUnit);
		
		addResult = teacherFacade.addTeacher(teacherToDB);
		if (addResult == "success") {
			try {
				RequestContext.getCurrentInstance().execute(
						"panelAddTeach.toggle();");
			} catch (Exception e) {
			}
		} else if (addResult.equals("loginExist")) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Existe!!", "Le login est déjà utilisé!");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		} else if (addResult.equals("passExist")) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Existe!!", "Le mot de passe est déjà utilisé!");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}
	
	public void handleToggle(ToggleEvent event) {
		teacherToDB = new Teacher();
		affectToUP = false;
		notAffectToUP = false;
		noUPdispo = false;
		upDispo = false;
		selectedTeachUnit = new TeachingUnit();
	}

	/************************************* constructor ***************************************/
	public AddTeacherBean() {
		super();
	}

	/*********************************** getter & setter ***************************************/

	public Teacher getTeacherToDB() {
		return teacherToDB;
	}

	public void setTeacherToDB(Teacher teacherToDB) {
		this.teacherToDB = teacherToDB;
	}

	public TeacherGrade[] getListgrades() {
		return listGrades;
	}

	public List<TeachingUnit> getAllTeachingUnit() {
		return allTeachingUnit;
	}

	public void setAllTeachingUnit(List<TeachingUnit> allTeachingUnit) {
		this.allTeachingUnit = allTeachingUnit;
	}

	public boolean isAffectToUP() {
		return affectToUP;
	}

	public void setAffectToUP(boolean affectToUP) {
		this.affectToUP = affectToUP;
	}

	public boolean isNotAffectToUP() {
		return notAffectToUP;
	}

	public void setNotAffectToUP(boolean notAffectToUP) {
		this.notAffectToUP = notAffectToUP;
	}

	public List<TeachingUnit> getListTeachUnitFromDB() {
		return listTeachUnitFromDB;
	}

	public void setListTeachUnitFromDB(List<TeachingUnit> listTeachUnitFromDB) {
		this.listTeachUnitFromDB = listTeachUnitFromDB;
	}

	public boolean isNoUPdispo() {
		return noUPdispo;
	}

	public void setNoUPdispo(boolean noUPdispo) {
		this.noUPdispo = noUPdispo;
	}

	public boolean isUpDispo() {
		return upDispo;
	}

	public void setUpDispo(boolean upDispo) {
		this.upDispo = upDispo;
	}

	public TeachingUnit getSelectedTeachUnit() {
		return selectedTeachUnit;
	}

	public void setSelectedTeachUnit(TeachingUnit selectedTeachUnit) {
		this.selectedTeachUnit = selectedTeachUnit;
	}

}
