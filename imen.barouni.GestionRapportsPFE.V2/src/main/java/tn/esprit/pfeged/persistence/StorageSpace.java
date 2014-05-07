package tn.esprit.pfeged.persistence;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class StorageSpace implements Serializable {

	private int allocatedSpace;
	private static final long serialVersionUID = 1L;

	public StorageSpace() {
		super();
	}

	public int getAllocatedSpace() {
		return this.allocatedSpace;
	}

	public void setAllocatedSpace(int allocatedSpace) {
		this.allocatedSpace = allocatedSpace;
	}

}
