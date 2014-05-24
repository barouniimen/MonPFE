package org.esprit.gestion.rapports.persistence;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
/**
 * @author Imen Barouni
 *
 */
@Entity
@NamedQueries({
	@NamedQuery(name = "StorageSpace.findAll", query = "SELECT s FROM StorageSpace s"),
	@NamedQuery(name = "StorageSpace.findByStudentID" , query = "SELECT s FROM StorageSpace s WHERE s.student.id = :studentid" )
	})

public class StorageSpace implements Serializable {

	private int id;
	private int allocatedSpace;
	private Student student;
	private static final long serialVersionUID = 1L;

	
	public StorageSpace(int allocatedSpace, Student student) {
		super();
		this.student = student;
		this.allocatedSpace = allocatedSpace;
	}
	public StorageSpace() {
		super();
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAllocatedSpace() {
		return this.allocatedSpace;
	}

	public void setAllocatedSpace(int allocatedSpace) {
		this.allocatedSpace = allocatedSpace;
	}
	
	@OneToOne
	public Student getStudent() {
		return student;
	}
	public void setStudent(Student student) {
		this.student = student;
	}


}
