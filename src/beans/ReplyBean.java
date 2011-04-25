package beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import models.ReplyModel;

@ManagedBean
@RequestScoped
public class ReplyBean {
	private String content;
	
	public String save(int threadId, int userId) throws Throwable {
		ReplyModel reply = new ReplyModel();
		reply.setContent(content);
		reply.setThreadId(threadId);
		reply.setUserId(userId);
		reply.save();
		
		content = "";
		
		return null;
	}

	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
}
