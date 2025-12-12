package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.Favorite;
import model.User;
import model.Video;


public class FavoriteDao implements DAOInterface<Favorite> {

	@Override
	public void insert(Favorite entity) {
		String sql = "INSERT INTO Favorites (UserId, VideoId, LikeDate) VALUES (?, ?, ?)";
		try (Connection conn = JDBCUtils.getConnection();
			 PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, entity.getUser().getUserId());
			ps.setString(2, entity.getVideo().getVideoId());
			ps.setDate(3, new java.sql.Date(entity.getLikeDate().getTime()));
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(Favorite entity) {
		// Bảng Favorite thường ít update, chủ yếu là insert/delete
		// Nhưng cứ viết cho đủ bộ
		String sql = "UPDATE Favorites SET UserId=?, VideoId=?, LikeDate=? WHERE FavoriteId=?";
		try (Connection conn = JDBCUtils.getConnection();
			 PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, entity.getUser().getUserId());
			ps.setString(2, entity.getVideo().getVideoId());
			ps.setDate(3, new java.sql.Date(entity.getLikeDate().getTime()));
			ps.setLong(4, entity.getFavoriteId());
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void delete(Object id) {
		String sql = "DELETE FROM Favorites WHERE FavoriteId = ?";
		try (Connection conn = JDBCUtils.getConnection();
			 PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setLong(1, (Long) id);
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Favorite findById(Object id) {
		String sql = "SELECT * FROM Favorites WHERE FavoriteId = ?";
		try (Connection conn = JDBCUtils.getConnection();
			 PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setLong(1, (Long) id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return mapRow(rs);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Favorite> findAll() {
		// JOIN 3 bảng để lấy đầy đủ thông tin: Ai thích? Phim gì?
		String sql = "SELECT f.*, u.Fullname, u.Email, v.Title, v.Poster " +
					 "FROM Favorites f " +
					 "JOIN Users u ON f.UserId = u.UserId " +
					 "JOIN Videos v ON f.VideoId = v.VideoId";
		List<Favorite> list = new ArrayList<>();
		try (Connection conn = JDBCUtils.getConnection();
			 PreparedStatement ps = conn.prepareStatement(sql);
			 ResultSet rs = ps.executeQuery()) {
			while (rs.next()) {
				list.add(mapRow(rs));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	// Hàm phụ để map dữ liệu từ ResultSet vào Object (đỡ viết lại nhiều lần)
	private Favorite mapRow(ResultSet rs) throws Exception {
		Favorite f = new Favorite();
		f.setFavoriteId(rs.getLong("FavoriteId"));
		f.setLikeDate(rs.getDate("LikeDate"));
		
		User u = new User();
		u.setUserId(rs.getString("UserId"));
		// Nếu join được bảng Users thì lấy thêm tên, email...
		try { u.setFullName(rs.getString("Fullname")); } catch (Exception e) {} 
		
		Video v = new Video();
		v.setVideoId(rs.getString("VideoId"));
		// Nếu join được bảng Videos thì lấy thêm title, poster...
		try { v.setTitle(rs.getString("Title")); v.setPoster(rs.getString("Poster")); } catch (Exception e) {}
		
		f.setUser(u);
		f.setVideo(v);
		return f;
	}
	
	// Hàm riêng: Kiểm tra xem User đã like Video này chưa (dùng cho nút Like)
	// Trong FavoriteDao.java

	public Favorite findByUserIdAndVideoId(String userId, String videoId) {
	    // Chỉ cần kiểm tra xem có tồn tại hay không, không cần map full dữ liệu
	    String sql = "SELECT * FROM Favorites WHERE UserId = ? AND VideoId = ?";
	    try (Connection conn = JDBCUtils.getConnection();
	         PreparedStatement ps = conn.prepareStatement(sql)) {
	        ps.setString(1, userId);
	        ps.setString(2, videoId);
	        ResultSet rs = ps.executeQuery();
	        
	        if (rs.next()) {
	            Favorite f = new Favorite();
	            f.setFavoriteId(rs.getLong("FavoriteId"));
	            // Set các ID để servlet dùng cho hàm delete nếu cần
	            User u = new User(); u.setUserId(userId);
	            Video v = new Video(); v.setVideoId(videoId);
	            f.setUser(u);
	            f.setVideo(v);
	            return f; 
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return null;
	}
}