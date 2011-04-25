package beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import models.ThreadModel;

@ManagedBean
@RequestScoped
public class NewThreadBean {
	private String title, content;
	
	public String save(Integer userId) throws Throwable {
		ThreadModel thread = new ThreadModel();
		
		int boardId = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("boardId"));
		
		thread.setBoardId(boardId);
		thread.setContent(content);
		thread.setTitle(title);
		thread.setUserId(userId);
		
		thread.save();
		
		return String.format("board.xhtml?boardId=%d", boardId);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
