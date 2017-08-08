package com.dcone.dtss.dao;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.dcone.dtss.model.dc_user;
import com.dcone.dtss.model.dc_user_wallet;
/**
 * 
 * @author wrs
 *���û�Ǯ����ͼ�в�ѯ
 */
public class UserWalletDAO {
	/**
	 * ��ȡ�û�Ǯ����Ϣ
	 * @param user �û�
	 * @param jdbctemplate
	 * @return �û�Ǯ����Ϣ
	 */
	public dc_user_wallet getWallInfoByUser(dc_user user,JdbcTemplate jdbctemplate) {
		RowMapper<dc_user_wallet> userwalletmapper=new BeanPropertyRowMapper<dc_user_wallet>(dc_user_wallet.class);
		try {
			dc_user_wallet wanted=jdbctemplate.queryForObject("select * from dc_user_wallet where uid =?;", userwalletmapper,new Object[] {user.getUid()});
			return wanted;
		}catch(Exception e) { 
			System.out.println("��ѯ�û�Ǯ����ʧ�ܣ�");
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * ��ȡ�û�Ǯ����Ϣ
	 * @param uid �û�id
	 * @param jdbctemplate
	 * @return �û�Ǯ����Ϣ
	 */
	public dc_user_wallet getWallInfoByUid(int uid,JdbcTemplate jdbctemplate) {
		dc_user user = UserDAO.getUserByUid(uid, jdbctemplate);
		return getWallInfoByUser(user, jdbctemplate);
	}
	/**
	 * ��ȡ�û�Ǯ����Ϣ
	 * @param itcode �û�Ա����
	 * @param jdbctemplate
	 * @return �û�Ǯ����Ϣ
	 */
	public dc_user_wallet getWallInfoByItcode(String itcode,JdbcTemplate jdbctemplate) {
		dc_user user = UserDAO.getUserByItcode(itcode, jdbctemplate);
		return getWallInfoByUser(user, jdbctemplate);
	}
	/**
	 * ��ȡȫ���û�Ǯ����Ϣ
	 * @param jdbctemplate
	 * @return ȫ���û�Ǯ����Ϣ
	 */
	public List<dc_user_wallet> getAllWallInfoByUser(JdbcTemplate jdbctemplate) {
		RowMapper<dc_user_wallet> userwalletmapper=new BeanPropertyRowMapper<dc_user_wallet>(dc_user_wallet.class);
		try {
			List<dc_user_wallet> wanted=jdbctemplate.query("select * from dc_user_wallet;", userwalletmapper);
			return wanted;
		}catch(Exception e) {
			System.out.println("��ȡȫ���û�Ǯ����Ϣʧ�ܣ�");
			e.printStackTrace();
		}
		return null;
	}
}
