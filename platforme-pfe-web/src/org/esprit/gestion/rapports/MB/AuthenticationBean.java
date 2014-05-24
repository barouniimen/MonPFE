package org.esprit.gestion.rapports.MB;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.esprit.gestion.rapports.persistence.Administrator;
import org.esprit.gestion.rapports.persistence.Student;
import org.esprit.gestion.rapports.persistence.Teacher;
import org.esprit.gestion.rapports.persistence.User;
import org.esprit.gestion.rapports.services.CRUD.Interfaces.IServiceLocal;
import org.esprit.gestion.rapports.services.CRUD.Util.UserQualifier;

@ManagedBean(name = "authBean")
@ApplicationScoped
public class AuthenticationBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	@UserQualifier
	IServiceLocal<User> userSer;

	private boolean loggedIn = false;
	private User user = new User();

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

	public String performAuthetication() {
		String forward = null;
		user = (User) userSer.retrieve(user, "LP");

		if (null != user) {

			if (user instanceof Student) {
				forward = "/pages/student/home?faces-redirect=true";
				loggedIn = true;

			} else if (user instanceof Teacher) {
				forward = "/pages/teacher/home?faces-redirect=true";
				loggedIn = true;

			} else if (user instanceof Administrator) {
				 forward = "/pages/admin/home?faces-redirect=true";
				//forward = "/pages/admin/Test?faces-redirect=true";
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
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		String forward = "/pages/index?faces-redirect=true";

		return forward;
	}

}
