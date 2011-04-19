package beans;

import java.util.ArrayList;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import models.ReplyModel;
import models.ThreadModel;
import models.UserModel;

@ManagedBean
@SessionScoped
public class UserBean {
	public void logout() {
		
	}
	
	public UserModel getUser() throws Throwable {
		Map<String, String> request = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		Integer userId = Integer.parseInt(request.get("userId"));
		
		if(userId == null) throw new Exception("Invalid Request");
		
		return new UserModel(userId);
	}
	
	public ArrayList<ThreadModel> getLatestThreads() throws Throwable {
		return this.getUser().getThreads(0, 5);
	}
	
	public ArrayList<ReplyModel> getLatestReplies() throws Throwable {
		return this.getUser().getReplies(0, 5);
	}
	
	public ArrayList<UserModel> getUsers() throws Throwable {
		return UserModel.getUsers(0, 20);
	}
}
