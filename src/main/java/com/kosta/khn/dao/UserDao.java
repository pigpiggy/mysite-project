package com.kosta.khn.dao;

import com.kosta.khn.vo.UserVo;

public interface UserDao {

	public int update(UserVo vo);
	public int insert(UserVo vo);
	public UserVo getUser(String email, String password);
	public UserVo getUser(int no);
	public String idCheck(String email); //아이디 중복체크
	
}
