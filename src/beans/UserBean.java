package beans;

import java.util.ArrayList;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import models.ReplyModel;
import models.ThreadModel;
import models.UserModel;

@ManagedBean
@RequestScoped
public class UserBean {
	private UserModel user;
	private ArrayList<ThreadModel> latestThreads;
	private ArrayList<ReplyModel> latestReplies;
	private ArrayList<UserModel> users;
	
	public UserModel getUser() throws Throwable {
		if(user == null) { 
			Map<String, String> request = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
			Integer userId = Integer.parseInt(request.get("userId"));
			
			if(userId == null) throw new Exception("Invalid Request");
			
			user = new UserModel(userId);
		}
		return user;
	}
	
	public ArrayList<ThreadModel> getLatestThreads() throws Throwable {
		if(latestThreads == null)
			latestThreads = this.getUser().getThreads(0, 5);
		return latestThreads;
	}
	
	public ArrayList<ReplyModel> getLatestReplies() throws Throwable {
		if(latestReplies == null)
			latestReplies = this.getUser().getReplies(0, 5);
		return latestReplies;
	}
	
	public ArrayList<UserModel> getUsers() throws Throwable {
		if(users == null)
			users = UserModel.getUsers(0, 20);
		return users;
	}
}
