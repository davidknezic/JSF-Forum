package models;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * The reply model
 * 
 * Represents a reply record from the database
 * 
 * @author David Knezic <davidknezic@gmail.com>
 */
public class ReplyModel {

	/**
	 * The replyId
	 */
	private int replyId;

	/**
	 * The parent threadId
	 */
	private int threadId;

	/**
	 * The userId
	 */
	private int userId;

	/**
	 * The user
	 */
	private UserModel user;

	/**
	 * The reply content
	 */
	private String content;

	/**
	 * The creation date
	 */
	private Date createdOn;

	/**
	 * Initialize an empty reply
	 */
	public ReplyModel() {
		// Indicate that this object is not yet saved
		this.replyId = 0;
	}

	/**
	 * Initialize the given reply
	 * 
	 * @param replyId
	 * @throws Throwable
	 */
	public ReplyModel(int replyId) throws Throwable {
		Connection conn = DBConnection.getInstance().getConnection();

		PreparedStatement stmt = conn
				.prepareStatement("SELECT threadId, userId, content, createdOn FROM reply WHERE replyId = ?");
		stmt.setInt(1, replyId);
		ResultSet res = stmt.executeQuery();

		if (res.first()) {
			// Assign the retrieved information
			this.replyId = replyId;
			this.threadId = res.getInt(1);
			this.userId = res.getInt(2);
			this.content = res.getString(3);
			this.createdOn = res.getDate(4);
		} else {
			// No reply with given id found
			throw new Exception("Could not find reply");
		}
	}

	/**
	 * Either inserts a new record or updates an existing one depending on the
	 * replyId
	 * 
	 * @throws Throwable
	 */
	public void save() throws Throwable {
		Connection conn = DBConnection.getInstance().getConnection();
		PreparedStatement stmt;

		if (this.replyId == 0) {
			// Insert a new record
			stmt = conn
					.prepareStatement(
							"INSERT INTO reply (threadId, userId, content, createdOn) VALUES (?, ?, ?, NOW())",
							Statement.RETURN_GENERATED_KEYS);
		} else {
			// Update record
			stmt = conn
					.prepareStatement(
							"UPDATE reply SET threadId = ?, userId = ?, content = ?, createdOn = ? WHERE replyId = ?",
							Statement.RETURN_GENERATED_KEYS);
			stmt.setDate(4, this.createdOn);
			stmt.setInt(5, this.replyId);
		}

		// Set global variables
		stmt.setInt(1, this.threadId);
		stmt.setInt(2, this.userId);
		stmt.setString(3, this.content);

		// Execute query
		stmt.executeUpdate();

		// Get the generated key if we made an insert
		if (this.replyId == 0) {
			ResultSet set = stmt.getGeneratedKeys();

			if (set.next()) {
				this.replyId = set.getInt(1);
			}
		}
	}

	/**
	 * Get the replyId
	 * 
	 * @return int
	 */
	public int getReplyId() {
		return replyId;
	}

	/**
	 * Get the parent threadId
	 * 
	 * @return int
	 */
	public int getThreadId() {
		return threadId;
	}

	/**
	 * Set the parent threadId
	 * 
	 * @param threadId
	 */
	public void setThreadId(int threadId) {
		this.threadId = threadId;
	}

	/**
	 * Get the userId
	 * 
	 * @return int
	 */
	public int getUserId() {
		return userId;
	}

	/**
	 * Set the userId
	 * 
	 * @param userId
	 */
	public void setUserId(int userId) {
		this.userId = userId;
	}

	/**
	 * Get the content
	 * 
	 * @return String
	 */
	public String getContent() {
		return content;
	}

	/**
	 * Set the content
	 * 
	 * @param content
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * Get the creation date
	 * 
	 * @return Date
	 */
	public Date getCreatedOn() {
		return createdOn;
	}

	/**
	 * Set the creation date
	 * 
	 * @param createdOn
	 */
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	/**
	 * Get user
	 * 
	 * @return UserModel
	 * @throws Throwable
	 */
	public UserModel getUser() throws Throwable {
		if(this.user == null) this.user = new UserModel(this.userId);
		return this.user;
	}

}
