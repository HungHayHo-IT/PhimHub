package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.Video;

public class VideoDao implements DAOInterface<Video> {

    @Override
    public void insert(Video entity) {
        // [SỬA] Thêm cột Price vào câu lệnh INSERT
        String sql = "INSERT INTO Videos (VideoId, Title, Poster, Views, Description, Active, Href, Price) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = JDBCUtils.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            
            ps.setString(1, entity.getVideoId());
            ps.setString(2, entity.getTitle());
            ps.setString(3, entity.getPoster());
            ps.setInt(4, entity.getViews());
            ps.setString(5, entity.getDescription());
            ps.setBoolean(6, entity.isActive());
            ps.setString(7, entity.getHref());
            ps.setDouble(8, entity.getPrice()); // [QUAN TRỌNG] Set giá tiền
            
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Video entity) {
        // [SỬA] Thêm Price=? vào câu lệnh UPDATE
        String sql = "UPDATE Videos SET Title=?, Poster=?, Views=?, Description=?, Active=?, Href=?, Price=? WHERE VideoId=?";
        
        try (Connection conn = JDBCUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, entity.getTitle());
            ps.setString(2, entity.getPoster());
            ps.setInt(3, entity.getViews());
            ps.setString(4, entity.getDescription());
            ps.setBoolean(5, entity.isActive());
            ps.setString(6, entity.getHref());
            ps.setDouble(7, entity.getPrice()); // [QUAN TRỌNG] Set giá tiền
            
            ps.setString(8, entity.getVideoId()); // Điều kiện WHERE
            
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Object id) {
        String sql = "DELETE FROM Videos WHERE VideoId=?";
        try (Connection connection = JDBCUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            
            preparedStatement.setString(1, (String) id);
            preparedStatement.executeUpdate();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Video findById(Object id) {
        String sql = "SELECT * FROM Videos WHERE VideoId = ?";
        Video video = null;
        
        try (Connection conn = JDBCUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, (String) id);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                video = mapRow(rs); // Sử dụng hàm mapRow để lấy dữ liệu (bao gồm Price)
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return video;
    }

    @Override
    public List<Video> findAll() {
        String sql = "SELECT * FROM Videos";
        List<Video> list = new ArrayList<>();
        
        try (Connection conn = JDBCUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                list.add(mapRow(rs)); // Sử dụng hàm mapRow
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    // Hàm lấy danh sách phim yêu thích
    public List<Video> findFavoriteVideosByUserId(String userId) {
        String sql = "SELECT v.* FROM Videos v " +
                     "JOIN Favorites f ON v.VideoId = f.VideoId " +
                     "WHERE f.UserId = ?";
        List<Video> list = new ArrayList<>();
        try (Connection conn = JDBCUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapRow(rs)); // Sử dụng hàm mapRow
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
 // Hàm tìm kiếm phim theo tên (gần đúng)
    public List<Video> findByName(String keyword) {
        String sql = "SELECT * FROM Videos WHERE Title LIKE ?";
        List<Video> list = new ArrayList<>();
        try (Connection conn = JDBCUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            // Dấu % ở hai đầu để tìm kiếm mọi phim CÓ CHỨA từ khóa
            ps.setString(1, "%" + keyword + "%"); 
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                // Tái sử dụng hàm mapRow để lấy đầy đủ thông tin (bao gồm cả giá)
                list.add(mapRow(rs)); 
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // [QUAN TRỌNG] Hàm này giúp map dữ liệu và KHÔNG QUÊN cột Price
    private Video mapRow(ResultSet rs) throws Exception {
        Video video = new Video();
        video.setVideoId(rs.getString("VideoId"));
        video.setTitle(rs.getString("Title"));
        video.setPoster(rs.getString("Poster"));
        video.setViews(rs.getInt("Views"));
        video.setDescription(rs.getString("Description"));
        video.setActive(rs.getBoolean("Active"));
        video.setHref(rs.getString("Href"));
        
        // Lấy giá tiền từ DB (Nếu null hoặc lỗi thì mặc định 0)
        try {
            video.setPrice(rs.getDouble("Price")); 
        } catch (Exception e) {
            video.setPrice(0);
        }
        
        return video;
    }
}