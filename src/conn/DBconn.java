package conn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBconn {

	public static Connection getConnection() throws SQLException {
		// driver ����
		try {
			Class.forName("oracle.jdbc.OracleDriver");

		} catch (ClassNotFoundException e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		// Connection ��ü ����
		String url = "jdbc:oracle:thin:@kodica0303-PC:1521:xe";
		String user = "rental_server";
		String password = "java1234";

		Connection conn = DriverManager.getConnection(url, user, password);
		
		System.out.println("���� ����");
		
		return conn;
	}
}
