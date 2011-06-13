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
	
	@ManagedProperty(value = "#{loginBean}")
	private LoginBean loginBean;
	
	public String getFirstName() {
		return loginBean.getUser().getFirstName();
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return loginBean.getUser().getLastName();
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return "";
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getPasswordRepeated() {
		return "";
	}
	
	public void setPasswordRepeated(String passwordRepeated) {
		this.passwordRepeated = passwordRepeated;
	}
	
	public String getEmail() {
		return loginBean.getUser().getEmail();
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getWebsite() {
		return loginBean.getUser().getWebsite();
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getLocation() {
		return loginBean.getUser().getLocation();
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Date getDob() {
			return loginBean.getUser().getDateOfBirth();
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public LoginBean getLoginBean() {
		return loginBean;
	}

	public void setLoginBean(LoginBean loginBean) {
		this.loginBean = loginBean;
	}

	public String save() throws Throwable {
		UserModel u = loginBean.getUser();
		if(!password.equals("")) {
			u.setPassword(password);
		}
		u.setEmail(email);
		u.setWebsite(website);
		u.setLocation(location);
		u.setDateOfBirth(dob);
		u.setFirstName(firstName);
		u.setLastName(lastName);
		
		u.save();
		
		String re = String.format("profile.xhtml?userId=%d", u.getUserId());
		FacesContext.getCurrentInstance().getExternalContext().redirect(re);
		
		return null;
	}
}
