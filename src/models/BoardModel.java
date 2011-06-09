package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * The board model
 * 
 * Represents a board record from the database
 * 
 * @author David Knezic <davidknezic@gmail.com>
 */
public class BoardModel {

	/**
	 * The boardId
	 */
	private int boardId;

	/**
	 * The parent categoryId
	 */
	private int categoryId;

	/**
	 * The board title
	 */
	private String title;

	/**
	 * The board description
	 */
	private String description;

	/**
	 * Initialize an empty board
	 */
	public BoardModel() {
		// Indicate that this object is not yet saved
		this.boardId = 0;
	}

	/**
	 * Initialize the given board
	 * 
	 * @param boardId
	 * @throws Throwable
	 */
	public BoardModel(int boardId) throws Throwable {
		Connection conn = DBConnection.getInstance().getConnection();

		PreparedStatement stmt = conn
				.prepareStatement("SELECT categoryId, title, description FROM board WHERE boardId = ?");
		stmt.setInt(1, boardId);
		ResultSet res = stmt.executeQuery();

		if (res.first()) {
			// Assign the retrieved information
			this.boardId = boardId;
			this.categoryId = res.getInt(1);
			this.title = res.getString(2);
			this.description = res.getString(3);
		} else {
			// No board with given id found
			throw new Exception("Could not find board");
		}
	}

	/**
	 * Either inserts a new record or updates an existing one depending on the
	 * boardId
	 * 
	 * @throws Throwable
	 */
	public void save() throws Throwable {
		Connection conn = DBConnection.getInstance().getConnection();
		PreparedStatement stmt;

		if (this.boardId == 0) {
			// Insert a new record
			stmt = conn
					.prepareStatement(
							"INSERT INTO board (categoryId, title, description) VALUES (?, ?, ?)",
							Statement.RETURN_GENERATED_KEYS);
		} else {
			// Update record
			stmt = conn
					.prepareStatement(
							"UPDATE board SET categoryId = ?, title = ?, description = ? WHERE boardId = ?",
							Statement.RETURN_GENERATED_KEYS);
			stmt.setInt(4, this.boardId);
		}

		// Set global variables
		stmt.setInt(1, this.categoryId);
		stmt.setString(2, this.title);
		stmt.setString(3, this.description);

		// Execute query
		stmt.executeUpdate();

		// Get the generated key if we made an insert
		if (this.boardId == 0) {
			ResultSet set = stmt.getGeneratedKeys();

			if (set.next()) {
				this.boardId = set.getInt(1);
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
		
		stmt = conn.prepareStatement("DELETE FROM board WHERE boardId = ?");
		stmt.setInt(1, this.boardId);
		
		stmt.executeUpdate();
	}

	/**
	 * Get the boardId
	 * 
	 * @return int
	 */
	public int getBoardId() {
		return boardId;
	}

	/**
	 * Get the parent categoryId
	 * 
	 * @return int
	 */
	public int getCategoryId() {
		return boardId;
	}

	/**
	 * Set the parent categoryId
	 * 
	 * @param categoryId
	 */
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	/**
	 * Get the title
	 * 
	 * @return String
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Set the title
	 * 
	 * @param title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Get the description
	 * 
	 * @return String
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Set the description
	 * 
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Get parent category
	 * 
	 * @return CategoryModel
	 * @throws Throwable
	 */
	public CategoryModel getCategory() throws Throwable {
		return new CategoryModel(this.categoryId);
	}

	/**
	 * Get last active threads
	 * 
	 * @return ArrayList<ThreadModel>
	 * @throws Throwable
	 */
	public ArrayList<ThreadModel> getThreads(int off, int max) throws Throwable {
		Connection conn = DBConnection.getInstance().getConnection();
		ArrayList<ThreadModel> threads = new ArrayList<ThreadModel>();

		PreparedStatement stmt = conn
				.prepareStatement("SELECT t.threadId, COALESCE(MAX(r.createdOn), t.createdOn) "
						+ "FROM thread t LEFT JOIN reply r "
						+ "ON t.threadId = r.threadId "
						+ "WHERE t.boardId = ? "
						+ "GROUP BY t.threadId "
						+ "ORDER BY 2 DESC " + "LIMIT ?, ?");
		stmt.setInt(1, this.boardId);
		stmt.setInt(2, off);
		stmt.setInt(3, max);
		ResultSet res = stmt.executeQuery();

		// For each thread
		while (res.next()) {
			// Add current thread object to array
			threads.add(new ThreadModel(res.getInt(1)));
		}

		return threads;
	}

	/**
	 * Get last thread
	 * 
	 * @return ThreadModel
	 * @throws Throwable
	 */
	public ThreadModel getLastThread() throws Throwable {
		ArrayList<ThreadModel> threads = this.getThreads(0, 1);

		if (threads.size() > 0) {
			return threads.get(0);
		} else {
			return null;
		}
	}

	/**
	 * Get number of threads in board
	 * 
	 * @return int
	 * @throws Throwable
	 */
	public int getThreadCount() throws Throwable {
		Connection conn = DBConnection.getInstance().getConnection();

		PreparedStatement stmt = conn
				.prepareStatement("SELECT COUNT(*) FROM thread WHERE boardId = ?");
		stmt.setInt(1, this.boardId);
		ResultSet res = stmt.executeQuery();

		if (res.first()) {
			// Return the thread count
			return res.getInt(1);
		} else {
			// An error occured, return zero
			return 0;
		}
	}
	
	/**
	 * Get number of replies in board
	 * 
	 * @return int
	 * @throws Throwable
	 */
	public int getReplyCount() throws Throwable {
		Connection conn = DBConnection.getInstance().getConnection();

		PreparedStatement stmt = conn
				.prepareStatement("SELECT COUNT(*) FROM reply r JOIN thread t ON r.threadId = t.threadId WHERE t.boardId = ?");
		stmt.setInt(1, this.boardId);
		ResultSet res = stmt.executeQuery();

		if (res.first()) {
			// Return the reply count
			return res.getInt(1);
		} else {
			// An error occured, return zero
			return 0;
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof BoardModel)) {
			return false;
		}

		BoardModel board = (BoardModel) obj;

		return (this.boardId == board.boardId);
	}

}
