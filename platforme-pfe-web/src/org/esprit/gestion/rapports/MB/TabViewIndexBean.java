package org.esprit.gestion.rapports.MB;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.primefaces.component.tabview.TabView;
import org.primefaces.event.TabChangeEvent;

@ManagedBean
@SessionScoped
public class TabViewIndexBean {
	private int tabIndex=0;
	


	/******************************* action listener *****************************************/
	public void onChange(TabChangeEvent e) {
		TabView tv = (TabView) e.getComponent();
		this.setTabIndex(tv.getChildren().indexOf(e.getTab()));
		
	}

	
	/************************************ constructor ********************************************/

	public TabViewIndexBean() {
		super();
		
	}

	/*********************************** getter && setter ******************************************/
	
	public int getTabIndex() {
		return tabIndex;
	}

	public void setTabIndex(int tabIndex) {
		this.tabIndex = tabIndex;
	}


	/*public void setMailboxBean(MailBoxBean mailboxBean) {
		this.mailboxBean = mailboxBean;
	}*/
}
