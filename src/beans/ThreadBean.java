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
	private String title, content;
	private ThreadModel thread;
	private ArrayList<ReplyModel> replies;
	
	private int currentPage;
	
	public ThreadBean() {
		Integer page;

		// Get the requested page number
		Map<String, String> request = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		try {
			page = Integer.parseInt(request.get("page"));
			
			// Check boundaries
			if (page < 0) {
				page = 0;
			}
		} catch (Exception e) {
			page = 0;
		}
		
		this.currentPage = page.intValue();
	}
	
	public int getCurrentPage() {
		return this.currentPage;
	}
	
	public boolean hasPrev() {
		return (this.currentPage > 0);
	}
	
	public boolean hasNext() throws Throwable {
		return (this.thread.getReplyCount() > (this.currentPage + 1)*2);
	}
	
	public ThreadModel getThread() throws Throwable {
		if(thread == null) {
			Map<String, String> request = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
			Integer threadId = Integer.parseInt(request.get("threadId"));
			
			if(threadId == null) throw new Exception("Invalid Request");
			
			thread = new ThreadModel(threadId);
		}
		
		return thread;
	}
	
	public ArrayList<ReplyModel> getReplies() throws Throwable {
		if(replies == null) {
			ThreadModel thread = getThread();
			
			replies = thread.getReplies(currentPage*2, 2);
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
