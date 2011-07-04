package validators;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@ManagedBean
@RequestScoped
public class EqualityValidator implements Validator { 
	private UIInput v2;

	public UIInput getV2() {
		return v2;
	}

	public void setV2(UIInput v2) {
		this.v2 = v2;
	}

	@Override
	public void validate(FacesContext context, UIComponent component, Object v1)
			throws ValidatorException {
        if (!v1.equals(v2.getLocalValue())) {
            FacesMessage message = new FacesMessage();
            message.setDetail("Values are not equal");
            message.setSummary("Values are not equal");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(message);
        }

	}

}
