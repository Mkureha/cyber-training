package com.example.demo.sys.Domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class UserVO {
	
	@Id @GeneratedValue
	private String USER_ID;

	private String USER_PW;
	private String MAIL_ADR;
	private int USER_AUTH;
	private int LOGIN_LOCK_FLG;
	private int LOGIN_FAIL_CNT;
	private String LOGIN_YMD;
	private String USER_CRT_YMD;
	private String USER_MOD_YMD;
	
	public void incFailCnt() {
		this.LOGIN_FAIL_CNT++;
		if(this.LOGIN_FAIL_CNT > 4) {
			this.LOGIN_LOCK_FLG = 1;
		}
	}
}
