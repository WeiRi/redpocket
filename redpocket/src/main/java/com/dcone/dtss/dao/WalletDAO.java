package com.dcone.dtss.dao;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.dcone.dtss.model.dc_trade;
import com.dcone.dtss.model.dc_user;
import com.dcone.dtss.model.dc_wallet;

public class WalletDAO {
	/**
	 * �û��˻���ֵ
	 * @param itcode �û���Ա����
	 * @param username �û�������
	 * @param amount ��ֵ�Ľ��
	 * @param locale ʱ������
	 * @param jdbcTemplate spring jdbcTemplate ����
	 * @return 1,�ɹ�;-2,�û�������Ա���Ų�ƥ��
	 */
	public static int balance_add(String itcode,String username, int amount ,Locale locale, JdbcTemplate jdbcTemplate) {
		RowMapper<dc_user> user_mapper = new BeanPropertyRowMapper<dc_user>(dc_user.class);
		try {
			//dc_user user = jdbcTemplate.queryForObject("select * from dc_user where itcode=? and username=?", user_mapper, new Object[] {itcode, username});
			dc_user user=UserDAO.getUserByItcode(itcode, jdbcTemplate);
			dc_user user2=UserDAO.getUserByName(username, jdbcTemplate);
			if(user.getUid()!=user2.getUid()) {
				System.out.println("�û�����itcode��ƥ�䣡");
				return -2;
			}
			RowMapper<dc_wallet> wallet_mapper = new BeanPropertyRowMapper<dc_wallet>(dc_wallet.class);
			dc_wallet wallet  = jdbcTemplate.queryForObject("select * from dc_wallet where uid  = ?", wallet_mapper, user.getUid());
			
			Date date = new Date();
			System.out.println(date.toString());
			//DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
			//String formattedDate = dateFormat.format(date);
			//int i = jdbcTemplate.update("insert into dc_trade values(null, ?,?,?);", new Object[] {wallet.getWid(),amount,formattedDate});
			String url1=" yyyy-MM-dd";
	        String url2=" HH:mm:ss";
	        SimpleDateFormat fmtDate1 = new SimpleDateFormat(url1);
	        SimpleDateFormat fmtDate2 = new SimpleDateFormat(url2);
	        String temp=fmtDate1.format(date)+fmtDate2.format(date);
	        int i=jdbcTemplate.update("insert into dc_trade(wid,volume,tradetime,tip) values(?,?,?,?);",new Object[] {wallet.getWid(),amount,temp,"��ֵ"});
			if(i>0) {
				int j = jdbcTemplate.update("update dc_wallet set amount = amount + ? where uid = ?",new Object[] {amount,wallet.getUid()});
				if(j>0) {
					return 1;
				}
			}
			
			
		}catch(Exception e) {
			return -1;
		}
		
		return 0;
	}
	public static dc_wallet getWalletByUid(int uid,JdbcTemplate jdbcTemplate) {
		RowMapper<dc_wallet> wallet_mapper=new BeanPropertyRowMapper<dc_wallet>(dc_wallet.class);
		try {
			dc_wallet wanted=jdbcTemplate.queryForObject("select * from dc_wallet where uid=?;", wallet_mapper,new Object[] {uid});
			return wanted;
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("uid����,�Ҳ����û�Ǯ��!");
		}
		return null;
	}
	public  static dc_wallet getWalletByWid(int wid,JdbcTemplate jdbcTemplate) {
		RowMapper<dc_wallet> wallet_mapper=new BeanPropertyRowMapper<dc_wallet>(dc_wallet.class);
		try {
			dc_wallet wanted=jdbcTemplate.queryForObject("select * from dc_wallet where wid=?;", wallet_mapper,new Object[] {wid});
			return wanted;
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("wid����,�Ҳ����û�Ǯ��!");
		}
		return null;
	}
	public static dc_wallet getWalletByItcode(String itcode,JdbcTemplate jdbcTemplate) {
		try {
			dc_user user=UserDAO.getUserByItcode(itcode, jdbcTemplate);
			dc_wallet wanted=getWalletByUid(user.getUid(),jdbcTemplate);
			return wanted;
		}
		catch(Exception e) {
			System.out.println("����itcodeѰ���û�ʧ�ܣ�");
			e.printStackTrace();
		}
		return null;
	}
	
	public static boolean initWallet(int uid,JdbcTemplate jdbcTemplate) {
		RowMapper<dc_wallet> wallet_mapper=new BeanPropertyRowMapper<dc_wallet>(dc_wallet.class);
		try {
			int result=jdbcTemplate.update("insert into dc_wallet(uid,amount) values (?,?);", wallet_mapper,new Object[] {uid,0});
			if(result>0) {
				return true;
			}
			else {
				System.out.println("�������");
			}
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("uid����,��ʼ���û�Ǯ��ʧ��!");
		}
		return false;
	}
	
	public static boolean initWallet(String itcode,JdbcTemplate jdbcTemplate) {
		try {
			dc_user user=UserDAO.getUserByItcode(itcode, jdbcTemplate);;
			boolean result=initWallet(user.getUid(),jdbcTemplate);
			if(result) {
				return true;
			}
			else {
				System.out.println("�����д���");
				}
		}catch(Exception e) {
			System.out.println("itcode����,��ʼ���û�Ǯ��ʧ��!");
			e.printStackTrace();
		}
		return false;
	}
	
	public static int walletAddByWid(int wid,int number,JdbcTemplate jdbcTemplate) {
		try {
			int i=jdbcTemplate.update("update dc_wallet set amount=amount+? where wid=?",new Object[] {number,wid});
			if(i>0)
				return 1;
		}catch(Exception e) {
			System.out.println("����Ǯ�����ʧ�ܣ�");
			e.printStackTrace();
		}
		return 0;
	}
	public static int walletAddByUid(int uid,int number,JdbcTemplate jdbcTemplate) {
		dc_wallet wallet=getWalletByUid(uid,jdbcTemplate);
		return walletAddByWid(wallet.getWid(),number, jdbcTemplate);
	}
	public static int walletAddByItcode(String itcode,int number,JdbcTemplate jdbcTemplate) {
		dc_wallet wallet=getWalletByItcode(itcode,jdbcTemplate);
		return walletAddByWid(wallet.getWid(),number, jdbcTemplate);
	}
	public static List<dc_wallet> getAllWallets(JdbcTemplate jdbcTemplate) {
		RowMapper<dc_wallet> wallet_mapper=new BeanPropertyRowMapper<dc_wallet>(dc_wallet.class);
		try {
			List<dc_wallet> wanted=jdbcTemplate.query("select * from dc_wallet;", wallet_mapper);
			return wanted;
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("����,�Ҳ����κ��û�Ǯ��!");
		}
		return null;
	}
}
