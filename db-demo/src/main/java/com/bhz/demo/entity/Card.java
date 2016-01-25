package com.bhz.demo.entity;

import java.math.BigDecimal;

public class Card implements java.io.Serializable{
	private String cardNo;
	private int type;
	private BigDecimal amount;
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	
	
}
