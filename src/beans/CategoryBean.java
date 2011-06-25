package beans;

import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import models.BoardModel;
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
	
	public String deleteCategory(int categoryId) throws Throwable {
		CategoryModel category = new CategoryModel(categoryId);
		
		category.delete();
		
		String re = String.format("forum.xhtml");
		FacesContext.getCurrentInstance().getExternalContext().redirect(re);
		
		return null;
	}
	
	public String deleteBoard(int boardId) throws Throwable {
		BoardModel board = new BoardModel(boardId);
		
		board.delete();
		
		String re = String.format("forum.xhtml");
		FacesContext.getCurrentInstance().getExternalContext().redirect(re);
		
		return null;
	}

}
