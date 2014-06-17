package org.esprit.gestion.rapports.MB;

import java.text.SimpleDateFormat;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.esprit.gestion.rapports.persistence.Project;
import org.esprit.gestion.rapports.persistence.Teacher;
import org.esprit.gestion.rapports.services.facades.Interfaces.IStudentFacadeLocal;

@ManagedBean
@ViewScoped
public class ProjectDescriptionBean {

	Project studentProj;
	@ManagedProperty(value = "#{authenticationBean}")
	private AuthenticationBean authBean;

	@EJB
	IStudentFacadeLocal studentFacade;

	private boolean projFound;

	private boolean hasCoach;
	private boolean dontHaveCoach;
	
	private Teacher coach;

	private SimpleDateFormat dateFormat;

	/**************************** init method ****************************/
	@PostConstruct
	public void init() {

		dateFormat = new SimpleDateFormat("dd/MM/YYYY");
		studentProj = studentFacade.findStudentProj(authBean.getUser().getId());
		if (studentProj == null) {
			projFound = false;
		}

		else {
			projFound = true;
			hasCoach = studentFacade.hasCoach(authBean.getUser().getId());
			dontHaveCoach = !hasCoach;
		if(hasCoach){
			coach = new Teacher();
			coach = studentFacade.findCoach(authBean.getUser().getId());
		}
		
			

		}

	}

	/**************************** constructor ****************************/
	public ProjectDescriptionBean() {
		super();

	}

	/************************** getter && setter ***********************/
	public Project getStudentProj() {
		return studentProj;
	}

	public void setStudentProj(Project studentProj) {
		this.studentProj = studentProj;
	}

	public void setAuthBean(AuthenticationBean authBean) {
		this.authBean = authBean;
	}

	public boolean isProjFound() {
		return projFound;
	}

	public void setProjFound(boolean projFound) {
		this.projFound = projFound;
	}

	public SimpleDateFormat getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(SimpleDateFormat dateFormat) {
		this.dateFormat = dateFormat;
	}

	public boolean isHasCoach() {
		return hasCoach;
	}

	public void setHasCoach(boolean hasCoach) {
		this.hasCoach = hasCoach;
	}

	public boolean isDontHaveCoach() {
		return dontHaveCoach;
	}

	public void setDontHaveCoach(boolean dontHaveCoach) {
		this.dontHaveCoach = dontHaveCoach;
	}

	public Teacher getCoach() {
		return coach;
	}

	public void setCoach(Teacher coach) {
		this.coach = coach;
	}

}
