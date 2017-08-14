package com.dcone.dtss.model;
/**
 * 
 * @author wrs
 *记录用户钱包
 */
public class dc_wallet {
	int uid;
	int wid;
	int amount;
	public dc_wallet() {}
	/**
	 * 
	 * @param uid 用户id
	 * @param wid 钱包id
	 * @param amount 钱包余额
	 */
	public dc_wallet(int uid, int wid,int amount) {
		this.uid = uid;
		this.wid = wid;
		this.amount = amount;
	}
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public int getWid() {
		return wid;
	}
	public void setWid(int wid) {
		this.wid = wid;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	/**
	 * 串行输出
	 */
	@Override
	public String toString() {
		return "dc_wallet [uid=" + uid + ", wid=" + wid + ", amount=" + amount + "]";
	}
	
	
	
}
