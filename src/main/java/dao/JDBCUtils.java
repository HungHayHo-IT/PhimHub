package dao;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JDBCUtils {
	public static Connection getConnection() {
		Connection connection = null;
		
		
		try {
			String urlString = "jdbc:sqlserver://localhost:1433;databaseName=PhimHub;encrypt=true;trustServerCertificate=true;";
			String usernamString = "sa";
			String passwordString="123";
			
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			connection = DriverManager.getConnection(urlString,usernamString,passwordString);
		} catch (SQLException e) {
			e.printStackTrace();
		}catch (ClassNotFoundException e) {
			 System.out.println("Không tìm thấy Driver SQL Server");
	            e.printStackTrace();
		}
		return connection;
	}
	
	public static void closeConnection(Connection c) {
        try {
            if (c != null) {
                c.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
	public static void printInfo(Connection c) {
        try {
            if (c != null) {
                DatabaseMetaData mtdt = c.getMetaData();
                System.out.println("Database Name: " + mtdt.getDatabaseProductName());
                System.out.println("Database Version: " + mtdt.getDatabaseProductVersion());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
