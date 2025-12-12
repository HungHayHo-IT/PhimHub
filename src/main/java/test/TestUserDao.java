package test;

import dao.UserDao;
import model.User;

public class TestUserDao {
	public static void main(String[] args) {
		UserDao dao = new UserDao();
		
		User u = new User();
		u.setUserId("userTest01");
		u.setPassword("password123");
		u.setEmail("test@gmail.com");
		u.setFullName("Nguyen Van Test"); // Kiểm tra kỹ dòng này
		u.setAdmin(false);
	}
}
