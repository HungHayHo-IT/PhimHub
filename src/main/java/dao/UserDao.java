package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.User;



public class UserDao implements DAOInterface<User> {

	@Override
	public void insert(User entity) {
		String sql = "INSERT INTO Users (UserId, Password, Email, Fullname, Admin) VALUES (?, ?, ?, ?, ?)";
		
		try (
			Connection conn = JDBCUtils.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
		) {
			ps.setString(1, entity.getUserId());
			ps.setString(2, entity.getPassword());
			ps.setString(3, entity.getEmail());
			ps.setString(4, entity.getFullName());
			ps.setBoolean(5, entity.isAdmin());
			
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(User entity) {
		String sql = "UPDATE Users SET Password = ?, Email = ?, Fullname = ?, Admin = ? WHERE UserId = ?";
		
		try (
			Connection conn = JDBCUtils.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
		) {
			ps.setString(1, entity.getPassword());
			ps.setString(2, entity.getEmail());
			ps.setString(3, entity.getFullName());
			ps.setBoolean(4, entity.isAdmin());
			ps.setString(5, entity.getUserId()); // Điều kiện WHERE
			
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void delete(Object id) {
		String sql = "DELETE FROM Users WHERE UserId = ?";
		
		try (
			Connection conn = JDBCUtils.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
		) {
			ps.setString(1, (String) id);
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public User findById(Object id) {
		String sql = "SELECT * FROM Users WHERE UserId = ?";
		User user = null;
		
		try (
			Connection conn = JDBCUtils.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
		) {
			ps.setString(1, (String) id);
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				user = new User();
				user.setUserId(rs.getString("UserId"));
				user.setPassword(rs.getString("Password"));
				user.setEmail(rs.getString("Email"));
				user.setFullName(rs.getString("Fullname"));;
				user.setAdmin(rs.getBoolean("Admin"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}

	@Override
	public List<User> findAll() {
		String sql = "SELECT * FROM Users";
		List<User> list = new ArrayList<>();
		
		try (
			Connection conn = JDBCUtils.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
		) {
			while (rs.next()) {
				User user = new User();
				user.setUserId(rs.getString("UserId"));
				user.setPassword(rs.getString("Password"));
				user.setEmail(rs.getString("Email"));
				user.setFullName(rs.getString("Fullname"));
				user.setAdmin(rs.getBoolean("Admin"));
				list.add(user);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	// Hàm tìm kiếm user theo Username HOẶC Email
	public User findByUsernameOrEmail(String key) {
	    String sql = "SELECT * FROM Users WHERE UserId = ? OR Email = ?";
	    
	    try (Connection conn = JDBCUtils.getConnection();
	         PreparedStatement ps = conn.prepareStatement(sql)) {
	        
	        ps.setString(1, key);
	        ps.setString(2, key); // Set tham số thứ 2 cũng là key để tìm trong cột Email
	        
	        ResultSet rs = ps.executeQuery();
	        if (rs.next()) {
	            User user = new User();
	            user.setUserId(rs.getString("UserId"));
	            user.setPassword(rs.getString("Password"));
	            user.setEmail(rs.getString("Email"));
	            user.setFullName(rs.getString("Fullname"));
	            user.setAdmin(rs.getBoolean("Admin"));
	            return user;
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return null;
	}
}