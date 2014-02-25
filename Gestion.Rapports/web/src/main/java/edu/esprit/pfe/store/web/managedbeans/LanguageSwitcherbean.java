package edu.esprit.pfe.store.web.managedbeans;

import java.io.Serializable;
import java.util.Locale;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@SessionScoped
public class LanguageSwitcherbean implements Serializable {
	
	
	private Locale locale = new Locale("fr");

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}
	
	public void performLanguageSwith(String localeCode){
		
		locale = new Locale(localeCode);
		FacesContext.getCurrentInstance().getViewRoot().setLocale(locale);
		
	}

}
