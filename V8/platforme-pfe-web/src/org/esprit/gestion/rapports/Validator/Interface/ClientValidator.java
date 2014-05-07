package org.esprit.gestion.rapports.Validator.Interface;
import java.util.Map;

public interface ClientValidator {
    
    public Map<String,Object> getMetadata();
    
    public String getValidatorId();
}