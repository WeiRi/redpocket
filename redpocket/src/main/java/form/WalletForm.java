package form;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
/**
 * 
 * @author wrs
 *记录充值页面钱包表单
 *@param username 用户名
 *@param itcode 员工号
 *@param amount 充值数额
 */
public class WalletForm {
	@NotNull
	@Size(min=2,max=10)
	private String username;
	@NotNull
	@Size(min=5,max=11)
	private String itcode;
	@NotNull
	@Min(100)
	@Max(100000)
	private int amount;
	@Override
	public String toString() {
		return "WalletForm [username=" + username + ", itcode=" + itcode + ", amount=" + amount + "]";
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getItcode() {
		return itcode;
	}
	public void setItcode(String itcode) {
		this.itcode = itcode;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
}
