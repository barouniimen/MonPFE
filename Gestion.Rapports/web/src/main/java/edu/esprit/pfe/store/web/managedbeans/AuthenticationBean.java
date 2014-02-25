package edu.esprit.pfe.store.web.managedbeans;

import java.io.Serializable;
import java.util.ResourceBundle;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import edu.esprit.pfe.store.ejb.domain.SysAdmin;
import edu.esprit.pfe.store.ejb.domain.User;
import edu.esprit.pfe.store.ejb.services.AuthenticationServiceLocal;

@ManagedBean(name = "authBean")
@SessionScoped
public class AuthenticationBean implements Serializable {

	@EJB
	AuthenticationServiceLocal authenticationServiceLocal;

	@ManagedProperty(value = "#{languageSwitcherbean}")
	LanguageSwitcherbean languageSwitcher;

	ResourceBundle rb;

	// SETTING THE MODEL
	private User user = new User();
	private boolean loggedIn = false;

	// END SETTING MODEL

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
	
	public void setLanguageSwitcher(LanguageSwitcherbean languageSwitcher) {
		this.languageSwitcher = languageSwitcher;
	}

	public String performAuthetication() {

		String forward = null;
		user = authenticationServiceLocal.authenticate(user.getLogin(),
				user.getPassword());

		if (null != user) {

			if (user instanceof SysAdmin) {

				forward = "/pages/admin/home";
				loggedIn = true;

			} else {// Instance Of Student

				forward = "/pages/student/home";
				loggedIn = true;
			}

		} else {

			user = new User();
			loggedIn = false;

			rb = ResourceBundle.getBundle("edu.esprit.pfe.store.web.resources.messageResources", languageSwitcher.getLocale());
			String msgText = rb.getString("esprit.pfe.store.login.form.errors.credentials");

			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, msgText,
							msgText));

		}

		return forward;
	}

	public String performLogout() {

		String forward = "/pages/index";
		user = new User();
		loggedIn = false;

		return forward;
	}

}
