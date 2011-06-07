package converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

public class LengthConverter implements Converter {

	private static final int LENGTH = 40;
	
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		return null;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
			if(((String)arg2).length() < LENGTH){
				return (String)arg2;
			}
			return ((String)arg2).substring(0, LENGTH)+"...";
	}

}
