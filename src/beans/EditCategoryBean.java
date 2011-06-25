package beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import models.CategoryModel;
import models.UserModel;

@ManagedBean
@RequestScoped
public class EditCategoryBean {
	private CategoryModel category;

	private String title;
	
	@ManagedProperty(value = "#{loginBean}")
	private LoginBean loginBean;
	
	public EditCategoryBean() throws Throwable {
		int categoryId = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("categoryId"));
		
		this.category = new CategoryModel(categoryId);

		this.title = this.category.getTitle();
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public LoginBean getLoginBean() {
		return loginBean;
	}

	public void setLoginBean(LoginBean loginBean) {
		this.loginBean = loginBean;
	}
	
	public String save() throws Throwable {
		if(!loginBean.getLoggedin() || loginBean.getUser().getPermission() < UserModel.SUPER_ADMIN) {
			return "insufficientPermission.xhtml";
		}

		this.category.setTitle(this.title);

		this.category.save();
		
		String re = String.format("forum.xhtml");
		FacesContext.getCurrentInstance().getExternalContext().redirect(re);

		return null;
	}

	

}
