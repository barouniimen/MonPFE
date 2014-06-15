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
import org.esprit.gestion.rapports.persistence.Speciality;
import org.esprit.gestion.rapports.services.CRUD.Interfaces.IServiceLocal;
import org.esprit.gestion.rapports.services.CRUD.Util.SpecialityQualifier;
import org.esprit.gestion.rapports.services.facades.Interfaces.IClassGroupFacadeLocal;
import org.primefaces.context.RequestContext;
import org.primefaces.event.ToggleEvent;

@ManagedBean
@ViewScoped
public class AddClassGroupBean {

	private ClassGroup classGroupToDB;
	private List<Speciality> listSpecialityFromDB;
	private Speciality specialitySelected;
	private boolean newSpeciality;
	private boolean notNewSpeciality;

	/***** EJB service ****/
	@Inject
	@SpecialityQualifier
	IServiceLocal<Speciality> specialityServ;

	/***** EJB Facade *****/
	@EJB
	IClassGroupFacadeLocal classGroupFacade;

	/******************************** inti method ************************************/
	@PostConstruct
	public void init() {
		// init classgroup
		classGroupToDB = new ClassGroup();
		specialitySelected = new Speciality();

		// find all speciality
		listSpecialityFromDB = new ArrayList<Speciality>();
		listSpecialityFromDB = specialityServ.retrieveList(
				listSpecialityFromDB, "ALL");

		// init render vars
		setNewSpeciality(false);
		setNotNewSpeciality(false);

	}

	/*********************************** action listeners ******************************/

	public void renderNewSpeciality() {
		setNewSpeciality(true);
		setNotNewSpeciality(false);
	}

	public void renderNotNewSpeciality() {
		setNewSpeciality(false);
		setNotNewSpeciality(true);
	}

	public void addClass(ActionEvent event) {
		String resultCreate;

		if (specialitySelected.getId() == -1) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Donnée manquante!!",
					"Veuillez sélectionner une spécialité!");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		} else {
			classGroupToDB.setSpeciality(specialitySelected);

			resultCreate = classGroupFacade.addClassGroup(classGroupToDB);

			if (resultCreate.equals("unique")) {
				try {
					RequestContext.getCurrentInstance().execute(
							"panelAddClass.toggle();");
				} catch (Exception e) {
				}
			} else if (resultCreate.equals("exist")) {
				FacesMessage msg = new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "Existe!!",
						"La référence de la classe existe déjà!");
				FacesContext.getCurrentInstance().addMessage(null, msg);
			}
		}
	}

	public void handleToggle(ToggleEvent event) {
		classGroupToDB = new ClassGroup();
	specialitySelected = new Speciality();
		newSpeciality = false;
		notNewSpeciality = false;

	}

	/****************************** Constructor ***************************************/
	public AddClassGroupBean() {
		super();
	}

	/***************************** getter & setter *********************************/
	public ClassGroup getClassGroupToDB() {
		return classGroupToDB;
	}

	public void setClassGroupToDB(ClassGroup classGroupToDB) {
		this.classGroupToDB = classGroupToDB;
	}

	public List<Speciality> getListSpecialityFromDB() {
		return listSpecialityFromDB;
	}

	public void setListSpecialityFromDB(List<Speciality> listSpecialityFromDB) {
		this.listSpecialityFromDB = listSpecialityFromDB;
	}

	public Speciality getSpecialitySelected() {
		return specialitySelected;
	}

	public void setSpecialitySelected(Speciality specialitySelected) {
		this.specialitySelected = specialitySelected;
	}

	public boolean isNewSpeciality() {
		return newSpeciality;
	}

	public void setNewSpeciality(boolean newSpeciality) {
		this.newSpeciality = newSpeciality;
	}

	public boolean isNotNewSpeciality() {
		return notNewSpeciality;
	}

	public void setNotNewSpeciality(boolean notNewSpeciality) {
		this.notNewSpeciality = notNewSpeciality;
	}

}
