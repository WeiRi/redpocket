package com.dcone.dtss.model;
/**
 * 
 * @author wrs
 *记录红包账户
 */
public class l_number {
	int lid;
	int round;
	int total;
	String tips;
	public String getTips() {
		return tips;
	}
	public void setTips(String tips) {
		this.tips = tips;
	}
	public l_number() {}
	/**
	 * 
	 * @param l_id 红包账户id
	 * @param round 红包雨轮次
	 * @param total 红包账户余额
	 * @param tips 备注
	 */
	public l_number(int l_id, int round, int total,String tips) {
		super();
		this.lid = l_id;
		this.round = round;
		this.total = total;
		this.tips=tips;
	}
	/**
	 * 串行输出
	 */
	@Override
	public String toString() {
		return "LuckNumber [l_id=" + lid + ", round=" + round + ", total=" + total + ", tips=" + tips + "]";
	}
	public int getLid() {
		return lid;
	}
	public void setLid(int l_id) {
		this.lid = l_id;
	}
	public int getRound() {
		return round;
	}
	public void setRound(int round) {
		this.round = round;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	
	
}
