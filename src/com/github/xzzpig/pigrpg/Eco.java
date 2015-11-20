package com.github.xzzpig.pigrpg;
import java.math.*;

public class Eco
{
	@SuppressWarnings("unused")
	private User user;
	private com.earth2me.essentials.User euser;
	
	public Eco(User user){
		this.user = user;
		euser = Vars.ess.getUser(user.getPlayer());
	}
	
	public double getMoney(){
		return euser.getMoney().doubleValue();
	}
	
	public boolean hasMoney(double momey){
		return momey <= this.getMoney();
	}
	
	public boolean pay(String player,double money){
		try
		{
			euser.payUser(Vars.ess.getUser(player),BigDecimal.valueOf(money));
		}
		catch (Exception e)
		{
			return false;
		}
		return true;
	}
	
	public void setMoney(double money){
		euser.setMoney(BigDecimal.valueOf(money));
	}
	
	public boolean takeMoney(double money){
		if(!this.hasMoney(money))
			return false;
		this.setMoney(this.getMoney()-money);
		return true;
	}
}
