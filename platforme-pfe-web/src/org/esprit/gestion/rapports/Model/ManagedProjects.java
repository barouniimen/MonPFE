package org.esprit.gestion.rapports.Model;

import java.util.Date;
import java.util.List;

import org.esprit.gestion.rapports.persistence.ProjectDomain;
import org.esprit.gestion.rapports.persistence.ValidationState;

public class ManagedProjects {

	/******************************** Attributes ***************************************/
	
	//project Info----------------------------------
	private int idPorj;
	private String projTopic;
	private ValidationState projState;
	private List<ProjectDomain> projDomainList;
	private Date startDate;
	private String academicYear;
		
	//company Info----------------------------------
	private String companyName;
	private String companyAdress;
	
	//coach Info------------------------------------
	private int coachId;
	private String coachName;
	private String coachFirstName;
	
	//corrector Info--------------------------------
	private String correctorName;
	private String correctorFirstName;
	
	//companyCoach Info-----------------------------
	private int compCoachId;
	private String companyCoachName;
	private String companyCoachFirstName;
	private String compCoachEmail;
	private int compCoachphoneNbre;
	private String compCoachPosition;
	
	//Student Info----------------------------------
	private int studentId;
	private String studentName;
	private String studentFirstName;
	private String studentRegistrationNbre;
	

	
	
	/*************************** Constructors *********************************/
	public ManagedProjects() {
		// TODO Auto-generated constructor stub
	}


	public ManagedProjects(int idPorj, String projTopic,
			ValidationState projState, List<ProjectDomain> projDomainList,
			Date startDate, String academicYear, String companyName,
			String companyAdress, int coachId, String coachName,
			String coachFirstName, String correctorName,
			String correctorFirstName, int compCoachId,
			String companyCoachName, String companyCoachFirstName,
			String compCoachEmail, int compCoachphoneNbre,
			String compCoachPosition, int studentId, String studentName,
			String studentFirstName, String studentRegistrationNbre) {
		super();
		this.idPorj = idPorj;
		this.projTopic = projTopic;
		this.projState = projState;
		this.projDomainList = projDomainList;
		this.startDate = startDate;
		this.academicYear = academicYear;
		this.companyName = companyName;
		this.companyAdress = companyAdress;
		this.coachId = coachId;
		this.coachName = coachName;
		this.coachFirstName = coachFirstName;
		this.correctorName = correctorName;
		this.correctorFirstName = correctorFirstName;
		this.compCoachId = compCoachId;
		this.companyCoachName = companyCoachName;
		this.companyCoachFirstName = companyCoachFirstName;
		this.compCoachEmail = compCoachEmail;
		this.compCoachphoneNbre = compCoachphoneNbre;
		this.compCoachPosition = compCoachPosition;
		this.studentId = studentId;
		this.studentName = studentName;
		this.studentFirstName = studentFirstName;
		this.studentRegistrationNbre = studentRegistrationNbre;
	}





	/*************************** Getter && Setter *********************************/

	public int getIdPorj() {
		return idPorj;
	}

	public void setIdPorj(int idPorj) {
		this.idPorj = idPorj;
	}

	public String getProjTopic() {
		return projTopic;
	}

	public void setProjTopic(String projTopic) {
		this.projTopic = projTopic;
	}

	public ValidationState getProjState() {
		return projState;
	}

	public void setProjState(ValidationState projState) {
		this.projState = projState;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCoachName() {
		return coachName;
	}

	public void setCoachName(String coachName) {
		this.coachName = coachName;
	}

	public String getCoachFirstName() {
		return coachFirstName;
	}

	public void setCoachFirstName(String coachFirstName) {
		this.coachFirstName = coachFirstName;
	}

	public String getCorrectorName() {
		return correctorName;
	}

	public void setCorrectorName(String correctorName) {
		this.correctorName = correctorName;
	}

	public String getCorrectorFirstName() {
		return correctorFirstName;
	}

	public void setCorrectorFirstName(String correctorFirstName) {
		this.correctorFirstName = correctorFirstName;
	}

	public String getCompanyCoachName() {
		return companyCoachName;
	}

	public void setCompanyCoachName(String companyCoachName) {
		this.companyCoachName = companyCoachName;
	}

	public String getCompanyCoachFirstName() {
		return companyCoachFirstName;
	}

	public void setCompanyCoachFirstName(String companyCoachFirstName) {
		this.companyCoachFirstName = companyCoachFirstName;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getStudentFirstName() {
		return studentFirstName;
	}

	public void setStudentFirstName(String studentFirstName) {
		this.studentFirstName = studentFirstName;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/*************************** Hashcode && Equals *********************************/
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + idPorj;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ManagedProjects other = (ManagedProjects) obj;
		if (idPorj != other.idPorj)
			return false;
		return true;
	}



	public List<ProjectDomain> getProjDomainList() {
		return projDomainList;
	}



	public void setProjDomainList(List<ProjectDomain> projDomainList) {
		this.projDomainList = projDomainList;
	}



	public String getCompanyAdress() {
		return companyAdress;
	}



	public void setCompanyAdress(String companyAdress) {
		this.companyAdress = companyAdress;
	}



	public String getAcademicYear() {
		return academicYear;
	}



	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}



	public int getCoachId() {
		return coachId;
	}



	public void setCoachId(int coachId) {
		this.coachId = coachId;
	}



	public int getCompCoachId() {
		return compCoachId;
	}



	public void setCompCoachId(int compCoachId) {
		this.compCoachId = compCoachId;
	}



	public int getStudentId() {
		return studentId;
	}



	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}



	public String getCompCoachEmail() {
		return compCoachEmail;
	}



	public void setCompCoachEmail(String compCoachEmail) {
		this.compCoachEmail = compCoachEmail;
	}



	public int getCompCoachphoneNbre() {
		return compCoachphoneNbre;
	}



	public void setCompCoachphoneNbre(int compCoachphoneNbre) {
		this.compCoachphoneNbre = compCoachphoneNbre;
	}



	public String getCompCoachPosition() {
		return compCoachPosition;
	}



	public void setCompCoachPosition(String compCoachPosition) {
		this.compCoachPosition = compCoachPosition;
	}



	public String getStudentRegistrationNbre() {
		return studentRegistrationNbre;
	}



	public void setStudentRegistrationNbre(String studentRegistrationNbre) {
		this.studentRegistrationNbre = studentRegistrationNbre;
	}

}
