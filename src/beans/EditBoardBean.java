package beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import models.BoardModel;
import models.UserModel;

@ManagedBean
@RequestScoped
public class EditBoardBean {
	private BoardModel board;

	private String title;
	private String description;
	
	@ManagedProperty(value = "#{loginBean}")
	private LoginBean loginBean;
	
	public EditBoardBean() throws Throwable {
		int boardId = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("boardId"));
		
		this.board = new BoardModel(boardId);

		this.title = this.board.getTitle();
		this.description = this.board.getDescription();
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

		this.board.setTitle(this.title);
		this.board.setDescription(this.description);

		this.board.save();
		
		String re = String.format("forum.xhtml");
		FacesContext.getCurrentInstance().getExternalContext().redirect(re);

		return null;
	}

	

}
