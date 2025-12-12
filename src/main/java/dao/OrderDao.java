package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import dao.JDBCUtils;

public class OrderDao {
    
    // Kiểm tra xem User đã mua Video này chưa
    public boolean checkUserHasBought(String userId, String videoId) {
        String sql = "SELECT * FROM Orders WHERE UserId = ? AND VideoId = ?";
        try (Connection conn = JDBCUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, userId);
            ps.setString(2, videoId);
            ResultSet rs = ps.executeQuery();
            return rs.next(); // Nếu có dữ liệu nghĩa là đã mua
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Hàm tạo hóa đơn mua phim
    public void createOrder(String userId, String videoId, double amount) {
        String sql = "INSERT INTO Orders (UserId, VideoId, Amount, OrderDate) VALUES (?, ?, ?, GETDATE())";
        try (Connection conn = JDBCUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, userId);
            ps.setString(2, videoId);
            ps.setDouble(3, amount);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}