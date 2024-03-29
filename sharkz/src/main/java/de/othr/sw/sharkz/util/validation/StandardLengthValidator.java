package de.othr.sw.sharkz.util.validation;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator(value="standardLengthValidator")
public class StandardLengthValidator implements Validator {

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String input = (String) value;
        
        String label = (String) component.getAttributes().get("label");
        
        if (label == null || label.equals(""))
            label = "ERROR_NO_LABEL";
        else
        
        if (input.length() > 255)
            throw new ValidatorException(new FacesMessage(
                    "Bitte geben Sie höchstens 255 Zeichen im Feld "
                            + label + " ein!"));
    }
    
}
