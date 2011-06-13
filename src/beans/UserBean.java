package beans;

import java.util.ArrayList;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import models.Settings;
import models.UserModel;

@ManagedBean
@RequestScoped
public class UserBean {
	private ArrayList<UserModel> users;
	
	private int currentPage;
	private int pages;
	
	@ManagedProperty(value = "#{loginBean}")
	private LoginBean loginBean;

	/**
	 * UserPermissions
	 */
	public final int USER = 0;
	public final int ADMIN = 1;
	public final int SUPER_ADMIN = 2;
	
	public UserBean() throws Throwable {
		Map<String, String> request = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		
		/*
		 * Get page count
		 */
		this.pages = UserModel.getUserCount()/Settings.getInstance().getInt("usersPerPage");
		if (UserModel.getUserCount()%Settings.getInstance().getInt("usersPerPage") != 0) {
			this.pages++;
		}

		/*
		 * Save requested page number
		 */
		try {
			this.currentPage = Integer.parseInt(request.get("page"));
			
			// Check boundaries
			if (this.currentPage < 0) {
				this.currentPage = 0;
			}
			if (this.currentPage > this.pages) {
				this.currentPage = this.pages - 1;
			}
		} catch (Exception e) {
			this.currentPage = 0;
		}
	}

	public int getCurrentPage() {
		return this.currentPage;
	}
	
	public boolean hasPrev() {
		return (this.currentPage > 0);
	}
	
	public boolean hasNext() throws Throwable {
		return (this.currentPage + 1 < this.pages);
	}
	
	public int getUSER() {
		return USER;
	}

	public int getADMIN() {
		return ADMIN;
	}

	public int getSUPER_ADMIN() {
		return SUPER_ADMIN;
	}
	
	public String grant(UserModel user) throws Throwable {
		if(loginBean.getLoggedin() & loginBean.getUser().getPermission() >= this.SUPER_ADMIN)
			user.save();
		
		String re = String.format("users.xhtml?page=%d", this.currentPage);
		FacesContext.getCurrentInstance().getExternalContext().redirect(re);
		
		return null;
	}
	
	public ArrayList<UserModel> getUsers() throws Throwable {
		if(users == null)
			users = UserModel.getUsers(currentPage*Settings.getInstance().getInt("usersPerPage"), Settings.getInstance().getInt("usersPerPage"));
		return users;
	}
	
	public LoginBean getLoginBean() {
		return loginBean;
	}

	public void setLoginBean(LoginBean loginBean) {
		this.loginBean = loginBean;
	}
}
