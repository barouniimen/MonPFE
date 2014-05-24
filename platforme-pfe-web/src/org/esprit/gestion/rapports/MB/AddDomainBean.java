package org.esprit.gestion.rapports.MB;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.esprit.gestion.rapports.persistence.Domain;
import org.esprit.gestion.rapports.services.facades.Interfaces.IDomainFacadeLocal;
import org.primefaces.context.RequestContext;

@ManagedBean
@ViewScoped
public class AddDomainBean {

	private Domain domainToDB;

	@EJB
	IDomainFacadeLocal domainFacade;

	/******************************** inti method ************************************/
	@PostConstruct
	public void init() {
		domainToDB = new Domain();

	}

	/*********************************** action listeners ******************************/

	public void addDomain(ActionEvent event) {
		String resultCreate;

		resultCreate = domainFacade.addDomain(domainToDB);

		if (resultCreate.equals("unique")) {
			try {
				RequestContext.getCurrentInstance().execute(
						" location.reload();");
			} catch (Exception e) {
			}
		} else if (resultCreate.equals("exist")) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Existe!!", "Le domaine!");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}

	}

	/****************************** Constructor ***************************************/
	public AddDomainBean() {
		super();
	}

	/***************************** getter & setter *********************************/
	public Domain getDomainToDB() {
		return domainToDB;
	}

	public void setDomainToDB(Domain domainToDB) {
		this.domainToDB = domainToDB;
	}

}