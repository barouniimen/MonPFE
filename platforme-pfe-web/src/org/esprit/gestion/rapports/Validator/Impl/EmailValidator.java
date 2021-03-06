package org.esprit.gestion.rapports.Validator.Impl;
import java.util.Map;  
import java.util.regex.Matcher;  
import java.util.regex.Pattern;  

import javax.faces.application.FacesMessage;  
import javax.faces.component.UIComponent;  
import javax.faces.context.FacesContext;  
import javax.faces.validator.FacesValidator;  
import javax.faces.validator.Validator;  
import javax.faces.validator.ValidatorException;  

import org.esprit.gestion.rapports.Validator.Interface.ClientValidator;
  
/** 
 * Custom JSF Validator for Email input 
 */  
@FacesValidator("custom.emailValidator")  
public class EmailValidator implements Validator, ClientValidator {  
  
    private Pattern pattern;  
    private Matcher matcher;  
   
    private static final String EMAIL_PATTERN = "(^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"  
                                                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$)?";  
   
    public EmailValidator() {  
        pattern = Pattern.compile(EMAIL_PATTERN);  
    }  
  
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {  
        if(value == null) {  
            return;  
        }  
          
        if(!pattern.matcher(value.toString()).matches()) {  
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "format invalide: ",   
                        value + " l'email doit contenir un @ et se termier par un . suivi de deux lettres"));  
        }  
    }  
      
    public Map<String, Object> getMetadata() {  
        return null;  
    }  
  
    public String getValidatorId() {  
        return "custom.emailValidator";  
    }

	public Matcher getMatcher() {
		return matcher;
	}

	public void setMatcher(Matcher matcher) {
		this.matcher = matcher;
	}  
      
}  