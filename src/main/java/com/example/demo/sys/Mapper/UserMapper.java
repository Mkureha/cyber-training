package com.example.demo.sys.Mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.demo.sys.Domain.UserVO;

@Mapper
public interface UserMapper {

	public UserVO login(UserVO user) throws Exception;

	public UserVO loginFailCnt(UserVO user) throws Exception;

	public UserVO loginReset(String USER_ID) throws Exception;

	public int Usercount() throws Exception;

	public List<UserVO> listpage(@Param("startIndex") int startIndex, @Param("pageSize") int pageSize) throws Exception;

	public int UserInsert(UserVO user) throws Exception;

	public int UserModify(UserVO user) throws Exception;

	public int UserDelete(String USER_ID) throws Exception;
	
	public int UserReset(String USER_ID) throws Exception;
}