package domain;

public class BookVO {

	String bookId;
	String subject;
	String writer;
	String publisher;
	String publishDate;
	String isbn;
	boolean isRent;
	int genre1;
		
	public BookVO() {
		super();
	}

	public BookVO(String bookId, String subject, String writer, String publisher, String publishDate, String isbn, boolean isRent,
			int genre1) {
		super();
		this.bookId = bookId;
		this.subject = subject;
		this.writer = writer;
		this.publisher = publisher;
		this.publishDate = publishDate;
		this.isbn = isbn;
		this.isRent = isRent;
		this.genre1 = genre1;
	}

	public String getBookId() {
		return bookId;
	}

	public void setBookId(String bookId) {
		this.bookId = bookId;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(String publishDate) {
		this.publishDate = publishDate;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public boolean isRent() {
		return isRent;
	}

	public void setRent(boolean isRent) {
		this.isRent = isRent;
	}

	public int getGenre1() {
		return genre1;
	}

	public void setGenre1(int genre1) {
		this.genre1 = genre1;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}
	
}
