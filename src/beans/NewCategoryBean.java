package beans;


import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import models.CategoryModel;


@ManagedBean
@RequestScoped
public class NewCategoryBean {
	
	private String title;
	
	public String save() throws Throwable {
		CategoryModel category = new CategoryModel();
		
		category.setTitle(title);
		category.save();
		
		return "forum.xhtml";
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}

