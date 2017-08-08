package com.dcone.dtss.dao;

import org.springframework.jdbc.core.JdbcTemplate;

import com.dcone.dtss.model.l_number;
/**
 * 
 * @author wrs
 *�������˻�����
 */
public class l_numberDAO {
	/**
	 * ��ȡ����˻����
	 * @param i ������ִ�
	 * @param jdbcTemplate
	 * @return ����˻�������0
	 */
	public static int getTotalbyRound(int i,JdbcTemplate jdbcTemplate) {
		try {
			int w=jdbcTemplate.queryForInt("select total from luckynumber where round=?;",new Object[] {i});
			return w;
		}catch(Exception e) {
			System.out.println("��ȡ����˻����ʧ���գ�");
			e.printStackTrace();
		}
		return 0;
	}
	/**
	 * ����ܶ�Ƽ�
	 * @param round �ִ�
	 * @param number �������
	 * @return 1 �ɹ�, 0ʧ��
	 */
	public static int luckyRain(int round, int number,JdbcTemplate jdbcTemplate) {
		try {
			int i=jdbcTemplate.update("update luckynumber set total=total-? where round=?;",new Object[] {number,round});
			if(i>0)
				return 1;
		}catch(Exception e) {
			System.out.println("���ٺ���˻������ʧ�ܣ�");
			e.printStackTrace();
		}
		return 0;
	}
}
