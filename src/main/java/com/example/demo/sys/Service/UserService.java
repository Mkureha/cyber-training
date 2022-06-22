package com.example.demo.sys.Service;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.sys.Domain.UserVO;
import com.example.demo.sys.Mapper.UserMapper;

@Service
public class UserService {

	@Autowired
	UserMapper mUserMapper;

	public UserVO loginService(UserVO user) throws Exception {
		return mUserMapper.login(user);
	}
	
	public UserVO loginFailCnt(UserVO user) throws Exception {
		return mUserMapper.loginFailCnt(user);
	}

	public UserVO loginReset(String USER_ID) throws Exception {
		return mUserMapper.loginReset(USER_ID);
	}

	public void logout(HttpSession session) {
	}

	public int Usercount() throws Exception {
		return mUserMapper.Usercount();
	}
	
	public List<UserVO> listpage(int startIndex, int pageSize) throws Exception {
		return mUserMapper.listpage(startIndex, pageSize);
	}

	public int UserInsertService(UserVO user) throws Exception {
		return mUserMapper.UserInsert(user);
	}

	public void InsertOK() {
	}
	
	public int UserModifyService(UserVO user) throws Exception {
		return mUserMapper.UserModify(user);
	}

	public void ModifyOK() {
	}
	
	public int UserDeleteService(String USER_ID) throws Exception {
		return mUserMapper.UserDelete(USER_ID);
	}

	public int UserResetService(String USER_ID) throws Exception {
		return mUserMapper.UserReset(USER_ID);
	}

}
