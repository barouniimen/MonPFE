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
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;

import org.esprit.gestion.rapports.persistence.ClassGroup;
import org.esprit.gestion.rapports.persistence.Speciality;
import org.esprit.gestion.rapports.services.CRUD.Interfaces.IServiceLocal;
import org.esprit.gestion.rapports.services.CRUD.Util.SpecialityQualifier;
import org.esprit.gestion.rapports.services.facades.Interfaces.IClassGroupFacadeLocal;
import org.primefaces.context.RequestContext;

@ManagedBean
@ViewScoped
public class AddClassGroupBean {

	private ClassGroup classGroupToDB;
	private List<Speciality> listSpecialityFromDB;
	private Speciality specialitySelected;

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

	}

	/*********************************** action listeners ******************************/
	public void updateSelectedSpeciality(ValueChangeEvent event) {
		System.out.println("change!!!");

	}

	public void addClass(ActionEvent event) {
		String resultCreate;
		classGroupToDB.setSpeciality(specialitySelected);

		resultCreate = classGroupFacade.addClassGroup(classGroupToDB);

		if (resultCreate.equals("unique")) {
			try {
				RequestContext.getCurrentInstance().execute(
						" location.reload();");
			} catch (Exception e) {
			}
		} else if (resultCreate.equals("exist")) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Existe!!", "La référence de la classe existe déjà!");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}

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

}
