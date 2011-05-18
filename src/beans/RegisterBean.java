package beans;

import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import models.UserModel;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;


@ManagedBean
@RequestScoped
public class RegisterBean {
	private static final Pattern DUB_INDEX = Pattern.compile("Duplicate entry '([^']*)' for key '([^']*)'");
	private String name;
	private String password;
	private String passwordRepeated;
	private String email;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getPasswordRepeated() {
		return passwordRepeated;
	}
	
	public void setPasswordRepeated(String passwordRepeated) {
		this.passwordRepeated = passwordRepeated;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String save() {
		if(!password.equals(passwordRepeated)){
			FacesMessage message = new FacesMessage("Password mismatch");
			message.setSeverity(FacesMessage.SEVERITY_FATAL);
			FacesContext.getCurrentInstance().addMessage(null, message);
		}
		
		UserModel u = new UserModel();
		u.setUsername(name);
		u.setPassword(password);
		u.setEmail(email);
		u.setActive(true);
		
		try {
			u.save();
		} 
		catch(MySQLIntegrityConstraintViolationException e) {
			Matcher m = DUB_INDEX.matcher(e.getMessage());
			FacesMessage message;
			if(m.matches()) {
				if(m.group(2).equals("username")) {
					message = new FacesMessage("Username already in use");
				} else {
					message = new FacesMessage("Database error 1");
				}
			} else message = new FacesMessage("Database error 2");
			
			message.setSeverity(FacesMessage.SEVERITY_FATAL);
			FacesContext.getCurrentInstance().addMessage(null, message);
		}		
		catch(SQLException e) {
			//sqlstate
			e.printStackTrace();
			FacesMessage message = new FacesMessage("Database error");
			message.setSeverity(FacesMessage.SEVERITY_FATAL);
			FacesContext.getCurrentInstance().addMessage(null, message);
		} catch(Throwable e) {
			//sonstiges
			FacesMessage message = new FacesMessage("Error");
			message.setSeverity(FacesMessage.SEVERITY_FATAL);
			FacesContext.getCurrentInstance().addMessage(null, message);
		}
		
		return "index.xhtml";
	}
}
