package beans;

import java.util.ArrayList;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import models.ReplyModel;
import models.Settings;
import models.ThreadModel;
import models.UserModel;

@ManagedBean
@RequestScoped
public class ProfileBean {
	private UserModel user;
	private ArrayList<ThreadModel> latestThreads;
	private ArrayList<ReplyModel> latestReplies;
	
	private Paginator threadPaginator;
	private Paginator replyPaginator;
	
	public ProfileBean() throws Throwable {
		Map<String, String> request = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();

		/*
		 * Create the corresponding user model
		 */
		Integer userId = Integer.parseInt(request.get("userId"));
		if(userId == null) throw new Exception("Invalid Request");
		this.user = new UserModel(userId);

		/*
		 * Prepare thread paginator
		 */
		int threadsPerPage = Settings.getInstance().getInt("profileLatestThreads");
		int threadCount = this.user.getThreadCount();
		this.threadPaginator = new Paginator(threadsPerPage, threadCount);

		try {
			this.threadPaginator.setCurrentPage(Integer.parseInt(request.get("threadPage")));
		} catch (Throwable t) {
			this.threadPaginator.setCurrentPage(0);
		}

		/*
		 * Prepare reply paginator
		 */
		int repliesPerPage = Settings.getInstance().getInt("profileLatestReplies");
		int replyCount = this.user.getReplyCount();
		this.replyPaginator = new Paginator(repliesPerPage, replyCount);

		try {
			this.replyPaginator.setCurrentPage(Integer.parseInt(request.get("replyPage")));
		} catch (Throwable t) {
			this.replyPaginator.setCurrentPage(0);
		}

		/*
		 * Now, get the latest threads and replies
		 */
		this.latestThreads = this.user.getThreads(this.threadPaginator.generateOffset(), threadsPerPage);
		this.latestReplies = this.user.getReplies(this.replyPaginator.generateOffset(), repliesPerPage);
		
		System.out.println(this.threadPaginator.toString());
		System.out.println(this.replyPaginator.toString());
	}

	public UserModel getUser() {
		return this.user;
	}
	
	public ArrayList<ThreadModel> getLatestThreads() {
		return this.latestThreads;
	}
	
	public ArrayList<ReplyModel> getLatestReplies() {
		return this.latestReplies;
	}

	public Paginator getThreadPaginator() {
		return threadPaginator;
	}

	public Paginator getReplyPaginator() {
		return replyPaginator;
	}

}
