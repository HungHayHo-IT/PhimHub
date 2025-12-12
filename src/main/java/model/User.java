package model;

import java.io.Serializable;

public class User implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	// Tên biến nên đặt khớp với tên cột trong SQL cho dễ nhớ (hoặc camelCase tùy ý)
	private String userId;
	private String password;
	private String email;
	private String fullName;
	private boolean admin = false;

	public User() {
	}

	public User(String userId, String password, String email, String fullName, boolean admin) {
		super();
		this.userId = userId;
		this.password = password;
		this.email = email;
		this.fullName = fullName;
		this.admin = admin;
	}

	// Getter & Setter
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}
}