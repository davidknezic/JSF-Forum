package beans;

import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import models.BoardModel;
import models.CategoryModel;
import models.ThreadModel;

@ManagedBean
@RequestScoped
public class EditThreadBean {
	private ThreadModel thread;
	private String title, content;
	private ArrayList<SelectItem> boardList;
	private BoardModel currentBoard;
	
	public EditThreadBean() throws Throwable {
		int threadId = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("threadId"));
		
		this.thread = new ThreadModel(threadId);
		
		this.title = this.thread.getTitle();
		this.content = this.thread.getContent();
		
		this.boardList = new ArrayList<SelectItem>();
		this.currentBoard = thread.getBoard();
		
		// Save board list and current item
		ArrayList<CategoryModel> categories = CategoryModel.getCategories();
		for (CategoryModel category : categories) {
			ArrayList<BoardModel> boards = category.getBoards();
			for (BoardModel board : boards) {
				SelectItem item = new SelectItem();
				item.setLabel(board.getCategory().getTitle() + " + " + board.getTitle());
				item.setValue(board);
				this.boardList.add(item);
			}
		}
	}

	public String save() throws Throwable {
		this.thread.setTitle(this.title);
		this.thread.setContent(this.content);
		
		this.thread.setBoardId(this.currentBoard.getBoardId());
	
		thread.save();
		
		String re = String.format("thread.xhtml?threadId=%d", this.thread.getThreadId());
		FacesContext.getCurrentInstance().getExternalContext().redirect(re);
		
		return null;
	}
	
	public ArrayList<SelectItem> getBoardList() {
		return this.boardList;
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
	
	public BoardModel getCurrentBoard() {
		return currentBoard;
	}

	public void setCurrentBoard(BoardModel currentBoard) {
		this.currentBoard = currentBoard;
	}
}
