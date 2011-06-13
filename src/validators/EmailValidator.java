package validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

public class EmailValidator implements Validator {

	@Override
	public void validate(FacesContext context, UIComponent component, Object object)
			throws ValidatorException {
		String email = (String)object;

        Pattern p = Pattern.compile("^[_a-zA-Z0-9-]+(\\.[_a-zA-Z0-9\\-]+)*@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9\\-]+)*\\.(([0-9]{1,3})|([a-zA-Z]{2,3})|(aero|coop|info|museum|name))$");
        
        Matcher m = p.matcher(email);
        
        boolean matchFound = m.matches();
        
        if (!matchFound) {
            FacesMessage message = new FacesMessage();
            message.setDetail("Email not valid");
            message.setSummary("Email not valid");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(message);
        }

	}

}
