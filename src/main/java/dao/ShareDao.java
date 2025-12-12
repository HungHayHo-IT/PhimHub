package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.Share;


public class ShareDao implements DAOInterface<Share> {

	@Override
	public void insert(Share entity) {
		String sql = "INSERT INTO Shares (UserId, VideoId, Emails, ShareDate) VALUES (?, ?, ?, ?)";
		try (Connection conn = JDBCUtils.getConnection();
			 PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, entity.getUser().getUserId());
			ps.setString(2, entity.getVideo().getVideoId());
			ps.setString(3, entity.getEmails());
			ps.setDate(4, new java.sql.Date(entity.getShareDate().getTime()));
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(Share entity) {
		String sql = "UPDATE Shares SET UserId=?, VideoId=?, Emails=?, ShareDate=? WHERE ShareId=?";
		try (Connection conn = JDBCUtils.getConnection();
			 PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, entity.getUser().getUserId());
			ps.setString(2, entity.getVideo().getVideoId());
			ps.setString(3, entity.getEmails());
			ps.setDate(4, new java.sql.Date(entity.getShareDate().getTime()));
			ps.setLong(5, entity.getShareId());
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void delete(Object id) {
		String sql = "DELETE FROM Shares WHERE ShareId = ?";
		try (Connection conn = JDBCUtils.getConnection();
			 PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setLong(1, (Long) id);
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Share findById(Object id) {
		
		return null; 
	}

	@Override
	public List<Share> findAll() {
		
		return new ArrayList<>();
	}
}