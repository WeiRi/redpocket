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
 *发红包操作
 */
public class LuckyDAO {

	
	/**红包雨；
	 * 修改系统红包总余额;
	 * 添加红包记录;
	 * 添加交易记录;
	 * 给用户钱包加入指定数额;
	 * @param jdbcTemplate
	 * @param wallet
	 * @param lucknumber
	 * @param round
	 * @return 1成功，0连接失败，-1某步操作失败
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
			int l = TradeDAO.createTrade(wallet.getWid(), temp1, lucknumber, "第"+round+"轮红包雨", jdbcTemplate);
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
