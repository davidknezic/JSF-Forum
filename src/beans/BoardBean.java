package beans;

import java.util.ArrayList;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import models.BoardModel;
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

	/**
	 * Get board which is requesetd via RequestParameterMap
	 * @return BoardModel board requested via RequestParameterMap
	 * @throws Throwable
	 */
	public BoardModel getBoard() throws Throwable {
		if (board == null) {
			Map<String, String> request = FacesContext.getCurrentInstance()
					.getExternalContext().getRequestParameterMap();
			Integer boardId = Integer.parseInt(request.get("boardId"));

			if (boardId == null)
				throw new Exception("Invalid Request");

			board = new BoardModel(boardId);
		}

		return board;
	}

	/**
	 * Get threads to the requested Board
	 * @return ArrayList<ThreadModel> Requested Threads
	 * @throws Throwable
	 */
	public ArrayList<ThreadModel> getThreads() throws Throwable {
		if (threads == null)
			threads = getBoard().getThreads(0, 20);
		return threads;
	}
}
