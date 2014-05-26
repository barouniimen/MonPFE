package org.esprit.gestion.rapports.MB;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.esprit.gestion.rapports.persistence.TeachingUnit;
import org.esprit.gestion.rapports.services.facades.Interfaces.ITeachingUnitFacadeLocal;
import org.primefaces.context.RequestContext;
@ManagedBean
@ViewScoped
public class AddTeachingUnitBean {

	private TeachingUnit teachUnitToDB;
	
	/***** EJB facade *****/
	@EJB
	ITeachingUnitFacadeLocal teachUnitFacade;
	
	/********************************** init method ******************************************/
	@PostConstruct
	public void init(){
		teachUnitToDB = new TeachingUnit();
	}

	/******************************** action listeners ***************************************/
	public void addTeachingUnit(ActionEvent event){
		String addResult;
		addResult = teachUnitFacade.addTeachingUnit(teachUnitToDB);

		if(addResult == "name exist"){
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Existe!!", "Le nom de l'unité est déjà utilisé!");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		else if(addResult == "local exist"){
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Existe!!", "Le local est déjà utilisé!");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		else if(addResult == "success"){
			try {
				RequestContext.getCurrentInstance().execute(
						" location.reload();");
			} catch (Exception e) {
			}
		}
	}
	

	/*********************************** constructor ****************************************/
	public AddTeachingUnitBean() {
		super();
	}

	/******************************* getter & setter ****************************************/
	public TeachingUnit getTeachUnitToDB() {
		return teachUnitToDB;
	}

	public void setTeachUnitToDB(TeachingUnit teachUnitToDB) {
		this.teachUnitToDB = teachUnitToDB;
	}

}
