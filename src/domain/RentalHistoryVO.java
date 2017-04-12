package domain;

public class RentalHistoryVO {
	private int rentalId;
	private String bookId;
	private String bookTitle;
	private String memberId;
	private String memberName;
	private String phone;
	private String state;
	private String rentDate;
	private String returnDate;
	private String dueDate;
	
	public RentalHistoryVO() {
		super();
	}

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

	@Override
	public String toString() {
		return "RentalHistoryVO [bookId=" + bookId + ", bookTitle=" + bookTitle + ", memberId=" + memberId
				+ ", memberName=" + memberName + ", phone=" + phone + ", state=" + state + ", rentDate=" + rentDate
				+ ", returnDate=" + returnDate + ", dueDate=" + dueDate + "]";
	}
	

}
