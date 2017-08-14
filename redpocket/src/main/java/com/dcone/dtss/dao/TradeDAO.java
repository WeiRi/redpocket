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
 *处理交易信息
 */
public class TradeDAO {
	/**
	 * 获取用户全部交易
	 * @param uid 用户id
	 * @param jdbcTemplate
	 * @return 用户全部交易
	 */
	public List<dc_trade> getTradesByUid(int uid,JdbcTemplate jdbcTemplate){
		RowMapper<dc_trade> trade_mapper=new BeanPropertyRowMapper<dc_trade>(dc_trade.class);
		try {
			dc_wallet wallet= WalletDAO.getWalletByUid(uid, jdbcTemplate);
			List<dc_trade> wanted=jdbcTemplate.query("select * from dc_trade where wid=?;", trade_mapper,new Object[] {wallet.getWid()});
			return wanted;
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("uid错误,找不到用户!");
		}
		return null;
	}
	/**
	 * 获取用户全部交易
	 * @param itcode 员工号
	 * @param jdbcTemplate
	 * @return 用户全部交易
	 */
	public List<dc_trade> getTradesByItcode(String itcode,JdbcTemplate jdbcTemplate){
		dc_user user=UserDAO.getUserByItcode(itcode, jdbcTemplate);
		return getTradesByUid(user.getUid(), jdbcTemplate);
	}
	/**
	 * 获取用户全部交易
	 * @param username 用户名
	 * @param jdbcTemplate
	 * @return 用户全部交易
	 */
	public List<dc_trade> getTradesByUser(String username,JdbcTemplate jdbcTemplate){
		dc_user user=UserDAO.getUserByName(username, jdbcTemplate);
		return getTradesByUid(user.getUid(), jdbcTemplate);
	}
	/**
	 * 获取一段时间内用户全部交易
	 * @param uid 用户id
	 * @param start 开始时间
	 * @param end 结束时间
	 * @param jdbcTemplate
	 * @return 用户部分交易
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
			System.out.println("uid错误,找不到用户相关交易记录!");
		}
		return null;
	}
	/**
	 * 获取用户一段时间全部交易
	 * @param itcode 员工号
	 * @param start 开始时间
	 * @param end 结束时间
	 * @param jdbcTemplate
	 * @return 用户部分交易
	 */
	public List<dc_trade> getTimeTradesByItcode(String itcode,String start,String end,JdbcTemplate jdbcTemplate){
		dc_user user=UserDAO.getUserByItcode(itcode, jdbcTemplate);
		return getTimeTradesByUid(user.getUid(),start,end, jdbcTemplate);
	}
	/**
	 * 获取用户一段时间全部交易
	 * @param username 用户名
	 * @param start 开始时间
	 * @param end 结束时间
	 * @param jdbcTemplate
	 * @return 用户部分交易
	 */
	public List<dc_trade> getTimeTradesByUser(String username,String start,String end,JdbcTemplate jdbcTemplate){
		dc_user user=UserDAO.getUserByName(username, jdbcTemplate);
		return getTimeTradesByUid(user.getUid(),start,end, jdbcTemplate);
	}
	
	/**
	 * 判断用户支出交易能否进行
	 * @param wid 钱包id
	 * @param amount 交易额
	 * @param jdbcTemplate
	 * @return true可以，false不行
	 */
	public static boolean preTrade(int wid,int amount,JdbcTemplate jdbcTemplate) {
		RowMapper<dc_wallet> wallet_mapper=new BeanPropertyRowMapper<dc_wallet>(dc_wallet.class);
		try {
			dc_wallet wanted=jdbcTemplate.queryForObject("select * from dc_wallet where wid=?;", wallet_mapper,new Object[] {wid});
			if(wanted.getAmount()>=amount)
			return true;
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("wid错误,找不到用户钱包!");
		}
		return false;
	}
	/**
	 * 创建交易：用户账户转出
	 * @param wid 打赏用户钱包id
	 * @param amount 数额
	 * @param date 时间
	 * @param memo 备注
	 * @param jdbcTemplate
	 * @return true成功，false失败
	 */
	public static boolean createTrade( String date ,int wid, int amount, String memo,JdbcTemplate jdbcTemplate) {
		if(preTrade(wid,amount, jdbcTemplate)) {
			//写入交易数据
			int i = jdbcTemplate.update("insert into dc_trade(wid,volume,tradetime,tip) values(?,?,?,?)",new Object[] { wid, amount, date, memo });
			int j = WalletDAO.walletMinusByWid(wid, amount, jdbcTemplate);
			if (i*j > 0)
				return true;
		}
		return false;
	}
	/**
	 * 创建发红包交易
	 * @param wid 钱包id
	 * @param lucknumber 红包数额
	 * @param time 时间
	 * @param tip 备注
	 * @param jdbcTemplate
	 * @return 1成功，0失败
	 */
	public static int createTrade(int wid,int lucknumber, String time, String tip,JdbcTemplate jdbcTemplate) {
		
		return 0;
	}
	/**
	 * 创建交易，用户账户转入
	 * @param wid 钱包id
	 * @param time 时间
	 * @param amount 数额
	 * @param tip 备注
	 * @param jdbcTemplate
	 * @return 1成功，0失败
	 */
	public static int createTrade(int wid,String time,int amount,String tip,JdbcTemplate jdbcTemplate) {
		try {
			int i=jdbcTemplate.update("insert into dc_trade(wid,volume,tradetime,tip) values(?,?,?,?);",new Object[] {wid,amount,time,tip});
			if(i>0)
				return 1;
		}catch(Exception e) {
			System.out.println("创建充值记录失败！");
			e.printStackTrace();
		}
		return 0;
	}
}
