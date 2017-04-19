package dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import conn.DBconn;
import domain.BookVO;
import domain.GenreVO;

public class BookDAO {

	public List<GenreVO> retrieveGenreList() throws SQLException {			// 장르조회
		List<GenreVO> genres = new ArrayList<GenreVO>();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			conn = DBconn.getConnection();
			
			stmt = conn.createStatement();
			StringBuilder sql = new StringBuilder();
			sql.append("select genre_id, genre_name from genre ");
			rs = stmt.executeQuery(sql.toString());
			
			while(rs.next()) {
				genres.add(new GenreVO(rs.getInt(1), rs.getString(2)));		// list에 Id와 Name 추가
			}
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				if(rs != null)
					rs.close();
				if(stmt != null)
					stmt.close();
				if(conn != null)
					conn.close();
			}
			catch (Exception e2) {
				throw e2;
			}
		}
		
		return genres;
	}
	
	public Vector<Vector<Object>> selectBookById(String id) throws SQLException {			// 도서 상세 정보 조회(책 한권 클릭했을때 상세 정보)
		Connection conn = null;							// String id에서 isbn값을 넘길수 있는것인가?
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Vector<Vector<Object>> books = new Vector<Vector<Object>>();
		try {
			conn = DBconn.getConnection();
			StringBuilder sql = new StringBuilder();
			// 책 고유번호, 제목, 저자, 장르, 출판사, 출판일, isbn, 장르
			sql.append("select book_id, title, writer, publisher, isbn, status, publish_date, genre_id  ");
			sql.append("from book  ");
			sql.append("where isbn = ?  ");		
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, id);
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
				book.addElement(rs.getInt(8));
				
				books.add(book);
			}
			// 수정시 swing - setSelectedIndex 통해서 바꿔줌!
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				if(rs != null)
					rs.close();
				if(pstmt != null)
					pstmt.close();
				if(conn != null)
					conn.close();
			}
			catch (Exception e2) {
				throw e2;
			}
		}
		
		return books;	   
	}
	
	public Vector<Vector<Object>> selectBookList(int keyfield, String keyword) throws SQLException{			// 도서 전체 목록 조회
		Connection conn = null;							
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Vector<Vector<Object>> books = new Vector<Vector<Object>>();
		try {
			conn = DBconn.getConnection();
			StringBuilder sql = new StringBuilder();
			// 책 고유번호, 제목, 저자, 장르, 출판사, 출판일, isbn, 장르
			if(keyfield == 1) {		// 전체 검색
				sql.append("select b.book_id, b.title, b.writer, b.publisher, b.isbn, b.status, b.publish_date, g.genre_name  ");
				sql.append("from book b, genre g  ");
				sql.append("where b.genre_id = g.genre_id  ");
				sql.append("order by title ");
				pstmt = conn.prepareStatement(sql.toString());
				
				rs = pstmt.executeQuery();
			}
			else if(keyfield == 2) {		// 제목 검색
				sql.append("select b.book_id, b.title, b.writer, b.publisher, b.isbn, b.status, b.publish_date, g.genre_name  ");
				sql.append("from book b, genre g ");
				sql.append("where title like ? and b.genre_id = g.genre_id   ");		
				sql.append("order by title ");
				pstmt = conn.prepareStatement(sql.toString());
				
				pstmt.setString(1, "%"+keyword+"%");
				rs = pstmt.executeQuery();
			}
			else if(keyfield == 3) {			// 저자 검색
				sql.append("select b.book_id, b.title, b.writer, b.publisher, b.isbn, b.status, b.publish_date, g.genre_name  ");
				sql.append("from book b, genre g ");
				sql.append("where writer like ? and b.genre_id = g.genre_id  ");		
				sql.append("order by title ");
				pstmt = conn.prepareStatement(sql.toString());
				
				pstmt.setString(1, "%"+keyword+"%");
				rs = pstmt.executeQuery();
			}
			else if(keyfield == 4) {			// 출판사 검색
				sql.append("select b.book_id, b.title, b.writer, b.publisher, b.isbn, b.status, b.publish_date, g.genre_name  ");
				sql.append("from book b, genre g  ");
				sql.append("where publisher like ? and b.genre_id = g.genre_id   ");		
				sql.append("order by title ");
				pstmt = conn.prepareStatement(sql.toString());
				
				pstmt.setString(1, "%"+keyword+"%");
				rs = pstmt.executeQuery();
			}
			else if(keyfield == 5) {			// 장르 검색
				sql.append("select b.book_id, b.title, b.writer, b.publisher, b.isbn, b.status, b.publish_date, g.genre_name  ");
				sql.append("from book b, genre g  ");
				sql.append("where b.genre_id = g.genre_id and g.genre_name like ?  ");		
				sql.append("order by title ");
				pstmt = conn.prepareStatement(sql.toString());
				
				pstmt.setString(1, "%"+keyword+"%");
				rs = pstmt.executeQuery();
			}
			
			while(rs.next()) {
				Vector<Object> book = new Vector<Object>();
				book.addElement(rs.getString(1));
				book.addElement(rs.getString(2));
				book.addElement(rs.getString(3));
				book.addElement(rs.getString(4));
				book.addElement(rs.getString(5));
				book.addElement(rs.getString(6));
				book.addElement(rs.getString(7));
				book.addElement(rs.getString(8));
				
				/* for(int i=0; i<book.size(); i++)
					  System.out.println(book.get(i));
				*/
				books.addElement(book);
			}

			return books;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				if(rs != null)
					rs.close();
				if(pstmt != null)
					pstmt.close();
				if(conn != null)
					conn.close();
			}
			catch (Exception e2) {
				throw e2;
			}
		}
		
		return null;	   
	}

	public void insertBook(List<BookVO> book) throws SQLException { // 도서 등록
	      Connection conn = null;
	      CallableStatement cstmt = null;

	      try {
	         conn = DBconn.getConnection();

	         for (int i = 0; i < book.size(); i++) {
	            cstmt = conn.prepareCall("{ call registerBook(?, ?, ?, ?, ?, ?) }");
	            
	            cstmt.setString(1, book.get(i).getSubject());
	            cstmt.setString(2, book.get(i).getWriter());
	            cstmt.setString(3, book.get(i).getPublisher());
	            cstmt.setString(4, book.get(i).getIsbn());
	            cstmt.setInt(5, book.get(i).getGenre1());
	            cstmt.setString(6, book.get(i).getPublishDate());
	            
	            cstmt.execute();

	         }
	      } finally {
	         if (cstmt != null)
	            cstmt.close();
	         if (conn != null)
	            conn.close();
	      }

	   }
	
	public void updateBook(BookVO book) throws SQLException { // 도서 정보 수정(수정이 필요)
		Connection conn = null;
		PreparedStatement pstmt = null;
		//CallableStatement cstmt = null;

		try {
			conn = DBconn.getConnection();

			StringBuilder sql = new StringBuilder();
			
			sql.append("update book																				");
			sql.append("set title=?, writer=?, publisher=?, status=?,	genre_id=?, publish_date=TO_DATE(SUBSTR(?,0,10), 'YYYY-MM-DD')			");
			sql.append("where isbn = ?                                            ");
	
			pstmt = conn.prepareStatement(sql.toString());
	
			pstmt.setString(1, book.getSubject());
			pstmt.setString(2, book.getWriter());
			pstmt.setString(3, book.getPublisher());
			pstmt.setString(4, book.getIsRent());
			pstmt.setInt(5, book.getGenre1());
			pstmt.setString(6, book.getPublishDate());
			pstmt.setString(7, book.getIsbn());
			
			pstmt.executeUpdate();
			
			/*
			for(int i=0; i<book.size(); i++) {
				cstmt = conn.prepareCall("{ call modifyBook(?, ?, ?, ?, ?, ?, ?) }");
	            System.out.println(book.get(i).getBookId());
				//title,writer,publisher,ISBN,genre_id,publish_date
	            cstmt.setString(1, book.get(i).getBookId());
	            cstmt.setString(2, book.get(i).getSubject());
	            cstmt.setString(3, book.get(i).getWriter());
	            cstmt.setString(4, book.get(i).getPublisher());
	            cstmt.setString(5, book.get(i).getIsbn());
	            cstmt.setInt(6, book.get(i).getGenre1());
	            cstmt.setString(7, book.get(i).getPublishDate());
	            
	            cstmt.execute();
			
			}
/*
			if(!book.getIsbn().equals(book.getBookId().substring(0,13))) {		// book_id 13자리와 isbn 13자리가 다른 경우 프로시저로 수정
				cstmt = conn.prepareCall("{ call modifyBook(?, ?) }");
	            
	            cstmt.setString(1, book.getIsbn());
	            cstmt.setString(2, book.getBookId());	// 같은 isbn의 max(id)  바꾸기 전 isbn 추출.
	            
	            cstmt.execute();
			}
			
			else {
				sql.append("update book																				");
				sql.append("set title=?, writer=?, publisher=?, status=?,	genre_id=?, publish_date=TO_DATE(SUBSTR(?,0,10), 'YYYY-MM-DD')			");
				sql.append("where isbn = ?                                            ");
		
				pstmt = conn.prepareStatement(sql.toString());
		
				pstmt.setString(1, book.getSubject());
				pstmt.setString(2, book.getWriter());
				pstmt.setString(3, book.getPublisher());
				pstmt.setString(4, book.getIsbn());
				pstmt.setString(5, book.getIsRent());
				pstmt.setInt(6, book.getGenre1());
				pstmt.setString(7, book.getPublishDate());
				pstmt.setString(8, book.getIsbn());
				
				pstmt.executeUpdate();
			}
*/
			

		} finally {
			//if(cstmt != null)
			//	cstmt.close();
			if (pstmt != null)
				pstmt.close();
			if (conn != null)
				conn.close();
		}

	}

	public void deleteBookList(List<String> idList) throws SQLException { 			// 도서 일괄 삭제
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = DBconn.getConnection();
			
			for(int i=0; i<idList.size(); i++) {
				StringBuilder sql = new StringBuilder();
				
				sql.append("delete from book					");
				sql.append("where book_id = ?				");
				
				pstmt = conn.prepareStatement(sql.toString());
				
				pstmt.setString(1,idList.get(i));
				
				pstmt.executeUpdate();
			}

		} finally {
			if (pstmt != null)
				pstmt.close();
			if (conn != null)
				conn.close();
		}

	}
}
