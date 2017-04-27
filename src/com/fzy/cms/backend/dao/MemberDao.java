package com.fzy.cms.backend.dao;

import com.fzy.cms.backend.VO.PagerVO;
import com.fzy.cms.backend.mode1.Member;

public interface MemberDao {
	public void addMember(Member member);
	public void delMembers(String[] ids);
	public void updateMember(Member member);
	public void updatePassword(int memberId,String oldPass,String newPass);
	public Member findMemberById(int id);
	public Member findMemberByNickname(String nickname);
	public PagerVO findAllMembers();
}
