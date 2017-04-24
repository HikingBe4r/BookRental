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


	/**
	 * sub		: 회원 등록 메소드
	 * param	: MemberVO : 회원정보(MemberVO) 
	 * return 	: 
	 * dept		: 입력받은 회원을 등록한다.
	 */
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

	/**
	 * sub		: 회원정보 수정 메소드
	 * param	: MemberVO : 회원정보(MemberVO) 
	 * return 	: 
	 * dept		: 입력받은 회원정보를 수정한다.
	 */
	public void updateMember(MemberVO member) throws SQLException {
		Connection conn = null;
		PreparedStatement pstm = null;

		try {
			conn = DBconn.getConnection();
			conn.setAutoCommit(false);

			StringBuilder sql = new StringBuilder();
			sql.append("update member        					 	");
			sql.append("set name = ? , phoneNum = ? , birthDay = ? 	");
			sql.append("where id = ?    							");

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
		} finally {
			if (pstm != null)
				pstm.close();
			if (conn != null)
				conn.close();
		}
	}

	/**
	 * sub		: 전체 회원조회 메소드
	 * param	:  
	 * return 	: Vector<Vector<Object>> : JTable에서 사용할 rowData
	 * dept		: 전체 회원정보를 조회한다.
	 */
	public Vector<Vector<Object>> retrieveAllMemberList() throws SQLException {
		Vector<Vector<Object>> memall = new Vector<Vector<Object>>();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn = DBconn.getConnection();
			stmt = conn.createStatement();

			StringBuilder sql = new StringBuilder();
			sql.append("select id, name, phoneNum, birthDay ,withdraw 	");
			sql.append("from member 									");
			sql.append("order by id asc   								");

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

	/**
	 * sub		: 회원 조건검색 메소드
	 * param	: keyField: 검색조건(콤보박스 선택), keyWord: 검색어
	 * return 	: Vector<Vector<Object>> : JTable에서 사용할 rowData
	 * dept		: 조건에 맞는 회원을 검색한다.
	 */
	public Vector<Vector<Object>> retrieveMemberListByCondition(String keyField, String keyWord) throws SQLException {
		Vector<Vector<Object>> memberList = new Vector<Vector<Object>>(); 
		Connection conn = null; 
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = DBconn.getConnection();

			StringBuffer sql = new StringBuffer();
			sql.append("select id, name, phoneNum, birthday, withdraw   	");
			sql.append("from member    										");

			if (keyField == "id") {
				sql.append("where id like ?    								"); 
			} else if (keyField == "name") {
				sql.append("where name like ?    							");
			} else if (keyField == "phoneNum") {
				sql.append("where phoneNum like ?    						"); 
			}

			sql.append("order by id asc   									");

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

	/**
	 * sub		: 회원 탈퇴 메소드
	 * param	: ArrayList<String> idList: 탈퇴할 회원 id 리스트 
	 * return 	: boolean : true(탈퇴성공), false(탈퇴실패)
	 * dept		: 조건에 맞는 회원을 탈퇴한다.
	 */
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
			pstmt.executeBatch(); 
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
