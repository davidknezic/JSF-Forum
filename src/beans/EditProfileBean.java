package beans;

import java.util.Date;
import javax.annotation.PostConstruct;

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

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordRepeated() {
		return this.passwordRepeated;
	}

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
		if (!loginBean.getLoggedin() || !(loginBean.getUser().getPermission() >= UserModel.ADMIN || loginBean.getUser().equals(this.user))) {
			String re = String.format("insufficientPermission.xhtml");
			FacesContext.getCurrentInstance().getExternalContext().redirect(re);
		}

		this.user.setEmail(this.email);
		this.user.setWebsite(this.website);
		this.user.setLocation(this.location);
		this.user.setDateOfBirth(this.dob);
		this.user.setFirstName(this.firstName);
		this.user.setLastName(this.lastName);

		if (this.password != null && !this.password.equals("") && this.passwordRepeated != null && !this.passwordRepeated.equals("")) {
			this.user.setPassword(this.password);
		}

		this.user.save();

		return String.format("profile.xhtml?userId=%d", this.user.getUserId());
	}
}
