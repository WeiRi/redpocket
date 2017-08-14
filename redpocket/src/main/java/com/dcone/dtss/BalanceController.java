package com.dcone.dtss;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dcone.dtss.dao.TradeDAO;
import com.dcone.dtss.dao.WalletDAO;
import com.dcone.dtss.model.dc_trade;
import com.dcone.dtss.model.dc_user;
import com.dcone.dtss.model.dc_wallet;

import form.WalletForm;
/**
 * 
 * @author wrs
 *钱包账户操作
 */
@Controller
public class BalanceController {
	
	private static final Logger logger = LoggerFactory.getLogger(BalanceController.class);
	@Autowired
    JdbcTemplate jdbcTemplate;
	/**
	 * 进入充值页面
	 * @return
	 */
	@RequestMapping(value="/balance_add", method=RequestMethod.GET)
	public String balanceAdd() {
		return "balance_add";
	}

/**
	 * 进入红包界面
	 * @return
	 */
	@RequestMapping(value="/wallet", method=RequestMethod.GET)
	public String wallet() {
		return "wallet";
	}
	/**
	 * 进行充值操作
	 * @param walletForm 获取表单内容
	 * @param bindingResult 检查表单内容是否有错
	 * @param locale
	 * @param model
	 * @return 充值结果界面
	 */
	@RequestMapping(value="/balance_adding")
	public String balanceAdding(@Valid WalletForm walletForm ,BindingResult bindingResult ,Locale locale,  Model model) {
		logger.info("itcode:" +walletForm.getItcode() +"username:"+walletForm.getUsername() + " 充值 "+ walletForm.getAmount());
		logger.info(jdbcTemplate.toString());
		String result = "";
		if (bindingResult.hasErrors()) {
			result="<br>错误！具体如下："+"<br>";
			List<ObjectError> errors = bindingResult.getAllErrors();
			for (ObjectError error : errors) {
				result += error.getDefaultMessage() + "<br>";
			}
			System.out.println("walletform信息:"+walletForm.toString());
			model.addAttribute("msg", result);
			return "balance_add";
		} else {
			int i = WalletDAO.balance_add(walletForm.getItcode(), walletForm.getUsername(), walletForm.getAmount(),
					locale, jdbcTemplate);
			if (i == 1) {
				result = "充值成功";
			} else if (i == -1 || i == -2) {
				result = "用户信息填写错误!";
			} else {
				result = "充值失败,请稍后再试!";
			}
			TradeDAO td = new TradeDAO();
			/*List<dc_trade> wanted = td.getTimeTradesByItcode(walletForm.getItcode(), "2017-08-03 10:30:00",
					"2017-08-04 00:00:00", jdbcTemplate);
			for (dc_trade temp : wanted) {
				System.out.println(temp.toString());
			}*/
			dc_wallet wallet = WalletDAO.getWalletByItcode(walletForm.getItcode(), jdbcTemplate);
			model.addAttribute("money", wallet.getAmount());
			model.addAttribute("result", result);
			model.addAttribute("username", walletForm.getUsername());
			return "balance_add_result";
		}
	}
	
	
}
