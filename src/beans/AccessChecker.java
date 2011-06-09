package beans;

import java.util.HashMap;

import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import models.UserModel;

public class AccessChecker implements PhaseListener {
	private HashMap<String, Integer> permissions = new HashMap<String, Integer>();

	public AccessChecker() {
		permissions.put("/editProfile.xhtml", UserModel.USER);
		permissions.put("/newBoard.xhtml", UserModel.USER);
		permissions.put("/newThread.xhtml", UserModel.USER);
		permissions.put("/editReply.xhtml", UserModel.ADMIN);
		permissions.put("/editThread.xhtml", UserModel.ADMIN);
		permissions.put("/newBoard.xhtml", UserModel.SUPER_ADMIN);
		permissions.put("/newCategory.xhtml", UserModel.SUPER_ADMIN);
	}

	@Override
	public void afterPhase(PhaseEvent event) {

		FacesContext fc = event.getFacesContext();

		// Check to see if they are on the login page.
		String viewId = fc.getViewRoot().getViewId();

		System.out.println(viewId);

		Integer permission = permissions.get(viewId);

		if (permission != null && !hasPermission(permission)) {
			fc.getApplication()
					.getNavigationHandler()
					.handleNavigation(fc, null, "/insufficientPermission.xhtml");
			System.out.println("...");
			return;
		}

		System.out.println("ok");
	}

	private boolean hasPermission(int perm) {
		LoginBean loginBean = (LoginBean) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("loginBean");

		if (loginBean == null || loginBean.getLoggedin() == false
				|| perm > loginBean.getUser().getPermission())
			return false;

		return true;
	}

	@Override
	public void beforePhase(PhaseEvent arg0) {
	}

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.RESTORE_VIEW;
	}

}
