package tn.esprit.PlateformPFE.Persistence;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 * Entity implementation class for Entity: KeyWord
 * 
 */
@Entity
public class KeyWord implements Serializable {

	private int id;
	private String category;
	private String name;
	private List<ReportKeyWord> keyWords;
	private KeyWordType keyWordType;
	private static final long serialVersionUID = 1L;

	public KeyWord() {
		super();
	}

	@Id
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCategory() {
		return this.category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@OneToMany(mappedBy = "keyWord", cascade = CascadeType.ALL)
	public List<ReportKeyWord> getKeyWords() {
		return keyWords;
	}

	public void setKeyWords(List<ReportKeyWord> keyWords) {
		this.keyWords = keyWords;
	}

	@ManyToOne
	@JoinColumn(name="keyWordType")
	public KeyWordType getKeyWordType() {
		return keyWordType;
	}

	public void setKeyWordType(KeyWordType keyWordType) {
		this.keyWordType = keyWordType;
	}

}
