package test;

import java.sql.Connection;
import dao.JDBCUtils;

public class TestConnection {
	public static void main(String[] args) {
		System.out.println("Đang thử kết nối đến SQL Server bằng JDBC...");
		
		// 1. Lấy kết nối từ JDBCUtils
		Connection connection = JDBCUtils.getConnection();
		
		// 2. Kiểm tra kết nối
		if (connection != null) {
			System.out.println("---------------------------------------");
			System.out.println("✅ KẾT NỐI THÀNH CÔNG! (Connected Successfully)");
			
			// In thông tin Database để kiểm tra kỹ hơn
			JDBCUtils.printInfo(connection);
			
			System.out.println("---------------------------------------");
			
			// 3. Đóng kết nối sau khi dùng xong
			JDBCUtils.closeConnection(connection);
		} else {
			System.out.println("---------------------------------------");
			System.out.println("❌ KẾT NỐI THẤT BẠI! (Connection is null)");
			System.out.println("Vui lòng kiểm tra lại tên Database, User, Password trong JDBCUtils.");
			System.out.println("---------------------------------------");
		}
	}
}