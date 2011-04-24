package beans;

import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import models.CategoryModel;

@ManagedBean
@RequestScoped
public class CategoryBean {
	private ArrayList<CategoryModel> categories;
	
	/**
	 * Get all categories
	 * 
	 * @return ArrayList<CategoryModel>
	 */
	public ArrayList<CategoryModel> getCategories() throws Throwable {
		// Return the category array
		if(categories == null) categories = CategoryModel.getCategories();
		return categories;
	}

}
