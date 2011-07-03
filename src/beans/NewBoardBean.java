package beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import models.BoardModel;

@ManagedBean
@RequestScoped
public class NewBoardBean {
	private String title, description;

	public String save() throws Throwable {
		
		BoardModel board = new BoardModel();
		
		int categoryId = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("categoryId"));
		
		board.setCategoryId(categoryId);
		board.setDescription(description);
		board.setTitle(title);
		
		board.save();
		
		return "forum.xhtml";
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
