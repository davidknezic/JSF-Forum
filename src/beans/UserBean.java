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
	private ArrayList<ThreadModel> threads;
	private ArrayList<ReplyModel> replies;
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
		if(threads == null)
			threads = this.getUser().getThreads(0, 5);
		return threads;
	}
	
	public ArrayList<ReplyModel> getLatestReplies() throws Throwable {
		if(replies == null)
			replies = this.getUser().getReplies(0, 5);
		return replies;
	}
	
	public ArrayList<UserModel> getUsers() throws Throwable {
		if(users == null)
			users = UserModel.getUsers(0, 20);
		return users;
	}
}
