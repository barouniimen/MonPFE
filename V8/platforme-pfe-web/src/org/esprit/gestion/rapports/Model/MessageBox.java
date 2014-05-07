package org.esprit.gestion.rapports.Model;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.esprit.gestion.rapports.persistence.Message;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

@ManagedBean("mailbox")
@ViewScoped
public class MessageBox implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private TreeNode mailboxes;

    private List<Message> mails;

    private Message mail;

    private TreeNode mailbox;

    @PostConstruct
    public void init() {
        mailboxes = new DefaultTreeNode("root", null);

        TreeNode inbox = new DefaultTreeNode("i","Inbox", mailboxes);
		TreeNode sent = new DefaultTreeNode("s", "Sent", mailboxes);
		TreeNode trash = new DefaultTreeNode("t", "Trash", mailboxes);
        TreeNode junk = new DefaultTreeNode("j", "Junk", mailboxes);

        TreeNode gmail = new DefaultTreeNode("Gmail", inbox);
        TreeNode hotmail = new DefaultTreeNode("Hotmail", inbox);

        setMails(new ArrayList<Message>());
    
    }
    
    /***********************************  Methodes    *********************************************/
    public void send() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Mail Sent!"));
    }

    public TreeNode getMailboxes() {
        return mailboxes;
    }

   

    public TreeNode getMailbox() {
        return mailbox;
    }

    public void setMailbox(TreeNode mailbox) {
        this.mailbox = mailbox;
    }

   

	public List<Message> getMails() {
		return mails;
	}

	public void setMails(List<Message> mails) {
		this.mails = mails;
	}

	public Message getMail() {
		return mail;
	}

	public void setMail(Message mail) {
		this.mail = mail;
	}
}