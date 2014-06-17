package org.esprit.gestion.rapports.services.facades.Interfaces;

import java.util.List;

import javax.ejb.Remote;

import org.esprit.gestion.rapports.persistence.KeyWord;
import org.esprit.gestion.rapports.persistence.KeyWordCategory;
import org.esprit.gestion.rapports.persistence.Student;

@Remote
public interface IKeyWordFacadeRemote {
	
	public List<KeyWordCategory> allKeyWordCategories();
	
	public List<KeyWord> keyWordsByCateg(KeyWordCategory keyWordcategory);
	
	public void addKeyWord(KeyWord keyWord);
	
	public void addCategory(KeyWordCategory category);
	
	public void askNewKeyWord(String newCategory,
			String newKeyWord, Student sender);

}
