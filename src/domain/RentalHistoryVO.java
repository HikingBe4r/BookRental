package domain;

public class RentalHistoryVO {
	// 인스턴스 변수
	private int rentalId;		// 대여 일련번호
	private String bookId;		// 도서ID
	private String bookTitle;	// 도서명
	private String memberId;	// 회원ID
	private String memberName;	// 회원 이름
	private String phone;		// 회원 연락처
	private String state;		// 구분
	private String rentDate;	// 대여일
	private String returnDate;	// 반납일
	private String dueDate;		// 반납예정일
	
	// 디폴트 생성자
	public RentalHistoryVO() {
		super();
	} 
	
	// 매개변수 생성자
	public RentalHistoryVO(int rentalId, String bookId, String bookTitle, String memberId, String memberName, String phone,
			String state, String rentDate, String returnDate, String dueDate) {
		super();
		this.rentalId = rentalId;
		this.bookId = bookId;
		this.bookTitle = bookTitle;
		this.memberId = memberId;
		this.memberName = memberName;
		this.phone = phone;
		this.state = state;
		this.rentDate = rentDate;
		this.returnDate = returnDate;
		this.dueDate = dueDate;
	}

	
	// getter, setter 메소드
	public int getRentalId() {
		return rentalId;
	}

	public void setRentalId(int rentalId) {
		this.rentalId = rentalId;
	}
	
	public String getBookId() {
		return bookId;
	}
	
	public void setBookId(String bookId) {
		this.bookId = bookId;
	}

	public String getBookTitle() {
		return bookTitle;
	}

	public void setBookTitle(String bookTitle) {
		this.bookTitle = bookTitle;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getRentDate() {
		return rentDate;
	}

	public void setRentDate(String rentDate) {
		this.rentDate = rentDate;
	}

	public String getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(String returnDate) {
		this.returnDate = returnDate;
	}

	public String getDueDate() {
		return dueDate;
	}

	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}

	//toString 메소드 오버라이딩
	@Override
	public String toString() {
		return "RentalHistoryVO [bookId=" + bookId + ", bookTitle=" + bookTitle + ", memberId=" + memberId
				+ ", memberName=" + memberName + ", phone=" + phone + ", state=" + state + ", rentDate=" + rentDate
				+ ", returnDate=" + returnDate + ", dueDate=" + dueDate + "]";
	}
	

}
