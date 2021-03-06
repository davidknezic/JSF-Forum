package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;

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
	 * The parent thread
	 */
	private ThreadModel thread;

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
	 * The creation timestamp
	 */
	private Timestamp createdOn;

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
			this.createdOn = res.getTimestamp(4);
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
			stmt.setTimestamp(4, this.createdOn);
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
	 * Deletes the current row
	 * 
	 * @throws Throwable
	 */
	public void delete() throws Throwable {
		Connection conn = DBConnection.getInstance().getConnection();
		PreparedStatement stmt;
		
		stmt = conn.prepareStatement("DELETE FROM reply WHERE replyId = ?");
		stmt.setInt(1, this.replyId);
		
		stmt.executeUpdate();
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
	 * Get the parent thread
	 * 
	 * @return ThreadModel
	 */
	public ThreadModel getThread() throws Throwable {
		return new ThreadModel(this.getThreadId());
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
	 * Get the creation date as a standard java Date
	 * 
	 * @return Date
	 */
	public Date getCreatedOn() {
		return (Date) createdOn;
	}

	/**
	 * Set the creation timestamp as a standard java Date
	 * 
	 * @param createdOn
	 */
	public void setCreatedOn(Date createdOn) {
		this.createdOn = new Timestamp(createdOn.getTime());
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
