package org.esprit.gestion.rapports.persistence;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
/**
 * @author Imen Barouni
 *
 */
@Entity
public class StorageSpace implements Serializable {

	private int id;
	private int allocatedSpace;
	private static final long serialVersionUID = 1L;

	
	
	public StorageSpace(int allocatedSpace) {
		super();
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

}
