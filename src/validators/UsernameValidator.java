/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package validators;

import java.util.ResourceBundle;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import models.UserModel;

/**
 *
 * @author sven
 */
public class UsernameValidator implements Validator {

	private ResourceBundle getResourceBundle(FacesContext context) {
		ResourceBundle rb;
		
		Application app = context.getApplication();
		try {
			rb = ResourceBundle.getBundle(app.getMessageBundle());
		} catch (Exception e) {
			rb = ResourceBundle.getBundle(app.getMessageBundle(), app.getDefaultLocale());
		}
		
		return rb;
	}

	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		ResourceBundle rb;
		UserModel user;

		if (((String) value).length() < 3) {
			rb = this.getResourceBundle(context);
			
			FacesMessage message = new FacesMessage();
			message.setDetail(rb.getString("usernameMin"));
			message.setSummary(rb.getString("usernameMin"));
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(message);
		}

		try {
			user = UserModel.getUserByUsername((String) value);
		} catch (Throwable ex) {
			rb = this.getResourceBundle(context);

			FacesMessage message = new FacesMessage();
			message.setDetail(rb.getString("validationError"));
			message.setSummary(rb.getString("validationError"));
			message.setSeverity(FacesMessage.SEVERITY_FATAL);
			throw new ValidatorException(message);
		}

		if (user != null) {
			rb = this.getResourceBundle(context);

			FacesMessage message = new FacesMessage();
			message.setDetail(rb.getString("usernameAlreadyInUse"));
			message.setSummary(rb.getString("usernameAlreadyInUse"));
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(message);
		}
	}
}
