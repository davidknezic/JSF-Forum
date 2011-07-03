package beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import models.CategoryModel;

@ManagedBean
@RequestScoped
public class EditCategoryBean {
	private CategoryModel category;

	private String title;
	
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
	
	public String save() throws Throwable {
		this.category.setTitle(this.title);

		this.category.save();
		
		String re = String.format("forum.xhtml");
		FacesContext.getCurrentInstance().getExternalContext().redirect(re);

		return null;
	}

}
