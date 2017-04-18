package dao;

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
		PreparedStatement pstm = null;

		try {
			conn = DBconn.getConnection();
			conn.setAutoCommit(false);

			StringBuilder sql = new StringBuilder();
			sql.append("insert into member(id,name,phoneNum,birthDay,withdraw) ");
			sql.append("values (sysdate,?,?,?,0)");

			pstm = conn.prepareStatement(sql.toString());

			pstm.setString(1, member.getName());
			pstm.setString(2, member.getPhoneNum());
			pstm.setString(3, member.getBirthDay());

			pstm.executeUpdate();

		} finally {
			if (pstm != null)
				pstm.close();
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
			sql.append("set name = ? , phoneNum = ? , birthDay = ? , withdraw = ? ");
			sql.append("where id = ?    ");

			pstm = conn.prepareStatement(sql.toString());

			pstm.setString(1, member.getName());
			pstm.setString(2, member.getPhoneNum());
			pstm.setString(3, member.getBirthDay());
			pstm.setString(4, member.getWithdraw());
			pstm.setString(5, member.getId());

			pstm.executeUpdate();

		} finally {
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
				mem.addElement(rs.getString(4));
				mem.addElement(rs.getString(5));
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

		//System.out.println("keyfield: " + keyField);
		//System.out.println("keyWord: " + keyWord);

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
				member.addElement(rs.getString(4));
				member.addElement(rs.getString(5));

				memberList.addElement(member);
			}

		} finally {
			if (rs != null)
				rs.close();
			if (pstmt != null)
				pstmt.close();
			if (conn != null)
				conn.close();
		}

		return memberList;
	}

	// 회원상세 조회
	public MemberVO retrieveMember(String id) {
		MemberVO member = null; // 이거 고치세요.

		return member;
	}

	// 회원탈퇴처리
	public boolean withdrawMemberList(ArrayList<String> idList) throws SQLException {

		Connection conn = null; // DBconn.getConnection();
		PreparedStatement pstmt = null;
		try {
			conn = DBconn.getConnection();

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

			return true;

		} finally {
			if (pstmt != null)
				pstmt.close();
			if (conn != null)
				conn.close();
		}
	}

}
