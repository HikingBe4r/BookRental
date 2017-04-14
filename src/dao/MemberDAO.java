package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
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

	    pstm.executeUpdate();

	} finally {
	    if (pstm != null)
		pstm.close();
	    if (conn != null)
		conn.close();

	}
    }

    // 전체회원 조회
    public Vector<Vector<Object>> retrieveAllMemberList() throws SQLException{
	Vector<Vector<Object>> memall = new Vector<Vector<Object>>();
	Connection conn = null;
	Statement stmt = null;
	ResultSet rs = null;
	try {
	    conn = DBconn.getConnection();

	    stmt = conn.createStatement();

	    StringBuilder sql = new StringBuilder();
	    sql.append("select id, name, phoneNum, birthDay  ");
	    sql.append("from member ");
	    rs = stmt.executeQuery(sql.toString());
	    while (rs.next()) {
		Vector<Object> mem = new Vector<Object>();
		mem.addElement(rs.getString(1));
		mem.addElement(rs.getString(2));
		mem.addElement(rs.getString(3));
		mem.addElement(rs.getString(4));
		mem.add(memall);
	    }
	} finally {
	    if (rs != null)
		rs.close();
	    if (stmt != null)
		stmt.close();
	    if (conn != null)
		conn.close();
	}
	return memall;
    }
    // 이거 고치세요.

    // 회원조건 검색
    public Vector<Vector<Object>> retrieveMemberListByCondition(String keyField, String keyWord) {
	Vector<Vector<Object>> memberList = null; // 이거 고치세요.

	return memberList;
    }

    // 회원상세 조회
    public MemberVO retrieveMember(String id) {
	MemberVO member = null; // 이거 고치세요.

	return member;
    }

    // 회원탈퇴
    public void withdrawMemberList(List<String> idList) {

    }

}
