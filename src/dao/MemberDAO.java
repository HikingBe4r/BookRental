package dao;

import java.util.List;
import java.util.Vector;

import domain.MemberVO;

public class MemberDAO {
	// 회원등록
	public void insertMember(MemberVO member) {
		
	}
	
	// 회원정보수정
	public void updateMember(MemberVO member) {
		
	}
	
	// 전체회원 조회
	public Vector<Vector<Object>> retrieveAllMemberList (){
		Vector<Vector<Object>> memberList = null; 	// 이거 고치세요.
		
		return memberList;
	}
	
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
