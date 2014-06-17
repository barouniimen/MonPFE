package org.esprit.gestion.rapports.MB;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class ManageReportsByCoachBean {

	@ManagedProperty(value = "#{authenticationBean}")
	private AuthenticationBean authBean;

	@ManagedProperty(value = "#{tabViewIndexBean}")
	private TabViewIndexBean tabViewBean;

	/******************************* init method *******************************/
	@PostConstruct
	public void init() {

	}

	/****************************** action listeners ****************************/

	/******************************* constructor ******************************/
	public ManageReportsByCoachBean() {
		super();
	}

	/************************* getter && setter ********************************/
	public void setAuthBean(AuthenticationBean authBean) {
		this.authBean = authBean;
	}

	public void setTabViewBean(TabViewIndexBean tabViewBean) {
		this.tabViewBean = tabViewBean;
	}

}
