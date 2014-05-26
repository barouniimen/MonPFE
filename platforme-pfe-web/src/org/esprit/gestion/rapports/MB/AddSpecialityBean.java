package org.esprit.gestion.rapports.MB;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.esprit.gestion.rapports.persistence.Speciality;
import org.esprit.gestion.rapports.services.facades.Interfaces.ISpecialityFacadeLocal;
import org.primefaces.context.RequestContext;

@ManagedBean
@ViewScoped
public class AddSpecialityBean {

	private Speciality specialityToDB;

	
	@EJB
	private ISpecialityFacadeLocal specialityFacade;
	
	

	/************************************* init method *****************************************/
	@PostConstruct
	public void init() {
		specialityToDB = new Speciality();
		
	}

	/*********************************** actionListener *************************************/

	
	
	public void addSpeciality(ActionEvent event) {
		String resultCreate;
		
		resultCreate = specialityFacade.addSpeciality(specialityToDB);

		if (resultCreate.equals("unique")) {
			try {
				RequestContext.getCurrentInstance().execute(
						" location.reload();");
			} catch (Exception e) {
			}
		} else if (resultCreate.equals("exist")) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Existe!!", "La spécialité existe déjà!");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}

	}

	/************************************* constructor ***************************************/
	public AddSpecialityBean() {
		super();
	}

	/*********************************** getter & setter ***************************************/
	public Speciality getSpecialityToDB() {
		return specialityToDB;
	}

	public void setSpecialityToDB(Speciality specialityToDB) {
		this.specialityToDB = specialityToDB;
	}


}
