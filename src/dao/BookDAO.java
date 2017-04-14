package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

import com.sun.corba.se.spi.orbutil.fsm.Guard.Result;

import conn.DBconn;
import domain.BookVO;
import domain.GenreVO;

public class BookDAO {

	public List<GenreVO> retrieveGenreList() { // 장르조회
		List<GenreVO> genre = null;

		return genre;
	}

	public BookVO selectBookById(String id) { // 도서 상세 정보 조회
		BookVO book = new BookVO("A", "A", "A", "A", "A", "A", true, 1);

		return book;
	}

	public Vector<Vector<Object>> selectBookList(String keyfield, String keyword) { // 도서
																					// 전체
																					// 목록
																					// 조회
		Vector<Vector<Object>> books = new Vector();

		return books;
	}

	public void insertBook(List<BookVO> book) throws SQLException { // 도서 등록
		Connection conn = null;
		PreparedStatement pstmt = null;
		Result rs = null;

		try {
			conn = DBconn.getConnection();

			for (int i = 0; i < book.size(); i++) {
				StringBuilder sql = new StringBuilder();
				sql.append("insert into book_id,title,writer,publisher,ISBN,status,genre_id,publish_date				");
				sql.append("values (?,?,?,?,?,?,?,to_Date(?, 'YYYY/MM/DD'))							");

				pstmt = conn.prepareStatement(sql.toString());

				pstmt.setString(1, book.get(i).getBookId());
				pstmt.setString(2, book.get(i).getSubject());
				pstmt.setString(3, book.get(i).getWriter());
				pstmt.setString(4, book.get(i).getPublisher());
				pstmt.setString(5, book.get(i).getIsbn());
				pstmt.setString(6, book.get(i).getIsRent());
				pstmt.setInt(7, book.get(i).getGenre1());
				pstmt.setString(8, book.get(i).getPublishDate());

				pstmt.executeUpdate();

			}
		} finally {
			if (pstmt != null)
				pstmt.close();
			if (conn != null)
				conn.close();
		}

	}

	public void updateBook(BookVO book) throws SQLException { // 도서 정보 수정
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = DBconn.getConnection();

			StringBuilder sql = new StringBuilder();

			sql.append("update book																				");
			sql.append("set title=?,writer=?,publisher=?,ISBN=?,status=?,	genre_id=?,publish_date=to_Date(?, 'YYYY/MM/DD')			");
			sql.append("where book_id = ?                                            ");

			pstmt = conn.prepareStatement(sql.toString());

			pstmt.setString(1, book.getSubject());
			pstmt.setString(2, book.getWriter());
			pstmt.setString(3, book.getPublisher());
			pstmt.setString(4, book.getIsbn());
			pstmt.setInt(5, book.getGenre1());
			pstmt.setString(6, book.getPublishDate());
			pstmt.setString(7, book.getIsRent());
			pstmt.setString(8, book.getBookId());

			pstmt.executeUpdate();

		} finally {
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
