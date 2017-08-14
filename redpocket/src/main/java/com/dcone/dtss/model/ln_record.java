package com.dcone.dtss.model;
/**
 * 
 * @author wrs
 * 此数据结构记录红包发放记录
 */
public class ln_record {
	int rid;
	int wid;
	int lucky_number;
	int round;
	String tradetime;
	/**
	 * 串行输出
	 */
	@Override
	public String toString() {
		return "LuckyNumberRecord [rid=" + rid + ", wid=" + wid + ", lucky_number=" + lucky_number + ", round=" + round
				+ ",tradetime="+tradetime+"]";
	}
	public String getTradetime() {
		return tradetime;
	}
	public void setTradetime(String tradetime) {
		this.tradetime = tradetime;
	}
	public ln_record() {}
	/**
	 * 
	 * @param r_id 流水号
	 * @param wid 钱包id
	 * @param luck_number 红包数额
	 * @param round 红包雨轮次
	 */
	public ln_record(int r_id, int wid, int luck_number, int round,String tradetime) {
		super();
		this.rid = r_id;
		this.wid = wid;
		this.lucky_number = luck_number;
		this.round = round;
		this.tradetime=tradetime;
	}
	public int getRid() {
		return rid;
	}
	public void setRid(int r_id) {
		this.rid = r_id;
	}
	public int getWid() {
		return wid;
	}
	public void setWid(int wid) {
		this.wid = wid;
	}
	public int getLucky_number() {
		return lucky_number;
	}
	public void setLucky_number(int luck_number) {
		this.lucky_number = luck_number;
	}
	public int getRound() {
		return round;
	}
	public void setRound(int round) {
		this.round = round;
	}
	
}
