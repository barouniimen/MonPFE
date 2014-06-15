package org.esprit.gestion.rapports.MB;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.esprit.gestion.rapports.persistence.Project;
import org.esprit.gestion.rapports.services.facades.Interfaces.ICoachFacadeLocal;

@ManagedBean
@ViewScoped
public class CoachSpaceBean {

	private List<Project> coachedProjList;
	private SimpleDateFormat simpleDate;
	
	@EJB
	private ICoachFacadeLocal coachFacade;
	
	
	@ManagedProperty(value = "#{authenticationBean}")
	private AuthenticationBean authBean;
	
	
	/************************ init method **************************/
	@PostConstruct
	public void init(){
		simpleDate = new SimpleDateFormat("dd-M-yyyy");
		
		coachedProjList = coachFacade.listProjectsCoached(authBean.getUser().getId());
		
	}
	
	

	/******************** constructor *****************************/
	public CoachSpaceBean() {
		super();
		
	}

	/*********************** getter && setter *************************/
	public List<Project> getCoachedProjList() {
		return coachedProjList;
	}

	public void setCoachedProjList(List<Project> coachedProjList) {
		this.coachedProjList = coachedProjList;
	}



	public void setAuthBean(AuthenticationBean authBean) {
		this.authBean = authBean;
	}



	public SimpleDateFormat getSimpleDate() {
		return simpleDate;
	}



	public void setSimpleDate(SimpleDateFormat simpleDate) {
		this.simpleDate = simpleDate;
	}

}
