package beans;

import java.util.ArrayList;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import models.BoardModel;
import models.ThreadModel;

@ManagedBean
@SessionScoped
public class BoardBean {
	public BoardModel getBoard() throws Throwable {
		Map<String, String> request = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		Integer boardId = Integer.parseInt(request.get("boardId"));
		
		if(boardId == null) throw new Exception("Invalid Request");
		
		return new BoardModel(boardId);
	}
	
	public ArrayList<ThreadModel> getThreads() throws Throwable {
		return getBoard().getThreads(0, 20);
	}
}
