package org.esprit.gestion.rapports.MB;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;

import org.esprit.gestion.rapports.Model.ManagedProjects;
import org.esprit.gestion.rapports.persistence.Domain;
import org.esprit.gestion.rapports.services.CRUD.Interfaces.IServiceLocal;
import org.esprit.gestion.rapports.services.CRUD.Util.DomainQualifier;
import org.primefaces.model.DualListModel;

@ManagedBean
@ViewScoped
public class UpdateProjectFormBean {
	
	//var Project
	private ManagedProjects projToUpdate;
	private List<String> listDomSource;
	private List<String> listDomTarget;
	private DualListModel<String> dualListDomains;
	private List<Domain> listDomFromDB;
	/***************************** EJB Service ******************************/
	@Inject
	@DomainQualifier
	IServiceLocal<Domain> domainServ;
	
	
	/**********************************************************************************************************************/
	/************************************************ init method *********************************************************/
	/**********************************************************************************************************************/
	@PostConstruct
	public void init(){
		//init project vars
		projToUpdate = new ManagedProjects();
		listDomFromDB = new ArrayList<Domain>();
		System.out.println("dom serv: "+domainServ);
		listDomFromDB = domainServ.retrieveList(null, "ALL");
		listDomSource = new ArrayList<String>();
		listDomTarget = new ArrayList<String>();

		for (int i = 0; i < listDomFromDB.size(); i++) {
			listDomSource.add(listDomFromDB.get(i).getDomainName());
		}

		dualListDomains = new DualListModel<String>(listDomSource,
				listDomTarget);
	}
	
	/**********************************************************************************************************************/
	/************************************************ action listeners ****************************************************/
	/**********************************************************************************************************************/
	public void updateProj(ActionEvent event){
		System.out.println("proj update: "+projToUpdate.getProjTopic());
	}
	


	/**********************************************************************************************************************/
	/************************************************ Constructor *********************************************************/
	/**********************************************************************************************************************/
	public UpdateProjectFormBean() {
		super();
	}

	/**********************************************************************************************************************/
	/************************************************ getter & setter *****************************************************/
	/**********************************************************************************************************************/
	public ManagedProjects getProjToUpdate() {
		return projToUpdate;
	}

	public void setProjToUpdate(ManagedProjects projToUpdate) {
		this.projToUpdate = projToUpdate;
	}

	public List<String> getListDomSource() {
		return listDomSource;
	}

	public void setListDomSource(List<String> listDomSource) {
		this.listDomSource = listDomSource;
	}

	public List<String> getListDomTarget() {
		return listDomTarget;
	}

	public void setListDomTarget(List<String> listDomTarget) {
		this.listDomTarget = listDomTarget;
	}

	public DualListModel<String> getDualListDomains() {
		return dualListDomains;
	}

	public void setDualListDomains(DualListModel<String> dualListDomains) {
		this.dualListDomains = dualListDomains;
	}

	public List<Domain> getListDomFromDB() {
		return listDomFromDB;
	}

	public void setListDomFromDB(List<Domain> listDomFromDB) {
		this.listDomFromDB = listDomFromDB;
	}
	
	

}
