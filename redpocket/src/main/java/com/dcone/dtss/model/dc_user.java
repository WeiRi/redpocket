package com.dcone.dtss.model;
/**
 * 
 * @author wrs
 *记录用户
 */
public class dc_user {
	int uid;
	String itcode;
	String username;
	int wlock;
	String password;
	String portrait;
	public dc_user() {}
	/**
	 * 
	 * @param uid 用户id
	 * @param itcode 用户员工号
	 * @param username 用户名
	 * @param wlock 用户钱包是否被锁定
	 */
	public dc_user(int uid, String itcode, String username, int wlock, String password, String portrait) {
		super();
		this.uid = uid;
		this.itcode = itcode;
		this.username = username;
		this.wlock = wlock;
		this.password = password;
		this.portrait = portrait;
	}
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public String getItcode() {
		return itcode;
	}
	public int getWlock() {
		return wlock;
	}
	public void setWlock(int wlock) {
		this.wlock = wlock;
	}
	public void setItcode(String itcode) {
		this.itcode = itcode;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPortrait() {
		return portrait;
	}
	public void setPortrait(String portrait) {
		this.portrait = portrait;
	}
	/**
	 * �������
	 */
	@Override
	public String toString() {
		return "dc_user [uid=" + uid + ", itcode=" + itcode + ", username=" + username + ",wlock" + wlock + "]";
	}
	
	
	
}
