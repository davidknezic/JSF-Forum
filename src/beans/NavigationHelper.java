package beans;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

@ManagedBean
@ApplicationScoped
public class NavigationHelper {
	private static final String ACTIVE_CLASS = "active";
	
	public String getClass(String facelet) {
		String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
		if(viewId.equals(facelet))
			return NavigationHelper.ACTIVE_CLASS;
		return "";
	}
}
