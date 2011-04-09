package converters;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

/**
 * Simplifies the provided date. Only works for output fields.
 * 
 * @author davidknezic <davidknezic@gmail.com>
 */
public class DateConverter implements Converter {

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
	 * Converts the provided date into an simplified one.
	 * 
	 * @param FacesContext context
	 * @param UIComponent component
	 * @param Object value
	 * @return String
	 */
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		SimpleDateFormat formatter = new SimpleDateFormat();
		Calendar now = Calendar.getInstance();
		Calendar today;
		Calendar tmp;

		// Set now to original date
		now.setTime((Date) value);

		// Today's start date/time
		today = Calendar.getInstance();
		today.set(Calendar.HOUR, 0);
		today.set(Calendar.MINUTE, 0);
		today.set(Calendar.SECOND, 0);
		today.set(Calendar.MILLISECOND, 0);
		today.set(Calendar.AM_PM, Calendar.AM);

		// Today?
		tmp = (Calendar) today.clone();
		if (now.after(tmp)) {
			return "today";
		}

		// Yesterday?
		tmp = (Calendar) today.clone();
		tmp.add(Calendar.DAY_OF_MONTH, -1);
		if (now.after(tmp)) {
			return "yesterday";
		}

		// This week? Weekday
		tmp = (Calendar) today.clone();
		tmp.add(Calendar.WEEK_OF_YEAR, -1);
		if (now.after(tmp)) {
			formatter.applyPattern("EEEE");
			return formatter.format(now.getTime());
		}

		// This year? Day and month
		tmp = (Calendar) today.clone();
		tmp.add(Calendar.YEAR, -1);
		if (now.after(tmp)) {
			formatter.applyPattern("EEEE dd.");
			return formatter.format(now.getTime());
		}

		// Else? Day, month and year
		formatter.applyPattern("dd. MMMM yyyy");
		return formatter.format(now.getTime());
	}

}
