package org.esprit.gestion.rapports.services.facades.Impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.esprit.gestion.rapports.persistence.KeyWord;
import org.esprit.gestion.rapports.persistence.KeyWordCategory;
import org.esprit.gestion.rapports.persistence.Student;
import org.esprit.gestion.rapports.services.CRUD.Interfaces.IServiceLocal;
import org.esprit.gestion.rapports.services.CRUD.Util.KWcategoryQualifier;
import org.esprit.gestion.rapports.services.CRUD.Util.KeyWordsQualifier;
import org.esprit.gestion.rapports.services.facades.Interfaces.IKeyWordFacadeLocal;
import org.esprit.gestion.rapports.services.facades.Interfaces.IKeyWordFacadeRemote;
import org.esprit.gestion.rapports.services.facades.Interfaces.IMessageFacadeLocal;

@Stateless
public class KeyWordFacade implements IKeyWordFacadeLocal, IKeyWordFacadeRemote{

	@Inject
	@KWcategoryQualifier
	IServiceLocal<KeyWordCategory> keyWordCategServ;
	
	@Inject
	@KeyWordsQualifier
	IServiceLocal<KeyWord> keyWordServ;
	
	@EJB
	IMessageFacadeLocal msgFacade;
	
	@Override
	public List<KeyWordCategory> allKeyWordCategories() {
		List<KeyWordCategory> keyWordList = new ArrayList<KeyWordCategory>();
		
		keyWordList = keyWordCategServ.retrieveList(null, "ALL");
		
		return keyWordList;
	}

	@Override
	public List<KeyWord> keyWordsByCateg(KeyWordCategory keyWordcategory) {
		
		KeyWord kw = new KeyWord();
		kw.setCategory(keyWordcategory);
		
		List<KeyWord> listKeyWord = keyWordServ.retrieveList(kw, "Categ");
		
		return listKeyWord;
	}

	@Override
	public void addKeyWord(KeyWord keyWord) {
		keyWordServ.create(keyWord);
		
	}

	@Override
	public void addCategory(KeyWordCategory category) {
		keyWordCategServ.create(category);
	}

	@Override
	public void askNewKeyWord(String newCategory, String newKeyWord, Student sender) {
		msgFacade.sendAskNewKeyWord(newCategory,newKeyWord,sender);
		
	}

}
