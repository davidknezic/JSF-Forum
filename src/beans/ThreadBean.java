package beans;

import java.util.ArrayList;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import models.ReplyModel;
import models.ThreadModel;

@ManagedBean
@RequestScoped
public class ThreadBean {
	public static final int REPLIES_PER_PAGE = 3;
	
	private String title, content;
	private ThreadModel thread;
	private ArrayList<ReplyModel> replies;
	
	private int currentPage;
	private int pages;
	
	public ThreadBean() throws Throwable {
		Map<String, String> request = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		
		/*
		 * Save thread
		 */
		Integer threadId = Integer.parseInt(request.get("threadId"));
		this.thread = new ThreadModel(threadId);

		/*
		 * Save requested page number
		 */
		this.pages = this.thread.getReplyCount()/REPLIES_PER_PAGE;
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
	
	public ThreadModel getThread() {
		return thread;
	}
	
	public ArrayList<ReplyModel> getReplies() throws Throwable {
		if(replies == null) {
			ThreadModel thread = getThread();
			
			replies = thread.getReplies(currentPage*REPLIES_PER_PAGE, REPLIES_PER_PAGE);
			if(currentPage == 0)  {
				// Adding a "fake" reply representing the thread
				ReplyModel reply = new ReplyModel();
				reply.setContent(thread.getContent());
				reply.setCreatedOn(thread.getCreatedOn());
				reply.setThreadId(thread.getThreadId());
				reply.setUserId(thread.getUserId());
				replies.add(0, reply);
			}
		}
		return replies;
	}
	
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
