package com.dcone.dtss.dao;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.dcone.dtss.model.dc_user;
import com.dcone.dtss.model.dc_user_wallet;

public class UserWalletDAO {
	public dc_user_wallet getWallInfoByUser(dc_user user,JdbcTemplate jdbctemplate) {
		RowMapper<dc_user_wallet> userwalletmapper=new BeanPropertyRowMapper<dc_user_wallet>(dc_user_wallet.class);
		try {
			dc_user_wallet wanted=jdbctemplate.queryForObject("select * from dc_user_wallet where uid =?;", userwalletmapper,new Object[] {user.getUid()});
			return wanted;
		}catch(Exception e) { 
			System.out.println("查询用户钱包表失败！");
			e.printStackTrace();
		}
		return null;
	}
	
	public dc_user_wallet getWallInfoByUid(int uid,JdbcTemplate jdbctemplate) {
		dc_user user = UserDAO.getUserByUid(uid, jdbctemplate);
		return getWallInfoByUser(user, jdbctemplate);
	}
	public dc_user_wallet getWallInfoByItcode(String itcode,JdbcTemplate jdbctemplate) {
		dc_user user = UserDAO.getUserByItcode(itcode, jdbctemplate);
		return getWallInfoByUser(user, jdbctemplate);
	}
	
	public List<dc_user_wallet> getAllWallInfoByUser(JdbcTemplate jdbctemplate) {
		RowMapper<dc_user_wallet> userwalletmapper=new BeanPropertyRowMapper<dc_user_wallet>(dc_user_wallet.class);
		try {
			List<dc_user_wallet> wanted=jdbctemplate.query("select * from dc_user_wallet;", userwalletmapper);
			return wanted;
		}catch(Exception e) {
			System.out.println("获取全部用户钱包信息失败！");
			e.printStackTrace();
		}
		return null;
	}
}
