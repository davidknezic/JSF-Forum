package beans;

import java.util.ArrayList;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import models.ReplyModel;
import models.Settings;
import models.ThreadModel;

@ManagedBean
@RequestScoped
public class ThreadBean {
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
		 * Get page count
		 */
		this.pages = this.thread.getReplyCount()/Settings.getInstance().getInt("repliesPerPage");
		if (this.thread.getReplyCount()%Settings.getInstance().getInt("repliesPerPage") != 0) {
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
	
	public String delete() throws Throwable {
		int boardId = this.thread.getBoardId();

		this.thread.delete();

		String re = String.format("board.xhtml?boardId=%d", boardId);
		FacesContext.getCurrentInstance().getExternalContext().redirect(re);

		return null;
	}
	
	public String deleteReply(int replyId) throws Throwable {
		ReplyModel reply = new ReplyModel(replyId);
		reply.delete();
		
		String re = String.format("thread.xhtml?threadId=%d&page=%d", this.thread.getThreadId(), this.currentPage);
		FacesContext.getCurrentInstance().getExternalContext().redirect(re);
		
		return null;
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
			
			replies = thread.getReplies(currentPage*Settings.getInstance().getInt("repliesPerPage"), Settings.getInstance().getInt("repliesPerPage"));
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

}
