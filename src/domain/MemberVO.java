package domain;

public class MemberVO {
	String id;
	String name;
	String phone;
	String birthDay;
	Boolean withdraw;
	
	public MemberVO() {
		super();
	}

	public MemberVO(String id, String name, String phone, String birthDay, Boolean withdraw) {
		super();
		this.id = id;
		this.name = name;
		this.phone = phone;
		this.birthDay = birthDay;
		this.withdraw = withdraw;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getBirthDay() {
		return birthDay;
	}
	public void setBirthDay(String birthDay) {
		this.birthDay = birthDay;
	}
	public Boolean getWithdraw() {
		return withdraw;
	}
	public void setWithdraw(Boolean withdraw) {
		this.withdraw = withdraw;
	}
	
	
}
