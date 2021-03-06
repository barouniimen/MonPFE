package org.esprit.gestion.rapports.persistence;

import java.io.Serializable;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Administrator
 *
 */
@Entity
@DiscriminatorValue(value = "Administrator")
@NamedQueries({
	@NamedQuery(name = "Administrator.findAll", query = "SELECT a FROM Administrator a")
	})
public class Administrator extends User implements Serializable {

	
	private static final long serialVersionUID = 1L;

	public Administrator() {
		super();
	}
   
}
