package beans;

import java.util.ArrayList;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

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
	
	public ArrayList<UserModel> getUsers() throws Throwable {
		return UserModel.getUsers(0, 20);
	}
}
