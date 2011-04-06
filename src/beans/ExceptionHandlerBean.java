package beans;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

/**
 * Exception handler bean
 * 
 * @author davidknezic <davidknezic@gmail.com>
 */
@ManagedBean(name = "e")
@RequestScoped
public class ExceptionHandlerBean {

	/**
	 * The exception
	 */
	private Throwable exception;

	/**
	 * Save a reference to the exception
	 */
	public ExceptionHandlerBean() {
		FacesContext fc = FacesContext.getCurrentInstance();
		Map<String, Object> requestMap = fc.getExternalContext()
				.getRequestMap();

		exception = (Throwable) requestMap.get("javax.servlet.error.exception");
	}

	/**
	 * Get exception message
	 * 
	 * @return String
	 */
	public String getMessage() {
		return exception.getMessage();
	}

	/**
	 * Get exception stack trace
	 * 
	 * @return String
	 */
	public String getStackTrace() {
		StringWriter writer = new StringWriter();
		PrintWriter pw = new PrintWriter(writer);

		exception.printStackTrace(pw);

		return writer.toString();
	}

}
