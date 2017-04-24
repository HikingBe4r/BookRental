package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import conn.DBconn;
import domain.AdminVO;

public class AdminDAO {
	
	/**
	 * sub		: 관리자 로그인 method
	 * param	: AdminVO(id, pw)
	 * return	: boolean (true: 성공, false: 실패) 
	 * dept		: db의 관리자 정보와 입력받은 정보의 일치 여부를 따진다.
	 */
	public boolean loginAdmin(AdminVO admin) throws SQLException {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			conn = DBconn.getConnection();
			stmt = conn.createStatement();

			StringBuffer sql = new StringBuffer();
			sql.append("select admin_id, admin_pw		");
			sql.append("from admin						");

			rs = stmt.executeQuery(sql.toString());
			if (rs.next()) {
				if (rs.getString(1).equals(admin.getId()) && rs.getString(2).equals(admin.getPassword())) {
					return true;
				}
			}

		} finally {
			if (stmt != null)
				stmt.close();
			if (conn != null)
				conn.close();
		}

		return false;
	}
}
