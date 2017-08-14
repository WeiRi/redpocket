package com.dcone.dtss;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.dcone.dtss.dao.LuckyDAO;
import com.dcone.dtss.dao.WalletDAO;
import com.dcone.dtss.dao.LN_RecordDAO;
import com.dcone.dtss.model.dc_wallet;
import com.dcone.dtss.model.ln_record;

import MyThead.LuckyNumberThread;
import form.WalletForm;
/**
 * 
 * @author wrs
 *超级用户进行红包雨等操作
 *后期应设置检查用户是否为超级用户，否则拒绝访问
 */
@Controller
public class AdminController {
	
	boolean flag = false;
	@Autowired
	JdbcTemplate template;
	/**判断用户是否登录
	 * 登录成功后显示admin页面
	 * @return
	 */
	@RequestMapping("/luckyadmin")
	public String admin() {
		//判断用户是否登录
		//登录成功后显示admin页面
		return "luckymoney";
	}
	
	
	/**
	 * 开启红包雨
	 * @param round 红包雨轮次
	 * @return 红包雨结果界面
	 */
	@RequestMapping("/luck_check") 
	public String Lucky_on(String round) {
		System.out.println("红包雨开启！");
		LuckyNumberThread t = new LuckyNumberThread();
		t.setTemplate(template);
		int r = 0;
		try {
			r = Integer.parseInt(round);
		} catch (Exception e) {
			// TODO: handle exception
		}
		t.setRound(r);
		t.setFlag(true);		
		t.start();
		return "luckymoney";
	}
	/**
	 * 查询全部红包发放记录
	 * @param model
	 * @return 显示所有红包发放记录
	 */
	@RequestMapping("/viewrecord")
	public String viewrecord(Model model) {
		List<ln_record> wanted=LN_RecordDAO.getAllRecords(template);
		String str1="",str2="",str3="",str4="",str5="";
		if(wanted!=null) {
			str1="流水号";
			str2="红包雨轮次";
			str3="用户钱包id";
			str4="红包数额";
			str5="交易时间";
		}
		else {
			str1="暂无红包雨相关交易记录！";
		}
		model.addAttribute("str1", str1);
		model.addAttribute("str2", str2);
		model.addAttribute("str3", str3);
		model.addAttribute("str4", str4);
		model.addAttribute("str5", str5);
		model.addAttribute("record",wanted);
		return "viewrecord";
	}
	
}
