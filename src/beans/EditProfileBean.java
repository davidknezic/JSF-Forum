package beans;

import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import models.UserModel;


@ManagedBean
@RequestScoped
public class EditProfileBean {
	private String firstName;
	private String lastName;
	private String password;
	private String passwordRepeated;
	private String email;
	private String website;
	private String location;
	private Date dob;
	
	private UserModel user;
	
	@ManagedProperty(value = "#{loginBean}")
	private LoginBean loginBean;
	
	public EditProfileBean() throws Throwable {
		int userId = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("userId"));
		
		this.user = new UserModel(userId);
		
		this.firstName = this.user.getFirstName();
		this.lastName = this.user.getLastName();
		this.email = this.user.getEmail();
		this.website = this.user.getWebsite();
		this.location = this.user.getLocation();
		
		this.dob = this.user.getDateOfBirth();
	}
	
	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	// FIXME: I'm broken!
	public String getPassword() {
		return "";
	}
	
	// FIXME: I'm broken!
	public void setPassword(String password) {
		this.password = password;
	}
	
	// FIXME: I'm broken!
	public String getPasswordRepeated() {
		return "";
	}
	
	// FIXME: I'm broken!
	public void setPasswordRepeated(String passwordRepeated) {
		this.passwordRepeated = passwordRepeated;
	}
	
	public String getEmail() {
		return this.email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getWebsite() {
		return this.website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getLocation() {
		return this.location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Date getDob() {
		return this.dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public UserModel getUser() {
		return this.user;
	}
	
	public LoginBean getLoginBean() {
		return loginBean;
	}

	public void setLoginBean(LoginBean loginBean) {
		this.loginBean = loginBean;
	}

	public String save() throws Throwable {
		if(!loginBean.getLoggedin() || loginBean.getUser().getPermission() < UserModel.ADMIN) {
			if (!loginBean.getUser().equals(this.user)) {
				String re = String.format("insufficientPermission.xhtml");
				FacesContext.getCurrentInstance().getExternalContext().redirect(re);
			}
		}
		
		this.user.setEmail(email);
		this.user.setWebsite(website);
		this.user.setLocation(location);
		this.user.setDateOfBirth(dob);
		this.user.setFirstName(firstName);
		this.user.setLastName(lastName);
		
		this.user.save();
		
		return String.format("profile.xhtml?userId=%d", this.user.getUserId());
	}
}
