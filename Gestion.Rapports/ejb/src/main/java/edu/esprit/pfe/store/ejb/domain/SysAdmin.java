package edu.esprit.pfe.store.ejb.domain;

import java.io.Serializable;
import java.lang.String;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: SysAdmin
 * 
 */
@Entity
@Table(name = "tab_sysadmin")
public class SysAdmin extends User implements Serializable {

	@Column(name = "SYS_ADMIN_CODE")
	private String code;
	private static final long serialVersionUID = 1L;

	public SysAdmin() {
		super();
	}

	public SysAdmin(String firstName, String lastName,
			String email, String phoneNumber, String login, String password,
			String code) {
		super(firstName, lastName, email, phoneNumber, login, password);
		this.code = code;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
