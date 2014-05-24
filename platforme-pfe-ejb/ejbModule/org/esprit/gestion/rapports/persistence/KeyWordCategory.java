package org.esprit.gestion.rapports.persistence;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
/**
 * @author Imen Barouni
 *
 */
@Entity
@NamedQueries({
		@NamedQuery(name = "Keywordcategory.findAll", query = "SELECT k FROM KeyWordCategory k"),
		@NamedQuery(name = "Keywordcategory.findById", query = "SELECT k FROM KeyWordCategory k WHERE k.id = :id"),
		@NamedQuery(name = "Keywordcategory.findByCategoryName", query = "SELECT k FROM KeyWordCategory k WHERE k.categoryName = :categoryName") })
public class KeyWordCategory implements Serializable {

	private int id;
	private String categoryName;
	private List<KeyWord> keyWords;
	private static final long serialVersionUID = 1L;

	
	
	public KeyWordCategory(String categoryName) {
		super();
		this.categoryName = categoryName;
	}

	public KeyWordCategory() {
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

	public String getCategoryName() {
		return this.categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	@OneToMany(mappedBy = "category")
	public List<KeyWord> getKeyWords() {
		return keyWords;
	}

	public void setKeyWords(List<KeyWord> keyWords) {
		this.keyWords = keyWords;
	}

}
