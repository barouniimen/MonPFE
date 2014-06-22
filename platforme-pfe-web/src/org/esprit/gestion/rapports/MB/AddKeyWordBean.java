package org.esprit.gestion.rapports.MB;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.esprit.gestion.rapports.persistence.KeyWord;
import org.esprit.gestion.rapports.persistence.KeyWordCategory;
import org.esprit.gestion.rapports.services.facades.Interfaces.IKeyWordFacadeLocal;
import org.primefaces.context.RequestContext;
import org.primefaces.event.ToggleEvent;
import org.primefaces.model.DualListModel;

@ManagedBean
@ViewScoped
public class AddKeyWordBean {

	private List<KeyWordCategory> listKwordCateg;
	private KeyWordCategory selectedCategory;
	private List<KeyWord> listKeyWord;
	private boolean renderlistKeyW;
	private boolean categSelected;
	private boolean notrenderlistKw;
	private boolean doAddKeyWord;
	private boolean renderAddButton;
	private KeyWord kwToDB;
	private boolean doAddCateg;
	private KeyWordCategory categToDB;
	private DualListModel<KeyWord> keyWordPicklis;
	private List<KeyWord> listKwSource;
	private List<KeyWord> listKwTarget;
	private int idProjectKeyWord;
	
	@ManagedProperty(value = "#{mailBoxBean}")
	private MailBoxBean mailBoxBean;

	
	
	@EJB
	IKeyWordFacadeLocal keyWordFacade;

	/*********************** init method ********************************/

	@PostConstruct
	public void init() {

		listKwordCateg = new ArrayList<KeyWordCategory>();
		listKwordCateg = keyWordFacade.allKeyWordCategories();
		selectedCategory = new KeyWordCategory();
		renderlistKeyW = false;
		setCategSelected(false);
		notrenderlistKw = false;
		setDoAddKeyWord(false);
		renderAddButton = false;
		
		listKwSource = new ArrayList<KeyWord>();
		listKwTarget = new ArrayList<KeyWord>();
		KeyWord kw = new KeyWord();
		kw.setName("name");
		kw.setId(15);
		
		listKwSource.add(kw);
		keyWordPicklis = new DualListModel<>(listKwSource, listKwTarget);
		
		kwToDB = new KeyWord();
		categToDB = new KeyWordCategory();
	}

	/************************ listeners ********************************/
	public void addKeyWordStudent(ActionEvent event) {
		idProjectKeyWord = mailBoxBean.getIdProjectKeyWord();
		System.out.println("id proj "+idProjectKeyWord);
		kwToDB.setCategory(categToDB);
		keyWordFacade.addKeyWordToStudent(kwToDB, idProjectKeyWord);
		try {
			RequestContext.getCurrentInstance().execute("acceptAddKw.hide();");
		} catch (Exception e) {
		}
	}
	

	public void addCateg(ActionEvent event) {
		keyWordFacade.addCategory(categToDB);
		try {
			RequestContext.getCurrentInstance().execute(
					"panelAddKw.toggle();");
		} catch (Exception e) {
		}
	}

	
	
	public void addKeyWord(ActionEvent event) {
		kwToDB.setCategory(selectedCategory);
		keyWordFacade.addKeyWord(kwToDB);
		try {
			RequestContext.getCurrentInstance().execute(
					"panelAddKw.toggle();");
		} catch (Exception e) {
		}
	}

	public void renderAddKwCateg(ActionEvent event) {
		categToDB = new KeyWordCategory();
		setDoAddCateg(true);
	}

	public void renderAddKw(ActionEvent event) {
		setDoAddKeyWord(true);
	}

	public void onChangeCategory() {
		
		// find catégories
		listKeyWord = keyWordFacade.keyWordsByCateg(selectedCategory);

		if (selectedCategory.getId() == -1) {
			categSelected = false;
			renderAddButton = false;
		} else {
			categSelected = true;
			renderAddButton = true;
		}

		if (categSelected && listKeyWord.isEmpty()) {
			renderlistKeyW = false;
			notrenderlistKw = true;
		}

		else if (categSelected && !(listKeyWord.isEmpty())) {
			renderlistKeyW = true;
			notrenderlistKw = false;
		}

	}

	public void handleToggle(ToggleEvent event) {
		categToDB = new KeyWordCategory();
		categSelected = false;
		doAddCateg = false;
		doAddKeyWord = false;
		kwToDB = new KeyWord();
		notrenderlistKw = false;
		renderAddButton = false;
		renderlistKeyW = false;
		selectedCategory = new KeyWordCategory();
		listKwordCateg = keyWordFacade.allKeyWordCategories();
	}

	/************************** constructor ****************************/
	public AddKeyWordBean() {
		super();
	}

	/*************************** getter && setter **********************/
	public List<KeyWordCategory> getListKwordCateg() {
		return listKwordCateg;
	}

	public void setListKwordCateg(List<KeyWordCategory> listKwordCateg) {
		this.listKwordCateg = listKwordCateg;
	}

	public KeyWordCategory getSelectedCategory() {
		return selectedCategory;
	}

	public void setSelectedCategory(KeyWordCategory selectedCategory) {
		this.selectedCategory = selectedCategory;
	}

	public List<KeyWord> getListKeyWord() {
		return listKeyWord;
	}

	public void setListKeyWord(List<KeyWord> listKeyWord) {
		this.listKeyWord = listKeyWord;
	}

	public boolean isRenderlistKeyW() {
		return renderlistKeyW;
	}

	public void setRenderlistKeyW(boolean renderlistKeyW) {
		this.renderlistKeyW = renderlistKeyW;
	}

	public boolean isCategSelected() {
		return categSelected;
	}

	public void setCategSelected(boolean categSelected) {
		this.categSelected = categSelected;
	}

	public boolean isNotrenderlistKw() {
		return notrenderlistKw;
	}

	public void setNotrenderlistKw(boolean notrenderlistKw) {
		this.notrenderlistKw = notrenderlistKw;
	}

	public boolean isRenderAddButton() {
		return renderAddButton;
	}

	public void setRenderAddButton(boolean renderAddButton) {
		this.renderAddButton = renderAddButton;
	}

	public KeyWord getKwToDB() {
		return kwToDB;
	}

	public void setKwToDB(KeyWord kwToDB) {
		this.kwToDB = kwToDB;
	}

	public boolean isDoAddKeyWord() {
		return doAddKeyWord;
	}

	public void setDoAddKeyWord(boolean doAddKeyWord) {
		this.doAddKeyWord = doAddKeyWord;
	}

	public boolean isDoAddCateg() {
		return doAddCateg;
	}

	public void setDoAddCateg(boolean doAddCateg) {
		this.doAddCateg = doAddCateg;
	}

	public KeyWordCategory getCategToDB() {
		return categToDB;
	}

	public void setCategToDB(KeyWordCategory categToDB) {
		this.categToDB = categToDB;
	}

	public DualListModel<KeyWord> getKeyWordPicklis() {
		return keyWordPicklis;
	}

	public void setKeyWordPicklis(DualListModel<KeyWord> keyWordPicklis) {
		this.keyWordPicklis = keyWordPicklis;
	}

	public List<KeyWord> getListKwSource() {
		return listKwSource;
	}

	public void setListKwSource(List<KeyWord> listKwSource) {
		this.listKwSource = listKwSource;
	}

	public List<KeyWord> getListKwTarget() {
		return listKwTarget;
	}

	public void setListKwTarget(List<KeyWord> listKwTarget) {
		this.listKwTarget = listKwTarget;
	}

	public int getIdProjectKeyWord() {
		return idProjectKeyWord;
	}

	public void setIdProjectKeyWord(int idProjectKeyWord) {
		this.idProjectKeyWord = idProjectKeyWord;
	}

	public void setMailBoxBean(MailBoxBean mailBoxBean) {
		this.mailBoxBean = mailBoxBean;
	}



}
