package org.esprit.gestion.rapports.Convertor;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.esprit.gestion.rapports.persistence.Domain;

@FacesConverter(value="StringToDom")
public class DomainStringConvertor implements Converter{
	
	
	

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		System.out.println("valu: "+value);
		Domain dom = new Domain();
		dom.setDomainName(value);
		return dom;
		
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object obj) {
		Domain dom = (Domain) obj;
		return dom.toString();
	}

}
