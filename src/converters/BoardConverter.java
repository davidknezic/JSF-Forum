package converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import models.BoardModel;

/**
 * Converts a board object to its string representation or the
 * string back into the corresponding object
 * 
 * @author davidknezic <davidknezic@gmail.com>
 */
public class BoardConverter implements Converter {

	/**
	 * Convert a string to the corresponding board object
	 * 
	 * @param FacesContext context
	 * @param UIComponent component
	 * @param String value
	 * @return Object
	 */
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		try {
			return new BoardModel(Integer.parseInt(value));
		} catch (Throwable e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Convert the board object to its string representation
	 * 
	 * @param FacesContext context
	 * @param UIComponent component
	 * @param Object value
	 * @return String
	 */
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		BoardModel board = (BoardModel) value;
		return String.format("%d", board.getBoardId());
	}

}
