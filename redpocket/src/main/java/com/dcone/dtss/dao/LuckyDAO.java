package com.dcone.dtss.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

import com.dcone.dtss.model.dc_wallet;
import com.dcone.dtss.model.ln_record;
/**
 * 
 * @author wrs
 *���������
 */
public class LuckyDAO {

	
	/**����ꣻ
	 * �޸�ϵͳ��������;
	 * ��Ӻ����¼;
	 * ��ӽ��׼�¼;
	 * ���û�Ǯ������ָ������;
	 * @param jdbcTemplate
	 * @param wallet
	 * @param lucknumber
	 * @param round
	 * @return 1�ɹ���0����ʧ�ܣ�-1ĳ������ʧ��
	 */
	public static int LuckyRain(JdbcTemplate jdbcTemplate, dc_wallet wallet, int lucknumber,int round) {
		try {
			Date date = new Date();
			String url1 = " yyyy-MM-dd";
			String url2 = " HH:mm:ss";
			SimpleDateFormat fmtDate1 = new SimpleDateFormat(url1);
			SimpleDateFormat fmtDate2 = new SimpleDateFormat(url2);
			String temp1 = fmtDate1.format(date) + fmtDate2.format(date);
			Thread.sleep(1000);
			int i = L_NumberDAO.luckyRain(round, lucknumber, jdbcTemplate);
			int j = LN_RecordDAO.newRecord(round, wallet, lucknumber, temp1, jdbcTemplate);
			int k = WalletDAO.walletAddByWid(wallet.getWid(), lucknumber, jdbcTemplate);
			int l = TradeDAO.createTrade(wallet.getWid(), temp1, lucknumber, "��"+round+"�ֺ����", jdbcTemplate);
			if (i * j * k * l > 0) {
				// ok
				List<ln_record> wanted=LN_RecordDAO.getAllRecords(jdbcTemplate);
				if(wanted!=null)
					for (ln_record temp : wanted)
						System.out.println("this is record:"+temp.toString());
				return 1;
			} else {
				return -1;
				// error
			}
		} catch (InterruptedException e) {

		}
		return 0;
	}
	
}
