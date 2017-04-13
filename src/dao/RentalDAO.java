package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

import conn.DBconn;

public class RentalDAO {
	
	// 도서 연장
	public void renewalBooksFromBasket(List<Integer> rentingBooksRecords) throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = DBconn.getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("update rental ");
			sql.append("set due_date = due_date + 7 ");
			sql.append("where rental_id = ?");
			pstmt = conn.prepareStatement(sql.toString());
			
			for(int i=0; i<rentingBooksRecords.size(); i++) {
				pstmt.setInt(1, rentingBooksRecords.get(i));
				pstmt.addBatch();
				pstmt.clearParameters();
			}		
			pstmt.executeBatch();
			conn.commit();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null) pstmt.close();
			if (conn != null) conn.close();
		}
	}
	
	
	// 대여 현황 조회
	public Vector<Vector<Object>> selectRentingBooksByMember(String memberId) throws SQLException{
		Vector<Vector<Object>> books = new Vector<Vector<Object>>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DBconn.getConnection();
			
			StringBuilder sql = new StringBuilder();
			sql.append("select b.book_id, b.title, b.writer, b.publisher, g.genre_name, to_char(r.due_date,'YYYY/MM/DD'), "); 
			sql.append("decode(trunc(r.due_date - r.rent_date), 7, 'O', 'X') ");
			sql.append("from book b, rental r, genre g ");
			sql.append("where b.book_id = r.book_id ");
			sql.append("and g.genre_id = b.genre_id ");
			sql.append("and r.member_id = ? ");
			sql.append("and r.return_date is null ");
			sql.append("order by r.rental_id asc");
			
			pstmt = conn.prepareStatement(sql.toString());
			
			pstmt.setString(1, memberId);
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				Vector<Object> book = new Vector<Object>();
				book.addElement(rs.getString(1));
				book.addElement(rs.getString(2));
				book.addElement(rs.getString(3));
				book.addElement(rs.getString(4));
				book.addElement(rs.getString(5));
				book.addElement(rs.getString(6));
				book.addElement(rs.getString(7));
				
				books.add(book);
			
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(rs != null) rs.close();
			if(pstmt != null) pstmt.close();
			if(conn != null) conn.close();
		}
		
		return books;
		
	}
	
	
	// 도서 대여
	public void rentalBooksFromBasket(String memberId, List<String> books) throws SQLException{
		Connection conn = null;
		PreparedStatement pstmt = null;	 // 도서 대여기록 추가
		PreparedStatement pstmt2 = null; // 도서 대여상태 변경

		try {
			conn = DBconn.getConnection();
			StringBuilder sql = new StringBuilder();
			StringBuilder sql2 = new StringBuilder();
			sql.append("insert into rental(rental_id, member_id, book_id, rent_date, due_date) " );
			sql.append("valus (rental_seq.nextval, ?, ?, sysdate, sysdate+7) ");
			pstmt = conn.prepareStatement(sql.toString());
			
			sql2.append("update book ");
			sql2.append("set status = 1 ");
			sql2.append("where book_id = ? ");
			pstmt2 = conn.prepareStatement(sql.toString());
			
			for(int i=0; i<books.size(); i++) {			
				pstmt.setString(1, memberId);
				pstmt.setString(2, books.get(i));
				pstmt.addBatch();
				
				String book = books.get(i);
				pstmt2.setString(1, book);
				pstmt2.addBatch();
				
				pstmt.clearParameters();
				pstmt2.clearParameters();
			}
			pstmt.executeBatch();
			conn.commit();
			pstmt2.executeBatch();
			conn.commit();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(pstmt != null) pstmt.close();
			if(pstmt2 != null) pstmt2.close();
			if(conn != null) conn.close();
			
		}
	}

}
