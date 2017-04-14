package dao;

import java.util.List;
import java.util.Vector;

import domain.BookVO;
import domain.GenreVO;

public class BookDAO {

	public List<GenreVO> retrieveGenreList() {			// 장르조회
		List<GenreVO> genre = null;
		
		return genre;
	}
	
	public BookVO selectBookById(String id) {			// 도서 상세 정보 조회
		BookVO book = new BookVO("A", "A", "A", "A", "A", "A", true, 1);
		
		return book;
	}
	
	public Vector<Vector<Object>> selectBookList(String keyfield, String keyword) {			// 도서 전체 목록 조회
		Vector<Vector<Object>> books = new Vector();
		
		return books;
	}
		
	public void insertBook(List<BookVO> book) {			// 도서 등록
		
	}
	
	public void updateBook(BookVO book) {			// 도서 정보 수정
		
	}
	
	public void deleteBookList(List<String> idList) {			// 도서 일괄 삭제
		
	}
}
