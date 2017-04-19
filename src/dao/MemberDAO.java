package dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;

import conn.DBconn;
import domain.MemberVO;

public class MemberDAO {
	Connection conn = null;
	PreparedStatement pstm = null;// 회원등록

	public void insertMember(MemberVO member) throws SQLException {

		Connection conn = null;
		CallableStatement stmt = null;

		try {
		    conn = DBconn.getConnection();
		    conn.setAutoCommit(false);

		    String sql = "{ call registerMember(?, ?, ?) }";
		    stmt = conn.prepareCall(sql);
		    stmt.setString(1, member.getName());
		    stmt.setString(2, member.getPhoneNum());
		    stmt.setString(3, member.getBirthDay());

		    stmt.execute();

		} finally {
		    if (stmt != null)
			stmt.close();
		    if (conn != null)
			conn.close();

		}
	    }



	// 회원정보수정
	public void updateMember(MemberVO member) throws SQLException {

		Connection conn = null;
		PreparedStatement pstm = null;

		try {
			conn = DBconn.getConnection();
			conn.setAutoCommit(false);

			StringBuilder sql = new StringBuilder();
			sql.append("update member         ");
			sql.append("set name = ? , phoneNum = ? , birthDay = ? ");
			sql.append("where id = ?    ");

			pstm = conn.prepareStatement(sql.toString());

			pstm.setString(1, member.getName());
			pstm.setString(2, member.getPhoneNum());
			pstm.setString(3, member.getBirthDay());
			pstm.setString(4, member.getId());

			pstm.executeUpdate();
			
			conn.commit();
			
		} catch (Exception e) {
			conn.rollback();
			throw e;
		}finally {
			if (pstm != null)
				pstm.close();
			if (conn != null)
				conn.close();
		}
	}

	// 전체회원 조회
	public Vector<Vector<Object>> retrieveAllMemberList() throws SQLException {
		Vector<Vector<Object>> memall = new Vector<Vector<Object>>();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn = DBconn.getConnection();
			stmt = conn.createStatement();

			StringBuilder sql = new StringBuilder();
			sql.append("select id, name, phoneNum, birthDay ,withdraw ");
			sql.append("from member ");
			sql.append("order by id asc   		");

			rs = stmt.executeQuery(sql.toString());

			while (rs.next()) {
				Vector<Object> mem = new Vector<Object>();
				mem.addElement(false);
				mem.addElement(rs.getString(1));
				mem.addElement(rs.getString(2));
				mem.addElement(rs.getString(3));
				mem.addElement(rs.getDate(4));
				if(rs.getString(5).equals("0")) {
					mem.addElement("");
				} else {
					mem.addElement("탈퇴");
				}
				memall.add(mem);
			}

			return memall;
		} finally {
			if (rs != null)
				rs.close();
			if (stmt != null)
				stmt.close();
			if (conn != null)
				conn.close();
		}
	}

	// 회원조건 검색 id순
	public Vector<Vector<Object>> retrieveMemberListByCondition(String keyField, String keyWord) throws SQLException {
		Vector<Vector<Object>> memberList = new Vector<Vector<Object>>(); // 이거
																			// 고치세요.
		Connection conn = null; // DBconn.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DBconn.getConnection();

			StringBuffer sql = new StringBuffer();
			sql.append("select id, name, phoneNum, birthday, withdraw    ");
			sql.append("from member    ");

			if (keyField == "id") {
				sql.append("where id like ?    "); // ? = keyword
			} else if (keyField == "name") {
				sql.append("where name like ?    "); // ? = keyword
			} else if (keyField == "phoneNum") {
				sql.append("where phoneNum like ?    "); // ? = keyword
			}

			sql.append("order by id asc   		");

			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, "%" + keyWord + "%");

			rs = pstmt.executeQuery();
			while (rs.next()) {
				Vector<Object> member = new Vector<Object>();
				
				member.addElement(false);
				member.addElement(rs.getString(1));
				member.addElement(rs.getString(2));
				member.addElement(rs.getString(3));
				member.addElement(rs.getDate(4));
				if(rs.getString(5).equals("0")) {
					member.addElement("");
				} else {
					member.addElement("탈퇴");
				}

				memberList.addElement(member);
			}

			return memberList;
		} finally {
			if (rs != null)
				rs.close();
			if (pstmt != null)
				pstmt.close();
			if (conn != null)
				conn.close();
		}
	}

	// 회원탈퇴처리
	public boolean withdrawMemberList(ArrayList<String> idList) throws SQLException {

		Connection conn = null; // DBconn.getConnection();
		PreparedStatement pstmt = null;
		try {
			conn = DBconn.getConnection();
			conn.setAutoCommit(false);

			StringBuffer sql = new StringBuffer();

			sql.append("update member 		");
			sql.append("set withdraw = 1	");
			sql.append("where id = ? 		");

			pstmt = conn.prepareStatement(sql.toString());

			for (int i = 0; i < idList.size(); i++) {
				pstmt.setString(1, idList.get(i));

				pstmt.addBatch();
				pstmt.clearParameters();
			}
			
			pstmt.executeBatch(); // 이줄이 실행이 안됨.
			
			conn.commit();

			return true;

		} catch (SQLException sqle) {
			conn.rollback();
			throw sqle;
		} catch (Exception e) {
			conn.rollback();
			throw e;			
		} finally {
			if (pstmt != null)
				pstmt.close();
			if (conn != null)
				conn.close();
		}
	}

}
