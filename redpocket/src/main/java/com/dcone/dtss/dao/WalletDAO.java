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
/**
 * 
 * @author wrs
 *处理用户钱包操作
 */
public class WalletDAO {
	/**
	 * 用户账户充值
	 * @param itcode 用户的员工号
	 * @param username 用户的姓名
	 * @param amount 充值的金额
	 * @param locale 时间区域
	 * @param jdbcTemplate spring jdbcTemplate 对象
	 * @return 1,成功;-2,用户姓名与员工号不匹配;-1,数据库操作错误
	 */
	public static int balance_add(String itcode,String username, int amount ,Locale locale, JdbcTemplate jdbcTemplate) {
		//RowMapper<dc_user> user_mapper = new BeanPropertyRowMapper<dc_user>(dc_user.class);
		try {
			//dc_user user = jdbcTemplate.queryForObject("select * from dc_user where itcode=? and username=?", user_mapper, new Object[] {itcode, username});
			
			dc_user user=UserDAO.getUserByItcode(itcode, jdbcTemplate);
			/*dc_user user2=UserDAO.getUserByName(username, jdbcTemplate);
			if(user.getUid()!=user2.getUid()) {
				System.out.println("用户名和itcode不匹配！");
				return -2;
			}*/
			//RowMapper<dc_wallet> wallet_mapper = new BeanPropertyRowMapper<dc_wallet>(dc_wallet.class);
			//dc_wallet wallet  = jdbcTemplate.queryForObject("select * from dc_wallet where uid  = ?", wallet_mapper, user.getUid());
			dc_wallet wallet=WalletDAO.getWalletByUid(user.getUid(), jdbcTemplate);
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
	        //需要向tradedao中加入充值一项
	        int i=TradeDAO.createTrade(wallet.getWid(), temp, amount, "充值", jdbcTemplate);
			if(i>0) {
				int j = walletAddByUid(wallet.getUid(), amount, jdbcTemplate);
				if(j>0) {
					return 1;
				}
			}
			
			
		}catch(Exception e) {
			return -1;
		}
		
		return 0;
	}
	/**
	 * 获取钱包
	 * @param uid 用户id
	 * @param jdbcTemplate
	 * @return 用户钱包
	 */
	public static dc_wallet getWalletByUid(int uid,JdbcTemplate jdbcTemplate) {
		RowMapper<dc_wallet> wallet_mapper=new BeanPropertyRowMapper<dc_wallet>(dc_wallet.class);
		try {
			dc_wallet wanted=jdbcTemplate.queryForObject("select * from dc_wallet where uid=?;", wallet_mapper,new Object[] {uid});
			return wanted;
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("uid错误,找不到用户钱包!");
		}
		return null;
	}
	/**
	 * 获取钱包
	 * @param wid 用户钱包id
	 * @param jdbcTemplate
	 * @return 用户钱包
	 */
	public  static dc_wallet getWalletByWid(int wid,JdbcTemplate jdbcTemplate) {
		RowMapper<dc_wallet> wallet_mapper=new BeanPropertyRowMapper<dc_wallet>(dc_wallet.class);
		try {
			dc_wallet wanted=jdbcTemplate.queryForObject("select * from dc_wallet where wid=?;", wallet_mapper,new Object[] {wid});
			return wanted;
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("wid错误,找不到用户钱包!");
		}
		return null;
	}
	/**
	 * 获取钱包
	 * @param itcode 用户员工号
	 * @param jdbcTemplate
	 * @return 用户钱包
	 */
	public static dc_wallet getWalletByItcode(String itcode,JdbcTemplate jdbcTemplate) {
		try {
			dc_user user=UserDAO.getUserByItcode(itcode, jdbcTemplate);
			dc_wallet wanted=getWalletByUid(user.getUid(),jdbcTemplate);
			return wanted;
		}
		catch(Exception e) {
			System.out.println("根据itcode寻找用户失败！");
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 初始化用户钱包
	 * @param uid 用户id
	 * @param jdbcTemplate
	 * @return true成功，false失败
	 */
	public static boolean initWallet(int uid,JdbcTemplate jdbcTemplate) {
		RowMapper<dc_wallet> wallet_mapper=new BeanPropertyRowMapper<dc_wallet>(dc_wallet.class);
		try {
			int result=jdbcTemplate.update("insert into dc_wallet(uid,amount) values (?,?);", wallet_mapper,new Object[] {uid,0});
			if(result>0) {
				return true;
			}
			else {
				System.out.println("插入错误！");
			}
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("uid错误,初始化用户钱包失败!");
		}
		return false;
	}
	/**
	 * 初始化用户钱包
	 * @param itcode 用户员工号
	 * @param jdbcTemplate
	 * @return true成功，false失败
	 */
	public static boolean initWallet(String itcode,JdbcTemplate jdbcTemplate) {
		try {
			dc_user user=UserDAO.getUserByItcode(itcode, jdbcTemplate);;
			boolean result=initWallet(user.getUid(),jdbcTemplate);
			if(result) {
				return true;
			}
			else {
				System.out.println("插入有错误！");
				}
		}catch(Exception e) {
			System.out.println("itcode错误,初始化用户钱包失败!");
			e.printStackTrace();
		}
		return false;
	}
	/**
	 * 钱包充值
	 * @param wid 钱包id
	 * @param number 数额
	 * @param jdbcTemplate
	 * @return 1成功，0失败
	 */
	public static int walletAddByWid(int wid,int number,JdbcTemplate jdbcTemplate) {
		try {
			int i=jdbcTemplate.update("update dc_wallet set amount=amount+? where wid=?",new Object[] {number,wid});
			if(i>0)
				return 1;
		}catch(Exception e) {
			System.out.println("增加钱包余额失败！");
			e.printStackTrace();
		}
		return 0;
	}
	/**
	 * 钱包充值
	 * @param uid 用户id
	 * @param number 数额
	 * @param jdbcTemplate
	 * @return 1成功，0失败
	 */
	public static int walletAddByUid(int uid,int number,JdbcTemplate jdbcTemplate) {
		dc_wallet wallet=getWalletByUid(uid,jdbcTemplate);
		return walletAddByWid(wallet.getWid(),number, jdbcTemplate);
	}
	/**
	 * 钱包充值
	 * @param itcode 用户员工号
	 * @param number 数额
	 * @param jdbcTemplate
	 * @return 1成功，0失败
	 */
	public static int walletAddByItcode(String itcode,int number,JdbcTemplate jdbcTemplate) {
		dc_wallet wallet=getWalletByItcode(itcode,jdbcTemplate);
		return walletAddByWid(wallet.getWid(),number, jdbcTemplate);
	}
	/**
	 * 获取全部钱包
	 * @param jdbcTemplate
	 * @return 全部用户钱包
	 */
	public static List<dc_wallet> getAllWallets(JdbcTemplate jdbcTemplate) {
		RowMapper<dc_wallet> wallet_mapper=new BeanPropertyRowMapper<dc_wallet>(dc_wallet.class);
		try {
			List<dc_wallet> wanted=jdbcTemplate.query("select * from dc_wallet;", wallet_mapper);
			return wanted;
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("错误,找不到任何用户钱包!");
		}
		return null;
	}
	/**
	 * 减少账户余额
	 * @param uid 用户id
	 * @param number 数额
	 * @param jdbcTemplate
	 * @return 1成功，0失败
	 */
	public static int walletMinusByUid(int uid,int number,JdbcTemplate jdbcTemplate) {
		try {
			int i=jdbcTemplate.update("update dc_wallet set amount=amount-? where uid=?;",new Object[] {number,uid});
			if(i>0)
				return 1;
		}catch(Exception e) {
			System.out.println("减少钱包余额失败哒！");
			e.printStackTrace();
		}
		return 0;
	}
	/**
	 * 减少账户余额
	 * @param wid 钱包id
	 * @param number 数额
	 * @param jdbcTemplate
	 * @return 1成功，0失败
	 */
	public static int walletMinusByWid(int wid,int number,JdbcTemplate jdbcTemplate) {
		dc_wallet temp=getWalletByWid(wid,jdbcTemplate);
		return walletMinusByUid(temp.getUid(),number,jdbcTemplate);
	}
	/**
	 * 减少账户余额
	 * @param itcode 员工号
	 * @param number 数额
	 * @param jdbcTemplate
	 * @return 1成功，0失败
	 */
	public static int walletMinusByItcode(String itcode,int number,JdbcTemplate jdbcTemplate) {
		dc_wallet temp=getWalletByItcode(itcode,jdbcTemplate);
		return walletMinusByUid(temp.getUid(),number,jdbcTemplate);
	}
}
