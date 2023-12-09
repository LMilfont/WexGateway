package com.gateway.wex.model;

import java.math.BigDecimal;
import com.gateway.wex.helper.GlobalUtils;

public class Purchase
{
    private String id;
	private String description;
	private String transaction_date;
	private BigDecimal amount;
	
	public Purchase(String description, String transaction_date, BigDecimal amount)
	{
		this.id = String.valueOf(GlobalUtils.generateShortUuid()); 
		this.description = description;
		this.transaction_date = transaction_date;
		this.amount = amount;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTransaction_date() {
		return transaction_date;
	}

	public void setTransaction_date(String transaction_date) {
		this.transaction_date = transaction_date;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

}

