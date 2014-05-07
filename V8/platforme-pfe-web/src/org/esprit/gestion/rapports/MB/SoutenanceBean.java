package org.esprit.gestion.rapports.MB;

import java.io.Serializable;
import org.primefaces.event.CloseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.esprit.gestion.rapports.persistence.Project;
import org.esprit.gestion.rapports.persistence.ValidationState;
import org.esprit.gestion.rapports.services.facades.Interfaces.ISoutenanceFacadeLocal;
import org.primefaces.event.RowEditEvent;

@ManagedBean
@ViewScoped
public class SoutenanceBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EJB
	private ISoutenanceFacadeLocal soutFacade;
	private List<Project> listProj;
	private final static String[] listStates;
	
	static {
		listStates = new String[3];
		listStates[0] = ValidationState.WAITINGSOUT.getDescription();
		listStates[1] = ValidationState.SOUTNONVALID.getDescription();
		listStates[2] = ValidationState.SOUTVALID.getDescription();
	}


	/***************** PostConstruct ************************/

	@PostConstruct
	public void init() {
		setListProj(new ArrayList<Project>());
		setListProj(soutFacade.listProjectToplanSoutenance());
	}

	/***************** Events Handling **********************/

	public void onEdit(RowEditEvent event) {
		System.out.println("event edit ok");

	}

	public void onCancel(RowEditEvent event) {
		System.out.println("oncancel");

	}
	
	public void handleClose(CloseEvent event) {
/*		try {
			RequestContext.getCurrentInstance().reset("tabView:formAddDepartment");
			RequestContext.getCurrentInstance().reset("tabView:formUpdateDepartment");

		} catch (Exception e) {
		}*/
		System.out.println("on close");
	}

	/***************** Constructor ************************/
	public SoutenanceBean() {

	}

	/*************** Getter & Setter **********************/

	public List<Project> getListProj() {
		return listProj;
	}

	public void setListProj(List<Project> listProj) {
		this.listProj = listProj;
	}

	public String[] getListstates() {
		return listStates;
	}

}
