package com.dcone.dtss.model;
/**
 * 
 * @author wrs
 *��¼����˻�
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
	 * @param l_id ����˻�id
	 * @param round ������ִ�
	 * @param total ����˻����
	 * @param tips ��ע
	 */
	public l_number(int l_id, int round, int total,String tips) {
		super();
		this.lid = l_id;
		this.round = round;
		this.total = total;
		this.tips=tips;
	}
	/**
	 * �������
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
