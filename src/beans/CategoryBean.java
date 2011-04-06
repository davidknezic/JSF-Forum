package beans;

import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import models.CategoryModel;

@ManagedBean
@SessionScoped
public class CategoryBean {

	/**
	 * Get all categories
	 * 
	 * @return ArrayList<CategoryModel>
	 */
	public ArrayList<CategoryModel> getCategories() throws Throwable {
		// Return the category array
		return CategoryModel.getCategories();
	}

}
