package beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import models.UserModel;

@ManagedBean
@SessionScoped
public class UserBean {
	private UserModel loggedUser;
	
	public void logout() {
		
	}
}
