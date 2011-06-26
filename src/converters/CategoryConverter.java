package converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import models.CategoryModel;

/**
 * Converts a category object to its string representation or the
 * string back into the corresponding object
 * 
 * @author davidknezic <davidknezic@gmail.com>
 */
public class CategoryConverter implements Converter {

	/**
	 * Convert a string to the corresponding category object
	 * 
	 * @param FacesContext context
	 * @param UIComponent component
	 * @param String value
	 * @return Object
	 */
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		try {
			return new CategoryModel(Integer.parseInt(value));
		} catch (Throwable e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Convert the category object to its string representation
	 * 
	 * @param FacesContext context
	 * @param UIComponent component
	 * @param Object value
	 * @return String
	 */
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		CategoryModel category = (CategoryModel) value;
		return String.format("%d", category.getCategoryId());
	}

}
