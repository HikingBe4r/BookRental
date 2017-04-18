package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

import conn.DBconn;

public class RentalDAO {
	
	// 대여 가능권수
	public int rentableBookNum(String memberId) throws SQLException {
		int num = 0;
		if(isDelayer(memberId)) {
			return 0;
		} else {
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			try {
				conn = DBconn.getConnection();
				StringBuilder sql = new StringBuilder();
				sql.append("select count(rental_id) ");
				sql.append("from rental ");
				sql.append("where member_id = ? ");
				sql.append("and return_date is null ");
				
				pstmt = conn.prepareStatement(sql.toString());
				
				pstmt.setString(1, memberId);
				
				rs = pstmt.executeQuery();
				if(rs.next()) {
					num = 5 - rs.getInt(1);
				}			
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			}
			return num;
		}
	}
	
	// 연체자인지 확인(맞으면 true)
	public boolean isDelayer(String memberId) throws SQLException{
		Connection conn = null;
		PreparedStatement pstmt = null;		
		ResultSet rs = null;
		try {
			conn = DBconn.getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("select trunc(due_date) - trunc(sysdate) ");
			sql.append("from rental ");
			sql.append("where member_id = ? and return_date is null ");
			pstmt = conn.prepareStatement(sql.toString());
			
			pstmt.setString(1, memberId);
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				if(rs.getInt(1) < 0) return true;				
			}
					
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(rs != null) rs.close();
			if(pstmt != null) pstmt.close();
			if(conn != null) conn.close();
		}
		
		return false;
	}
	
	// 대여 히스토리 검색
	public Vector<Vector<Object>> selectRentalHistoryList
				(String keyword, boolean[] pattern, String startDate, String endDate) throws SQLException {
		// pattern : String 배열에서 boolean 배열로 바꿈
		// [0] : 도서명(true) or 회원명(false), [1] : 대여, [2] : 반납, [3] : 연장
		Vector<Vector<Object>> historys = new Vector<Vector<Object>>();
		
		
		int numOfSelectedBox = 0; 	// 체크박스가 설정된 개수 
		for(int i=1; i<pattern.length; i++) {
			if(pattern[i]) numOfSelectedBox++; 
		}
		
		// 체크박스 모두 해제 되어있을 때
		if(numOfSelectedBox == 0) {
			return historys;	// 비어있는 벡터
		}
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DBconn.getConnection();
			
			// 대여,반납,연장 조건처리 어떻게 할것인지 생각해볼 필요
			// 대여,반납,연장 sql문 따로 생성한 후, union?
			
			StringBuilder rentSql = new StringBuilder();
			// 대여 일련번호, 도서ID, 도서명, 회원ID, 회원명, 연락처, 구분, 대여일, 반납일, 반납예정일
			rentSql.append("select r.rental_id, b.book_id, b.title, m.id, m.name, m.phonenum, ");
			rentSql.append("nvl2(r.return_date, '반납', decode(trunc(r.due_date) - trunc(r.rent_date), 7, '대여', '연장')), ");
			rentSql.append("to_char(r.rent_date,'YYYY/MM/DD'), to_char(r.return_date,'YYYY/MM/DD'), to_char(r.due_date,'YYYY/MM/DD') ");
			rentSql.append("from book b, rental r, member m ");
			rentSql.append("where b.book_id = r.book_id and m.id = r.member_id and ");
			rentSql.append("m.name like ? and b.title like ? and r.rent_date between to_date(?, 'YYYY/MM/DD') and trunc(to_date(?, 'YYYY/MM/DD'))+1 and "); 
			rentSql.append("r.return_date is null and ");
			rentSql.append("r.due_date - r.rent_date = 7 ");
			
			StringBuilder returnSql = new StringBuilder();
			returnSql.append("select r.rental_id, b.book_id, b.title, m.id, m.name, m.phonenum, ");
			returnSql.append("nvl2(r.return_date, '반납', decode(trunc(r.due_date) - trunc(r.rent_date), 7, '대여', '연장')), ");
			returnSql.append("to_char(r.rent_date,'YYYY/MM/DD'), to_char(r.return_date,'YYYY/MM/DD'), to_char(r.due_date,'YYYY/MM/DD') ");
			returnSql.append("from book b, rental r, member m ");
			returnSql.append("where b.book_id = r.book_id and m.id = r.member_id and ");
			returnSql.append("m.name like ? and b.title like ? and r.rent_date between to_date(?, 'YYYY/MM/DD') and trunc(to_date(?, 'YYYY/MM/DD'))+1 and ");
			returnSql.append("r.return_date is not null ");
			
			StringBuilder renewalSql = new StringBuilder();
			renewalSql.append("select r.rental_id, b.book_id, b.title, m.id, m.name, m.phonenum, ");
			renewalSql.append("nvl2(r.return_date, '반납', decode(trunc(r.due_date) - trunc(r.rent_date), 7, '대여', '연장')), ");
			renewalSql.append("to_char(r.rent_date,'YYYY/MM/DD'), to_char(r.return_date,'YYYY/MM/DD'), to_char(r.due_date,'YYYY/MM/DD') ");
			renewalSql.append("from book b, rental r, member m ");
			renewalSql.append("where b.book_id = r.book_id and m.id = r.member_id and ");
			renewalSql.append("m.name like ? and b.title like ? and r.rent_date between to_date(?, 'YYYY/MM/DD') and trunc(to_date(?, 'YYYY/MM/DD'))+1 and ");
			renewalSql.append("r.return_date is null and ");
			renewalSql.append("r.due_date - r.rent_date = 14 ");
			
			StringBuilder sql = new StringBuilder();
			if(pattern[1]) {
				sql.append(rentSql);
				if(pattern[2]) sql.append(" union ").append(returnSql);
				if(pattern[3]) sql.append(" union ").append(renewalSql);
			} else if(pattern[2]) {
				sql.append(returnSql);
				if(pattern[3]) sql.append(" union ").append(renewalSql);
			} else {
				sql.append(renewalSql);
			}
			sql.append("order by rental_id desc ");
			pstmt = conn.prepareStatement(sql.toString());
					
			if(pattern[0]) { // 도서명
				pstmt.setString(1, "%");
				pstmt.setString(2, "%" + keyword + "%");
			} else { // 회원명
				pstmt.setString(1, "%" + keyword + "%");
				pstmt.setString(2, "%");
			}
			pstmt.setString(3, startDate);
			pstmt.setString(4, endDate);	
			
			if (numOfSelectedBox >= 2) {
				if(pattern[0]) { // 도서명
					pstmt.setString(5, "%");
					pstmt.setString(6, "%" + keyword + "%");
				} else { // 회원명
					pstmt.setString(5, "%" + keyword + "%");
					pstmt.setString(6, "%");
				}
				pstmt.setString(7, startDate);
				pstmt.setString(8, endDate);
			} 
			
			if (numOfSelectedBox == 3) {
				if(pattern[0]) { // 도서명
					pstmt.setString(9, "%");
					pstmt.setString(10, "%" + keyword + "%");
				} else { // 회원명
					pstmt.setString(9, "%" + keyword + "%");
					pstmt.setString(10, "%");
				}
				pstmt.setString(11, startDate);
				pstmt.setString(12, endDate);
			} 
			
			rs = pstmt.executeQuery();
			sql.delete(0, sql.length());
			
			while(rs.next()) {
				Vector<Object> history = new Vector<Object>();
				history.addElement(rs.getInt(1));		// 대여 일련번호
				history.addElement(rs.getString(2));	// 도서ID
				history.addElement(rs.getString(3));	// 도서명
				history.addElement(rs.getString(4));	// 회원ID
				history.addElement(rs.getString(5));	// 회원명
				history.addElement(rs.getString(6));	// 연락처
				history.addElement(rs.getString(7));	// 구분
				history.addElement(rs.getString(8));	// 대여일
				history.addElement(rs.getString(9));	// 반납일
				history.addElement(rs.getString(10));	// 반납예정일
				//System.out.println(history.toString());
				historys.add(history);
			}			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(rs != null) rs.close();
			if(pstmt != null) pstmt.close();
			if(conn != null) conn.close();
		}		
		
		return historys;
	}
	
	
	// 도서 연장
	public void renewalBooksFromBasket(List<String> rentingBooksId) throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = DBconn.getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("update rental ");
			sql.append("set due_date = due_date + 7 ");	// 연장시 반납예정일 7일 증가
			sql.append("where book_id = ?");
			pstmt = conn.prepareStatement(sql.toString());
			
			for(int i=0; i<rentingBooksId.size(); i++) {
				pstmt.setString(1, rentingBooksId.get(i));
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
			// 도서ID, 도서명, 저자, 출판사, 장르, 반납예정일, (기)연장여부
			sql.append("select b.book_id, b.title, b.writer, b.publisher, g.genre_name, to_char(r.due_date,'YYYY/MM/DD'), "); 
			sql.append("decode(trunc(r.due_date - r.rent_date), 14, 'O', 'X') "); // 연장을 이미 했는지 여부(연장완료? 기연장여부?)
			sql.append("from book b, rental r, genre g ");
			sql.append("where b.book_id = r.book_id ");
			sql.append("and g.genre_id = b.genre_id ");
			sql.append("and r.member_id = ? ");			// 해당 회원의
			sql.append("and r.return_date is null ");	// 현재 대여(연장)상태인 기록 
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
			sql.append("values (rental_seq.nextval, ?, ?, sysdate, sysdate+7) ");
			pstmt = conn.prepareStatement(sql.toString());
			
			sql2.append("update book ");
			sql2.append("set status = '1' "); // 0 : 대여가능, 1 : 대여중
			sql2.append("where book_id = ? ");
			pstmt2 = conn.prepareStatement(sql2.toString());
			
			for(int i=0; i<books.size(); i++) {		
				String book = books.get(i);
				
				pstmt.setString(1, memberId);
				pstmt.setString(2, book);
				pstmt.addBatch();
							
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
	//도서 반납
		public void returnBooksFromBasket(List<String> rentingBookId) throws SQLException {
			Connection conn = null;
			PreparedStatement pstmt = null; //도서반납일 추가
			PreparedStatement pstmt2 = null; //대여현황확인 변경 - 대여중 1, 대여가능 0
			try {
				conn = DBconn.getConnection();
				
				//도서반납일 추가 sql문
				StringBuilder sql = new StringBuilder();
				sql.append("update rental ");
				sql.append("set return_date = sysdate ");
				sql.append("where book_id = ? and return_date is null ");
				pstmt = conn.prepareStatement(sql.toString());
							
				//대여현황확인 변경 sql문
				StringBuilder sql2 = new StringBuilder();
				sql2.append("update book ");
				sql2.append("set status = '0' ");
				sql2.append("where book_id = ?");
				pstmt2 = conn.prepareStatement(sql2.toString());
				
				
				for(int i = 0; i<rentingBookId.size(); i++){
					pstmt.setString(1, rentingBookId.get(i));
					pstmt.addBatch();
					pstmt.clearParameters();
					
					pstmt2.setString(1, rentingBookId.get(i));
					pstmt2.addBatch();
					pstmt2.clearParameters();
				}
				
				pstmt.executeBatch();
				conn.commit();
				pstmt2.executeBatch();
				conn.commit();
				
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			}
		}

	
}
