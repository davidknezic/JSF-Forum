package beans;

import java.util.ArrayList;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import models.ReplyModel;
import models.ThreadModel;

@ManagedBean
@SessionScoped
public class ThreadBean {
	public ThreadModel getThread() throws Throwable {
		Map<String, String> request = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		Integer threadId = Integer.parseInt(request.get("threadId"));
		
		if(threadId == null) throw new Exception("Invalid Request");
		
		return new ThreadModel(threadId);
	}
	
	public ArrayList<ReplyModel> getReplies() throws Throwable {
		ThreadModel thread = getThread();
		
		ArrayList<ReplyModel> replies = thread.getReplies(0, 20);
		if(true)  { // Auf erster Seite TODO !!
			// Adding a "fake" reply representing the thread
			ReplyModel reply = new ReplyModel();
			reply.setContent(thread.getContent());
			reply.setCreatedOn(thread.getCreatedOn());
			reply.setThreadId(thread.getThreadId());
			reply.setUserId(thread.getUserId());
			replies.add(0, reply);
		}
		return replies;
	}
}
