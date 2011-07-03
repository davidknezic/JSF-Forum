package beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import models.ReplyModel;

@ManagedBean
@RequestScoped
public class EditReplyBean {
	private ReplyModel reply;

	private String content;
	
	public EditReplyBean() throws Throwable {
		int replyId = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("replyId"));
		
		this.reply = new ReplyModel(replyId);

		this.content = this.reply.getContent();
	}

	public String save() throws Throwable {
		this.reply.setContent(this.content);

		this.reply.save();
		
		String re = String.format("thread.xhtml?threadId=%d&page=999", this.reply.getThreadId());
		FacesContext.getCurrentInstance().getExternalContext().redirect(re);

		return null;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
