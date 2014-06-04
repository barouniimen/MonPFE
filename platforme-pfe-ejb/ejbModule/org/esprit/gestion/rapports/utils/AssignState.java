package org.esprit.gestion.rapports.utils;

import java.util.Date;

import org.esprit.gestion.rapports.persistence.AssignResponseState;

public class AssignState {
	
	private int idMsg;
	private Date sendingDate;
	private String teacherName;
	private int idProj;
	private int idTeacher;
	
	private AssignResponseState responseState;
	
	
	
	
	
	public AssignState(int idMsg, Date sendingDate, String teacherName,
			int idProj, int idTeacher, AssignResponseState responseState) {
		super();
		this.idMsg = idMsg;
		this.sendingDate = sendingDate;
		this.teacherName = teacherName;
		this.idProj = idProj;
		this.idTeacher = idTeacher;
		this.responseState = responseState;
	}



	public AssignState() {
		super();
		// TODO Auto-generated constructor stub
	}



	public int getIdMsg() {
		return idMsg;
	}
	public void setIdMsg(int idMsg) {
		this.idMsg = idMsg;
	}
	public Date getSendingDate() {
		return sendingDate;
	}
	public void setSendingDate(Date sendingDate) {
		this.sendingDate = sendingDate;
	}
	
	public int getIdProj() {
		return idProj;
	}
	public void setIdProj(int idProj) {
		this.idProj = idProj;
	}

	public AssignResponseState getResponseState() {
		return responseState;
	}
	public void setResponseState(AssignResponseState responseState) {
		this.responseState = responseState;
	}



	public String getTeacherName() {
		return teacherName;
	}



	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}



	public int getIdTeacher() {
		return idTeacher;
	}



	public void setIdTeacher(int idTeacher) {
		this.idTeacher = idTeacher;
	}
	
	
	

}
