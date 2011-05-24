package beans;

import java.util.ArrayList;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import models.ReplyModel;
import models.Settings;
import models.ThreadModel;
import models.UserModel;

@ManagedBean
@RequestScoped
public class UserBean {
	private UserModel user;
	private ArrayList<ThreadModel> latestThreads;
	private ArrayList<ReplyModel> latestReplies;
	private ArrayList<UserModel> users;
	
	@ManagedProperty(value = "#{loginBean}")
	private LoginBean loginBean;

	/**
	 * UserPermissions
	 */
	public final int USER = 0;
	public final int ADMIN = 1;
	public final int SUPER_ADMIN = 2;
	
	public int getUSER() {
		return USER;
	}

	public int getADMIN() {
		return ADMIN;
	}

	public int getSUPER_ADMIN() {
		return SUPER_ADMIN;
	}

	public UserModel getUser() throws Throwable {
		if(user == null) { 
			Map<String, String> request = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
			Integer userId = Integer.parseInt(request.get("userId"));
			
			if(userId == null) throw new Exception("Invalid Request");
			
			user = new UserModel(userId);
		}
		return user;
	}
	
	public void grant(UserModel user) throws Throwable {
		if(loginBean.getLoggedin() & loginBean.getUser().getPermission() >= this.SUPER_ADMIN)
			user.save();
	}
	
	public ArrayList<ThreadModel> getLatestThreads() throws Throwable {
		if(latestThreads == null)
			latestThreads = this.getUser().getThreads(0, Settings.getInstance().getInt("profileLatestThreads"));
		return latestThreads;
	}
	
	public ArrayList<ReplyModel> getLatestReplies() throws Throwable {
		if(latestReplies == null)
			latestReplies = this.getUser().getReplies(0, Settings.getInstance().getInt("profileLatestReplies"));
		return latestReplies;
	}
	
	public ArrayList<UserModel> getUsers() throws Throwable {
		if(users == null)
			users = UserModel.getUsers(0, 20);
		return users;
	}
	
	public LoginBean getLoginBean() {
		return loginBean;
	}

	public void setLoginBean(LoginBean loginBean) {
		this.loginBean = loginBean;
	}
}
