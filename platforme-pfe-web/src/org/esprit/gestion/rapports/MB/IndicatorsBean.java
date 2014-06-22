package org.esprit.gestion.rapports.MB;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.esprit.gestion.rapports.persistence.EventState;
import org.esprit.gestion.rapports.persistence.SubmissionEvent;
import org.esprit.gestion.rapports.services.facades.Interfaces.IStorageSpaceFacadeLocal;
import org.esprit.gestion.rapports.services.facades.Interfaces.ISubmissionFacadeLocal;
import org.primefaces.model.chart.MeterGaugeChartModel;

@ManagedBean
@ViewScoped
public class IndicatorsBean {

	private MeterGaugeChartModel model;
	private int allocatedSpace;
	private int spaceInUse;
	private List<SubmissionEvent> listSubmitEvent;
	private SubmissionEvent currentSubmitEvent;
	private SubmissionEvent lastSubmitEvent;
	private boolean noSubmitEvent;
	private boolean noCurrentSubmitEvent;
	private boolean submitEventExist;
	private boolean openedSubmitEvent;
	private SimpleDateFormat dateFormat;

	@ManagedProperty(value = "#{authenticationBean}")
	private AuthenticationBean authBean;

	@EJB
	private IStorageSpaceFacadeLocal storageSpaceFacade;

	@EJB
	private ISubmissionFacadeLocal submitEventFacade;

	/********************************** init method *************************/
	@PostConstruct
	public void init() {
		noSubmitEvent = false;
		noCurrentSubmitEvent = false;
		submitEventExist = false;
		openedSubmitEvent = false;
		
		dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		
		// init storageSpace indicators
		setAllocatedSpace(storageSpaceFacade.allocatedSpace(authBean.getUser()
				.getId()));
		setSpaceInUse(storageSpaceFacade.spaceInUse(authBean.getUser().getId()));

		List<Number> intervals = new ArrayList<Number>() {
			private static final long serialVersionUID = 1L;
			{
				add((allocatedSpace / 3));
				add(((allocatedSpace / 3) * 2));
				add(allocatedSpace);
			}
		};

		model = new MeterGaugeChartModel(spaceInUse, intervals);

		// inti submit session indicator

		setListSubmitEvent(new ArrayList<SubmissionEvent>());
		setListSubmitEvent(submitEventFacade.listAllSubmitEvent());

		currentSubmitEvent = new SubmissionEvent();
		currentSubmitEvent = null;
		lastSubmitEvent = new SubmissionEvent();

		if (listSubmitEvent.isEmpty()) {
			noSubmitEvent = true;
		} else {
			submitEventExist = true;

			lastSubmitEvent = listSubmitEvent.get(0);

			for (int i = 0; i < listSubmitEvent.size(); i++) {
				if (listSubmitEvent.get(i).getState()
						.equals(EventState.STARTED)) {

					currentSubmitEvent = listSubmitEvent.get(i);
					openedSubmitEvent = true;

				} else if (listSubmitEvent.get(i).getDueDate()
						.after(lastSubmitEvent.getDueDate())) {

					lastSubmitEvent = listSubmitEvent.get(i);
					

				}
			}
		}

		if (currentSubmitEvent == null) {
			noCurrentSubmitEvent = true;
		} 

	}

	/***************************** constructor ************************************/

	public IndicatorsBean() {
		super();
	}

	/***************************** getter && setter *****************************/

	public MeterGaugeChartModel getModel() {
		return model;
	}

	public void setModel(MeterGaugeChartModel model) {
		this.model = model;
	}

	public void setAuthBean(AuthenticationBean authBean) {
		this.authBean = authBean;
	}

	public int getAllocatedSpace() {
		return allocatedSpace;
	}

	public void setAllocatedSpace(int allocatedSpace) {
		this.allocatedSpace = allocatedSpace;
	}

	public int getSpaceInUse() {
		return spaceInUse;
	}

	public void setSpaceInUse(int spaceInUse) {
		this.spaceInUse = spaceInUse;
	}

	public List<SubmissionEvent> getListSubmitEvent() {
		return listSubmitEvent;
	}

	public void setListSubmitEvent(List<SubmissionEvent> listSubmitEvent) {
		this.listSubmitEvent = listSubmitEvent;
	}

	public SubmissionEvent getCurrentSubmitEvent() {
		return currentSubmitEvent;
	}

	public void setCurrentSubmitEvent(SubmissionEvent currentSubmitEvent) {
		this.currentSubmitEvent = currentSubmitEvent;
	}

	public SubmissionEvent getLastSubmitEvent() {
		return lastSubmitEvent;
	}

	public void setLastSubmitEvent(SubmissionEvent lastSubmitEvent) {
		this.lastSubmitEvent = lastSubmitEvent;
	}

	public boolean isNoSubmitEvent() {
		return noSubmitEvent;
	}

	public void setNoSubmitEvent(boolean noSubmitEvent) {
		this.noSubmitEvent = noSubmitEvent;
	}

	public boolean isNoCurrentSubmitEvent() {
		return noCurrentSubmitEvent;
	}

	public void setNoCurrentSubmitEvent(boolean noCurrentSubmitEvent) {
		this.noCurrentSubmitEvent = noCurrentSubmitEvent;
	}

	public boolean isSubmitEventExist() {
		return submitEventExist;
	}

	public void setSubmitEventExist(boolean submitEventExist) {
		this.submitEventExist = submitEventExist;
	}

	public boolean isOpenedSubmitEvent() {
		return openedSubmitEvent;
	}

	public void setOpenedSubmitEvent(boolean openedSubmitEvent) {
		this.openedSubmitEvent = openedSubmitEvent;
	}

	public SimpleDateFormat getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(SimpleDateFormat dateFormat) {
		this.dateFormat = dateFormat;
	}
}
