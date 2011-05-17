package beans;

import java.util.ArrayList;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import models.BoardModel;
import models.Settings;
import models.ThreadModel;
/**
 * 
 * @author Sven Tschui
 *
 */
@ManagedBean
@RequestScoped
public class BoardBean {
	private BoardModel board;
	private ArrayList<ThreadModel> threads;

	private int currentPage;
	private int pages;
	
	public BoardBean() throws Throwable {
		Map<String, String> request = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		
		/*
		 * Save board
		 */
		Integer boardId = Integer.parseInt(request.get("boardId"));
		this.board = new BoardModel(boardId);
		
		/*
		 * Get page count
		 */
		this.pages = this.board.getThreadCount()/Settings.getInstance().getInt("threadsPerPage");
		if (this.board.getThreadCount()%Settings.getInstance().getInt("threadsPerPage") != 0) {
			this.pages++;
		}

		/*
		 * Save requested page number
		 */
		try {
			this.currentPage = Integer.parseInt(request.get("page"));
			
			// Check boundaries
			if (this.currentPage < 0) {
				this.currentPage = 0;
			}
			if (this.currentPage > this.pages) {
				this.currentPage = this.pages - 1;
			}
		} catch (Exception e) {
			this.currentPage = 0;
		}
	}
	
	public int getCurrentPage() {
		return this.currentPage;
	}
	
	public boolean hasPrev() {
		return (this.currentPage > 0);
	}
	
	public boolean hasNext() throws Throwable {
		return (this.currentPage + 1 < this.pages);
	}
	
	/**
	 * Get board
	 * @return BoardModel requested board
	 * @throws Throwable
	 */
	public BoardModel getBoard() throws Throwable {
		return board;
	}

	/**
	 * Get threads to the requested Board
	 * @return ArrayList<ThreadModel> Requested Threads
	 * @throws Throwable
	 */
	public ArrayList<ThreadModel> getThreads() throws Throwable {
		if (threads == null)
			threads = getBoard().getThreads(currentPage*Settings.getInstance().getInt("threadsPerPage"), Settings.getInstance().getInt("threadsPerPage"));
		return threads;
	}
}
