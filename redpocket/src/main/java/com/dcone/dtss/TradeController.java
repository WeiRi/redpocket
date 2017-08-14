package com.dcone.dtss;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dcone.dtss.dao.TradeDAO;
import com.dcone.dtss.dao.UserDAO;
import com.dcone.dtss.model.dc_trade;

@Controller
/**
 * 
 * @author wrs
 *交易处理控制器
 */
public class TradeController {
	private static final Logger logger = LoggerFactory.getLogger(BalanceController.class);
	@Autowired
    JdbcTemplate jdbcTemplate;
	/**
	 * 查询某天交易记录
	 * @param username 用户名
	 * @param itcode 员工号
	 * @param date 日期，格式2017-2-1
	 * @param model 
	 * @return 查询结果界面
	 */
	@RequestMapping(value="/trade_check")
	public String tradeCheck(String username,String itcode,String date,Model model) {
		String starttime=date+" 00:00:00",endtime=date+" 23:59:59";
		//UserDAO.unlockUserByItcode(itcode, jdbcTemplate);
		/*if(UserDAO.isLock(itcode, jdbcTemplate))
			System.out.println("unlock failed!");
		else
			System.out.println("unlock successed!");*/
		System.out.println("starttime is:"+starttime+" and endtime is:"+endtime);
		TradeDAO td=new TradeDAO();
		List<dc_trade> wanted=td.getTimeTradesByItcode(itcode, starttime, endtime, jdbcTemplate);
		if(wanted!=null) {
			for(dc_trade temp:wanted) 
				System.out.println(temp.toString());
			model.addAttribute("list",wanted);
			model.addAttribute("result", "查询成功！，以下是您"+date+"的交易记录：");
			model.addAttribute("str1", "交易流水号");
			model.addAttribute("str2", "钱包id");
			model.addAttribute("str3", "转入金额");
			model.addAttribute("str4", "交易时间");
			model.addAttribute("str5","备注");
		}
		return "trade_check_result";
	}
}
