package org.esprit.gestion.rapports.MB;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.esprit.gestion.rapports.persistence.Administrator;
import org.esprit.gestion.rapports.persistence.Student;
import org.esprit.gestion.rapports.persistence.Teacher;
import org.esprit.gestion.rapports.persistence.User;
import org.esprit.gestion.rapports.services.CRUD.Interfaces.IServiceLocal;
import org.esprit.gestion.rapports.services.CRUD.Util.UserQualifier;
import org.esprit.gestion.rapports.services.facades.Interfaces.ISubmissionFacadeLocal;

@ManagedBean
@SessionScoped
public class AuthenticationBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	@UserQualifier
	IServiceLocal<User> userSer;

	private boolean loggedIn = false;
	private User user;
	private boolean teacher;
	private boolean student;
	private boolean admin;

	@EJB
	ISubmissionFacadeLocal submitEventFacade;

	/************************************** init method ***************************/
	@PostConstruct
	public void init() {
		user = new User();
		student = false;
		admin = false;
		teacher = false;
	}

	/************************************* constructor ****************************/
	public AuthenticationBean() {
	}

	/****************************************** listeners *********************************/
	public String performAuthetication() {
		String forward = null;
		user = (User) userSer.retrieve(user, "LP");
		
		if (null != user) {

			if (user instanceof Student) {
				submitEventFacade.updateDates();

				student = true;
				admin = false;
				teacher = false;
				forward = "/pages/student/home?faces-redirect=true";
				loggedIn = true;

			} else if (user instanceof Teacher) {
				submitEventFacade.updateDates();

				student = false;
				admin = false;
				teacher = true;
				forward = "/pages/teacher/home?faces-redirect=true";
				loggedIn = true;

			} else if (user instanceof Administrator) {
				submitEventFacade.updateDates();

				student = false;
				admin = true;
				teacher = false;
				user.setFirstName("Administrateur");
				user.setLastName(null);
				forward = "/pages/admin/home?faces-redirect=true";
				loggedIn = true;

			}

		} else {

			user = new User();
			loggedIn = false;

			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN,
							"erreur login", "erreur login"));

		}

		return forward;
	}

	public String performLogout() {

		user = new User();
		loggedIn = false;

		FacesContext.getCurrentInstance().getExternalContext()
				.invalidateSession();

		String forward = "/pages/index?faces-redirect=true";

		return forward;
	}

	/*************************************** getter && setter ********************************/
	public boolean isTeacher() {
		return teacher;
	}

	public void setTeacher(boolean teacher) {
		this.teacher = teacher;
	}

	public boolean isStudent() {
		return student;
	}

	public void setStudent(boolean student) {
		this.student = student;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public boolean isLoggedIn() {
		return loggedIn;
	}

	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}

}
