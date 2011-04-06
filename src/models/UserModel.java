package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import jgravatar.Gravatar;
import jgravatar.GravatarDefaultImage;
import jgravatar.GravatarRating;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * The user model
 * 
 * Represents a user record from the database
 * 
 * @author David Knezic <davidknezic@gmail.com>
 */
public class UserModel {

	/**
	 * The userId
	 */
	private int userId;

	/**
	 * The username
	 */
	private String username;

	/**
	 * The user password
	 */
	private String password;

	/**
	 * The email address
	 */
	private String email;

	/**
	 * The activation status
	 */
	private boolean active;

	/**
	 * Initialize an empty user
	 */
	public UserModel() {
		// Indicate that this object is not yet saved
		this.userId = 0;
	}

	/**
	 * Initialize the given user
	 * 
	 * @param userId
	 * @throws Throwable
	 */
	public UserModel(int userId) throws Throwable {
		Connection conn = DBConnection.getInstance().getConnection();

		PreparedStatement stmt = conn
				.prepareStatement("SELECT username, password, email, active FROM user WHERE userId = ?");
		stmt.setInt(1, userId);
		ResultSet res = stmt.executeQuery();

		if (res.first()) {
			// Assign the retrieved information
			this.userId = userId;
			this.username = res.getString(1);
			this.password = res.getString(2);
			this.email = res.getString(3);
			this.active = (res.getInt(4) == 0 ? false : true);
		} else {
			// No user with given id found
			throw new Exception("Could not find user");
		}
	}

	/**
	 * Either inserts a new record or updates an existing one depending on the
	 * userId
	 * 
	 * @throws Throwable
	 */
	public void save() throws Throwable {
		Connection conn = DBConnection.getInstance().getConnection();
		PreparedStatement stmt;

		if (this.userId == 0) {
			// Insert a new record
			stmt = conn
					.prepareStatement(
							"INSERT INTO user (username, password, email, active) VALUES (?, ?, ?, ?)",
							Statement.RETURN_GENERATED_KEYS);
		} else {
			// Update record
			stmt = conn
					.prepareStatement(
							"UPDATE user SET username = ?, password = ?, email = ?, active = ? WHERE userId = ?",
							Statement.RETURN_GENERATED_KEYS);
			stmt.setInt(4, this.userId);
		}

		// Set global variables
		stmt.setString(1, this.username);
		stmt.setString(2, this.password);
		stmt.setString(3, this.email);
		stmt.setInt(4, (this.active == true ? 1 : 0));

		// Execute query
		stmt.executeUpdate();

		// Get the generated key if we made an insert
		if (this.userId == 0) {
			ResultSet set = stmt.getGeneratedKeys();

			if (set.next()) {
				this.userId = set.getInt(1);
			}
		}
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
	 * Get the username
	 * 
	 * @return String
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Set the username
	 * 
	 * @param username
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Get the password
	 * 
	 * @return String
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Set the password
	 * 
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = DigestUtils.md5Hex(password);
	}

	/**
	 * Get email address
	 * 
	 * @return String
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Set email address
	 * 
	 * @param email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Is active
	 * 
	 * @return boolean
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * Set active
	 * 
	 * @param active
	 */
	public void setActive(boolean active) {
		this.active = active;
	}

	/**
	 * Get threads
	 * 
	 * @return ArrayList<ThreadModel>
	 * @throws Throwable
	 */
	public ArrayList<ThreadModel> getThreads(int off, int max) throws Throwable {
		Connection conn = DBConnection.getInstance().getConnection();
		ArrayList<ThreadModel> threads = new ArrayList<ThreadModel>();

		PreparedStatement stmt = conn
				.prepareStatement("SELECT threadId FROM thread WHERE userId = ? ORDER BY createdOn ASC LIMIT ?, ?");
		stmt.setInt(1, this.userId);
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
	 * Get replies
	 * 
	 * @return ArrayList<ReplyModel>
	 * @throws Throwable
	 */
	public ArrayList<ReplyModel> getReplies(int off, int max) throws Throwable {
		Connection conn = DBConnection.getInstance().getConnection();
		ArrayList<ReplyModel> threads = new ArrayList<ReplyModel>();

		PreparedStatement stmt = conn
				.prepareStatement("SELECT replyId FROM reply WHERE userId = ? ORDER BY createdOn ASC LIMIT ?, ?");
		stmt.setInt(1, this.userId);
		stmt.setInt(2, off);
		stmt.setInt(3, max);
		ResultSet res = stmt.executeQuery();

		// For each reply
		while (res.next()) {
			// Add current reply object to array
			threads.add(new ReplyModel(res.getInt(1)));
		}

		return threads;
	}

	/**
	 * Get the avatar picture url
	 * 
	 * @return String
	 */
	public String getAvatar() {
		Gravatar gravatar = new Gravatar();
		gravatar.setSize(50);
		gravatar.setRating(GravatarRating.GENERAL_AUDIENCES);
		gravatar.setDefaultImage(GravatarDefaultImage.IDENTICON);
		String url = gravatar.getUrl(this.email);
		return url;
	}

	/**
	 * Get user list
	 * 
	 * @return ArrayList<UserModel>
	 * @throws Throwable
	 */
	public static ArrayList<UserModel> getUsers(int off, int max)
			throws Throwable {
		Connection conn = DBConnection.getInstance().getConnection();
		ArrayList<UserModel> users = new ArrayList<UserModel>();

		PreparedStatement stmt = conn
				.prepareStatement("SELECT userId FROM user LIMIT ?, ?");
		stmt.setInt(1, off);
		stmt.setInt(2, max);
		ResultSet res = stmt.executeQuery();

		// For each user
		while (res.next()) {
			// Add current user object to array
			users.add(new UserModel(res.getInt(1)));
		}

		return users;
	}

	/**
	 * Get user by username
	 * 
	 * TODO: Check if this is necessary!
	 * 
	 * @return UserModel
	 * @throws Throwable
	 */
	public static UserModel getUserByUsername(String username) throws Throwable {
		Connection conn = DBConnection.getInstance().getConnection();

		PreparedStatement stmt = conn
				.prepareStatement("SELECT userId FROM user WHERE username = ?");
		stmt.setString(1, username);
		ResultSet res = stmt.executeQuery();

		if (res.first()) {
			// Return the user object
			return new UserModel(res.getInt(1));
		} else {
			// No user found, return null
			return null;
		}
	}

	/**
	 * Get user by identity / credential
	 * 
	 * @return UserModel
	 * @throws Throwable
	 */
	public static UserModel authenticate(String username, String password)
			throws Throwable {
		Connection conn = DBConnection.getInstance().getConnection();

		PreparedStatement stmt = conn
				.prepareStatement("SELECT userId FROM user WHERE username = ? AND password = MD5(?)");
		stmt.setString(1, username);
		stmt.setString(2, password);
		ResultSet res = stmt.executeQuery();

		if (res.first()) {
			// Return the user object
			return new UserModel(res.getInt(1));
		} else {
			// No user found, return null
			return null;
		}
	}

}
