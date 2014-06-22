package org.esprit.gestion.rapports.services.facades.Impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.esprit.gestion.rapports.persistence.KeyWord;
import org.esprit.gestion.rapports.persistence.KeyWordCategory;
import org.esprit.gestion.rapports.persistence.Project;
import org.esprit.gestion.rapports.persistence.Report;
import org.esprit.gestion.rapports.persistence.ReportKeyWord;
import org.esprit.gestion.rapports.persistence.ReportKeyWordPk;
import org.esprit.gestion.rapports.persistence.ReportState;
import org.esprit.gestion.rapports.persistence.Student;
import org.esprit.gestion.rapports.services.CRUD.Interfaces.IServiceLocal;
import org.esprit.gestion.rapports.services.CRUD.Util.KWcategoryQualifier;
import org.esprit.gestion.rapports.services.CRUD.Util.KeyWordsQualifier;
import org.esprit.gestion.rapports.services.CRUD.Util.ProjectQualifier;
import org.esprit.gestion.rapports.services.CRUD.Util.ReportKeyWordQualifier;
import org.esprit.gestion.rapports.services.CRUD.Util.ReportQualifier;
import org.esprit.gestion.rapports.services.facades.Interfaces.IKeyWordFacadeLocal;
import org.esprit.gestion.rapports.services.facades.Interfaces.IKeyWordFacadeRemote;
import org.esprit.gestion.rapports.services.facades.Interfaces.IMessageFacadeLocal;

@Stateless
public class KeyWordFacade implements IKeyWordFacadeLocal, IKeyWordFacadeRemote {

	@Inject
	@KWcategoryQualifier
	IServiceLocal<KeyWordCategory> keyWordCategServ;

	@Inject
	@KeyWordsQualifier
	IServiceLocal<KeyWord> keyWordServ;

	@Inject
	@ReportQualifier
	IServiceLocal<Report> reportServ;

	@Inject
	@ReportKeyWordQualifier
	IServiceLocal<ReportKeyWord> repKwServ;

	@Inject
	@ProjectQualifier
	IServiceLocal<Project> projServ;

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
	public void askNewKeyWord(String newCategory, String newKeyWord,
			Student sender) {
		msgFacade.sendAskNewKeyWord(newCategory, newKeyWord, sender);

	}

	@Override
	public void addKeyWordToStudent(KeyWord keyWord, int idProject) {

		System.out.println("on facade!!!!!!!!!!!!!!!!!!");
		
		KeyWordCategory categ = new KeyWordCategory();

		categ.setCategoryName(keyWord.getCategory().getCategoryName());

		categ = (KeyWordCategory) keyWordCategServ.retrieve(categ, "name");
		
	
		keyWord.setCategory(categ);

		keyWordServ.create(keyWord);

		// assign kw to report
		Project proj = new Project();
		proj.setId(idProject);
		proj = (Project) projServ.retrieve(proj, "ID");

		Report report = new Report();
		report.setProject(proj);
		List<Report> listRepot = new ArrayList<Report>();

		listRepot = reportServ.retrieveList(report, "proj");

		for (int i = 0; i < listRepot.size(); i++) {
			if (listRepot.get(i).getState().equals(ReportState.DEPOSED)) {
				report = listRepot.get(i);
				i = listRepot.size();
			}
		}

		ReportKeyWord repKw = new ReportKeyWord();
		ReportKeyWordPk pk = new ReportKeyWordPk();
		pk.setKeyWordId(keyWord.getId());
		pk.setReportId(report.getId());

		repKw.setPk(pk);

		repKwServ.create(repKw);

	}

}
