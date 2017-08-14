package com.dcone.dtss.dao;

import org.springframework.jdbc.core.JdbcTemplate;

import com.dcone.dtss.model.l_number;
/**
 * 
 * @author wrs
 *处理红包账户操作
 */
public class L_NumberDAO {
	/**
	 * 获取红包账户余额
	 * @param i 红包雨轮次
	 * @param jdbcTemplate
	 * @return 红包账户余额或者0
	 */
	public static int getTotalbyRound(int i,JdbcTemplate jdbcTemplate) {
		try {
			int w=jdbcTemplate.queryForInt("select total from luckynumber where round=?;",new Object[] {i});
			return w;
		}catch(Exception e) {
			System.out.println("获取红包账户余额失败哒！");
			e.printStackTrace();
		}
		return 0;
	}
	/**
	 * 红包总额计减
	 * @param round 轮次
	 * @param number 红包数额
	 * @return 1 成功, 0失败
	 */
	public static int luckyRain(int round, int number,JdbcTemplate jdbcTemplate) {
		try {
			int i=jdbcTemplate.update("update luckynumber set total=total-? where round=?;",new Object[] {number,round});
			if(i>0)
				return 1;
		}catch(Exception e) {
			System.out.println("减少红包账户总余额失败！");
			e.printStackTrace();
		}
		return 0;
	}
}
