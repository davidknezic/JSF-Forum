package converters;

import java.util.Date;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

/**
 * Converts a date to the Age. Only works for output fields.
 * 
 * @author Sven Tschui <info@eniu.ch>
 */
public class AgeConverter implements Converter {

	/**
	 * Not implemented! This is an output only converter.
	 * 
	 * @param FacesContext context
	 * @param UIComponent component
	 * @param String value
	 * @return Object
	 */
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		return null;
	}

	/**
	 * Converts the provided date into an age.
	 * 
	 * @param FacesContext context
	 * @param UIComponent component
	 * @param Object value
	 * @return String
	 */
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		Date dateValue = (Date)value;
		Date now = new Date();
		
		if(dateValue.after(new Date())) return Integer.toString(now.getYear() - dateValue.getYear() - 1);
		
		return Integer.toString(now.getYear() - dateValue.getYear());
	}

}
