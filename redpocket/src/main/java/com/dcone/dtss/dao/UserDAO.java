package com.dcone.dtss.dao;

import org.springframework.jdbc.core.*;

import com.dcone.dtss.model.dc_user;
/**
 * 
 * @author wrs
 *�����û���Ϣ
 */
public class UserDAO {
	/**
	 * ��ȡ�û�
	 * @param uid �û�id
	 * @param jdbcTemplate
	 * @return �û�
	 */
	public static dc_user getUserByUid(int uid,JdbcTemplate jdbcTemplate) {
		RowMapper<dc_user> user_mapper=new BeanPropertyRowMapper<dc_user>(dc_user.class);
		try {
			dc_user wanted=jdbcTemplate.queryForObject("select * from dc_user where uid=?;",user_mapper, new Object[] {uid});
			return wanted;
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("uid����,�Ҳ����û�!");
		}
		return null;
	}
	/**
	 * ��ȡ�û�
	 * @param itcode �û�Ա����
	 * @param jdbcTemplate
	 * @return �û�
	 */
	public static dc_user getUserByItcode(String itcode,JdbcTemplate jdbcTemplate) {
		RowMapper<dc_user> user_mapper=new BeanPropertyRowMapper<dc_user>(dc_user.class);
		try {
			dc_user wanted=jdbcTemplate.queryForObject("select * from dc_user where itcode=?;",user_mapper, new Object[] {itcode});
			return wanted;
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("itcode����,�Ҳ����û�!");
		}
		return null;
	}
	/**
	 * ��ȡ�û�
	 * @param username �û���
	 * @param jdbcTemplate
	 * @return �û�
	 */
	public static dc_user getUserByName(String username,JdbcTemplate jdbcTemplate) {
		RowMapper<dc_user> user_mapper=new BeanPropertyRowMapper<dc_user>(dc_user.class);
		try {
			dc_user wanted=jdbcTemplate.queryForObject("select * from dc_user where username=?;",user_mapper, new Object[] {username});
			return wanted;
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("�û�������,�Ҳ����û�!");
		}
		return null;
	}
	/**
	 * �ڳ�ֵǰ�����û�Ա�������û����Ƿ������ƥ��
	 * @param itcode Ա����
	 * @param username �û���
	 * @param jdbcTemplate
	 * @return true��Ϣ��ȷ��false��Ϣ����
	 */
	public static boolean checkItcodeUsername(String itcode,String username,JdbcTemplate jdbcTemplate) {
		try {
			int i=jdbcTemplate.queryForInt("select count(*) from dc_user where itcode=? or username=?;",new Object[] {itcode,username});
			if(i==1)
				return true;
			else {
				System.out.println("�û�����itcode�ظ���");
			}
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("�û�����itcode��ƥ�䣡");
		}
		return false;
	}
	/**
	 * �����û�
	 * @param itcode Ա����
	 * @param username �û���
	 * @param jdbcTemplate
	 * @return 1�ɹ���-2����ʧ�ܣ�-1Ա���Ż��û����ظ���0����ʧ��
	 */
	public static int createUser(String itcode, String username,JdbcTemplate jdbcTemplate) {
		RowMapper<dc_user> user_mapper=new BeanPropertyRowMapper<dc_user>(dc_user.class);
		try {
			if(checkUserInfo(itcode, username, jdbcTemplate)) {
				int i=jdbcTemplate.update("insert into dc_user (itcode,username) values(?,?);", user_mapper,new Object[] {itcode,username});
				if(i>0) {
					return 1;
				}
				else {
					return -2;
				}
			}
			else {
				return -1;
			}
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("��ʼ��ʧ�ܣ�itcode���û����ظ���");
		}
		return 0;
	}
	/**
	 * ��ע��ʱ���Ա���ź��û����Ƿ��ظ�
	 * @param itcode Ա����
	 * @param username �û���
	 * @param jdbcTemplate
	 * @return true���ظ�������ʹ�ã�false�ظ�������ʹ��
	 */
	public static boolean checkUserInfo(String itcode, String username,JdbcTemplate jdbcTemplate) {
		try {
			int i=jdbcTemplate.queryForInt("select count(*) from dc_user where itcode=? or username=?;",new Object[] {itcode,username});
			if(i==0)
				return true;
			else {
				System.out.println("�û�����itcode�ظ���");
			}
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("�û�����itcode��ƥ�䣡");
		}
		return false;
	}
	/**
	 * �޸����ݿ�,�޸�model,����û�����
	 * @param uid �û�id
	 * @return true�����ɹ���false����ʧ��
	 */
	public static boolean lockUserById(int uid,JdbcTemplate jdbcTemplate) {
		RowMapper<dc_user> user_mapper=new BeanPropertyRowMapper<dc_user>(dc_user.class);
		try {
			int i=jdbcTemplate.update("update dc_user set wlock = 1 where uid=?;",user_mapper, new Object[] {uid});
			if(i>0)
				return true;
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("uid����,�Ҳ����û�!");
		}
		return false;
	}
	/**
	 * �����û�
	 * @param itcode Ա����
	 * @return true�����ɹ���false����ʧ��
	 */
	public static boolean lockUserByItcode(String itcode,JdbcTemplate jdbcTemplate) {
		RowMapper<dc_user> user_mapper=new BeanPropertyRowMapper<dc_user>(dc_user.class);
		try {
			int i=jdbcTemplate.update("update dc_user set wlock = 1 where itcode=?;",user_mapper, new Object[] {itcode});
			if(i>0)
				return true;
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("itcode����,�Ҳ����û�!");
		}
		return false;
	}
	/**
	 * �����û�
	 * @param uid �û�id
	 * @param jdbcTemplate
	 * @return true�����ɹ���false����ʧ��
	 */
	public static boolean unlockUserByID(int uid,JdbcTemplate jdbcTemplate) {
		RowMapper<dc_user> user_mapper=new BeanPropertyRowMapper<dc_user>(dc_user.class);
		try {
			int i=jdbcTemplate.update("update dc_user set wlock = 0 where uid=?;",user_mapper, new Object[] {uid});
			if(i>0)
				return true;
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("uid����,�Ҳ����û�!");
		}
		return false;
	}
	/**
	 * �����û�
	 * @param itcode Ա����
	 * @param jdbcTemplate
	 * @return true�����ɹ���false����ʧ��
	 */
	public static boolean unlockUserByItcode(String itcode,JdbcTemplate jdbcTemplate) {
		RowMapper<dc_user> user_mapper=new BeanPropertyRowMapper<dc_user>(dc_user.class);
		try {
			int i=jdbcTemplate.update("update dc_user set wlock = 1 where itcode=?;",user_mapper, new Object[] {itcode});
			if(i>0)
				return true;
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("itcode����,�Ҳ����û�!");
		}
		return false;
	}
	/**
	 * ����û��Ƿ�����
	 * @param uid �û�id
	 * @param jdbcTemplate
	 * @return true������falseδ������
	 */
	public static boolean isLock(int uid,JdbcTemplate jdbcTemplate) {
		try {
			int i=jdbcTemplate.queryForInt("select wlock from dc_user where uid=?;",new Object[] {uid});
			if(i==0)
				return true;
		}
		catch(Exception e) {
			System.out.println("��ѯ�û�״̬ʧ�ܣ�");
			e.printStackTrace();
		}
		return false;
	}
	/**
	 * ��ѯ�û��Ƿ�����
	 * @param itcode Ա����
	 * @param jdbcTemplate
	 * @return true������falseδ������
	 */
	public static boolean isLock(String itcode,JdbcTemplate jdbcTemplate) {
		dc_user user=getUserByItcode(itcode,jdbcTemplate);
		return isLock(user.getUid(),jdbcTemplate);
	}
}
