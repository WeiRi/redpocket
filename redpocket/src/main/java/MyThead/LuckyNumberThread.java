package MyThead;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import org.springframework.jdbc.core.JdbcTemplate;

import com.dcone.dtss.dao.LuckyDAO;
import com.dcone.dtss.dao.l_numberDAO;
import com.dcone.dtss.dao.ln_recordDAO;
import com.dcone.dtss.dao.TradeDAO;
import com.dcone.dtss.dao.WalletDAO;
import com.dcone.dtss.model.dc_wallet;
/**
 * 
 * @author wrs
 *������߳�
 *��Ҫ����JdbcTemplate��������Ҫ��flag��Ϊtrue
 */
public class LuckyNumberThread extends Thread {
	boolean flag = false;
	JdbcTemplate template;
	int round=0;
	/**
	 * ��ʼ���ź��
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("����꿪ʼ��!");
		List<dc_wallet> wallets = WalletDAO.getAllWallets(template);
		System.out.println("flag is:"+flag);
		if(wallets==null)
			System.out.println("no wallet!");
		else {
			System.out.println("wallet exist!");
		}
		for (dc_wallet temp :  wallets) {
			System.out.println(temp.toString());
			if (flag) {
				int total = l_numberDAO.getTotalbyRound(round, template);
				System.out.println("total is:"+total);
				int lucknumber = 0;
				Random r = new Random();
				if(total >0) {
					if(total >5000) {
						lucknumber = r.nextInt(5000);
					} else {
						lucknumber = total;
					}
				} else {
					break;
				}
				
				System.out.println("test1");
				try {
					LuckyDAO.LuckyRain(template, temp, lucknumber, round);	
					System.out.println("test2");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else  {
				break;
			}
		}

	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public JdbcTemplate getTemplate() {
		return template;
	}

	public void setTemplate(JdbcTemplate template) {
		this.template = template;
	}

	public int getRound() {
		return round;
	}

	public void setRound(int round) {
		this.round = round;
	}

}
