package org.esprit.gestion.rapports.MB;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;

import org.esprit.gestion.rapports.Model.MessageModel;
import org.esprit.gestion.rapports.persistence.Administrator;
import org.esprit.gestion.rapports.persistence.AssignResponseState;
import org.esprit.gestion.rapports.persistence.Message;
import org.esprit.gestion.rapports.persistence.MessageAccess;
import org.esprit.gestion.rapports.persistence.MessageType;
import org.esprit.gestion.rapports.persistence.User;
import org.esprit.gestion.rapports.services.CRUD.Interfaces.IServiceLocal;
import org.esprit.gestion.rapports.services.CRUD.Util.UserQualifier;
import org.esprit.gestion.rapports.services.facades.Interfaces.ICoachFacadeLocal;
import org.esprit.gestion.rapports.services.facades.Interfaces.ICorrectorFacadeLocal;
import org.esprit.gestion.rapports.services.facades.Interfaces.IMessageFacadeLocal;
import org.esprit.gestion.rapports.utils.MessageStats;
import org.primefaces.context.RequestContext;

@ManagedBean
@ViewScoped
public class MailBoxBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private SimpleDateFormat dateFormat;

	private boolean sent;
	private boolean seen;
	private boolean toRead;
	private MessageStats msgStats;

	private List<Message> listMsgSent;
	private MessageModel selectedMsgSent;
	private List<MessageModel> listMsgModelSent;

	private List<Message> listMsgToRead;
	private List<MessageModel> listMsgModelToRead;
	private MessageModel selectedMsgToRead;

	private List<Message> listMsgSeen;
	private List<MessageModel> listMsgModelSeen;
	private MessageModel selectedMsgSeen;

	private boolean assignConfirmation;
	private boolean declined;
	private boolean accepted;

	private boolean assignAccepted;
	private boolean assignCancelled;
	private boolean assignDeclined;
	
	private String declineCause;

	@Inject
	@UserQualifier
	IServiceLocal<User> userServ;

	@EJB
	ICoachFacadeLocal coachFacade;

	@EJB
	ICorrectorFacadeLocal correctorFacade;

	@ManagedProperty(value = "#{authenticationBean}")
	private AuthenticationBean authBean;

	@ManagedProperty(value = "#{tabViewIndexBean}")
	private TabViewIndexBean tabViewBean;

	@EJB
	IMessageFacadeLocal msgFacade;

	/******************************* Init Bean *************************************/

	@PostConstruct
	public void init() {
		sent = false;
		seen = false;
		toRead = false;
		msgStats = new MessageStats();
		msgStats = msgFacade.listNbrMsg(authBean.getUser().getId());
		dateFormat = new SimpleDateFormat("dd-M-yyyy");

		assignConfirmation = false;

		declined = false;
		accepted = false;

		assignAccepted = false;
		assignCancelled = false;
		assignDeclined = false;
	}

	/******************************* action listeners ************************************/
	public void handleClose() {

		int tabIndex;

		assignConfirmation = false;
		declined = false;

		if (sent) {
			tabIndex = tabViewBean.getTabIndex();
			try {
				RequestContext.getCurrentInstance().execute(
						"location.reload();");
			} catch (Exception e) {
			}

			tabViewBean.setTabIndex(tabIndex);
		}

		else if (toRead) {

			tabIndex = tabViewBean.getTabIndex();
			// mark as seen
			MessageAccess seen = MessageAccess.SEEN;
			msgFacade.changeAccess(selectedMsgToRead.getId(), authBean
					.getUser().getId(), seen);

			try {
				RequestContext.getCurrentInstance().execute(
						"location.reload();");
			} catch (Exception e) {
			}

			tabViewBean.setTabIndex(tabIndex);

		}

		else if (seen) {
			tabIndex = tabViewBean.getTabIndex();

			try {
				RequestContext.getCurrentInstance().execute(
						"location.reload();");
			} catch (Exception e) {
			}

			tabViewBean.setTabIndex(tabIndex);

		}
	}

	public void acceptAssignement() {
		int tabIndex;

		MessageModel selectedMsgOnAccept = new MessageModel();

		if (seen) {
			selectedMsgOnAccept = selectedMsgSeen;
		}

		else if (toRead) {
			MessageAccess seen = MessageAccess.SEEN;
			msgFacade.changeAccess(selectedMsgToRead.getId(), authBean
					.getUser().getId(), seen);
			selectedMsgOnAccept = selectedMsgToRead;
		}

		if (selectedMsgOnAccept.getType().equals(MessageType.COACHASSIGN)) {
			coachFacade.CoachProjectAccept(authBean.getUser().getId(),
					selectedMsgOnAccept.getId());
		}

		else if (selectedMsgOnAccept.getType().equals(
				MessageType.CORRECTORASSIGN)) {

			correctorFacade.CorrectorProjectAccept(authBean.getUser().getId(),
					selectedMsgOnAccept.getId());

		}

		assignConfirmation = false;
		tabIndex = tabViewBean.getTabIndex();

		try {
			RequestContext.getCurrentInstance().execute("location.reload();");
		} catch (Exception e) {
		}

		tabViewBean.setTabIndex(tabIndex);

	}

	public void declineAssignement() {

		int tabIndex;

		MessageModel selectedMsgOnDecline = new MessageModel();

		if (seen) {
			selectedMsgOnDecline = selectedMsgSeen;

		} else if (toRead) {
			MessageAccess seen = MessageAccess.SEEN;
			msgFacade.changeAccess(selectedMsgToRead.getId(), authBean
					.getUser().getId(), seen);
			selectedMsgOnDecline = selectedMsgToRead;

		}

		if (selectedMsgOnDecline.getType().equals(MessageType.COACHASSIGN)) {

			coachFacade.coachDeclineAssign(authBean.getUser().getId(),
					selectedMsgOnDecline.getIncludedRef(),
					declineCause,
					selectedMsgOnDecline.getIdSender(),
					selectedMsgOnDecline.getId());
		}

		else if (selectedMsgOnDecline.getType().equals(
				MessageType.CORRECTORASSIGN)) {
			correctorFacade.correctorDeclineAssign(authBean.getUser().getId(),
					selectedMsgOnDecline.getIncludedRef(),
					selectedMsgOnDecline.getDeclineCause(),
					selectedMsgOnDecline.getId(), selectedMsgOnDecline.getId());

		}

		assignConfirmation = false;
		tabIndex = tabViewBean.getTabIndex();

		try {
			RequestContext.getCurrentInstance().execute("location.reload();");
		} catch (Exception e) {
		}

		tabViewBean.setTabIndex(tabIndex);

	}

	public void showContent(ActionEvent event) {

		// MSG SENT
		if (sent) {
			try {
				RequestContext.getCurrentInstance().execute(
						"sentDialog.show();");
			} catch (Exception e) {
				System.out.println("exception " + e);
			}
		}

		// MSG TOREAD
		else if (toRead) {

			if (selectedMsgToRead.getType().equals(MessageType.COACHASSIGN)
					|| selectedMsgToRead.getType().equals(
							MessageType.CORRECTORASSIGN)) {

				if (selectedMsgToRead.getResponseState().equals(
						AssignResponseState.REFUSED)) {
					assignConfirmation = false;
					assignAccepted = false;
					assignCancelled = false;
					assignDeclined = true;
					

				}

				else if (selectedMsgToRead.getResponseState().equals(
						AssignResponseState.ACCEPTED)) {
					assignConfirmation = false;
					assignAccepted = true;
					assignCancelled = false;
					assignDeclined = false;
				}

				else if (selectedMsgToRead.getResponseState().equals(
						AssignResponseState.CANCELED)) {
					assignConfirmation = false;
					assignAccepted = false;
					assignCancelled = true;
					assignDeclined = false;
				}

				else if (selectedMsgToRead.getResponseState().equals(
						AssignResponseState.WAITING)) {
					assignConfirmation = true;
					assignAccepted = false;
					assignCancelled = false;
					assignDeclined = false;
				}

			}

			try {
				RequestContext.getCurrentInstance().execute(
						"toReadDialog.show();");
			} catch (Exception e) {
				System.out.println("exception " + e);
			}

		}

		// MSG SEEN
		else if (seen) {

			if (selectedMsgSeen.getType().equals(MessageType.COACHASSIGN)
					|| selectedMsgSeen.getType().equals(
							MessageType.CORRECTORASSIGN)) {

				if (selectedMsgSeen.getResponseState().equals(
						AssignResponseState.REFUSED)) {
					assignConfirmation = false;
					assignAccepted = false;
					assignCancelled = false;
					assignDeclined = true;
				

				}

				else if (selectedMsgSeen.getResponseState().equals(
						AssignResponseState.ACCEPTED)) {
					assignConfirmation = false;
					assignAccepted = true;
					assignCancelled = false;
					assignDeclined = false;
				}

				else if (selectedMsgSeen.getResponseState().equals(
						AssignResponseState.CANCELED)) {
					assignConfirmation = false;
					assignAccepted = false;
					assignCancelled = true;
					assignDeclined = false;
				}

				else if (selectedMsgSeen.getResponseState().equals(
						AssignResponseState.WAITING)) {
					assignConfirmation = true;
					assignAccepted = false;
					assignCancelled = false;
					assignDeclined = false;
				}
			}

			try {
				RequestContext.getCurrentInstance().execute(
						"seenDialog.show();");
			} catch (Exception e) {
				System.out.println("exception " + e);
			}
		}
	}

	public void renderSent() {
		listMsgSent = new ArrayList<Message>();
		listMsgSent = msgFacade.listMsgSent(authBean.getUser().getId());
		if (listMsgSent.isEmpty()) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Aucun message", "Vous n'avez envoyé aucun message");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}

		else {

			selectedMsgSent = new MessageModel();
			listMsgModelSent = new ArrayList<MessageModel>();

			// FORMAT MSG LIST
			for (int i = 0; i < listMsgSent.size(); i++) {
				MessageModel msgModel = new MessageModel();
				msgModel = formatMsg(listMsgSent.get(i));
				listMsgModelSent.add(msgModel);
			}

		}

		sent = true;
		seen = false;
		toRead = false;

	}

	public void renderSeen() {

		listMsgSeen = new ArrayList<Message>();
		listMsgSeen = msgFacade.listMsgSeen(authBean.getUser().getId());
		
		if (listMsgSeen.isEmpty()) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Aucun message", "Vous n'avez lu aucun message");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}

		else {

			selectedMsgSeen = new MessageModel();
			listMsgModelSeen = new ArrayList<MessageModel>();

			// FORMAT MSG LIST
			for (int i = 0; i < listMsgSeen.size(); i++) {
				MessageModel msgModel = new MessageModel();
				msgModel = formatMsg(listMsgSeen.get(i));
				
				System.out.println("dec cause "+i+"  "+listMsgSeen.get(i).getDeclineCause());
				
				listMsgModelSeen.add(msgModel);
			}

		}

		sent = false;
		seen = true;
		toRead = false;
	}

	public void renderToRead() {
		listMsgToRead = new ArrayList<Message>();
		listMsgToRead = msgFacade.listMsgToRead(authBean.getUser().getId());

		if (listMsgToRead.isEmpty()) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Aucun message", "Vous n'avez aucun message non lu");
			FacesContext.getCurrentInstance().addMessage(null, msg);

		}

		else {
			selectedMsgToRead = new MessageModel();
			listMsgModelToRead = new ArrayList<MessageModel>();
			// FORMAT
			for (int i = 0; i < listMsgToRead.size(); i++) {
				MessageModel msgModel = new MessageModel();
				msgModel = formatMsg(listMsgToRead.get(i));
				listMsgModelToRead.add(msgModel);
			}

		}

		sent = false;
		seen = false;
		toRead = true;

	}

	/****************************** format method ***********************************/

	public MessageModel formatMsg(Message msg) {

		User reciever = new User();
		reciever.setId(msg.getIdReceiver());
		reciever = (User) userServ.retrieve(reciever, "ID");

		User sender = new User();
		sender.setId(msg.getIdSender());
		sender = (User) userServ.retrieve(sender, "ID");

		String senderName;
		String receiverName;

		if (sender instanceof Administrator) {
			senderName = "Direction des stages et PFE";
		} else {
			senderName = sender.getLastName() + " " + sender.getFirstName();
		}

		if (reciever instanceof Administrator) {
			receiverName = "Direction des stages et PFE";
		} else {
			receiverName = reciever.getLastName() + " "
					+ reciever.getFirstName();
		}

		MessageModel msgModel = new MessageModel(msg.getId(),
				msg.getIncludedRef(), msg.getSubject(), msg.getContent(),
				msg.getIdReceiver(), receiverName, msg.getIdSender(),
				senderName, msg.getSendingDate(), msg.getType(),
				msg.getResponseState(), msg.getDeclineCause());

		return msgModel;
	}

	/******************************* Constructor ************************************/

	public MailBoxBean() {
		super();

	}

	/******************************* getter && setter ************************************/

	public boolean isSent() {
		return sent;
	}

	public void setSent(boolean sent) {
		this.sent = sent;
	}

	public boolean isSeen() {
		return seen;
	}

	public void setSeen(boolean seen) {
		this.seen = seen;
	}

	public boolean isToRead() {
		return toRead;
	}

	public void setToRead(boolean toRead) {
		this.toRead = toRead;
	}

	public MessageStats getMsgStats() {
		return msgStats;
	}

	public void setMsgStats(MessageStats msgStats) {
		this.msgStats = msgStats;
	}

	public void setAuthBean(AuthenticationBean authBean) {
		this.authBean = authBean;
	}

	public MessageModel getSelectedMsgSent() {
		return selectedMsgSent;
	}

	public void setSelectedMsgSent(MessageModel selectedMsgSent) {
		this.selectedMsgSent = selectedMsgSent;
	}

	public List<Message> getListMsgSent() {
		return listMsgSent;
	}

	public void setListMsgSent(List<Message> listMsgSent) {
		this.listMsgSent = listMsgSent;
	}

	public SimpleDateFormat getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(SimpleDateFormat dateFormat) {
		this.dateFormat = dateFormat;
	}

	public List<MessageModel> getListMsgModelSent() {
		return listMsgModelSent;
	}

	public void setListMsgModelSent(List<MessageModel> listMsgModelSent) {
		this.listMsgModelSent = listMsgModelSent;
	}

	public List<Message> getListMsgToRead() {
		return listMsgToRead;
	}

	public void setListMsgToRead(List<Message> listMsgToRead) {
		this.listMsgToRead = listMsgToRead;
	}

	public List<MessageModel> getListMsgModelToRead() {
		return listMsgModelToRead;
	}

	public void setListMsgModelToRead(List<MessageModel> listMsgModelToRead) {
		this.listMsgModelToRead = listMsgModelToRead;
	}

	public List<Message> getListMsgSeen() {
		return listMsgSeen;
	}

	public void setListMsgSeen(List<Message> listMsgSeen) {
		this.listMsgSeen = listMsgSeen;
	}

	public List<MessageModel> getListMsgModelSeen() {
		return listMsgModelSeen;
	}

	public void setListMsgModelSeen(List<MessageModel> listMsgModelSeen) {
		this.listMsgModelSeen = listMsgModelSeen;
	}

	public MessageModel getSelectedMsgToRead() {
		return selectedMsgToRead;
	}

	public void setSelectedMsgToRead(MessageModel selectedMsgToRead) {
		this.selectedMsgToRead = selectedMsgToRead;
	}

	public MessageModel getSelectedMsgSeen() {
		return selectedMsgSeen;
	}

	public void setSelectedMsgSeen(MessageModel selectedMsgSeen) {
		this.selectedMsgSeen = selectedMsgSeen;
	}

	public boolean isAssignConfirmation() {
		return assignConfirmation;
	}

	public void setAssignConfirmation(boolean assignConfirmation) {
		this.assignConfirmation = assignConfirmation;
	}

	public boolean isDeclined() {
		return declined;
	}

	public void setDeclined(boolean declined) {
		this.declined = declined;
	}

	public boolean isAccepted() {
		return accepted;
	}

	public void setAccepted(boolean accepted) {
		this.accepted = accepted;
	}

	public boolean isAssignAccepted() {
		return assignAccepted;
	}

	public void setAssignAccepted(boolean assignAccepted) {
		this.assignAccepted = assignAccepted;
	}

	public boolean isAssignCancelled() {
		return assignCancelled;
	}

	public void setAssignCancelled(boolean assignCancelled) {
		this.assignCancelled = assignCancelled;
	}

	public boolean isAssignDeclined() {
		return assignDeclined;
	}

	public void setAssignDeclined(boolean assignDeclined) {
		this.assignDeclined = assignDeclined;
	}

	public void setTabViewBean(TabViewIndexBean tabViewBean) {
		this.tabViewBean = tabViewBean;
	}

	public String getDeclineCause() {
		return declineCause;
	}

	public void setDeclineCause(String declineCause) {
		this.declineCause = declineCause;
	}

}
