package org.esprit.gestion.rapports.persistence;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
/**
 * @author Imen Barouni
 *
 */
@Entity
@Table(name = "t_user")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "userType", discriminatorType = DiscriminatorType.STRING)
@NamedQueries({
	@NamedQuery(name = "User.findById", query = "SELECT u FROM User u WHERE u.id = :id"),
	@NamedQuery(name = "User.findByFirstAndLastName", query = "SELECT u FROM User u WHERE u.firstName = :firstName and u.lastName = :lastName"),
	@NamedQuery(name = "User.findByLoginAndPassword", query = "SELECT u FROM User u WHERE u.login = :login and u.password = :password")
})
public class User implements Serializable {

	private int id;
	private String firstName;
	private String lastName;
	private String login;
	private String password;
	private String email;
	private int phoneNumber;
	private List<UserMessage> messages;
	private List<Appointement> appointements;
	private static final long serialVersionUID = 1L;

	
	
	

	public User(String firstName, String lastName, String login,
			String password, String email, int phoneNumber,
			List<UserMessage> messages, List<Appointement> appointements) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.login = login;
		this.password = password;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.messages = messages;
		this.appointements = appointements;
	}

	public User() {
		super();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Column(name="login", unique=true)
	
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	@Column(unique=true)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getPhoneNumber() {
		return this.phoneNumber;
	}

	public void setPhoneNumber(int phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@OneToMany(mappedBy = "user")
	public List<UserMessage> getMessages() {
		return messages;
	}

	public void setMessages(List<UserMessage> messages) {
		this.messages = messages;
	}

	@OneToMany(mappedBy = "personToMeet")
	public List<Appointement> getAppointements() {
		return appointements;
	}

	public void setAppointements(List<Appointement> appointements) {
		this.appointements = appointements;
	}
}