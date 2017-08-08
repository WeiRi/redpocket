package com.dcone.dtss.dao;

import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.dcone.dtss.model.*;
/**
 * 
 * @author wrs
 *��������Ϣ
 */
public class TradeDAO {
	/**
	 * ��ȡ�û�ȫ������
	 * @param uid �û�id
	 * @param jdbcTemplate
	 * @return �û�ȫ������
	 */
	public List<dc_trade> getTradesByUid(int uid,JdbcTemplate jdbcTemplate){
		RowMapper<dc_trade> trade_mapper=new BeanPropertyRowMapper<dc_trade>(dc_trade.class);
		try {
			dc_wallet wallet= WalletDAO.getWalletByUid(uid, jdbcTemplate);
			List<dc_trade> wanted=jdbcTemplate.query("select * from dc_trade where wid=?;", trade_mapper,new Object[] {wallet.getWid()});
			return wanted;
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("uid����,�Ҳ����û�!");
		}
		return null;
	}
	/**
	 * ��ȡ�û�ȫ������
	 * @param itcode Ա����
	 * @param jdbcTemplate
	 * @return �û�ȫ������
	 */
	public List<dc_trade> getTradesByItcode(String itcode,JdbcTemplate jdbcTemplate){
		dc_user user=UserDAO.getUserByItcode(itcode, jdbcTemplate);
		return getTradesByUid(user.getUid(), jdbcTemplate);
	}
	/**
	 * ��ȡ�û�ȫ������
	 * @param username �û���
	 * @param jdbcTemplate
	 * @return �û�ȫ������
	 */
	public List<dc_trade> getTradesByUser(String username,JdbcTemplate jdbcTemplate){
		dc_user user=UserDAO.getUserByName(username, jdbcTemplate);
		return getTradesByUid(user.getUid(), jdbcTemplate);
	}
	/**
	 * ��ȡһ��ʱ�����û�ȫ������
	 * @param uid �û�id
	 * @param start ��ʼʱ��
	 * @param end ����ʱ��
	 * @param jdbcTemplate
	 * @return �û����ֽ���
	 */
	public List<dc_trade> getTimeTradesByUid(int uid,String start,String end,JdbcTemplate jdbcTemplate){
		RowMapper<dc_trade> trade_mapper=new BeanPropertyRowMapper<dc_trade>(dc_trade.class);
		try {
			dc_wallet wallet= WalletDAO.getWalletByUid(uid, jdbcTemplate);
			List<dc_trade> wanted=jdbcTemplate.query("select * from dc_trade where wid=? and tradetime>? and tradetime<?;", trade_mapper,new Object[] {wallet.getWid(),start,end});
			int i=0;
			System.out.println("start is "+start+",end is "+end);
			for(dc_trade temp:wanted) {
				System.out.println("i is "+i);
				i++;
				temp.toString();
			}
			return wanted;
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("uid����,�Ҳ����û���ؽ��׼�¼!");
		}
		return null;
	}
	/**
	 * ��ȡ�û�һ��ʱ��ȫ������
	 * @param itcode Ա����
	 * @param start ��ʼʱ��
	 * @param end ����ʱ��
	 * @param jdbcTemplate
	 * @return �û����ֽ���
	 */
	public List<dc_trade> getTimeTradesByItcode(String itcode,String start,String end,JdbcTemplate jdbcTemplate){
		dc_user user=UserDAO.getUserByItcode(itcode, jdbcTemplate);
		return getTimeTradesByUid(user.getUid(),start,end, jdbcTemplate);
	}
	/**
	 * ��ȡ�û�һ��ʱ��ȫ������
	 * @param username �û���
	 * @param start ��ʼʱ��
	 * @param end ����ʱ��
	 * @param jdbcTemplate
	 * @return �û����ֽ���
	 */
	public List<dc_trade> getTimeTradesByUser(String username,String start,String end,JdbcTemplate jdbcTemplate){
		dc_user user=UserDAO.getUserByName(username, jdbcTemplate);
		return getTimeTradesByUid(user.getUid(),start,end, jdbcTemplate);
	}
	
	/**
	 * �жϽ����ܷ����
	 * @param wid Ǯ��id
	 * @param amount ���׶�
	 * @param jdbcTemplate
	 * @return true���ԣ�false����
	 */
	private static boolean preTrade(int wid,int amount,JdbcTemplate jdbcTemplate) {
		RowMapper<dc_wallet> wallet_mapper=new BeanPropertyRowMapper<dc_wallet>(dc_wallet.class);
		try {
			dc_wallet wanted=jdbcTemplate.queryForObject("select * from dc_wallet where wid=?;", wallet_mapper,new Object[] {wid});
			if(wanted.getAmount()>=amount)
			return true;
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("wid����,�Ҳ����û�Ǯ��!");
		}
		return false;
	}
	/**
	 * �������ף�����
	 * @param wid1 �����û�Ǯ��id
	 * @param wid2 �����ͽ�ĿǮ��id
	 * @param amount ����
	 * @param date ʱ��
	 * @param memo ��ע
	 * @param jdbcTemplate
	 * @return true�ɹ���falseʧ��
	 */
	public static boolean createTrade(int wid1,int wid2, int amount, String date , String memo,JdbcTemplate jdbcTemplate) {
		if(preTrade(wid1,amount, jdbcTemplate)) {
			//д�뽻������
			int i=jdbcTemplate.update("insert into dc_trade(wid,volume,tradetime,tip) values(?,?,?,?)",new Object[] {wid2,amount,date,memo});
			if(i>0) {
				i=jdbcTemplate.update("insert into dc_trade(wid,volume,tradetime,tip) values(?,?,?,?)",new Object[] {wid1,amount,date,"ת��Ǯ:"+memo});
				i=jdbcTemplate.update("update dc_wallet set amount=amount-? where wid =?;",new Object[] {amount,wid1});
				i=jdbcTemplate.update("update dc_wallet set amount=amount+? where wid =?;",new Object[] {amount,wid2});
				if(i>0)
					return true;
			}
		}
		return false;
	}
	/**
	 * �������������
	 * @param wid Ǯ��id
	 * @param lucknumber �������
	 * @param time ʱ��
	 * @param tip ��ע
	 * @param jdbcTemplate
	 * @return 1�ɹ���0ʧ��
	 */
	public static int createTrade(int wid,int lucknumber, String time, String tip,JdbcTemplate jdbcTemplate) {
		
		return 0;
	}
	/**
	 * �������ף���ֵ
	 * @param wid Ǯ��id
	 * @param time ʱ��
	 * @param amount ����
	 * @param tip ��ע
	 * @param jdbcTemplate
	 * @return 1�ɹ���0ʧ��
	 */
	public static int createTrade(int wid,String time,int amount,String tip,JdbcTemplate jdbcTemplate) {
		try {
			int i=jdbcTemplate.update("insert into dc_trade(wid,volume,tradetime,tip) values(?,?,?,?);",new Object[] {wid,amount,time,tip});
		}catch(Exception e) {
			System.out.println("������ֵ��¼ʧ�ܣ�");
			e.printStackTrace();
		}
		return 0;
	}
}
