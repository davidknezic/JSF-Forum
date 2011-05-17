package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * The category model
 * 
 * Represents a category record from the database
 * 
 * @author David Knezic <davidknezic@gmail.com>
 */
public class CategoryModel {

	/**
	 * The categoryId
	 */
	private int categoryId;

	/**
	 * The category title
	 */
	private String title;

	/**
	 * Initialize an empty category
	 */
	public CategoryModel() {
		// Indicate that this object is not yet saved
		this.categoryId = 0;
	}

	/**
	 * Initialize the given category
	 * 
	 * @param categoryId
	 * @throws Throwable
	 */
	public CategoryModel(int categoryId) throws Throwable {
		Connection conn = DBConnection.getInstance().getConnection();

		PreparedStatement stmt = conn
				.prepareStatement("SELECT title FROM category WHERE categoryId = ?");
		stmt.setInt(1, categoryId);
		ResultSet res = stmt.executeQuery();

		if (res.first()) {
			// Assign the retrieved information
			this.categoryId = categoryId;
			this.title = res.getString(1);
		} else {
			// No category with given id found
			throw new Exception("Could not find category");
		}
	}

	/**
	 * Either inserts a new record or updates an existing one depending on the
	 * categoryId
	 * 
	 * @throws Throwable
	 */
	public void save() throws Throwable {
		Connection conn = DBConnection.getInstance().getConnection();
		PreparedStatement stmt;

		if (this.categoryId == 0) {
			// Insert a new record
			stmt = conn.prepareStatement(
					"INSERT INTO category (title) VALUES (?)",
					Statement.RETURN_GENERATED_KEYS);
		} else {
			// Update record
			stmt = conn.prepareStatement(
					"UPDATE category SET title = ? WHERE categoryId = ?",
					Statement.RETURN_GENERATED_KEYS);
			stmt.setInt(2, this.categoryId);
		}

		// Set global variables
		stmt.setString(1, this.title);

		// Execute query
		stmt.executeUpdate();

		// Get the generated key if we made an insert
		if (this.categoryId == 0) {
			ResultSet set = stmt.getGeneratedKeys();

			if (set.next()) {
				this.categoryId = set.getInt(1);
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
		
		stmt = conn.prepareStatement("DELETE FROM category WHERE categoryId = ?");
		stmt.setInt(1, this.categoryId);
		
		stmt.executeUpdate();
	}

	/**
	 * Get the categoryId
	 * 
	 * @return int
	 */
	public int getCategoryId() {
		return categoryId;
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
	 * Get all dependent boards
	 * 
	 * @return ArrayList<BoardModel>
	 * @throws Throwable
	 */
	public ArrayList<BoardModel> getBoards() throws Throwable {
		Connection conn = DBConnection.getInstance().getConnection();
		ArrayList<BoardModel> boards = new ArrayList<BoardModel>();

		PreparedStatement stmt = conn
				.prepareStatement("SELECT boardId FROM board WHERE categoryId = ?");
		stmt.setInt(1, this.categoryId);
		ResultSet res = stmt.executeQuery();

		// For each board
		while (res.next()) {
			// Add current board object to array
			boards.add(new BoardModel(res.getInt(1)));
		}

		return boards;
	}

	/**
	 * Get all categories
	 * 
	 * This is the entry point when all objects are traversed
	 * 
	 * @return ArrayList<CategoryModel>
	 * @throws Throwable
	 */
	public static ArrayList<CategoryModel> getCategories() throws Throwable {
		Connection conn = DBConnection.getInstance().getConnection();
		ArrayList<CategoryModel> categories = new ArrayList<CategoryModel>();

		PreparedStatement stmt = conn
				.prepareStatement("SELECT categoryId FROM category");
		ResultSet res = stmt.executeQuery();

		// For each category
		while (res.next()) {
			// Add current category object to array
			categories.add(new CategoryModel(res.getInt(1)));
		}

		return categories;
	}

}
