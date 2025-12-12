package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class StatsDao {
	// 1. Đếm tổng số người dùng
    public long countUsers() {
        String sql = "SELECT COUNT(*) FROM Users";
        try (Connection conn = JDBCUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getLong(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    
 // 2. Đếm tổng số video
    public long countVideos() {
        String sql = "SELECT COUNT(*) FROM Videos";
        try (Connection conn = JDBCUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getLong(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    
 // 3. Tính tổng lượt xem của tất cả video
    public long sumViews() {
        String sql = "SELECT SUM(Views) FROM Videos";
        try (Connection conn = JDBCUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getLong(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    public long countFavorites() {
        String sql = "SELECT COUNT(*) FROM Favorites";
        try (Connection conn = JDBCUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getLong(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
